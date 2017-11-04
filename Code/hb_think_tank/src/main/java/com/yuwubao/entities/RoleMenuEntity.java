package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/10/17.
 * 角色菜单
 */
@Entity
@Table(name = "sys_role_menu")
public class RoleMenuEntity {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 角色Id
     */
    private int roleId;

    /**
     * 菜单Id
     */
    private int menuId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
