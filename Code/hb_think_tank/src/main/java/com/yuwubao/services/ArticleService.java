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

    List<ArticleEntity> findByCriteria(Map<String, String> map, int parentId, int sort, int index, int size);

    List<ArticleEntity> getKind(int textTypeId, int index, int size);

    List<ArticleEntity> getRecommendArticle(int recommend, int index, int size);

    List<ArticleEntity> getNewestArticle(int index, int size);

    ArticleEntity getAnOrganizationNotice(int id, int textTypeId);

    List<ArticleEntity> getOrganizationActivity(int id, int textTypeId, int index, int size);

    List<ArticleEntity> findByTextTypeId(int id);

    List<ArticleEntity> findByTextTypeIdAndShield(int textTypeId, int shield);

    List<ArticleEntity> findOrganizationArticle(int textTypeId, int organizationId, int index, int size, int i);

    List<ArticleEntity> getDatabase(int index, int size, String keyword, String beginTime, String endTime);

    List<ArticleEntity> findByString(String query, int textTypeId, int index, int size);
}
