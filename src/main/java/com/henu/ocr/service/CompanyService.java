package com.henu.ocr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henu.ocr.entity.Company;

import java.util.List;

public interface CompanyService extends IService<Company> {
    List<Company> findAllCompany();
    Company findCompanyById(Integer id);
    List<Company> findSubCompanyById(Integer id);
}
