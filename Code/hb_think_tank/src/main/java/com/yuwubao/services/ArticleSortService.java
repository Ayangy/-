package com.yuwubao.services;

import com.yuwubao.entities.ArticleSortEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/24.
 */
public interface ArticleSortService {
    Page<ArticleSortEntity> findAll(Map<String, String> map, Pageable pageAble);

    ArticleSortEntity add(ArticleSortEntity articleSortEntity);

    ArticleSortEntity delete(int id);

    ArticleSortEntity update(ArticleSortEntity articleSortEntity);

    List<ArticleSortEntity> getAll();

    ArticleSortEntity findByType(int type);
}
