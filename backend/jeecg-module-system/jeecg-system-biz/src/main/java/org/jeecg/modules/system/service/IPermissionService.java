package org.jeecg.modules.system.service;

import org.jeecg.modules.system.entity.PageEntity;

import java.util.List;

public interface IPermissionService {
    List<PageEntity> getPermissionList();
}
