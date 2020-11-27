package com.game.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ Idea IDEA
 * java version "1.8.0_91"
 * Author: Ean Song
 * ProjectName：gameunion-crawler
 * DateTime: 2020-11-24 16:08
 */
@Entity //声明为实体类
@Table(name = "jd_item") //与数据库表jd_item映射
public class Item {
    //主键
    //    声明主键的自增方式
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    商品集合id
    private Long spu;
    //    商品最小品类单元id
    private Long sku;
    //    商品标题
    private String title;
    //    商品价格
    private Double price;
    //    商品图片
    private String pic;
    //    商品详情地址
    private String url;
    //    创建时间
    private Date created;
    //    更新时间
    private Date updated;


    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpu() {
        return spu;
    }

    public void setSpu(Long spu) {
        this.spu = spu;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
