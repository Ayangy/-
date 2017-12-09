package com.yuwubao.entities.repository;

import com.yuwubao.entities.ArticleSortEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by yangyu on 2017/10/24.
 */
public interface ArticleSortRepository extends JpaRepository<ArticleSortEntity, Integer>, JpaSpecificationExecutor<ArticleSortEntity> {
    List<ArticleSortEntity> findByParentId(int parentId);
}
