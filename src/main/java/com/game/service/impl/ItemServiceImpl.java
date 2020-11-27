package com.game.service.impl;

import com.game.dao.ItemDao;
import com.game.pojo.Item;
import com.game.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * Created by IntelliJ Idea IDEA
 * java version "1.8.0_91"
 * Author: Ean Song
 * ProjectName：gameunion-crawler
 * DateTime: 2020-11-24 16:46
 */
@Service
public class ItemServiceImpl implements ItemService {
    //    自动注入商品信息类
    @Autowired
    private ItemDao itemDao;

    @Override
    @Transactional
    public void save(Item item) {
        this.itemDao.save(item);
    }

    @Override
    public List<Item> findAll(Item item) {
//        声明查询条件
        Example<Item> example = Example.of(item);
//        根据查询条件进行数据查询
        List<Item> itemList = this.itemDao.findAll(example);
        return itemList;
    }
}
