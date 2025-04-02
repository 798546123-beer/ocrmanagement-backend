package com.henu.ocr.api.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class DataDistributionRequestModel {
    private String systemCode;
    private String mdType;
    private String masterData;
    private String distributeToken;
    public Map<String,String>  get(){
       return (new ArrayList<>(JSON.parseObject(
               this.getMasterData(),
               new TypeReference<List<Map<String, String>>>() {
               })).get(0)
       );
    }
    public String get(String key){
        log.debug(get().get(key));
        return get().get(key);
    }
//    public String get(String key){
//        return masterData.get(key).asText();
//    }

//public String get(String key) {
//    return masterData != null && masterData.has(key)
//            ? masterData.get(key).asText()
//            : null;
//}

}
