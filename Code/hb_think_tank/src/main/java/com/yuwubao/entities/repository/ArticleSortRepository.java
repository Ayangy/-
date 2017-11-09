package com.yuwubao.entities.repository;

import com.yuwubao.entities.ArticleSortEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/10/24.
 */
public interface ArticleSortRepository extends JpaRepository<ArticleSortEntity, Integer>, JpaSpecificationExecutor<ArticleSortEntity> {
    ArticleSortEntity findByType(int type);
}
