package com.henu.ocr.controller;

import com.henu.ocr.model.WeatherResponse;
import com.henu.ocr.model.ZoneRequest;
import com.henu.ocr.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weathers")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public WeatherResponse getMojiWeatherByCity(@PathVariable String city) {
        return weatherService.getWeatherDataByCity(city);
    }

    @GetMapping("/zone")
    public WeatherResponse getMojiWeatherByZone(@RequestParam String province, @RequestParam String city) {
        ZoneRequest zone = new ZoneRequest(province, city);
        return weatherService.getWeatherDataByZone(zone);
    }

    @GetMapping
    public WeatherResponse getWeatherNoParameter() {
        return weatherService.getWeatherDataNoParameter();
    }

}
