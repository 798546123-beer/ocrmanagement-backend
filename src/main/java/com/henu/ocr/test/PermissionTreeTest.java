package com.henu.ocr.test;

import com.henu.ocr.model.PageTreeModel;
import com.henu.ocr.service.PageService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class PermissionTreeTest {
    @Resource
    private PageService pageService;
    @Test
    public void test() {
        List<PageTreeModel> permissionTree = pageService.getPermissionTree();
        System.out.println("here");
        System.out.println(permissionTree);
    }
}
