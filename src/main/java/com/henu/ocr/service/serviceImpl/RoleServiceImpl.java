package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.PageEntity;
import com.henu.ocr.entity.Permission;
import com.henu.ocr.entity.Role;
import com.henu.ocr.mapper.PageMapper;
import com.henu.ocr.mapper.PermissionMapper;
import com.henu.ocr.mapper.RoleMapper;
import com.henu.ocr.model.PermissionModel;
import com.henu.ocr.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public List<Role> getAllRolesWithPermissions() {
        List<Role> roles = this.list();
        roles.forEach(role ->
                getRoleWithPermissions(role.getRoleId())
        );
        return roles;
    }
}

