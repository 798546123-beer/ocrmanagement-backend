package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.Company;
import com.henu.ocr.mapper.CompanyMapper;
import com.henu.ocr.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Override
    public List<Company> findAllCompany() {
        return this.list();
    }
    @Override
    public Company findCompanyById(Integer id) {
        return this.getById(id);
    }
    @Override
    public List<Company> findSubCompanyById(Integer id) {
        //查找father_company为id的子公司
        return this.list((new QueryWrapper<Company>()).eq("father_company", id));
    }
}
