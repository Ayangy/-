package com.yuwubao.services;

import com.yuwubao.entities.RoleMenuEntity;

import java.util.List;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface RoleMenuService {
    void deleteByRoleId(int roleId);

    void saveRoleMenus(List<RoleMenuEntity> roleMenus);
}
