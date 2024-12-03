package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.mapper.UserMapper;
import org.jeecg.modules.system.service.UserService;
import org.jeecg.modules.system.util.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/19:41
 * @Description:
 */
@Slf4j
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
        } catch (Exception e) {
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

    @ApiOperation(value = "新增用户")
    @IgnoreAuth
    @PostMapping("addUser")
    public Result<JSONObject> addUser(@Valid @RequestBody User user) {
        try {
            // 检查用户名是否已存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", user.getName());
            User existUser = userMapper.selectOne(queryWrapper);
            if (existUser != null) {
                return Result.error("用户名已存在！");
            }
            boolean saveResult = userService.save(user);
            if (saveResult) {
                return Result.ok("新增用户成功");
            } else {
                return Result.error("新增用户失败");
            }
        } catch (Exception e) {
            log.error("新增用户失败", e);
            return Result.error("新增用户失败: " + e.getMessage());
        }
    }

    @ApiOperation(value = "删除用户")
    @IgnoreAuth
    @DeleteMapping("deleteUserById")
    public Result<JSONObject> deleteUser(@Param("id") Integer id) {
        try {
            User user = userMapper.selectById(id);
            if (user == null) {
                return Result.error("用户不存在！");
            }
            boolean removeResult = userService.removeById(id);
            if (removeResult) {
                return Result.ok("删除用户成功");
            } else {
                return Result.error("删除用户失败");
            }
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return Result.error("删除用户失败: " + e.getMessage());
        }
    }

    // 修改用户
    @ApiOperation(value = "修改用户")
    @IgnoreAuth
    @PutMapping("updateUserById")
    public Result<JSONObject> updateUser(@Valid @RequestBody User user) {
        try {
            User existUser = userMapper.selectById(user.getId());
            if (existUser == null) {
                return Result.error("用户不存在！");
            }
            if (user.getPwd() != null && !(EncodeUtil.encodePassword(user.getPwd())).equals(existUser.getPwd())) {
                user.setPwd(EncodeUtil.encodePassword(user.getPwd()));
            }
            
            boolean updateResult = userService.updateById(user);
            if (updateResult) {
                return Result.ok("修改成功");
            } else {
                return Result.error("修改失败");
            }
        } catch (Exception e) {
            log.error("修改用户失败", e);
            return Result.error("修改用户失败: " + e.getMessage());
        }
    }
}

