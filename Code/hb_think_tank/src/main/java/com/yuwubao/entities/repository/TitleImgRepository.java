package com.yuwubao.entities.repository;

import com.yuwubao.entities.TitleImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by yangyu on 2017/11/8.
 */
public interface TitleImgRepository extends JpaRepository<TitleImgEntity, Integer>, JpaSpecificationExecutor<TitleImgEntity> {
    List<TitleImgEntity> findByState(int state);
}
