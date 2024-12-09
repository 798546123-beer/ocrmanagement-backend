package org.jeecg.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.model.SysLoginModel;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/test")
@Api(tags = "测试常用接口")
@Slf4j
public class TokenController {
    @ApiOperation("获取TOKEN")
    @IgnoreAuth
    @RequestMapping(value = "/getToken",method = RequestMethod.POST)
    public Result<JSONObject> getToken(@RequestBody SysLoginModel sysLoginModel){
            String token= JwtUtil.sign(sysLoginModel.getUsername(), sysLoginModel.getPassword());
            return new Result<JSONObject>(200,token);
    }

}
