package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.system.entity.PageEntity;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<PageEntity> {
    List<PageEntity> getPermissionList();

}
