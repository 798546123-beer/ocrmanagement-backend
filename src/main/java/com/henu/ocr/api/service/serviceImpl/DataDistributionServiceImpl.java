package com.henu.ocr.api.service.serviceImpl;

import com.henu.ocr.api.model.DataDistributionRequestModel;
import com.henu.ocr.entity.Company;
import com.henu.ocr.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class DataDistributionServiceImpl {
    @Resource
    private CompanyService companyService;
    @Transactional
    public boolean dealWithData(DataDistributionRequestModel model) throws Exception {
        String type = model.getMdType();
        switch (type) {
            case "org001":
                return this.dealWithOrg(model);
            case "dept001":
                return this.dealWithDept(model);
            case "psn001":
                return this.dealWithPsn(model);
            case "trader001":
                return this.dealWithTrader(model);
            case "pro001":
                return this.dealWithPro(model);
            default:
                throw new Exception("数据分发类型错误,mdType wrong");
        }
    }

    public boolean dealWithPro(DataDistributionRequestModel model) {
        return false;
    }

    public boolean dealWithTrader(DataDistributionRequestModel model) {
        return false;
    }

    public boolean dealWithPsn(DataDistributionRequestModel model) {
        return false;
    }

    public boolean dealWithDept(DataDistributionRequestModel model) {
        return false;
    }

    @Transactional
    public boolean dealWithOrg(DataDistributionRequestModel model) {
        Company company = Company.builder()
                .companyId((Integer) model.get("pk_corp"))
                .companyName((String) model.get("unitname"))
                .fatherCompanyId((Integer) model.get("fathercorp"))
                .build();
        return companyService.save(company);
    }
}