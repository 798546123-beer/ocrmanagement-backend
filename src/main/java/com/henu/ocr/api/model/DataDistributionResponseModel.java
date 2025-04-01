package com.henu.ocr.api.model;

import lombok.Data;

@Data
public class DataDistributionResponseModel {
    private String success;
    private String message;
    private String mdMappings;
    private String mdmCode;
    private String entityCode;
    private String busiDataId;
}
