package com.yuwubao.services;

import com.yuwubao.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface UserService {
    UserEntity add(UserEntity userEntity);

    UserEntity delete(int id);

    UserEntity update(UserEntity userEntity);

    Page<UserEntity> findAll(Map<String, String> map, Pageable pageAble) throws Exception;

    UserEntity findById(int userId);

    UserEntity findByUsernameAndPassword(String username, String password, int type);

    UserEntity findByUsername(String username);
}
