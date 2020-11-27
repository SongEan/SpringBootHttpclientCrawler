package com.game.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by IntelliJ Idea IDEA
 * java version "1.8.0_91"
 * Author: Ean Song
 * ProjectName：gameunion-crawler
 * DateTime: 2020-11-25 19:15
 */
public class HttpUtils {
    PoolingHttpClientConnectionManager httpClientConnectionManager;

    public HttpUtils() {
        this.httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        this.httpClientConnectionManager.setMaxTotal(100);
        this.httpClientConnectionManager.setDefaultMaxPerRoute(10);
    }

    /**
     * 获取页面信息
     *
     * @param url
     * @return
     */
    public String doGetHtml(String url) {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(httpClientConnectionManager).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                } else {
                    return "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 获取图片
     *
     * @param url
     * @return
     */
    public String doGetPic(String url) {
//        通过线程池生成httpclient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
//                判断响应体是否为空
                if (response.getEntity() != null) {
//                    获取图片后缀
                    String extName = url.substring(url.lastIndexOf('.'));
//                    创建图片名称，重命名
                    String picName = UUID.randomUUID().toString() + extName;
//                    下载图片
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            new File("/Users/SSJ/Downloads/icastCrawler/" + picName));
                    response.getEntity().writeTo(fileOutputStream);
//                    返回图片名称
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    /**
     * 设置请求参数
     *
     * @return
     */
    private RequestConfig getConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)//创建连接最长时间
                .setConnectionRequestTimeout(100)//获取链接最长时间
                .setSocketTimeout(10 * 1000)//数据传输最长时间
                .build();
        return requestConfig;

    }
}
