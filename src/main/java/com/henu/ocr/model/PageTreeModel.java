package com.henu.ocr.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.henu.ocr.entity.PageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageTreeModel extends PageEntity {
    private String pageName;
    private String page;
    private Integer id;
    private Integer fatherPage;
    @JsonInclude
    private List<PageTreeModel> children= new ArrayList<>();
    public PageTreeModel(PageEntity pageEntity){
        this.setId(pageEntity.getId());
        this.setPage(pageEntity.getPage());
        this.setPageName(pageEntity.getPageName());
        this.setFatherPage(pageEntity.getFatherPage());
    }
}
