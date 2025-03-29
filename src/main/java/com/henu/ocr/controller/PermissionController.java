package com.henu.ocr.controller;

import com.henu.ocr.entity.PageEntity;
import com.henu.ocr.mapper.PageMapper;
import com.henu.ocr.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("permission")
@Slf4j
@Tag(name = "权限相关接口")
public class PermissionController {
    @Resource
    private PageMapper pageMapper;
    @Operation(summary = "查询全部权限", description = "返回所有权限信息")
    @GetMapping("getPermissionList")
    public Result<?> getPermissionList() {
        try {
            List<PageEntity> permissions = pageMapper.selectList(null);
            return Result.ok(permissions);
        } catch (Exception e) {
            return Result.error("获取权限列表失败,"+e.getMessage());
        }
    }
}
