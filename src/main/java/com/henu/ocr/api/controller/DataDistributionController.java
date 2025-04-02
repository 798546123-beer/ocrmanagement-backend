package com.henu.ocr.api.controller;

import com.henu.ocr.api.model.DataDistributionRequestModel;
import com.henu.ocr.api.model.DataDistributionResponseModel;
import com.henu.ocr.api.service.serviceImpl.DataDistributionServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Tag(name = "数据分发接口")
@Slf4j
@RequestMapping("data/api")
public class DataDistributionController {
    @Resource
    DataDistributionServiceImpl dataDistributionService;

    @PostMapping("test")
    public DataDistributionResponseModel test(@RequestBody DataDistributionRequestModel model) {
        try {
            log.info("test");
            boolean isSuccess = dataDistributionService.dealWithData(model);
            if(isSuccess){
                return new DataDistributionResponseModel("主数据分发成功",  model.get("mdmCode"),model.get("entityCode"), model.get("busiDataId"));
            }
            return new DataDistributionResponseModel("主数据分发失败",null,null,null);
        } catch (Exception e) {
            return DataDistributionResponseModel.builder().message(e.getMessage()).build();
        }
    }
}
