package com.yuwubao.services;

import com.yuwubao.entities.BlogrollEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/12/10.
 */
public interface BlogrollService {
    Page<BlogrollEntity> findAll(Map<String, String> map, Pageable pageAble);

    BlogrollEntity findByName(String name);

    BlogrollEntity add(BlogrollEntity blogrollEntity);

    BlogrollEntity delete(int id);

    BlogrollEntity findOne(int id);

    List<BlogrollEntity> findByType(int type);
}
