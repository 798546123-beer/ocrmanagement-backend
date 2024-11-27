package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Role;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/19:17
 * @Description:
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT * FROM role WHERE role_id = #{roleId}")
    Role getRoleById(Integer roleId);
}
