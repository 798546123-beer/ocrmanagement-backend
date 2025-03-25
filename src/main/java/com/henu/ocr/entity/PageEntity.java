package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
/**
 * @author 25612
 */

@Data
@TableName("role_page")
public class PageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField(value = "page_name")
    private String pageName;
    @TableField(value = "page")
    private String page;
    @TableId
    private Integer id;
    @TableField(value = "father_page")
    private Integer fatherPage;
}
