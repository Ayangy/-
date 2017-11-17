package com.yuwubao.services;

import com.yuwubao.entities.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/20.
 */
public interface ArticleService {
    Page<ArticleEntity> findAll(Map<String, String> map, Pageable pageAble, int textTypeId, int organizationId);

    ArticleEntity add(ArticleEntity articleEntity);

    ArticleEntity delete(int id);

    ArticleEntity update(ArticleEntity articleEntity);

    List<Map<String, Object>> getAll(int pageIndex,int pageSize);

    List<ArticleEntity> getHomeArticle(int textTypeId, int shield, int index, int size);

    ArticleEntity findById(int id);

    List<ArticleEntity> findByArticleSortAndShield(int textTypeId, int parentId, int shield, int index, int size);

    List<ArticleEntity> findByOrganizationId(int id);
}
