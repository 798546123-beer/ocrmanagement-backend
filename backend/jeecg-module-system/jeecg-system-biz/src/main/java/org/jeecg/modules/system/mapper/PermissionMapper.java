package org.jeecg.modules.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.system.entity.PageEntity;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<PageEntity> getPermissionList();

}
