package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.jeecg.modules.system.vo.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.jeecg.common.api.vo.Result.ok;

@RestController
@RequestMapping("/role")
@Api(tags="角色管理")
@Slf4j
public class RoleController {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisUtil redisUtil;

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
    public Result<String> deleteRole(@Param("roleId") String roleId) {
        try {
            //先查询数据库中有没有，没有的话报错不存在
            Role role = roleMapper.selectById(roleId);
            if (role == null) {
                return Result.Error("角色不存在！");
            }
            roleMapper.deleteById(roleId);
            return Result.OK("角色删除成功！");
        } catch (Exception e) {
            log.error("删除角色失败", e);
            return Result.Error("删除角色失败: " + e.getMessage());
        }
    }

    /*
    * 根据角色ID修改权限
    * @param roleId
    * @param permission
     */
    @ApiOperation("根据角色ID修改权限")
    @PutMapping("/updateRolePermission")
    @IgnoreAuth
    public Result<String> updateRolePermission(@RequestParam("roleId") Integer roleId, @RequestParam("permission") String permission) {
        try {
            Role role = roleMapper.selectById(roleId);
            if (role == null) {
                return Result.Error("角色不存在！");
            }
            role.setPermission(permission);
            // 假设Role实体有permissions字段
            roleMapper.updateById(role);
            return Result.OK("角色权限更新成功！");
        } catch (Exception e) {
            log.error("更新角色权限失败", e);
            return Result.Error("更新角色权限失败: " + e.getMessage());
        }
    }
}
