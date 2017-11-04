package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/10/23.
 * 文章分类实体
 */
@Entity
@Table(name = "article_sort")
public class ArticleSortEntity {

    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 父ID
     */
    private int parentId;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
