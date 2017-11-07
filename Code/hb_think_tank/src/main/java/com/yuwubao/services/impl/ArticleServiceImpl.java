package com.yuwubao.services.impl;

import com.yuwubao.entities.ArticleEntity;
import com.yuwubao.entities.repository.ArticleRepository;
import com.yuwubao.services.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/20.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<ArticleEntity> findAll(Map<String, String> map, Pageable pageAble, int articleType, int organizationId) {
        String field = map.get("field");
        String keyword = map.get("keyword");
        String start = map.get("beginTime");
        String end = map.get("endTime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Specification<ArticleEntity> spec = new Specification<ArticleEntity>() {
            @Override
            public Predicate toPredicate(Root<ArticleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                Predicate predict = criteriaBuilder.conjunction();
                if (StringUtils.isNotBlank(field)) {
                    Path<String> exp1 = root.get(field);
                    if (StringUtils.isNotBlank(keyword)) {
                        predict.getExpressions().add(criteriaBuilder.like(exp1, "%" + keyword + "%"));
                    }
                }

                if (StringUtils.isNotBlank(start)) {
                    Path<Date> incept = root.get("addTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(incept, dateFormat.parse(start)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if (StringUtils.isNotBlank(end)) {
                    Path<Date> finish = root.get("addTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(finish, dateFormat.parse(end)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (articleType != 0) {
                    Path<Integer> type = root.get("articleType");
                    predict.getExpressions().add(criteriaBuilder.equal(type, articleType));
                }
                if (organizationId != 0) {
                    Path<Integer> oId = root.get("organizationId");
                    predict.getExpressions().add(criteriaBuilder.equal(oId, organizationId));
                }
                return predict;
            }
        };
        return articleRepository.findAll(spec, pageAble);
    }

    @Override
    public ArticleEntity add(ArticleEntity articleEntity) {
        ArticleEntity entity = articleRepository.save(articleEntity);
        return entity;
    }

    @Override
    public ArticleEntity delete(int id) {
        ArticleEntity entity = articleRepository.findOne(id);
        if (entity != null) {
            articleRepository.delete(id);
            return entity;
        }
        return null;
    }

    @Override
    public ArticleEntity update(ArticleEntity articleEntity) {
        ArticleEntity oldEntity = articleRepository.findOne(articleEntity.getId());
        if (oldEntity != null) {
            articleEntity.setAddTime(oldEntity.getAddTime());
            ModelMapper model = new ModelMapper();
            ArticleEntity entity = model.map(articleEntity, ArticleEntity.class);
            return articleRepository.save(entity);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getAll(int pageIndex,int pageSize) {
        String sql = "SELECT * FROM article limit " + pageIndex + ","+ pageSize ;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    @Override
    public List<ArticleEntity> getHomeArticle(int articleType, int index, int size) {
        String sql = "select * from article WHERE articleType = ? order by addTime desc limit ?, ?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, articleType, index, size);
        return list;
    }

    @Override
    public ArticleEntity findById(int id) {
        return articleRepository.findOne(id);

    }

    @Override
    public List<ArticleEntity> findByArticleSort(int type, int parentType) {
        String sql = "SELECT a.id," +
                "a.title," +
                " a.createdDate from article a , article_sort s where a.articleType = s.type";
        if (type != 0) {
            sql += " AND s.type = " + type;
        }
        if (parentType != 0) {
            sql += " AND s.parentType = " + parentType;
        }
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper);
        return list;
    }

}
