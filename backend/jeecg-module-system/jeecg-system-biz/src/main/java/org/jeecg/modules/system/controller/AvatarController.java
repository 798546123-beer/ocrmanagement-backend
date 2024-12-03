package org.jeecg.modules.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("avatar")
@Api(tags = "头像相关接口")
//在这个类里写头像的增删查改
public class AvatarController {
    
}
