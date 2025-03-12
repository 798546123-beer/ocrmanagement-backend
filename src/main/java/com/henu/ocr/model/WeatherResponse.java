package com.henu.ocr.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class WeatherResponse {
    private String country;
    private String province;
    private String city;
    private String currentTemp;

    public WeatherResponse() {}

    public WeatherResponse(String country, String province, String city, String currentTemp) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.currentTemp = currentTemp;
    }

}
