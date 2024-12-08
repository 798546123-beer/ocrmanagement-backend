package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Project;


@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    @Select("SELECT * FROM project WHERE project_id = #{projectId}")
    Project getProjectById(Integer projectId);
}
