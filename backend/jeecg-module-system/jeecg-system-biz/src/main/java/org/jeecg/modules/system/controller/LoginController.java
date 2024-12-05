package org.jeecg.modules.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.enums.DySmsEnum;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.*;
import org.jeecg.common.util.encryption.EncryptedString;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.model.SysLoginModel;
import org.jeecg.modules.system.service.ISysLogService;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.system.service.RoleService;
import org.jeecg.modules.system.service.UserService;
import org.jeecg.modules.system.service.impl.SysBaseApiImpl;
import org.jeecg.modules.system.util.EncodeUtil;
import org.jeecg.modules.system.util.RandImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@RequestMapping("/login")
@Api(tags = "用户登录")
@Slf4j
public class LoginController {
    private final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private SysBaseApiImpl sysBaseApi;
    @Autowired
    private ISysLogService logService;
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
            log.debug("{}" + CommonConstant.LOG_TYPE_1 + "用户登录次数过多",username);
            return result.error500("该用户登录失败次数过多，请于10分钟后再次登录！");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, username);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            log.debug("{}" + CommonConstant.LOG_TYPE_1 + "失败，用户不存在！",username);
            return result.error500("该用户不存在，请注册");
        }
        if (user.getIsDelete()) {
            log.debug("{}" + CommonConstant.LOG_TYPE_1 + "失败，账号被停用！",username);
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
     * 【vue3专用】获取用户信息
     */
    @GetMapping("/user/getUserInfo")
    public Result<JSONObject> getUserInfo(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        Result<JSONObject> result = new Result<JSONObject>();
        String username = JwtUtil.getUserNameByToken(request);
        if (oConvertUtils.isNotEmpty(username)) {
            // 根据用户名查询用户信息
            SysUser sysUser = sysUserService.getUserByName(username);
            JSONObject obj = new JSONObject();
            log.info("1 获取用户信息耗时（用户基础信息）" + (System.currentTimeMillis() - start) + "毫秒");
            String vue3Version = request.getHeader(CommonConstant.VERSION);
            result.success("");
        }
        log.info("end 获取用户信息耗时 " + (System.currentTimeMillis() - start) + "毫秒");
        return result;

    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (oConvertUtils.isEmpty(token)) {
            return Result.error("退出登录失败！");
        }
        String username = JwtUtil.getUsername(token);
        LoginUser sysUser = sysBaseApi.getUserByName(username);
        if (sysUser != null) {
            //update-begin--Author:wangshuai  Date:20200714  for：登出日志没有记录人员
            baseCommonService.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_1, null, sysUser);
            //update-end--Author:wangshuai  Date:20200714  for：登出日志没有记录人员
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
            //清空用户的缓存信息（包括公司信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            //调用shiro的logout
            SecurityUtils.getSubject().logout();
            return Result.ok("退出登录成功！");
        } else {
            return Result.error("Token无效!");
        }
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("loginfo")
    public Result<JSONObject> loginfo() {
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        //update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        Long totalVisitCount = logService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = logService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = logService.findTodayIp(dayStart, dayEnd);
        //update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        obj.put("todayIp", todayIp);
        result.setResult(obj);
        result.success("登录成功");
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
        List<Map<String, Object>> list = logService.findVisitCount(dayStart, dayEnd);
        result.setResult(oConvertUtils.toLowerCasePageList(list));
        return result;
    }


    @RequestMapping(value = "/selectDepart", method = RequestMethod.PUT)
    public Result<JSONObject> selectDepart(@RequestBody SysUser user) {
        Result<JSONObject> result = new Result<JSONObject>();
        String username = user.getUsername();
        if (oConvertUtils.isEmpty(username)) {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            username = sysUser.getUsername();
        }
        String orgCode = user.getOrgCode();
        Integer tenantId = user.getLoginTenantId();
        //设置用户登录公司和登录租户
        this.sysUserService.updateUserDepart(username, orgCode, tenantId);
        SysUser sysUser = sysUserService.getUserByName(username);
        JSONObject obj = new JSONObject();
        obj.put("userInfo", sysUser);
        result.setResult(obj);
        return result;
    }

    /**
     * 短信登录接口
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/sms")
    public Result<String> sms(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        Result<String> result = new Result<String>();
        String clientIp = IpUtils.getIpAddr(request);
        String mobile = jsonObject.get("mobile").toString();
        //手机号模式 登录模式: "2"  注册模式: "1"
        String smsmode = jsonObject.get("smsmode").toString();
        log.info("-------- IP:{}, 手机号：{}，获取绑定验证码", clientIp, mobile);

        if (oConvertUtils.isEmpty(mobile)) {
            result.setMessage("手机号不允许为空！");
            result.setSuccess(false);
            return result;
        }

        //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
        String redisKey = CommonConstant.PHONE_REDIS_KEY_PRE + mobile;
        Object object = redisUtil.get(redisKey);
        //update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906

        if (object != null) {
            result.setMessage("验证码10分钟内，仍然有效！");
            result.setSuccess(false);
            return result;
        }

        //-------------------------------------------------------------------------------------
        //增加 check防止恶意刷短信接口
        if (!DySmsLimit.canSendSms(clientIp)) {
            log.warn("--------[警告] IP地址:{}, 短信接口请求太多-------", clientIp);
            result.setMessage("短信接口请求太多，请稍后再试！");
            result.setCode(CommonConstant.PHONE_SMS_FAIL_CODE);
            result.setSuccess(false);
            return result;
        }
        //-------------------------------------------------------------------------------------

        //随机数
        String captcha = RandomUtil.randomNumbers(6);
        JSONObject obj = new JSONObject();
        obj.put("code", captcha);
        try {
            boolean b = false;
            //注册模板
            if (CommonConstant.SMS_TPL_TYPE_1.equals(smsmode)) {
                SysUser sysUser = sysUserService.getUserByPhone(mobile);
                if (sysUser != null) {
                    result.error500(" 手机号已经注册，请直接登录！");
                    baseCommonService.addLog("手机号已经注册，请直接登录！", CommonConstant.LOG_TYPE_1, null);
                    return result;
                }
                b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.REGISTER_TEMPLATE_CODE);
            } else {
                //登录模式，校验用户有效性
                SysUser sysUser = sysUserService.getUserByPhone(mobile);
                result = sysUserService.checkUserIsEffective(sysUser);
                if (!result.isSuccess()) {
                    String message = result.getMessage();
                    String userNotExist = "该用户不存在，请注册";
                    if (userNotExist.equals(message)) {
                        result.error500("该用户不存在或未绑定手机号");
                    }
                    return result;
                }

                /**
                 * smsmode 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
                 */
                if (CommonConstant.SMS_TPL_TYPE_0.equals(smsmode)) {
                    //登录模板
                    b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.LOGIN_TEMPLATE_CODE);
                } else if (CommonConstant.SMS_TPL_TYPE_2.equals(smsmode)) {
                    //忘记密码模板
                    b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.FORGET_PASSWORD_TEMPLATE_CODE);
                }
            }

            if (b == false) {
                result.setMessage("短信验证码发送失败,请稍后重试");
                result.setSuccess(false);
                return result;
            }

            //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
            //验证码10分钟内有效
            redisUtil.set(redisKey, captcha, 600);
            //update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906

            //update-begin--Author:scott  Date:20190812 for：issues#391
            //result.setResult(captcha);
            //update-end--Author:scott  Date:20190812 for：issues#391
            result.setSuccess(true);

        } catch (ClientException e) {
            e.printStackTrace();
            result.error500(" 短信接口未配置，请联系管理员！");
            return result;
        }
        return result;
    }


    /**
     * 手机号登录接口
     *
     * @param jsonObject
     * @return
     */
    @ApiOperation("手机号登录接口")
    @PostMapping("/phoneLogin")
    public Result<JSONObject> phoneLogin(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        Result<JSONObject> result = new Result<JSONObject>();
        String phone = jsonObject.getString("mobile");
        //update-begin-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
        if (isLoginFailOvertimes(phone)) {
            return result.error500("该用户登录失败次数过多，请于10分钟后再次登录！");
        }
        //update-end-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
        //校验用户有效性
        SysUser sysUser = sysUserService.getUserByPhone(phone);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        String smscode = jsonObject.getString("captcha");

        //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
        String redisKey = CommonConstant.PHONE_REDIS_KEY_PRE + phone;
        Object code = redisUtil.get(redisKey);
        //update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906

        if (!smscode.equals(code)) {
            //update-begin-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
            addLoginFailOvertimes(phone);
            //update-end-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
            return Result.error("手机验证码错误");
        }
        //用户信息
        userInfo(sysUser, result, request);
        //添加日志
        baseCommonService.addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_1, null);
        return result;
    }

    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result, HttpServletRequest request) {
        String username = sysUser.getUsername();
        String syspassword = sysUser.getPassword();
        JSONObject obj = new JSONObject(new LinkedHashMap<>());
        String token = JwtUtil.sign(username, syspassword);
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        obj.put("token", token);
        Result<JSONObject> loginTenantError = sysUserService.setLoginTenant(sysUser, obj, username, result);
        if (loginTenantError != null) {
            return loginTenantError;
        }
        //3.设置登录用户信息
        obj.put("userInfo", sysUser);
        String vue3Version = request.getHeader(CommonConstant.VERSION);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }

    /**
     * 获取加密字符串
     *
     * @return
     */
    @GetMapping(value = "/getEncryptedString")
    public Result<Map<String, String>> getEncryptedString() {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap(5);
        map.put("key", EncryptedString.key);
        map.put("iv", EncryptedString.iv);
        result.setResult(map);
        return result;
    }

    /**
     * 后台生成图形验证码 ：有效
     *
     * @param response
     * @param key
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage/{key}")
    public Result<String> randomImage(HttpServletResponse response, @PathVariable("key") String key) {
        Result<String> res = new Result<String>();
        try {
            //生成验证码
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            //存到redis中
            String lowerCaseCode = code.toLowerCase();
            //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
            // 加入密钥作为混淆，避免简单的拼接，被外部利用，用户自定义该密钥即可
            String origin = lowerCaseCode + key + jeecgBaseConfig.getSignatureSecret();
            String realKey = Md5Util.md5Encode(origin, "utf-8");
            //update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906

            redisUtil.set(realKey, lowerCaseCode, 60);
            log.info("获取验证码，Redis key = {}，checkCode = {}", realKey, code);
            //返回前端
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.error500("获取验证码失败,请检查redis配置!");
            return res;
        }
        return res;
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
            if (val > 5) {
                return true;
            }
        }
        return false;
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

    /**
     * 发送短信验证码接口(修改密码)
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/sendChangePwdSms")
    public Result<String> sendSms(@RequestBody JSONObject jsonObject) {
        Result<String> result = new Result<>();
        String mobile = jsonObject.get("mobile").toString();
        if (oConvertUtils.isEmpty(mobile)) {
            result.setMessage("手机号不允许为空！");
            result.setSuccess(false);
            return result;
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String username = sysUser.getUsername();
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getUsername, username).eq(SysUser::getPhone, mobile);
        SysUser user = sysUserService.getOne(query);
        if (null == user) {
            return Result.error("当前登录用户和绑定的手机号不匹配，无法修改密码！");
        }
        String redisKey = CommonConstant.PHONE_REDIS_KEY_PRE + mobile;
        Object object = redisUtil.get(redisKey);
        if (object != null) {
            result.setMessage("验证码10分钟内，仍然有效！");
            result.setSuccess(false);
            return result;
        }
        //随机数
        String captcha = RandomUtil.randomNumbers(6);
        JSONObject obj = new JSONObject();
        obj.put("code", captcha);
        try {
            boolean b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.CHANGE_PASSWORD_TEMPLATE_CODE);
            if (!b) {
                result.setMessage("短信验证码发送失败,请稍后重试");
                result.setSuccess(false);
                return result;
            }
            //验证码5分钟内有效
            redisUtil.set(redisKey, captcha, 300);
            result.setSuccess(true);
        } catch (ClientException e) {
            e.printStackTrace();
            result.error500(" 短信接口未配置，请联系管理员！");
            return result;
        }
        return result;
    }


    /**
     * 图形验证码
     *
     * @param sysLoginModel
     * @return
     */
    @RequestMapping(value = "/smsCheckCaptcha", method = RequestMethod.POST)
    public Result<?> smsCheckCaptcha(@RequestBody SysLoginModel sysLoginModel, HttpServletRequest request) {
        String captcha = sysLoginModel.getCaptcha();
        String checkKey = sysLoginModel.getCheckKey();
        if (captcha == null) {
            return Result.error("验证码无效");
        }
        String lowerCaseCaptcha = captcha.toLowerCase();
        String realKey = Md5Util.md5Encode(lowerCaseCaptcha + checkKey + jeecgBaseConfig.getSignatureSecret(), "utf-8");
        Object checkCode = redisUtil.get(realKey);
        if (checkCode == null || !checkCode.equals(lowerCaseCaptcha)) {
            return Result.error("验证码错误");
        }
        String clientIp = IpUtils.getIpAddr(request);
        //清空短信记录数量
        DySmsLimit.clearSendSmsCount(clientIp);
        redisUtil.removeAll(realKey);
        return Result.ok();
    }

}
