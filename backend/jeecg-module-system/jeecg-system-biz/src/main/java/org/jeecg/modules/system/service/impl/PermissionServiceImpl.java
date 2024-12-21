package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.PageEntity;
import org.jeecg.modules.system.mapper.PermissionMapper;
import org.jeecg.modules.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PageEntity> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PageEntity> getPermissionList() {
        return permissionMapper.getPermissionList();
    }
}
