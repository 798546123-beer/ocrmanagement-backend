package com.henu.ocr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.ocr.entity.Role;
import com.henu.ocr.service.RoleService;
import com.henu.ocr.util.RedisUtil;
import com.henu.ocr.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Tag(name = "角色管理接口", description = "角色管理接口")
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RedisUtil redisUtil;

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

    @Operation(summary = "添加角色")
    @PostMapping("/addRoleWithPermissions")
    public Result addRoleWithPermissions(@RequestParam String roleName, @RequestParam String permissions) {
        try {
            List<Integer> permissionList = java.util.Arrays.stream(permissions.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            boolean success = roleService.addRoleWithPermissions(roleName, permissionList);
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

    @Operation(summary = "修改角色信息", description = "roleId是角色ID；roleName是角色名称,非必须，带上roleName会修改角色名称;permissions是权限列表，以逗号分隔")
    @PutMapping("/updateRole")
    public Result updateRole(@RequestParam Integer roleId, @RequestParam(defaultValue = "null") String roleName, @RequestParam String permissions) {
        try {
            boolean success = roleService.updateById(roleId, roleName, permissions);
            return success ? Result.OK("修改成功") : Result.error("修改失败");
        } catch (Exception e) {
            return Result.Exception();
        }
    }

    @Operation(summary = "获取所有角色列表（分页）", description = "pageNum是页码，size是页面数据行数")
    @GetMapping("/getAllRoles")
    public Result<?> getAllRoles(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "15") Integer pageSize) {
        try {
            // 调用 MyBatis-Plus 的分页方法
            IPage<Role> rolesPage = roleService.getAllRolesWithPermissions(pageNum, pageSize);
            return Result.OK("查询成功", rolesPage);
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
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
