package com.henu.ocr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henu.ocr.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    Role getRoleWithPermissions(Integer roleId);
    List<Role> getRolesByNameFuzzy(String keyword);

    List<Role> getAllRolesWithPermissions();
}
