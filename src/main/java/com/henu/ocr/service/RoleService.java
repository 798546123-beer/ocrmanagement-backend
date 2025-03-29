package com.henu.ocr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.ocr.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRoleWithPermissions(Integer roleId);

    IPage<Role> getRolesByNameFuzzy(String keyword);

    IPage<Role> getAllRolesWithPermissions(Integer pageNum, Integer pageSize);

    boolean addRoleWithPermissions(String roleName, List<Integer> permissionIds);

    boolean removeById(Integer roleId);

    boolean updateById(Integer roleId, String roleName, String permissions);
}
