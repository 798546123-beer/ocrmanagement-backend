package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Api("角色管理")
@Slf4j
public class RoleController {
    @Autowired
    private RoleMapper roleMapper;
    @ApiOperation("获取角色权限")
    @RequestMapping("/getRolePermission")
    @IgnoreAuth
    public Result<JSONObject> getRolePermission(@Param(value = "role_id") Integer role_id){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",role_id);
        Role role = roleMapper.selectOne(queryWrapper);
        return Result.ok(role.getPermission());

//        return Result.OK();
//        return null;
    }
    // 新增角色接口
    @ApiOperation("新增角色")
    @PostMapping("/addRole")
    @IgnoreAuth
    public Result<String> addRole(@RequestBody Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name", role.getRoleName());
        if (roleMapper.selectOne(queryWrapper) != null) {
            return Result.Error("角色名已存在！");
        }
        try {
            roleMapper.insert(role);
            return Result.OK("角色新增成功！");
        } catch (Exception e) {
            log.error("新增角色失败", e);
            return Result.Error("新增角色失败: " + e.getMessage());
        }
    }

    // 删除角色接口
    @ApiOperation("删除角色")
    @DeleteMapping("/deleteRole")
    @IgnoreAuth
    public Result<String> deleteRole(@Param("roleId") Integer roleId) {
        try {
            roleMapper.deleteById(roleId);
            return Result.OK("角色删除成功！");
        } catch (Exception e) {
            log.error("删除角色失败", e);
            return Result.Error("删除角色失败: " + e.getMessage());
        }
    }

    // 修改角色权限接口
    @ApiOperation("根据角色ID修改权限")
    @PutMapping("/updateRolePermission")
    @IgnoreAuth
    public Result<String> updateRolePermission(@RequestParam("roleId") Integer roleId, @RequestParam("permissions") String permissions) {
        try {
            Role role = roleMapper.selectById(roleId);
            if (role == null) {
                return Result.Error("角色不存在！");
            }
            role.setPermissions(permissions); // 假设Role实体有permissions字段
            roleMapper.updateById(role);
            return Result.OK("角色权限更新成功！");
        } catch (Exception e) {
            log.error("更新角色权限失败", e);
            return Result.Error("更新角色权限失败: " + e.getMessage());
        }
    }

}
