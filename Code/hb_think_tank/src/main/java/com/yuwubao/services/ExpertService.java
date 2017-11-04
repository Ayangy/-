package com.yuwubao.services;

import com.yuwubao.entities.ExpertEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by yangyu on 2017/11/3.
 */
public interface ExpertService {
    Page<ExpertEntity> findAll(Map<String, String> map, Pageable pageAble);

    ExpertEntity add(ExpertEntity expertEntity);

    ExpertEntity delete(int id);

    ExpertEntity findOne(int id);

    ExpertEntity update(ExpertEntity expertEntity);
}
