package com.yuwubao.services.impl;

import com.yuwubao.entities.ClientUserEntity;
import com.yuwubao.entities.repository.ClientUserRepository;
import com.yuwubao.services.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangyu on 2017/12/20.
 */
@Service
public class ClientUserServiceImpl implements ClientUserService {

    @Autowired
    private ClientUserRepository clientUserRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public ClientUserEntity add(ClientUserEntity clientUserEntity) {
        return clientUserRepository.save(clientUserEntity);
    }

    @Override
    public ClientUserEntity findByEmail(String email) {
        ClientUserEntity entity = clientUserRepository.findByEmail(email);
        return entity;
    }

    @Override
    public ClientUserEntity findOne(int id) {
        return clientUserRepository.findOne(id);
    }

    @Override
    public ClientUserEntity findByEmailAndPwd(String email, String pwd) {
        ClientUserEntity entity = clientUserRepository.findByEmailAndPwd(email, pwd);
        return entity;
    }

    @Override
    public ClientUserEntity findByNameAndPwd(String name, String pwd) {
        ClientUserEntity entity = clientUserRepository.findByNameAndPwd(name, pwd);
        return entity;
    }
}
