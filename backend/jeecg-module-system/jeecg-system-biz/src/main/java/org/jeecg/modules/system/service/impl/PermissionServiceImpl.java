package org.jeecg.modules.system.service.impl;

import org.jeecg.modules.system.entity.PageEntity;
import org.jeecg.modules.system.mapper.PermissionMapper;
import org.jeecg.modules.system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PageEntity> getPermissionList() {
        return permissionMapper.getPermissionList();
    }
}
