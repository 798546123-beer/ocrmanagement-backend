package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.mapper.UserMapper;
import org.jeecg.modules.system.pojo.UserInfo;
import org.jeecg.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/19:41
 * @Description:
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户相关接口")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Resource
    private UserService userService;

    //这个类里写用户的新增，删除（根据id删除），修改，查询，分页查询
    @ApiOperation(value = "用户基础信息接口")
    @IgnoreAuth
    @PostMapping("updateUserInfo")
    public Result<JSONObject> UpdateUserInfo(@RequestBody User user) {
        try {
            userMapper.updateById(user);
        }catch (Exception e){
            return Result.error("修改失败");
        }
        return Result.ok("修改成功");
    }
    //根据用户名模糊查询用户
    @ApiOperation(value = "根据用户名模糊查询用户")
    @IgnoreAuth
    @GetMapping("getUserByLikeName")
    public Result<JSONObject> GetUserByLikeName(@Param(value = "likeName") String likeName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", likeName);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return Result.OK(String.valueOf(new org.jeecg.modules.system.vo.User(userService.getUserInfo(user))));
        }
        return Result.error("没有查找到对应的用户！");
    }

    @ApiOperation(value = "根据用户id查询用户")
    @IgnoreAuth
    @GetMapping("getUserById")
    public Result<JSONObject> GetUserById(@Param(value = "id") String id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return Result.OK(String.valueOf(new org.jeecg.modules.system.vo.User(userService.getUserInfo(user))));
        }
        return Result.error("没有查找到对应的用户！");
    }
}

