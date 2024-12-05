package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/19:56
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
@Select("SELECT NAME FROM TABLE ( USER ) ")
String getUserName(String username);
}
