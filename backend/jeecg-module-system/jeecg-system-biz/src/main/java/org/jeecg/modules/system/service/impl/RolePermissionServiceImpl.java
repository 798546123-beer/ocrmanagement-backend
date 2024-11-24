//package org.jeecg.modules.system.service.impl;
//

//import org.jeecg.modules.system.entity.Role;
//import org.jeecg.modules.system.entity.Permission;
//import org.jeecg.modules.system.mapper.RoleMapper;
//import org.jeecg.modules.system.mapper.PermissionMapper;
//import org.jeecg.modules.system.service.RolePermissionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * RolePermissionServiceImpl - 角色权限服务实现类
// */
//@Service
//public class RolePermissionServiceImpl implements RolePermissionService {
//
//    @Autowired
//    private RoleMapper roleMapper;
//
//    @Autowired
//    private PermissionMapper permissionMapper;
//
//    @Autowired
//    private RolePermissionMapper rolePermissionMapper;
//
//    @Override
//    public void addRole(RolePermissionDTO roleDTO) {
//        Role role = new Role(roleDTO.getRoleName(), roleDTO.getDescription());
//        roleMapper.insert(role); // 插入角色
//    }
//
//    @Override
//    public void deleteRole(Long roleId) {
//        roleMapper.deleteById(roleId); // 删除角色
//        rolePermissionMapper.deleteByRoleId(roleId); // 删除角色与权限的关系
//    }
//
//    @Override
//    public RolePermissionDTO getRolePermissions(Long roleId) {
//        Role role = roleMapper.selectById(roleId);
//        List<Long> permissionIds = rolePermissionMapper.selectPermissionIdsByRoleId(roleId);
//        return new RolePermissionDTO(roleId, role.getName(), role.getDescription(), permissionIds);
//    }
//
//    @Override
//    public void updateRolePermissions(Long roleId, List<Long> permissionIds) {
//        rolePermissionMapper.deleteByRoleId(roleId); // 清空旧的权限
//        permissionIds.forEach(permissionId -> rolePermissionMapper.insert(roleId, permissionId)); // 插入新的权限
//    }
//}
