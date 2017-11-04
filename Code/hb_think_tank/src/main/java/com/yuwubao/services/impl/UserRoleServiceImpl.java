package com.yuwubao.services.impl;

import com.yuwubao.entities.UserRoleEntity;
import com.yuwubao.entities.repository.UserRoleRopository;
import com.yuwubao.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangyu on 2017/10/17.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRopository userRoleRopository;

    @Override
    public UserRoleEntity findByUserId(int userId) {
        return userRoleRopository.findByUserId(userId);
    }

    @Override
    public UserRoleEntity save(UserRoleEntity entity) {
        return userRoleRopository.save(entity);
    }
}
