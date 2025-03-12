package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId
    private String userId;
    @TableField
    private String username;
    @TableField
    private String password;
    @TableField
    private String realname;
    @TableField
    private String userTypeId;
    @TableField
    private String userPhone;
    @TableField
    private int userNumber;
    @TableField
    private String userCompanyId;
}
