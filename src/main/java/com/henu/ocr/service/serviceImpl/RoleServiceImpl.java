package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.PageEntity;
import com.henu.ocr.entity.Permission;
import com.henu.ocr.entity.Role;
import com.henu.ocr.mapper.PageMapper;
import com.henu.ocr.mapper.PermissionMapper;
import com.henu.ocr.mapper.RoleMapper;
import com.henu.ocr.pojo.PermissionModel;
import com.henu.ocr.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
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

    public Role getRoleWithPermissions(Integer roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role != null) {
            List<Permission> permissions = permissionMapper.selectList(
                    new LambdaQueryWrapper<Permission>()
                            .select(Permission::getPermissionId)
                            .eq(Permission::getRoleId, roleId)
            );
            List<Integer> permissionIds = permissions.stream()
                    .map(Permission::getPermissionId)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            Map<Integer, String> pageNames = pageMapper.selectBatchIds(permissionIds)
                    .stream()
                    .collect(Collectors.toMap(
                            PageEntity::getId,
                            PageEntity::getPageName
                    ));
            List<PermissionModel> permissionModels = permissions.stream()
                    .map(p -> new PermissionModel(
                            Integer.parseInt(p.getPermissionId()),
                            pageNames.get(Integer.parseInt(p.getPermissionId()))
                    ))
                    .collect(Collectors.toList());

            role.setPermissions(permissionModels);
        }
        return role;
    }
    @Override
    public List<Role> getRolesByNameFuzzy(String roleName) {
        return this.list(new LambdaQueryWrapper<Role>()
                .like(Role::getRoleName, roleName));
    }

}
