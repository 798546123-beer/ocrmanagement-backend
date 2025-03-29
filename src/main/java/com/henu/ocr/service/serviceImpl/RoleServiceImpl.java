package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.PageEntity;
import com.henu.ocr.entity.Permission;
import com.henu.ocr.entity.Role;
import com.henu.ocr.mapper.PageMapper;
import com.henu.ocr.mapper.PermissionMapper;
import com.henu.ocr.mapper.RoleMapper;
import com.henu.ocr.model.PageTreeModel;
import com.henu.ocr.model.PermissionModel;
import com.henu.ocr.service.PageService;
import com.henu.ocr.service.RoleService;
import com.henu.ocr.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private final RoleMapper roleMapper;

    @Resource
    private final PermissionMapper permissionMapper;

    @Resource
    private final PageMapper pageMapper;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PageService pageService;

    public RoleServiceImpl(RoleMapper roleMapper, PermissionMapper permissionMapper, PageMapper pageMapper) {
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public Role getRoleWithPermissions(Integer roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role != null) {
            List<Permission> permissions = permissionMapper.selectList(
                    new LambdaQueryWrapper<Permission>()
                            .select(Permission::getPermissionId)
                            .eq(Permission::getRoleId, roleId)
            );
            List<Integer> permissionIds = permissions.stream()
                    .map(p -> Integer.parseInt(p.getPermissionId()))
                    .collect(Collectors.toList());
            Map<Integer, PageEntity> pageNames = pageMapper.selectBatchIds(permissionIds)
                    .stream()
                    .collect(Collectors.toMap(
                            PageEntity::getId,
                            page -> page
                    ));
            List<PermissionModel> permissionModels = permissions.stream()
                    .map(p -> new PermissionModel(
                            Integer.parseInt(p.getPermissionId()),
                            pageNames.get(Integer.parseInt(p.getPermissionId())).getPage(),
                            pageNames.get(Integer.parseInt(p.getPermissionId())).getPageName()
                    ))
                    .collect(Collectors.toList());

            role.setPermissions(permissionModels);
        }
        return role;
    }


    @Override
    public List<Role> getRolesByNameFuzzy(String keyword) {
        List<Role> roles = this.list(new LambdaQueryWrapper<Role>()
                .like(Role::getRoleName, keyword));
        if (!roles.isEmpty()) {
            List<Integer> roleIds = roles.stream()
                    .map(Role::getRoleId)
                    .collect(Collectors.toList());
            List<Permission> allPermissions = permissionMapper.selectList(
                    new LambdaQueryWrapper<Permission>()
                            .in(Permission::getRoleId, roleIds)
            );
            Map<Integer, List<Permission>> rolePermissionMap = allPermissions.stream()
                    .collect(Collectors.groupingBy(Permission::getRoleId));
            List<Integer> permissionIds = allPermissions.stream()
                    .map(p -> {
                        try {
                            return Integer.parseInt(p.getPermissionId());
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            Map<Integer, PageEntity> pageMap = !permissionIds.isEmpty() ?
                    pageMapper.selectBatchIds(permissionIds)
                            .stream()
                            .collect(Collectors.toMap(
                                    PageEntity::getId,
                                    PageEntity -> PageEntity
                            )) :
                    Collections.emptyMap();
            roles.forEach(role -> {
                List<PermissionModel> permissionModels = rolePermissionMap
                        .getOrDefault(role.getRoleId(), Collections.emptyList())
                        .stream()
                        .map(p -> {
                            try {
                                int pid = Integer.parseInt(p.getPermissionId());
                                return new PermissionModel(pid, pageMap.get(pid).getPage(), pageMap.get(pid).getPageName());
                            } catch (NumberFormatException e) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                role.setPermissions(permissionModels != null ? permissionModels : Collections.emptyList());
            });
        }
        return roles;
    }

    @Override
    public IPage<Role> getRolesByNameFuzzy(String keyword, Integer pageNum, Integer pageSize) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        return roleMapper.selectPage(page, new LambdaQueryWrapper<Role>()
                .like(Role::getRoleName, keyword));
    }

    @Override
    public IPage<Role> getAllRolesWithPermissions(Integer pageNum, Integer pageSize) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        return roleMapper.selectPage(page, null);
    }

    @Override
    @Transactional
    public boolean addRoleWithPermissions(String roleName, List<Integer> permissions) {
        try {
            boolean roleSaved = save(new Role(roleName));
            if (!roleSaved) {
                return false;
            }
            List<PageTreeModel> permissionTree = pageService.getPermissionTree();
            if (permissionTree == null) {
                return false;
            }
            List<Integer> allPermissionIds = new ArrayList<>(permissions);
            for (Integer childId : permissions) {
                allPermissionIds.addAll(findParentIds(permissionTree, childId));
            }
            allPermissionIds = allPermissionIds.stream().distinct().collect(Collectors.toList());
            Role role = getOne((new QueryWrapper<Role>()).eq("role_name", roleName));
            for (Integer permissionId : allPermissionIds) {
                Permission permission = new Permission();
                permission.setPermissionId(permissionId.toString());
                permission.setRoleId(role.getRoleId());
                permissionMapper.insert(permission);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to add role with permissions", e);
        }
    }

    private List<Integer> findParentIds(List<PageTreeModel> permissionTree, Integer childId) {
        List<Integer> parentIds = new ArrayList<>();
        for (PageTreeModel node : permissionTree) {
            if (findParentIdsRecursive(node, childId, parentIds)) {
                break;
            }
        }
        return parentIds;
    }

    private boolean findParentIdsRecursive(PageTreeModel node, Integer childId, List<Integer> parentIds) {
        if (node.getId().equals(childId)) {
            return true;
        }
        for (PageTreeModel child : node.getChildren()) {
            if (findParentIdsRecursive(child, childId, parentIds)) {
                parentIds.add(node.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeById(Integer roleId) {
        // 先删除permission表中所有role_id等于roleId的记录
        boolean permissionsDeleted = permissionMapper.delete(
                new LambdaQueryWrapper<Permission>()
                        .eq(Permission::getRoleId, roleId)
        ) >= 0;
        if (!permissionsDeleted) {
            return false;
        }
        // 再删除role表中role_id等于roleId的记录
        boolean roleDeleted = super.removeById(roleId);
        if (!roleDeleted) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(Integer roleId, String roleName, String permissions) {
        boolean success = true;
        if (roleName != null) {
            Role role = new Role(roleId, null, roleName);
            success = roleMapper.updateById(role)>=0;
        }
        List<Integer> permissionList = java.util.Arrays.stream(permissions.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        success = permissionMapper.delete(
                new LambdaQueryWrapper<Permission>()
                        .eq(Permission::getRoleId, roleId)
        ) >= 0;
        if (!success) {
            return false;
        }
        for (Integer permissionId : permissionList) {
            Permission permission = new Permission();
            permission.setPermissionId(permissionId.toString());
            permission.setRoleId(roleId);
            success = permissionMapper.insert(permission) >= 0;
            if (!success) {
                return false;
            }
        }
        return true;
    }
}

