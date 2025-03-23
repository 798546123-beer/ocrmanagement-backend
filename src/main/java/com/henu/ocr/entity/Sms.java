package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sms")
public class Sms {
    private Integer id;
    private String title;
    private byte[] additions;
    private String content;
    @TableField("send_time")
    private LocalDateTime sendTime;
    private String status;
    private Integer remark;
}