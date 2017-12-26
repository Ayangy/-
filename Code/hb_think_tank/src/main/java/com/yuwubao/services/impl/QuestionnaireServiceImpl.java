package com.yuwubao.services.impl;

import com.yuwubao.entities.QuestionnaireEntity;
import com.yuwubao.entities.repository.QuestionnaireRepository;
import com.yuwubao.services.QuestionnaireService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by yangyu on 2017/12/26.
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService{

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Override
    public QuestionnaireEntity findByClientUserId(int clientUserId) {
        QuestionnaireEntity entity = questionnaireRepository.findByClientUserId(clientUserId);
        return entity;
    }

    @Override
    public QuestionnaireEntity save(QuestionnaireEntity questionnaireEntity) {
        return questionnaireRepository.save(questionnaireEntity);
    }

    @Override
    public Page<QuestionnaireEntity> findAll(Map<String, String> map, Pageable pageAble) {
        String articleTitle = map.get("articleTitle");
        String clientUserName = map.get("clientUserName");
        String start = map.get("beginTime");
        String end = map.get("endTime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Specification<QuestionnaireEntity> spec = new Specification<QuestionnaireEntity>() {
            @Override
            public Predicate toPredicate(Root<QuestionnaireEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                Predicate predict = criteriaBuilder.conjunction();
                if (StringUtils.isNotBlank(articleTitle)) {
                    Path<String> exp1 = root.get("articleTitle");
                    predict.getExpressions().add(criteriaBuilder.like(exp1, "%" + articleTitle + "%"));
                }

                if (StringUtils.isNotBlank(clientUserName)) {
                    Path<String> name = root.get("clientUserName");
                    predict.getExpressions().add(criteriaBuilder.like(name, "%" + clientUserName + "%"));
                }

                if (StringUtils.isNotBlank(start)) {
                    Path<Date> incept = root.get("submitTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(incept, dateFormat.parse(start)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if (StringUtils.isNotBlank(end)) {
                    Path<Date> finish = root.get("submitTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(finish, dateFormat.parse(end)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return predict;
            }
        };
        return questionnaireRepository.findAll(spec, pageAble);
    }
}
