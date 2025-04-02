package com.henu.ocr.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDistributionResponseModel {
    private String success;
    private String message;
    private String mdMappings;
    private String mdmCode;
    private String entityCode;
    private String busiDataId;
    public DataDistributionResponseModel(String message, String mdMappings, String mdmCode, String entityCode, String busiDataId) {
        this.success = String.valueOf(false);
        this.message = message;
        this.mdMappings = mdMappings;
        this.mdmCode = mdmCode;
        this.entityCode = entityCode;
        this.busiDataId = busiDataId;
    }
}
