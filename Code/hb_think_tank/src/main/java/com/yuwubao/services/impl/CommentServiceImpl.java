package com.yuwubao.services.impl;

import com.yuwubao.entities.CommentEntity;
import com.yuwubao.entities.repository.CommentRepository;
import com.yuwubao.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;

/**
 * Created by yangyu on 2017/12/21.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public CommentEntity add(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Page<CommentEntity> findAll(Pageable pageAble, int articleId) {
        Specification<CommentEntity> spec = new Specification<CommentEntity>() {
            @Override
            public Predicate toPredicate(Root<CommentEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                Predicate predict = criteriaBuilder.conjunction();
                if (articleId != 0) {
                    Path<Integer> type = root.get("articleId");
                    predict.getExpressions().add(criteriaBuilder.equal(type, articleId));
                }
                return predict;
            }
        };
        return commentRepository.findAll(spec, pageAble);
    }
}
