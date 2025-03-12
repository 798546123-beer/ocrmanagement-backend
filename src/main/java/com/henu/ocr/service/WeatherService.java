package com.henu.ocr.service;

import com.henu.ocr.model.WeatherResponse;
import com.henu.ocr.model.ZoneRequest;
import org.springframework.stereotype.Service;

//@Service
public interface WeatherService {
    WeatherResponse getWeatherDataByCity(String city);

    WeatherResponse getWeatherDataNoParameter();

    WeatherResponse getWeatherDataByZone(ZoneRequest zone);
}
