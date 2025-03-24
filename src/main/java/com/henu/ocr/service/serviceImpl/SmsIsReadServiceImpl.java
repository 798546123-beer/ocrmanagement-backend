package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.henu.ocr.entity.SmsIsRead;
import com.henu.ocr.mapper.SmsIsReadMapper;
import com.henu.ocr.service.SmsIsReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class SmsIsReadServiceImpl implements SmsIsReadService {

    @Autowired private SmsIsReadMapper smsIsReadMapper;

    @Override
    @Transactional
    public void markAsRead(Integer smsId, Integer userId) {
        SmsIsRead record = new SmsIsRead();
        record.setSmsId(smsId);
        record.setUserId(userId);
        record.setReadTime(LocalDateTime.now());
        int updated = smsIsReadMapper.update(record,
                new UpdateWrapper<SmsIsRead>().eq("sms_id", smsId).eq("user_id", userId));
        if (updated == 0) {
            smsIsReadMapper.insert(record);
        }
    }
}