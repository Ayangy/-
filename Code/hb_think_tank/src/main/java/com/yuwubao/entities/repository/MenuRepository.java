package com.yuwubao.entities.repository;

import com.yuwubao.entities.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Integer>, JpaSpecificationExecutor<MenuEntity> {
}
