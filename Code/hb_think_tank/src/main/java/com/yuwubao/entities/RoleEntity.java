package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/10/17.
 * 角色实体
 */
@Entity
@Table(name = "sys_role")
public class RoleEntity {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色备注
     */
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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
