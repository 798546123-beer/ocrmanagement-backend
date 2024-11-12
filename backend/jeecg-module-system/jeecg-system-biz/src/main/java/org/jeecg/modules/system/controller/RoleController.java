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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
