package com.henu.ocr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henu.ocr.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
