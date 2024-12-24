package org.jeecg.modules.system.controller;

import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.system.model.SysLoginModel;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class LoginControllerTest {
//@Resource
//private RedisCacheUtil redisCacheUtil;
    @Test
    void login() {
        LoginController loginController=new LoginController();
        SysLoginModel testModel = new SysLoginModel();
        testModel.setUsername("admin");
        testModel.setPassword("123456");
        loginController.login(testModel,null);
    }

    @Test
    void getUserInfo() {
    }
}
