package com.yuwubao.entities.repository;

import com.yuwubao.entities.BlogrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by yangyu on 2017/12/10.
 */
public interface BlogrollRepository extends JpaRepository<BlogrollEntity, Integer>, JpaSpecificationExecutor<BlogrollEntity> {
    BlogrollEntity findByName(String name);

    List<BlogrollEntity> findByType(int type);
}
