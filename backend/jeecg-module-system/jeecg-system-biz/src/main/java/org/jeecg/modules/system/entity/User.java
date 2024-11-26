package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

//@Entity
@Data
@TableName("user")
@Component
public class User implements Serializable {
//    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "pwd")
    private String pwd;
    @TableField(value = "avatar")
    private String avatar;
    @TableField(value = "user_gender")
    private String userGender;
    @TableField(value = "user_type_id")
    private Integer userTypeId;
    @TableField(value = "user_age")
    private Integer userAge;
    @TableField(value = "user_phone")
    private String userPhone;
    @TableField(value = "user_number")
    private String userNumber;
    @TableField(value = "user_company_id")
    private Integer userCompanyId;


}
