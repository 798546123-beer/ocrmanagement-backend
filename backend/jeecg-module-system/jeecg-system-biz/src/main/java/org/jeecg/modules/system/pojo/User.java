package org.jeecg.modules.system.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String gender;
    private Integer age;
    private String phone;
    private String number;
    private String type_name;


}
