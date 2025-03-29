package com.henu.ocr.controller;

import com.henu.ocr.entity.Company;
import com.henu.ocr.service.CompanyService;
import com.henu.ocr.util.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("company")
@Tag(name = "公司管理接口")
public class CompanyController {
    @Resource
    private CompanyService companyService;

    @GetMapping("findAllCompany")
    public Result<?> findAllCompany() {
        try {
            List<Company> companyList = companyService.findAllCompany();
            return Result.OK("查询成功", companyList);
        } catch (Exception e) {
            return Result.error("查询失败," + e.getMessage());
        }

    }

    @GetMapping("findSubCompanyById")
    public Result<?> findSubCompanyById(Integer id) {
        try {
            List<Company> companyList = companyService.findSubCompanyById(id);
            return Result.OK("查询成功", companyList);
        } catch (Exception e) {
            return Result.error("查询失败," + e.getMessage());
        }
    }
}
