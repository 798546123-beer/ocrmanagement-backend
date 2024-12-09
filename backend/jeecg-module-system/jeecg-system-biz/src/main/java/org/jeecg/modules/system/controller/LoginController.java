package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.model.SysLoginModel;
import org.jeecg.modules.system.service.RoleService;
import org.jeecg.modules.system.service.UserService;
import org.jeecg.modules.system.util.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/login")
@Api(tags = "用户登录")
@Slf4j
public class LoginController {
    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";
    @Autowired
    private RoleService roleService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseCommonService baseCommonService;
    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;
    @Resource
    private UserService userService;

    @ApiOperation("登录接口")
    @IgnoreAuth
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<?> login(@RequestBody SysLoginModel sysLoginModel, HttpServletRequest request) {
        Result<org.jeecg.modules.system.vo.User> result = new Result<>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();
        if (username == null || password == null) {
            log.debug("{}" + CommonConstant.LOG_TYPE_1 + "账号或密码为空！", username);
            return result.error500("用户账号或密码为空");
        }
        if (isLoginFailOvertimes(username)) {
            log.debug("{}" + CommonConstant.LOG_TYPE_1 + "用户登录次数过多", username);
            return result.error500("该用户登录失败次数过多，请于10分钟后再次登录！");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, username);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            log.debug("{}" + CommonConstant.LOG_TYPE_1 + "失败，用户不存在！", username);
            return result.error500("该用户不存在，请注册");
        }
        if (user.getIsDelete()) {
            log.debug("{}" + CommonConstant.LOG_TYPE_1 + "失败，账号被停用！", username);
            return result.error500("账号已停用！");
        }
        result.setResult(userService.getUserVO(userService.getUserInfo(user)));
        System.out.println(result + "result");
        String userpassword = user.getPwd();
        if (!userpassword.equals(EncodeUtil.encodePassword(password))) {
            addLoginFailOvertimes(username);
            return new Result<JSONObject>(500, "用户名或密码错误");
        }

        // step.4  登录成功获取用户信息
//        userInfo(user, result, request);
        // step.5  登录成功删除验证码
//        redisUtil.del(realKey);
//        redisUtil.del(CommonConstant.LOGIN_FAIL + username);
//        BeanUtils.copyProperties(user, loginUser);
        log.info("用户名: {},{}成功！\n{}", username, CommonConstant.LOG_TYPE_1, user);
        result.setSuccess(true);
        return result;
    }


    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("/visitInfo")
    public Result<List<Map<String, Object>>> visitInfo() {
        Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        return result;
    }

    /**
     * 登录失败超出次数5 返回true
     *
     * @param username
     * @return
     */
    private boolean isLoginFailOvertimes(String username) {
        String key = CommonConstant.LOGIN_FAIL + username;
        Object failTime = redisUtil.get(key);
        if (failTime != null) {
            Integer val = Integer.parseInt(failTime.toString());
            return val > 5;
        }
        return false;
    }

    /**
     * 【vue3专用】获取用户信息
     */
    @GetMapping("/user/getUserInfo")
    public Result<JSONObject> getUserInfo(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        Result<JSONObject> result = new Result<JSONObject>();
        String username = JwtUtil.getUserNameByToken(request);
        if (oConvertUtils.isNotEmpty(username)) {
            // 根据用户名查询用户信息
            User user = userService.getUserByName(username);
            JSONObject obj = new JSONObject();
            log.info("1 获取用户信息耗时（用户基础信息）" + (System.currentTimeMillis() - start) + "毫秒");
            String vue3Version = request.getHeader(CommonConstant.VERSION);
            result.success("");
        }
        log.info("end 获取用户信息耗时 " + (System.currentTimeMillis() - start) + "毫秒");
        return result;

    }

    /**
     * 记录登录失败次数
     *
     * @param username
     */
    private void addLoginFailOvertimes(String username) {
        String key = CommonConstant.LOGIN_FAIL + username;
        Object failTime = redisUtil.get(key);
        Integer val = 0;
        if (failTime != null) {
            val = Integer.parseInt(failTime.toString());
        }
        // 10分钟，一分钟为60s
        redisUtil.set(key, ++val, 600);
    }


}

