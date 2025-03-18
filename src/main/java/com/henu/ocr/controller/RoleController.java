package com.henu.ocr.controller;

import com.henu.ocr.entity.Role;
import com.henu.ocr.service.RoleService;
import com.henu.ocr.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Tag(name = "角色管理接口", description = "角色管理接口")
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Operation(summary = "根据ID查询角色")
    @GetMapping("/getRoleById")
    public Result getRoleById(@RequestParam Integer roleId) {
        try {
            Role role = roleService.getRoleWithPermissions(roleId);
            return role != null ? Result.OK("查询成功", role) : Result.error("角色不存在");
        } catch (Exception e) {
            return Result.Exception();
        }
    }

    @Operation(summary = "新增角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody Role role) {
        try {
            boolean success = roleService.save(role);
            return success ? Result.OK("添加成功") : Result.error("添加失败");
        } catch (Exception e) {
            return Result.Exception();
        }
    }

    @Operation(summary = "根据ID删除角色")
    @DeleteMapping("/deleteRoleById")
    public Result deleteRoleById(@RequestParam Integer roleId) {
        try {
            boolean success = roleService.removeById(roleId);
            return success ? Result.OK("删除成功") : Result.error("删除失败");
        } catch (Exception e) {
            return Result.Exception();
        }
    }

    @Operation(summary = "修改角色信息")
    @PutMapping("/updateRole")
    public Result updateRole(@RequestBody Role role) {
        try {
            boolean success = roleService.updateById(role);
            return success ? Result.OK("修改成功") : Result.error("修改失败");
        } catch (Exception e) {
            return Result.Exception();
        }
    }

    @Operation(summary = "获取所有角色列表")
    @GetMapping("/getAllRoles")
    public Result getAllRoles() {
        try {
            List<Role> roles = roleService.getAllRolesWithPermissions();
            return !roles.isEmpty() ? Result.OK("查询成功", roles) : Result.error("暂无数据");
        } catch (Exception e) {
            return Result.Exception();
        }
    }

    @Operation(summary = "根据角色名称模糊查询")
    @GetMapping("/getRoleByNameFuzzy")
    public Result fuzzyQuery(@RequestParam String keyword) {
        try {
            List<Role> roles = roleService.getRolesByNameFuzzy(keyword);
            return !roles.isEmpty() ? Result.OK("查询成功", roles) : Result.error("未找到相关角色");
        } catch (Exception e) {
            return Result.Exception();
        }
    }
}
