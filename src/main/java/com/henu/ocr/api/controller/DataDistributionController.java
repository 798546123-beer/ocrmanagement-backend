package com.henu.ocr.api.controller;

import com.henu.ocr.api.model.DataDistributionRequestModel;
import com.henu.ocr.api.model.DataDistributionResponseModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "数据分发接口")
@Slf4j
@RequestMapping("data/api")
public class DataDistributionController {
    @PostMapping("test")
    public DataDistributionResponseModel test(@RequestBody DataDistributionRequestModel model) {
        log.info("test");

        return "test";
    }
}
