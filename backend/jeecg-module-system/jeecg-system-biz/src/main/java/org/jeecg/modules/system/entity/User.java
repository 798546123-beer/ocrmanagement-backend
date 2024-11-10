package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    @Excel(name = "登录账号", width = 15)
    private String user_name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String user_pwd;
    @Excel(name = "头像", width = 15,type = 2)
    private String avatar;
    @Excel(name = "性别", width = 15)
    private String gender;
    @Excel(name = "用户角色", width = 15)
    private Integer user_type_id;
    @Excel(name = "用户年龄", width = 15)
    private Integer user_age;
    @Excel(name = "手机号", width = 15)
    private String user_phone;
    @Excel(name = "用户编号", width = 15)
    private String user_number;
}
