package org.jeecg.modules.system.controller;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.PageEntity;
import org.jeecg.modules.system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/getPermissionList")
    public Result<List<PageEntity>> getPermissionList() {
        List<PageEntity> permissionList = permissionService.getPermissionList();
        return Result.OK(permissionList);
    }
}
