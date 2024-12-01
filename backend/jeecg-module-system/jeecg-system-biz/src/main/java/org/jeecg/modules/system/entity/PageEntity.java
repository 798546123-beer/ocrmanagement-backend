package org.jeecg.modules.system.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pageName;
    private String pageId;
    private Integer id;
}
