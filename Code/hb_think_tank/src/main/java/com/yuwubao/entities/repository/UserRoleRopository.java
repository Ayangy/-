package com.yuwubao.entities.repository;

import com.yuwubao.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface UserRoleRopository extends JpaRepository<UserRoleEntity, Integer>, JpaSpecificationExecutor<UserRoleEntity> {
    UserRoleEntity findByUserId(int userId);
}
