package com.game.dao;

import com.game.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ Idea IDEA
 * java version "1.8.0_91"
 * Author: Ean Song
 * ProjectName：gameunion-crawler
 * DateTime: 2020-11-24 16:19
 */

public interface ItemDao extends JpaRepository<Item, Long> {//Long 数据库表主键数据类型

}
