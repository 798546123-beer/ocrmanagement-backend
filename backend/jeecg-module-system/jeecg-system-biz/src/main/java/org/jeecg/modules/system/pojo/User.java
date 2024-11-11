package org.jeecg.modules.system.pojo;

import lombok.Data;
import org.jeecg.modules.system.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {
    private Integer id;
    private String username;
    private String password;
    private String gender;
    private Integer age;
    private String phone;
    private String number;
    private String type_name;
    private Integer user_type_id;
    @Autowired
    private Role role;
    //根据roleId 查一下数据库对应的角色类型名称
public User(@Autowired org.jeecg.modules.system.entity.User user){
    this.id=user.getId();
    this.age=user.getUser_age();
    this.username=user.getUser_name();
    this.password=user.getUser_pwd();
    this.gender=user.getGender();
    this.number=user.getUser_number();
    this.phone=user.getUser_phone();
    this.type_name=role.getRole_name();
    this.user_type_id=role.getRole_id();

}

}
