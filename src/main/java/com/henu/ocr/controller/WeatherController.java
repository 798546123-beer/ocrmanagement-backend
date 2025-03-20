package com.henu.ocr.controller;

import com.henu.ocr.model.WeatherResponse;
import com.henu.ocr.model.ZoneRequest;
import com.henu.ocr.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weathers")
@Tag(name = "天气接口", description = "天气查询")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "根据城市名查询天气")
    @GetMapping("/{city}")
    public WeatherResponse getMojiWeatherByCity(@PathVariable String city) {
        return weatherService.getWeatherDataByCity(city);
    }

    @Operation(summary = "根据省+市 查询天气")
    @GetMapping("/zone")
    public WeatherResponse getMojiWeatherByZone(@RequestParam String province, @RequestParam String city) {
        ZoneRequest zone = new ZoneRequest(province, city);
        return weatherService.getWeatherDataByZone(zone);
    }

    @Operation(summary = "无参构造，默认查询ip位置天气")
    @GetMapping
    public WeatherResponse getWeatherNoParameter() {
        return weatherService.getWeatherDataNoParameter();
    }

}
