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
     * 类型码
     */
    private int type;

    /**
     * 父级类型
     */
    private int parentType;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }
}
