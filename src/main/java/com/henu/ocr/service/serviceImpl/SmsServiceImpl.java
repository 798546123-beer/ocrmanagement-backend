package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.henu.ocr.entity.Sms;
import com.henu.ocr.entity.SmsIsRead;
import com.henu.ocr.mapper.SmsIsReadMapper;
import com.henu.ocr.mapper.SmsMapper;
import com.henu.ocr.model.SmsDTO;
import com.henu.ocr.service.SmsService;
import com.henu.ocr.util.UserContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired private SmsMapper smsMapper;
    @Autowired private SmsIsReadMapper smsIsReadMapper;

    @Override
    @Transactional
    public void addSms(Sms sms) {
        sms.setSendTime(LocalDateTime.now());
        smsMapper.insert(sms);
    }

    @Override
    @Transactional
    public void deleteSms(Integer id) {
        smsMapper.deleteById(id);
        smsIsReadMapper.delete(new QueryWrapper<SmsIsRead>().eq("sms_id", id));
    }

    @Override
    @Transactional
    public void updateSms(Sms sms) {
        smsMapper.updateById(sms);
    }

    @Override
    public List<SmsDTO> getAllSms() {
        Integer userId = UserContext.getCurrentUser();
        return smsMapper.selectList(null).stream().map(sms -> {
            SmsDTO dto = new SmsDTO();
            BeanUtils.copyProperties(sms, dto);
            SmsIsRead isRead = smsIsReadMapper.selectOne(new QueryWrapper<SmsIsRead>()
                    .eq("sms_id", sms.getId()).eq("user_id", userId));
            dto.setReadTime(isRead != null ? isRead.getReadTime() : null);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public SmsDTO getSmsById(Integer id) {
        Sms sms = smsMapper.selectById(id);
        SmsDTO dto = new SmsDTO();
        BeanUtils.copyProperties(sms, dto);
        SmsIsRead isRead = smsIsReadMapper.selectOne(new QueryWrapper<SmsIsRead>()
                .eq("sms_id", id).eq("user_id",     UserContext.getCurrentUser()));
        dto.setReadTime(isRead != null ? isRead.getReadTime() : null);
        return dto;
    }

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