package org.jeecg.modules.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.system.entity.Project;

import java.util.List;

/**
 * Mapper interface for Project entity, using XML configuration.
 */
@Mapper
public interface ProjectMapper {

    // 查询单个项目（根据项目ID）
    Project findById(Integer projectId);

    // 查询所有项目
    List<Project> findAll();

    // 插入新项目
    void insert(Project project);

    // 更新项目信息
    void update(Project project);

    // 删除项目（根据项目ID）
    void delete(Integer projectId);
}
