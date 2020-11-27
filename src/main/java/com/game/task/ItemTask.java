package com.game.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.pojo.Item;
import com.game.service.ItemService;
import com.game.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ Idea IDEA
 * java version "1.8.0_91"
 * Author: Ean Song
 * ProjectName：gameunion-crawler
 * DateTime: 2020-11-26 11:33
 */
@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private ItemService itemService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //    间隔100秒请求下一次
    @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception {
        String url = "https://search.jd.com/search?keyword=手机&wq=手机&page=";
//        遍历单页码页面
        for (int i = 1; i < 10; i = i + 2) {
            String html = httpUtils.doGetHtml(url + i);
//            解析页面，获取商品数据并存储
            this.parse(html);
        }
    }

    /**
     * 获取商品数据并存储
     *
     * @param html
     */
    private void parse(String html) {
//        通过jsoup获取页面数据
        Document document = Jsoup.parse(html);
//        获取页面商品列表 获取spu信息
        Elements spuElements = document.select("div#J_goodsList > ul > li");
        for (Element spuElement : spuElements) {
//            获取spu
            long spu = Long.parseLong(spuElement.attr("data-spu"));

//            获取sku商品信息 li里面的class属性ps-item
            Elements skuElements = spuElement.select("li.ps-item");
            for (Element skuElement : skuElements) {
//                获取sku
                long sku = Long.parseLong(skuElement.select("[data-sku]").attr("data-sku"));

//                根据sku查询商品数据，设置sku
                Item item = new Item();
                item.setSku(sku);
                List<Item> itemList = this.itemService.findAll(item);
//                如果存在不再保存,进行下一个循环
                if (itemList.size() > 0) {
                    continue;
                }
//                设置spu
                item.setSpu(spu);

//                获取商品URL详细地址
                String itemUrl = "https://item.jd.com/" + sku + ".html";
                item.setUrl(itemUrl);

//                获取商品图片并添加到数据库
                String picUrl = "https:" + skuElement.select("img[data-sku]").first().attr("data-lazy-img");
                picUrl = picUrl.replace("/n9/", "/n1");
                String getPic = this.httpUtils.doGetPic(picUrl);
                item.setUrl(getPic);
//                获取商品价格并添加到数据库
                String priceJson = this.httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                try {
                    double price = OBJECT_MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                    item.setPrice(price);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
//              商品标题
                String itemInfo = this.httpUtils.doGetHtml(item.getUrl());
                Document itemDoc = Jsoup.parse(itemInfo);
                String itemTitle = itemDoc.select("div.sku-name").text();
                item.setTitle(itemTitle);

//                商品创建时间
                item.setCreated(new Date());
//                商品更新时间
                item.setUpdated(item.getCreated());
//                保存商品信息到数据库
                this.itemService.save(item);
            }

        }
    }
}
