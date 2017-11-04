package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/10/17.
 * 菜单实体
 */
@Entity
@Table(name = "sys_menu")
public class MenuEntity {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 父菜单Id
     */
    private int menuId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
