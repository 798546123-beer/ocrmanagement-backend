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

    // Getters and Setters
//    public String getPageName() {
//        return pageName;
//    }
//
//    public void setPageName(String pageName) {
//        this.pageName = pageName;
//    }
//
//    public String getPageId() {
//        return pageId;
//    }
//
//    public void setPageId(String pageId) {
//        this.pageId = pageId;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
}
