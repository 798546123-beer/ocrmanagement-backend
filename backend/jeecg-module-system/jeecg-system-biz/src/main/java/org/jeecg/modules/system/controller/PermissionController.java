package org.jeecg.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.PageEntity;
import org.jeecg.modules.system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "权限相关接口")
@RequestMapping("/system/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;
    @IgnoreAuth
    @GetMapping("/getPermissionList")
    @ApiOperation("权限列表获取")
    public Result<List<PageEntity>> getPermissionList() {
        List<PageEntity> permissionList = permissionService.getPermissionList();
        return Result.OK(permissionList);
    }
}
