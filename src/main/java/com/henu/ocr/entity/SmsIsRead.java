package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sms_isread")
public class SmsIsRead {
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("sms_id")
    private Integer smsId;
    @TableField("read_time")
    private LocalDateTime readTime;
}