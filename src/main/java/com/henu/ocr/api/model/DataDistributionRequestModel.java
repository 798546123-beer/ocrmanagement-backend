package com.henu.ocr.api.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

@Data
public class DataDistributionRequestModel {
    private String systemCode;
    private String mdType;
    private ObjectNode masterData;
    private String distributeToken;
    public String get(String key){
        return masterData.get(key).asText();
    }
}
