package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

//@Entity
@Data
@TableName("user")
//@Component
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Integer id;
    @TableField(value = "user_name")
    private String user_name;
    @TableField(value = "user_pwd")
    private String user_pwd;
    @TableField(value = "avatar")
    private String avatar;
    @TableField(value = "user_gender")
    private String gender;
    @TableField(value = "user_type_id")
    private Integer user_type_id;
    @TableField(value = "user_age")
    private Integer user_age;
    @TableField(value = "user_phone")
    private String user_phone;
    @TableField(value = "user_number")
    private String user_number;
    @TableField(value = "user_company_id")
    private Integer user_company_id;


}
