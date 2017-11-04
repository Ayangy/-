package com.yuwubao.services.impl;

import com.yuwubao.entities.ArticleSortEntity;
import com.yuwubao.entities.repository.ArticleSortRepository;
import com.yuwubao.services.ArticleSortService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/24.
 */
@Service
public class ArticleSortServiceImpl implements ArticleSortService {

    @Autowired
    private ArticleSortRepository articleSortRepository;

    @Override
    public Page<ArticleSortEntity> findAll(Map<String, String> map, Pageable pageAble) {
        String field = map.get("field");
        String keyword = map.get("keyword");
        Specification<ArticleSortEntity> spec = new Specification<ArticleSortEntity>() {
            @Override
            public Predicate toPredicate(Root<ArticleSortEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
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
        return articleSortRepository.findAll(spec, pageAble);
    }

    @Override
    public ArticleSortEntity add(ArticleSortEntity articleSortEntity) {
        return articleSortRepository.save(articleSortEntity);
    }

    @Override
    public ArticleSortEntity delete(int id) {
        ArticleSortEntity entity = articleSortRepository.findOne(id);
        if (entity != null) {
            articleSortRepository.delete(id);
            return entity;
        }
        return null;
    }

    @Override
    public ArticleSortEntity update(ArticleSortEntity articleSortEntity) {
        ModelMapper mapper = new ModelMapper();
        ArticleSortEntity articleSort = mapper.map(articleSortEntity, ArticleSortEntity.class);
        return articleSortRepository.save(articleSort);
    }

    @Override
    public List<ArticleSortEntity> getAll() {
        ModelMapper mapper = new ModelMapper();
        Specification<ArticleSortEntity> spec = new Specification<ArticleSortEntity>() {
            @Override
            public Predicate toPredicate(Root<ArticleSortEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predict = criteriaBuilder.conjunction();
                return predict;
            }
        };
        List<ArticleSortEntity> list = articleSortRepository.findAll(spec);
        return list;
    }
}
