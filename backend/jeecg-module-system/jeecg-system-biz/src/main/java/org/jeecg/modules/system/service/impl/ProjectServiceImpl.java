package org.jeecg.modules.system.service.impl;

import org.jeecg.modules.system.mapper.ProjectMapper;
import org.jeecg.modules.system.entity.Project;
import org.jeecg.modules.system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Implementation of the ProjectService interface.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Project findById(Integer projectId) {
        return projectMapper.findById(projectId);
    }

    @Override
    public List<Project> findAll() {
        return projectMapper.findAll();
    }

    @Override
    public void save(Project project) {
        projectMapper.insert(project);
    }

    @Override
    public void update(Project project) {
        projectMapper.update(project);
    }

    @Override
    public void delete(Integer projectId) {
        projectMapper.delete(projectId);
    }
}
