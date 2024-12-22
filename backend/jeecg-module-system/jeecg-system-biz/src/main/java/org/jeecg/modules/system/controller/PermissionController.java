package org.jeecg.modules.system.controller;

import org.jeecg.common.api.vo.Result;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.PageEntity;
import org.jeecg.modules.system.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 25612
 */
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @IgnoreAuth
    @GetMapping("/getPermissionList")
    public Result<List<PageEntity>> getPermissionList() {
        List<PageEntity> permissionList = permissionService.getPermissionList();
        return Result.OK(permissionList);
    }
}
