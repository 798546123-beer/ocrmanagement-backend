package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
@Data
@Table(name = "role_page")
public class PageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField(value = "page_name")
    private String pageName;
    @TableField(value = "page")
    private String page;
    @TableId
    private Integer id;
}
