package org.jeecg.modules.system.pojo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private String gender;
    private Integer age;
    private String phone;
    private String number;
    private String type_name;
    private Integer user_type_id;
    //根据roleId 查一下数据库对应的角色类型名称
public UserInfo( org.jeecg.modules.system.entity.User user){
    this.id=user.getId();
    this.age=user.getUser_age();
    this.username=user.getUser_name();
    this.password=user.getUser_pwd();
    this.gender=user.getGender();
    this.number=user.getUser_number();
    this.phone=user.getUser_phone();
    QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("role_id", user.getUser_type_id());
    Role role=new Role();
    System.out.println(role);
//    System.out.println(roleMapper.selectOne(queryWrapper));

}

}
