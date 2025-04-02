package com.henu.ocr.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDistributionResponseModel {
    private String success="false";
    private String message;
    private HashMap<String, Object> mdMappings;
//    private String mdmCode;
//    private String entityCode;
//    private String busiDataId;
    public DataDistributionResponseModel(String message, String mdmCode, String entityCode, String busiDataId) {
        this.message = message;
        this.mdMappings = new HashMap<>();
        this.mdMappings.put("mdmCode", mdmCode);
        this.mdMappings.put("entityCode", entityCode);
        this.mdMappings.put("busiDataId", busiDataId);
    }
    public DataDistributionResponseModel(String isSuccess, String message,String mdmCode, String entityCode, String busiDataId) {
        this(message,mdmCode,entityCode,busiDataId);
        this.success = String.valueOf(isSuccess);
    }
}