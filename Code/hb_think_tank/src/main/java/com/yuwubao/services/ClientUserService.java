package com.yuwubao.services;

import com.yuwubao.entities.ClientUserEntity;

/**
 * Created by yangyu on 2017/12/20.
 */
public interface ClientUserService {
    ClientUserEntity add(ClientUserEntity clientUserEntity);

    ClientUserEntity findByEmail(String email);

    ClientUserEntity findOne(int id);

    ClientUserEntity findByEmailAndPwd(String email, String pwd);

    ClientUserEntity findByNameAndPwd(String name, String pwd);
}
