package com.yuwubao.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * 首页头部图片实体
 * Created by yangyu on 2017/11/8.
 */
@Entity
@Table(name = "title_img")
public class TitleImgEntity {

    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 状态 0(不展示)，1(展示)
     */
    private int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
