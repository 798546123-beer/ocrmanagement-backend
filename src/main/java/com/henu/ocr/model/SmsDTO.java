package com.henu.ocr.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SmsDTO {
    private Integer id;
    private String title;
    private byte[] additions;
    private String content;
    private LocalDateTime sendTime;
    private String status;
    private Integer remark;
    private LocalDateTime readTime;
}