package com.henu.ocr.service;

import com.henu.ocr.entity.Sms;
import com.henu.ocr.model.SmsDTO;
import java.util.List;

public interface SmsService {
    void addSms(Sms sms);
    void deleteSms(Integer id);
    void updateSms(Sms sms);
    List<SmsDTO> getAllSms();
    SmsDTO getSmsById(Integer id);
    void markAsRead(Integer smsId, Integer userId);
}