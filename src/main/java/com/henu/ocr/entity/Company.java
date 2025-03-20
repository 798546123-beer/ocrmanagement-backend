package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("company")
public class Company {

//    @ApiModelProperty(value = "公司ID，主键，不可为NULL")
    @TableId(value = "company_id")
    private Integer companyId;

//    @ApiModelProperty(value = "公司名称，长度不超过100字符，不可为NULL")
    @TableField("company_name")
    private String companyName;

//    @ApiModelProperty(value = "父公司ID")
    @TableField("father_company")
    private Integer fatherCompanyId;
//    @ApiModelProperty(value = "级别，局，公司，分公司，enum，不可为NULL")
    @TableField("type")
    private String type;
}