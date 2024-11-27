package org.jeecg.modules.system.pojo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.controller.RoleController;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
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
    private Role role;



}

