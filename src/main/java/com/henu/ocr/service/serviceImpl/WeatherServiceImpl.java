package com.henu.ocr.service.serviceImpl;


import com.henu.ocr.component.MojiCrawler;
import com.henu.ocr.model.WeatherResponse;
import com.henu.ocr.model.ZoneRequest;
import com.henu.ocr.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final MojiCrawler mojiCrawler;

    @Autowired
    public WeatherServiceImpl(MojiCrawler mojiCrawler) {
        this.mojiCrawler = mojiCrawler;
    }

    @Override
    public WeatherResponse getWeatherDataByCity(String city) {
        try {
            return mojiCrawler.crawlWeatherByCity(city);
        } catch (IOException e) {
            throw new RuntimeException("数据抓取失败: " + e.getMessage());
        }
    }

    @Override
    public WeatherResponse getWeatherDataByZone(ZoneRequest zone) {
        try {
            return mojiCrawler.crawlWeatherByZone(zone);
        } catch (IOException e) {
            throw new RuntimeException("数据抓取失败: " + e.getMessage());
        }
    }

    @Override
    public WeatherResponse getWeatherDataNoParameter() {
        try {
            return mojiCrawler.crawlWeatherNoParameter();
        } catch (IOException e) {
            throw new RuntimeException("数据抓取失败: " + e.getMessage());
        }
    }

}

