package com.yuwubao.entities.repository;

import com.yuwubao.entities.TerritoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/11/30.
 */
public interface TerritoryRepository extends JpaRepository<TerritoryEntity, Integer>, JpaSpecificationExecutor<TerritoryEntity> {
}
