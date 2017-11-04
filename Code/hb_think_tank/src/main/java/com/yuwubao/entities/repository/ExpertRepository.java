package com.yuwubao.entities.repository;

import com.yuwubao.entities.ExpertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/11/3.
 */
public interface ExpertRepository extends JpaRepository<ExpertEntity, Integer>, JpaSpecificationExecutor<ExpertEntity> {
}
