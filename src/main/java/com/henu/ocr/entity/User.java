package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableField(exist = false)
    private String token;
    @TableId("id")
    private String userId;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("realname")
    private String realname;
    @TableField("user_type_id")
    private String userTypeId;
    @TableField("phone")
    private String userPhone;
    @TableField("user_number")
    private int userNumber;
    @TableField("user_gender")
    private String userGender;
    @TableField("user_company_id")
    private String userCompanyId;
    @TableField("user_age")
    private int Age;
    @TableField("is_delete")
    private Boolean IsDelete;
}
