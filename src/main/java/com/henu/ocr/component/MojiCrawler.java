package com.henu.ocr.component;

import com.henu.ocr.model.WeatherResponse;
import com.henu.ocr.model.ZoneRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class MojiCrawler {
    private static final Logger logger = LoggerFactory.getLogger(MojiCrawler.class);

    @Value("${crawler.moji.url}")
    private String targetUrl;

    @Value("${crawler.timeout}")
    private int timeout;

    public WeatherResponse crawlWeatherByCity(String city) throws IOException {
        String URL = targetUrl + "/weather/china/henan/" + city;
        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .timeout(timeout)
                .get();

        // 解析数据
        return ParsingData(doc);
    }

    public WeatherResponse crawlWeatherByZone(ZoneRequest zone) throws IOException {
        String URL = targetUrl + "/weather/china/" + zone.getProvinceName() + "/" + zone.getCityName();
        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .timeout(timeout)
                .get();

        // 解析数据
        return ParsingData(doc);
    }

    public WeatherResponse crawlWeatherNoParameter() throws IOException {
        Document doc = Jsoup.connect(targetUrl)
                .userAgent("Mozilla/5.0")
                .timeout(timeout)
                .get();

        // 解析数据
        return ParsingData(doc);
    }

    private String[] processAreas(String rawArea) {
        return rawArea.replace("，", ",").split(",\\s*");
    }

    private WeatherResponse ParsingData(Document doc) {
        // 解析数据
        Element searchArea = doc.selectFirst("#search .search .search_default em");
        Element tempElement = doc.selectFirst(".wrap .left .wea_weather em");

        // 处理区域数据
        String[] areas = null;
        if (searchArea != null) {
            areas = processAreas(searchArea.text());
        }

        if (areas != null) {
            if (tempElement != null) {
                return new WeatherResponse(
                        areas[2],
                        areas[1],
                        areas[0],
                        tempElement.text().trim()
                );
            }
        }
        return null;
    }
}
