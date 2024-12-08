package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.Project;
import org.jeecg.modules.system.mapper.ProjectMapper;
import org.jeecg.modules.system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目管理控制器
 */
@RestController
@RequestMapping("/project")
@Api(tags = "项目管理")
@Slf4j
public class ProjectController {

    @Resource
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectService projectService;

    // 查询项目详情
    @ApiOperation("获取项目详情")
    @GetMapping("/getProjectById")
    public Result<JSONObject> getProjectById(@Param("projectId") Integer projectId) {
        Project project = projectMapper.selectById(projectId);
        if (project == null) {
            return Result.Error("项目不存在！");
        }
        return Result.ok(JSONObject.parseObject(JSONObject.toJSONString(project)));
    }

    // 新增项目接口
    @ApiOperation("新增项目")
    @PostMapping("/addProject")
    public Result<String> addProject(@RequestBody Project project) {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_name", project.getProjectName());
        if (projectMapper.selectOne(queryWrapper) != null) {
            return Result.Error("项目名称已存在！");
        }
        try {
            projectMapper.insert(project);
            return Result.OK("项目新增成功！");
        } catch (Exception e) {
            log.error("新增项目失败", e);
            return Result.Error("新增项目失败: " + e.getMessage());
        }
    }

    // 更新项目接口
    @ApiOperation("更新项目")
    @PutMapping("/updateProject")
    public Result<String> updateProject(@RequestBody Project project) {
        try {
            Project existingProject = projectMapper.selectById(project.getProjectId());
            if (existingProject == null) {
                return Result.Error("项目不存在！");
            }
            projectMapper.updateById(project);
            return Result.OK("项目更新成功！");
        } catch (Exception e) {
            log.error("更新项目失败", e);
            return Result.Error("更新项目失败: " + e.getMessage());
        }
    }

    // 删除项目接口
    @ApiOperation("删除项目")
    @DeleteMapping("/deleteProject")
    public Result<String> deleteProject(@Param("projectId") Integer projectId) {
        try {
            Project project = projectMapper.selectById(projectId);
            if (project == null) {
                return Result.Error("项目不存在！");
            }
            projectMapper.deleteById(projectId);
            return Result.OK("项目删除成功！");
        } catch (Exception e) {
            log.error("删除项目失败", e);
            return Result.Error("删除项目失败: " + e.getMessage());
        }
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}
