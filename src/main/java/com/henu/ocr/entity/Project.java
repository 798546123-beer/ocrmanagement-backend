package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.stereotype.Component;


@Data
@Component
@TableName("project")
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    @TableField(value = "project_name")
    private String projectName;

    @TableField(value = "project_part")
    private String projectPart;

    @TableField(value = "company_id")
    private Integer companyId;

    @TableField(value = "project_head_id")
    private Integer projectHeadId;

    @TableField(value = "sub_conductor")
    private String subConductor;

    @TableField(value = "sub_head")
    private String subHead;
}
