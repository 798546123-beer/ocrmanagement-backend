package com.henu.ocr.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String password;
    private String username;
    private String realname;
    private String userTypeId;
    private String userTypeName;
    private String userPhone;
    private int userNumber;
    private String userGender;
    private String userCompanyId;
    private String userCompanyName;
    private int age;
    private Boolean isDelete;
}
