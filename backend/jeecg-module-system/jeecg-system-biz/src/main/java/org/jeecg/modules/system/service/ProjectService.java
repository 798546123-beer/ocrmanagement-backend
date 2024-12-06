package org.jeecg.modules.system.service;


import org.jeecg.modules.system.entity.Project;
import java.util.List;

/**
 * Service interface for Project entity.
 */
public interface ProjectService {

    Project findById(Integer projectId);

    List<Project> findAll();

    void save(Project project);

    void update(Project project);

    void delete(Integer projectId);
}
