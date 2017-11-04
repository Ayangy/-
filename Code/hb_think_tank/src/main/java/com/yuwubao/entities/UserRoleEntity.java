package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/10/17.
 * 用户角色
 */
@Entity
@Table(name = "sys_user_role")
public class UserRoleEntity {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 角色Id
     */
    private int roleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
