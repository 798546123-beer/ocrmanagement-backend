package com.henu.ocr.api.model;

import io.swagger.v3.core.util.Json;
import lombok.Data;

@Data
public class DataDistributionRequestModel {
    private String systemCode;
    private String mdType;
    private Json masterData;
    private String distributeToken;
}
