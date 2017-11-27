package com.yuwubao.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yangyu on 2017/10/16.
 * 用户
 */
@Entity
@Table(name = "sys_user")
public class UserEntity {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */

    private String password;

    /**
     * 角色Id
     */
    private int roleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户类型0(后台用户),1(前端用户)
     */
    private int type;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
