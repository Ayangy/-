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
    public Page<ArticleEntity> findAll(Map<String, String> map, Pageable pageAble, int textTypeId, int organizationId) {
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
                if (textTypeId != 0) {
                    Path<Integer> type = root.get("textTypeId");
                    predict.getExpressions().add(criteriaBuilder.equal(type, textTypeId));
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
    public List<ArticleEntity> getHomeArticle(int textTypeId, int shield, int index, int size) {
        String sql = "select * from article WHERE textTypeId = ? AND shield = ? order by addTime desc limit ?, ?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, textTypeId, shield, index, size);
        return list;
    }

    @Override
    public ArticleEntity findById(int id) {
        return articleRepository.findOne(id);

    }

    @Override
    public List<ArticleEntity> findByArticleSortAndShield(int textTypeId, int parentId, int shield, int index, int size) {
        String sql = "SELECT a.id," +
                "a.title," +
                " a.createdDate from article a , article_sort s where a.textTypeId = s.id AND a.shield = ?";
        if (textTypeId != 0) {
            sql += " AND a.textTypeId = " + String.valueOf(textTypeId);
        }
        if (parentId != 0) {
            sql += " AND s.parentId = " + String.valueOf(parentId);
        }
        sql += " order by a.createdDate desc LIMIT ?, ?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, shield, index, size);
        return list;
    }

    @Override
    public List<ArticleEntity> findByOrganizationId(int id) {
        List<ArticleEntity> list = articleRepository.findByOrganizationId(id);
        return list;
    }

    @Override
    public List<ArticleEntity> findByCriteria(Map<String, String> map, int textTypeId, int sort, int index, int size) {
        String keyword = map.get("keyword");
        String sql = "SELECT * from article WHERE shield = 0";
        if (textTypeId != 0) {
            sql += " AND textTypeId = " + textTypeId;
        }
        if (StringUtils.isNotBlank(keyword)) {
            sql += " and ( title LIKE '%" + keyword + "%'" +
                    "OR author LIKE '%" + keyword +"%'" +
                    "OR content LIKE '%" + keyword + "%'" +
                    ")";
        }

        if (sort == 0) {
            sql += " ORDER BY addTime DESC";
        } else {
            sql += " ORDER BY addTime";
        }
        sql += " limit ?, ?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, index, size);
        return list;
    }

    @Override
    public List<ArticleEntity> getKind(int textTypeId, int index, int size) {
        String sql = "SELECT * FROM article WHERE textTypeId = ? AND shield = 0 LIMIT ?,?;";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, textTypeId, index, size);
        return list;
    }

    @Override
    public List<ArticleEntity> getRecommendArticle(int recommend, int index, int size) {
        String sql = "SELECT * from article WHERE recommend = ? AND shield = 0 LIMIT ?,?;";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, recommend, index, size);
        return list;
    }

    @Override
    public List<ArticleEntity> getNewestArticle(int index, int size) {
        String sql = "SELECT * from article WHERE shield = 0 ORDER BY addTime DESC LIMIT ?,?;";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, index, size);
        return list;
    }

    @Override
    public ArticleEntity getAnOrganizationNotice(int id, int textTypeId) {
        String sql = "SELECT * from article WHERE organizationId = ? AND textTypeId = ? AND shield = 0 ORDER BY addTime DESC LIMIT 0,1";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, id, textTypeId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<ArticleEntity> getOrganizationActivity(int id, int textTypeId, int index, int size) {
        String sql = "SELECT * from article WHERE organizationId = ? AND textTypeId = ? AND shield = 0 ORDER BY addTime DESC LIMIT ?,?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, id, textTypeId, index, size);
        return list;
    }

    @Override
    public List<ArticleEntity> findByTextTypeId(int id) {
        List<ArticleEntity> list = articleRepository.findByTextTypeId(id);
        return list;
    }

    @Override
    public List<ArticleEntity> findByTextTypeIdAndShield(int textTypeId, int shield) {
        String sql = "select a.id," +
                "a.title," +
                "a.author," +
                "a.createdDate," +
                "a.content," +
                "a.imgUrl," +
                "a.imgState," +
                "a.textTypeId," +
                "a.organizationId," +
                "a.addTime," +
                "a.shield," +
                "a.recommend from article a, article_sort s where a.textTypeId = s.id and s.parentId = ? AND a.shield = ?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, textTypeId, shield);
        return list;
    }

    @Override
    public List<ArticleEntity> findOrganizationArticle(int textTypeId, int organizationId, int index, int size, int shield) {
        String sql = "select a.id," +
                "a.title," +
                "a.author," +
                "a.createdDate," +
                "a.content," +
                "a.imgUrl," +
                "a.imgState," +
                "a.textTypeId," +
                "a.organizationId," +
                "a.addTime," +
                "a.shield," +
                "a.recommend from article a, article_sort s where a.textTypeId = s.id and a.textTypeId = ? AND a.shield = ? AND a.organizationId = ? ORDER BY a.addTime Desc limit ?,?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, textTypeId, shield, organizationId, index, size);
        return list;
    }

    @Override
    public List<ArticleEntity> getDatabase(int index, int size, String keyword, String beginTime, String endTime) {
        String sql = "SELECT * FROM article a, article_sort s WHERE a.textTypeId = s.id AND s.parentId = 25 ";
        if (StringUtils.isNotBlank(beginTime)) {
            sql += " AND a.addTime > " + beginTime;
            if (StringUtils.isNotBlank(endTime)) {
                sql += " AND a.addTime <" + endTime;
            }
        }
        if (StringUtils.isNotBlank(keyword)) {
            sql += " and ( title LIKE '%" + keyword + "%'" +
                    "OR author LIKE '%" + keyword +"%'" +
                    "OR content LIKE '%" + keyword + "%'" +
                    ")";
        }
        sql += " limit ?, ?";
        RowMapper<ArticleEntity> rowMapper = new BeanPropertyRowMapper<>(ArticleEntity.class);
        List<ArticleEntity> list = jdbcTemplate.query(sql, rowMapper, index, size);
        return list;
    }

}
