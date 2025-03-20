package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.PageEntity;
import com.henu.ocr.mapper.PageMapper;
import com.henu.ocr.service.PageService;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, PageEntity> implements PageService {
    //可以加一个缓存机制，让页面列表查询先缓存起来，减少数据库的访问
}
