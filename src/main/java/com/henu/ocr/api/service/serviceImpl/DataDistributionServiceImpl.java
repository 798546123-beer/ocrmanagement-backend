package com.henu.ocr.api.service.serviceImpl;

import com.henu.ocr.api.model.DataDistributionRequestModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataDistributionServiceImpl {
    @Transactional
    public boolean dealWithData(DataDistributionRequestModel model) throws Exception{
        String type = model.getMdType();
        switch (type){
            case "org001":
                return this.dealWithOrg(model);
                break;
            case "dept001":
                return this.dealWithDept(model);
                break;
            case "psn001":
                return this.dealWithPsn(model);
                break;
            case "trader001":
                return this.dealWithTrader(model);
                break;
            case "pro001":
                return this.dealWithPro(model);
                break;
            default:
                throw new Exception("数据分发类型错误,mdType wrong");
        }
        throw new Exception("未知异常, sort wrong");
    }

    private boolean dealWithPro(DataDistributionRequestModel model) {
    }

    private boolean dealWithTrader(DataDistributionRequestModel model) {
    }

    private boolean dealWithPsn(DataDistributionRequestModel model) {
    }

    private boolean dealWithDept(DataDistributionRequestModel model) {
    }

    private boolean dealWithOrg(DataDistributionRequestModel model) {
        
    }
}
