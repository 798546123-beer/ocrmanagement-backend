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
    private String userName;
    @TableField
    private String Password;
}
