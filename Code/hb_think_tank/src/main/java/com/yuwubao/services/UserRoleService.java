package com.yuwubao.services;

import com.yuwubao.entities.UserRoleEntity;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface UserRoleService {
    UserRoleEntity findByUserId(int userId);

    UserRoleEntity save(UserRoleEntity entity);
}
