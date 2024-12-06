package org.jeecg.modules.system.controller;

import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.Project;
import org.jeecg.modules.system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Project entity.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Get a project by ID
    @IgnoreAuth

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable("id") Integer id) {
        return projectService.findById(id);
    }

    // Get all projects
    @IgnoreAuth

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.findAll();
    }

    // Add a new project
    @IgnoreAuth
    @PostMapping
    public String addProject(@RequestBody Project project) {
        projectService.save(project);
        return "Project added successfully!";
    }

    // Update a project
    @PutMapping("/{id}")
    @IgnoreAuth
    public String updateProject(@PathVariable("id") Integer id, @RequestBody Project project) {
        project.setProjectId(id);
        projectService.update(project);
        return "Project updated successfully!";
    }

    // Delete a project
    @DeleteMapping("/{id}")
    @IgnoreAuth
    public String deleteProject(@PathVariable("id") Integer id) {
        projectService.delete(id);
        return "Project deleted successfully!";
    }
}
