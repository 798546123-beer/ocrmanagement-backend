package com.henu.ocr.service;

public interface SmsIsReadService {
    void markAsRead(Integer smsId, Integer userId);
}