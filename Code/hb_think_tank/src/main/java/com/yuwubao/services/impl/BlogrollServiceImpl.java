package com.yuwubao.services.impl;

import com.yuwubao.entities.BlogrollEntity;
import com.yuwubao.entities.repository.BlogrollRepository;
import com.yuwubao.services.BlogrollService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/12/10.
 */
@Service
public class BlogrollServiceImpl implements BlogrollService {

    @Autowired
    private BlogrollRepository blogrollRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<BlogrollEntity> findAll(Map<String, String> map, Pageable pageAble) {
        String field = map.get("field");
        String keyword = map.get("keyword");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Specification<BlogrollEntity> spec = new Specification<BlogrollEntity>() {
            @Override
            public Predicate toPredicate(Root<BlogrollEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                Predicate predict = criteriaBuilder.conjunction();
                if (StringUtils.isNotBlank(field)) {
                    Path<String> exp1 = root.get(field);
                    if (StringUtils.isNotBlank(keyword)) {
                        predict.getExpressions().add(criteriaBuilder.like(exp1, "%" + keyword + "%"));
                    }
                }
                return predict;
            }
        };
        return blogrollRepository.findAll(spec, pageAble);
    }

    @Override
    public BlogrollEntity findByName(String name) {
        BlogrollEntity blogrollEntity = blogrollRepository.findByName(name);
        return blogrollEntity;
    }

    @Override
    public BlogrollEntity add(BlogrollEntity blogrollEntity) {
        return blogrollRepository.save(blogrollEntity);
    }

    @Override
    public BlogrollEntity delete(int id) {
        BlogrollEntity entity = blogrollRepository.findOne(id);
        if (entity != null) {
            blogrollRepository.delete(id);
            return entity;
        }
        return null;

    }

    @Override
    public BlogrollEntity findOne(int id) {
        return blogrollRepository.findOne(id);
    }

    @Override
    public List<BlogrollEntity> findByType(int type) {
        List<BlogrollEntity> list = blogrollRepository.findByType(type);
        return list;
    }
}
