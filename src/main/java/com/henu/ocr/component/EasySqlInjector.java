package com.henu.ocr.component;

import java.util.List;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

public class EasySqlInjector extends DefaultSqlInjector {

    @Override
    //原先想写批量修改插入sql的
    public List<AbstractMethod> getMethodList(Class<?> mapperClass,TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass,tableInfo);
        // 使用InsertBatchSomeColumn方法替代batchInsert()
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }

}