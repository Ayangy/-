package com.yuwubao.entities.repository;

import com.yuwubao.entities.RoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity, Integer>, JpaSpecificationExecutor<RoleMenuEntity> {
    void deleteByRoleId(int roleId);
}
