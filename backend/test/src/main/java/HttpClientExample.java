package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class HttpClientExample {
    public static void main(String[] args) {
        try {
            // 创建URL对象，指定需要发送请求的网址
            URL url = new URL("http://cnzui.com/c1?c=10E-210359&r=SMN1");

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为GET
            connection.setRequestMethod("GET");

            // 设置请求头信息
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // 获取响应码
            int responseCode = connection.getResponseCode();
            StringBuilder response =null;
            // 如果响应码为200，表示请求成功
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 获取响应流
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                response = new StringBuilder();

                // 读取响应内容
                while ((line = reader.readLine()) != null) {
                    response.append(line+"\n\r");

                }
                reader.close();

                // 打印响应内容
                System.out.println(response.toString());
            } else {
                System.out.println("请求失败，响应码：" + responseCode);
            }

            // 关闭连接
            connection.disconnect();

            Document document = Jsoup.parse(response.toString());

            // 提取产品名称
            String productName = document.select("div.details_box div.details_row:contains(名称:) span").last().text();
            System.out.println("产品名称: " + productName);

            // 提取牌号
            String brand = document.select("div.details_box div.details_row:contains(牌号:) span").last().text();
            System.out.println("牌号: " + brand);

            // 提取批号
            String batchNumber = document.select("div.details_box div.details_row:contains(批号:) span").last().text();
            System.out.println("批号: " + batchNumber);

            // 提取规格
            String specification = document.select("div.details_box div.details_row:contains(规格:) span").last().text();
            System.out.println("规格: " + specification);

            // 提取重量
            String weight = document.select("div.details_box div.details_row:contains(重量:) span").last().text();
            System.out.println("重量: " + weight);

            // 提取支数
            String quantity = document.select("div.details_box div.details_row:contains(支数:) span").last().text();
            System.out.println("支数: " + quantity);

            // 提取生产日期
            String productionDate = document.select("div.details_box div.details_row:contains(生产日期:) span").last().text();
            System.out.println("生产日期: " + productionDate);

            // 提取捆号
            String bundleNumber = document.select("div.details_box div.details_row:contains(捆号:) span").last().text();
            System.out.println("捆号: " + bundleNumber);

            // 提取定尺
            String fixedLength = document.select("div.details_box div.details_row:contains(定尺:) span").last().text();
            System.out.println("定尺: " + fixedLength);

            // 提取批号校验码
            String batchCheckCode = document.select("div.details_box div.details_row:contains(批号校检码:) span").last().text();
            System.out.println("批号校验码: " + batchCheckCode);

            // 提取生产班组
            String productionTeam = document.select("div.details_box div.details_row:contains(生产班组:) span").last().text();
            System.out.println("生产班组: " + productionTeam);

            // 提取指定查询官网
            String officialWebsite = document.select("div.details_box div.details_row:contains(指定查询官网:) span").last().text();
            System.out.println("指定查询官网: " + officialWebsite);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
