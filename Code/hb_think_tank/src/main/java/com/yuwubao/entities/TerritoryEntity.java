package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/11/30.
 */
@Entity
@Table(name = "territory")
public class TerritoryEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 地域名
     */
    private String name;

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
}
