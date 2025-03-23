package com.henu.ocr.controller;

import com.henu.ocr.entity.Sms;
import com.henu.ocr.model.SmsDTO;
import com.henu.ocr.service.SmsIsReadService;
import com.henu.ocr.service.SmsService;
import com.henu.ocr.util.Result;
import com.henu.ocr.util.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "消息管理接口")
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsIsReadService smsIsReadService;

    @Operation(summary = "创建消息")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<?> createSms(@RequestBody Sms sms) {
        try {
            validateSmsRequest(sms);
            smsService.addSms(sms);
            return Result.OK("消息创建成功");
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            return Result.Exception("服务器处理异常");
        }
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Result<?> deleteSms(@PathVariable Integer id) {
        try {
            smsService.deleteSms(id);
            return Result.OK(null); // 204响应不返回内容
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @Operation(summary = "更新消息")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result<?> updateSms(@PathVariable Integer id, @RequestBody Sms sms) {
        try {
            if (!id.equals(sms.getId())) {
                return Result.error(400, "路径ID与消息体ID不一致");
            }
            smsService.updateSms(sms);
            return Result.OK("消息更新成功");
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @Operation(summary = "获取所有消息")
    @GetMapping
    public Result<List<SmsDTO>> getAllSms() {
        try {
            List<SmsDTO> data = smsService.getAllSms();
            return Result.OK(data.isEmpty() ? "暂无消息" : "消息获取成功", data);
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @Operation(summary = "获取消息详情")
    @GetMapping("/{id}")
    public Result<SmsDTO> getSmsById(@PathVariable Integer id) {
        try {
            SmsDTO data = smsService.getSmsById(id);
            return data != null ?
                    Result.OK("消息详情获取成功", data) :
                    Result.error(404, "消息不存在");
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @Operation(summary = "标记消息已读")
    @PostMapping("/{id}/read-status")
    @ResponseStatus(HttpStatus.OK)
    public Result<?> markAsRead(@PathVariable Integer id) {
        try {
            Integer userId = UserContext.getCurrentUser();
            if (userId == null) {
                return Result.error(401, "需要用户认证");
            }
            smsIsReadService.markAsRead(id, userId);
            return Result.OK("已标记为已读");
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    private void validateSmsRequest(Sms sms) {
        if (sms.getTitle() == null || sms.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("消息标题不能为空");
        }
        if (sms.getContent() == null || sms.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("消息内容不能为空");
        }
    }
}