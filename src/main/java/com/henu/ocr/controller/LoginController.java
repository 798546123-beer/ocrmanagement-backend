package com.henu.ocr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.henu.ocr.IgnoreToken;
import com.henu.ocr.entity.User;
import com.henu.ocr.pojo.LoginModel;
import com.henu.ocr.service.UserService;
import com.henu.ocr.service.serviceImpl.LoginServiceImpl;
import com.henu.ocr.util.EncodeUtil;
import com.henu.ocr.util.JWTUtil;
import com.henu.ocr.util.RedisUtil;
import com.henu.ocr.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;
    @Resource
    private LoginServiceImpl loginService;
    @IgnoreToken
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<?> login(@RequestBody LoginModel LoginModel, HttpServletRequest request) throws Exception {
        String username = LoginModel.getUsername();
        String password = LoginModel.getPassword();
        if (username == null || password == null) {
            log.debug("{}" + com.henu.ocrbackend.constant.CommonConstant.LOG_TYPE_1 + "账号或密码为空！", username);
            return Result.error("用户账号或密码为空");
        }
        if (loginService.isLoginFailOvertimes(username)) {
            log.debug("{}" + com.henu.ocrbackend.constant.CommonConstant.LOG_TYPE_1 + "用户登录次数过多", username);
            return Result.error("该用户登录失败次数过多，请于10分钟后再次登录！");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            log.debug("{}" + com.henu.ocrbackend.constant.CommonConstant.LOG_TYPE_1 + "失败，用户不存在！", username);
            return Result.error("该用户不存在，请注册");
        }
        if (user.getIsDelete()) {
            log.debug("{}" + com.henu.ocrbackend.constant.CommonConstant.LOG_TYPE_1 + "失败，账号被停用！", username);
            return Result.error("账号已停用");
        }
        String userpassword = user.getPassword();
        if (!userpassword.equals(EncodeUtil.encodePassword(password))) {
            loginService.addLoginFailTimes(username);
            return Result.error("用户名或密码错误");
        }
        //赋值一个token给前端
        user.setToken(JWTUtil.sign(username, password));
//        org.jeecg.modules.system.vo.User userVO =userService.getUserVO(userService.getUserInfo(user));
        log.info("用户名: {},{}成功！\n{}", username, com.henu.ocrbackend.constant.CommonConstant.LOG_TYPE_1, user);
        //在下面存放已登录用户的信息
        //在这里对用户账号密码进行加密返回给前端
//        redisUtil.setWithExpire(com.henu.ocrbackend.constant.CommonConstant.LOGIN_SUCCESS + username, user,  com.henu.ocrbackend.constant.CommonConstant.REDIS_EXPIRE_TIME / 1000);
        redisUtil.delete(com.henu.ocrbackend.constant.CommonConstant.LOGIN_FAIL + username);
//        redisCacheUtil.printAllRedisData();
        return new Result<User>(200,"登陆成功",user);
    }
    @IgnoreToken
    @GetMapping("getToken")
    public Result<?> getToken(@RequestParam String username,@RequestParam String password) {
        return Result.ok(JWTUtil.sign(username, password));
    }
    
}
