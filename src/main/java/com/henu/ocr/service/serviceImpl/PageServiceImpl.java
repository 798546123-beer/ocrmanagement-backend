package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.PageEntity;
import com.henu.ocr.mapper.PageMapper;
import com.henu.ocr.model.PageTreeModel;
import com.henu.ocr.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, PageEntity> implements PageService {
    //可以加一个缓存机制，让页面列表查询先缓存起来，减少数据库的访问
    @Resource
    private PageMapper pageMapper;

    @Cacheable(value = "permissionTree", key = "'permissionTree'")
    @Override
    public List<PageTreeModel> getPermissionTree() {
        List<PageEntity> allPages = pageMapper.selectList(null);
        return buildTree(allPages);
    }

    private List<PageTreeModel> buildTree(List<PageEntity> allPages) {
        Map<Integer, PageTreeModel> map = new HashMap<>();
        allPages.forEach(page -> {
            PageTreeModel dto = new PageTreeModel(page);
            map.put(dto.getId(), dto);
        });
        List<PageTreeModel> result = new ArrayList<>();
        map.values().forEach(node -> {
            if (node.getFatherPage() == null || node.getFatherPage() == -1) {
                result.add(node);
            } else {
                PageTreeModel parent = map.get(node.getFatherPage());
                if (parent != null) {
                    List<PageTreeModel> temp=parent.getChildren();
                    temp.add(node);
                    parent.setChildren(temp);
                }
            }
        });
        return result;
    }
    @CacheEvict(value = "permissionTree",allEntries = true)
    public void updatePages(PageEntity page){
        //更新页面,之后再实现逻辑
    }
}
