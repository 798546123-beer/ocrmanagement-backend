package com.henu.ocr.service;

import com.henu.ocr.entity.PageEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henu.ocr.model.PageTreeModel;

import java.util.List;


public interface PageService extends IService<PageEntity>{
    List<PageTreeModel> getPermissionTree();
}
