package org.jeecg.modules.system.pojo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
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
    @Autowired
    private RoleMapper roleMapper;
    //根据roleId 查一下数据库对应的角色类型名称
public UserInfo( org.jeecg.modules.system.entity.User user){
    this.id=user.getId();
    this.age=user.getUserAge();
    this.username=user.getName();
    this.password=user.getPwd();
    this.gender=user.getUserGender();
    this.number=user.getUserNumber();
    this.phone=user.getUserPhone();
    QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("role_id", user.getUserTypeId());
    Role role=roleMapper.selectOne(queryWrapper);
    System.out.print("role");
    System.out.print(role);
    if (role != null) {
        this.type_name=role.getRoleName();
        this.user_type_id=role.getRoleId();
    }else{
        System.out.println("用户角色不存在");
    }

}

}
