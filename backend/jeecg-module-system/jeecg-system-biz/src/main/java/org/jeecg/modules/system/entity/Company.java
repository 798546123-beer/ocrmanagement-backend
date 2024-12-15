package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@TableName("company")
@ApiModel(value = "Company对象")
public class Company {

    @ApiModelProperty(value = "公司ID，主键，不可为NULL")
    @TableId(value = "company_id")
    private Integer companyId;

    @ApiModelProperty(value = "公司名称，长度不超过100字符，不可为NULL")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "父公司ID")
    @TableField("father_company")
    private Integer fatherCompanyId;
}