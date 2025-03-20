package com.henu.ocr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.henu.ocr.IgnoreToken;
import com.henu.ocr.entity.User;
import com.henu.ocr.model.LoginModel;
import com.henu.ocr.service.UserService;
import com.henu.ocr.service.serviceImpl.LoginServiceImpl;
import com.henu.ocr.util.EncodeUtil;
import com.henu.ocr.util.JWTUtil;
import com.henu.ocr.util.RedisUtil;
import com.henu.ocr.util.Result;
import com.henu.ocr.vo.UserVO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import static com.henu.ocrbackend.constant.CommonConstant.*;

@RestController
@RequestMapping("/login")
@Slf4j
@Tag(name="登录相关接口")
public class LoginController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;
    @Resource
    private LoginServiceImpl loginService;

    @IgnoreToken
    @Operation(summary = "用户登录",description = "返回结果拿到token作为令牌")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<?> login(@RequestBody LoginModel LoginModel, HttpServletRequest request) throws Exception {
        String username = LoginModel.getUsername();
        String password = LoginModel.getPassword();
        if (username == null || password == null) {
            log.debug("{}" + LOG_TYPE_1 + "账号或密码为空！", username);
            return Result.error("用户账号或密码为空");
        }
        if (loginService.isLoginFailOvertimes(username)) {
            log.debug("{}" + LOG_TYPE_1 + "用户登录次数过多", username);
            return Result.error("该用户登录失败次数过多，请于10分钟后再次登录！");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            log.debug("{}" + LOG_TYPE_1 + "失败，用户不存在！", username);
            return Result.error("该用户不存在，请注册");
        }
        if (user.getIsDelete()) {
            log.debug("{}" + LOG_TYPE_1 + "失败，账号被停用！", username);
            return Result.error("账号已停用");
        }
        String userpassword = user.getPassword();
        if (!userpassword.equals(EncodeUtil.encodePassword(password))) {
            loginService.addLoginFailTimes(username);
            return Result.error("用户名或密码错误");
        }
        UserVO userVO = new UserVO(user);
        //赋值一个token给前端
        userVO.setToken(JWTUtil.sign(username, Integer.valueOf(user.getUserId())));
        log.info("用户名: {},{}成功！\n{}", username, "登录", user);
        redisUtil.setWithExpire(LOGIN_SUCCESS + username, user, REDIS_EXPIRE_TIME);
        redisUtil.delete(LOGIN_FAIL + username);
//        redisCacheUtil.printAllRedisData();
        return new Result<>(200, "登陆成功", userVO);
    }
    @Hidden
    @IgnoreToken
    @GetMapping("getToken")
    public Result<?> getToken(@RequestParam String username) {
        return Result.ok(JWTUtil.sign(username, Integer.valueOf(userService.getUserByNameFuzzy(username).get(0).getUserId())));
    }
    @Parameter(name = "userId", description = "用户id" ,required = true)
    @Operation(summary = "登出操作",description = "用户登出后会清除Redis中残留数据并记录日志")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result<?> logout(@NotNull @RequestParam String userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        String username = user.getUsername();
        redisUtil.delete(LOGIN_SUCCESS + username);
        log.info("用户名: {},{}成功！", username, LOG_TYPE_3);
        return Result.ok("登出成功");
    }
}
