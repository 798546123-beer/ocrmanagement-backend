package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSON;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private UserInfo userInfo;
    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "用户基础信息接口")
    @IgnoreAuth
    @GetMapping("getUserInfo")
    public Result<JSONObject> GetUserInfo(@Param(value = "user_id") String user_id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", user_id);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return Result.OK((new org.jeecg.modules.system.vo.User(new UserInfo(user))).toString());
        }
        return null;
    }

}

