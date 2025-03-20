package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@TableName("user")
@Component
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private int age;
    @TableField("is_delete")
    private Boolean isDelete;
}
