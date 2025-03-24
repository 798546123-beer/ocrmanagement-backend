package com.henu.ocr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henu.ocr.entity.Sms;
import com.henu.ocr.entity.SmsIsRead;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsMapper extends BaseMapper<Sms> {

}