package com.henu.ocr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.ocr.entity.User;
import com.henu.ocr.model.UserDTO;
import com.henu.ocr.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT u.id AS userId, u.username AS username, u.password AS password, u.realname AS realname, u.user_type_id AS userTypeId, r.role_name AS userTypeName, u.phone AS userPhone, u.user_number AS userNumber, u.user_gender AS userGender, u.user_company_id AS userCompanyId, c.company_name AS userCompanyName, u.user_age AS age, u.is_delete AS isDelete FROM user u LEFT JOIN role r ON u.user_type_id = r.role_id LEFT JOIN company c ON u.user_company_id = c.company_id ORDER BY user_number ASC")
    IPage<UserDTO> selectUserDTOPage(IPage<UserDTO> page);

    @Select("SELECT DISTINCT u.id AS userId, u.username AS username, u.realname AS realname, u.user_type_id AS userTypeId, r.role_name AS userTypeName, u.phone AS userPhone, u.user_number AS userNumber, u.user_gender AS userGender, u.user_company_id AS userCompanyId, c.company_name AS userCompanyName, u.user_age AS age, u.is_delete AS isDelete FROM user u LEFT JOIN role r ON u.user_type_id = r.role_id LEFT JOIN company c ON u.user_company_id = c.company_id WHERE u.id = #{id}")
    UserVO getUserVOById(@Param("id") String id);
}