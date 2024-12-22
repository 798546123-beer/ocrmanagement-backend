package org.jeecg.modules.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.system.entity.Role;

@Data
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
    private String typeName;
    private Integer userTypeId;
    private Role role;


}

