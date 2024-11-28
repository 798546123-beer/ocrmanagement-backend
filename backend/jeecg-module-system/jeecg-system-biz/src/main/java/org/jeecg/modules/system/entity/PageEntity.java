package org.jeecg.modules.system.entity;

import java.io.Serializable;

public class PageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pageName;
    private String pageId;
    private Integer id;

    // Getters and Setters
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
