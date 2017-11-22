package com.yuwubao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangyu on 2017/10/20.
 * 文章实体
 */
@Entity
@Table(name = "article")
public class ArticleEntity implements Serializable {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 创作时间
     */
    private Date createdDate;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章图片地址
     */
    private String imgUrl;

    /**
     * 图片说明
     */
    private String imgState;

    /**
     * 文章类型Id
     */
    private int textTypeId;

    /**
     * 机构ID
     */
    private int organizationId;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 是否屏蔽0（不屏蔽），1（屏蔽）
     */
    private int shield;

    /**
     * 是否推荐 0(否)， 0(是)
     */
    private int recommend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgState() {
        return imgState;
    }

    public void setImgState(String imgState) {
        this.imgState = imgState;
    }

    public int getTextTypeId() {
        return textTypeId;
    }

    public void setTextTypeId(int textTypeId) {
        this.textTypeId = textTypeId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
}
