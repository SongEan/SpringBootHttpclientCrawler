package com.game.service;

import com.game.pojo.Item;

import java.util.List;

/**
 * Created by IntelliJ Idea IDEA
 * java version "1.8.0_91"
 * Author: Ean Song
 * ProjectName：gameunion-crawler
 * DateTime: 2020-11-24 16:42
 */
public interface ItemService {
    /**
     * 保存商品
     *
     * @param item
     */
    public void save(Item item);

    /**
     * 根据条件查询商品
     *
     * @param item
     * @return
     */
    public List<Item> findAll(Item item);
}
