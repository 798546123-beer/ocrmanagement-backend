package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.PageEntity;

import java.util.List;

public interface PermissionService extends IService<PageEntity> {
    List<PageEntity> getPermissionList();
}
