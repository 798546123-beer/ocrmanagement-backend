package com.henu.ocr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ZoneRequest {
    private String provinceName;
    private String cityName;

    public ZoneRequest() {
    }

    public ZoneRequest(String provinceName, String cityName) {
        this.provinceName = provinceName;
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
