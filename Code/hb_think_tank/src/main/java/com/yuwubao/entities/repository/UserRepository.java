package com.yuwubao.entities.repository;

import com.yuwubao.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByUsername(String username);

    UserEntity findByUsernameAndPasswordAndType(String username, String password, int type);

    UserEntity findByUsernameAndType(String username, int type);
}
