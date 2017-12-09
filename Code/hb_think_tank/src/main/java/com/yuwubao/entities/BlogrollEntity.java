package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/12/10.
 * 友情链接实体
 */
@Entity
@Table(name = "blogroll")
public class BlogrollEntity {

    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 合作单位名
     */
    private String name;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 合作单位链接地址
     */
    private String linkurl;

    /**
     * 0(共建机构),1(友情链接),2(合作媒体),3(特别成员单位)
     */
    private int type;

    /**
     * 备注
     */
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
