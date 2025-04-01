package com.henu.ocr.test;

import com.henu.ocr.entity.Permission;
import com.henu.ocr.util.BatchSqlUtil;
import com.henu.ocr.mapper.PermissionMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BatchOperateTest {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private BatchSqlUtil batchSqlUtil;

    private static List<Permission> testPermissions;

    @BeforeAll
    public static void setup() {
        testPermissions = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            testPermissions.add(new Permission(12345, "1"));
        }
    }

    @Test
    public void testBatchInsertWithMyBatisPlus() {
        long startTime = System.currentTimeMillis();
        testPermissions.forEach(permission -> {permissionMapper.insert(permission);});
        long endTime = System.currentTimeMillis();

        System.out.println("MyBatis-Plus 批量插入耗时: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testBatchInsertWithCustomFunction() {
        long startTime = System.currentTimeMillis();
        batchSqlUtil.batchOperate(testPermissions, PermissionMapper.class, (permission, mapper) -> {
            mapper.insert(permission);
            return null;
        });
        long endTime = System.currentTimeMillis();

        System.out.println("自定义批量插入函数耗时: " + (endTime - startTime) + "ms");
    }
}