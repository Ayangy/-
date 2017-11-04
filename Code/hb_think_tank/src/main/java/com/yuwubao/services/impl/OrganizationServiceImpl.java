package com.yuwubao.services.impl;

import com.yuwubao.entities.OrganizationEntity;
import com.yuwubao.entities.repository.OrganizationRepository;
import com.yuwubao.services.OrganizationService;
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
 * Created by yangyu on 2017/10/23.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Page<OrganizationEntity> findAll(Map<String, String> map, Pageable pageAble) {
        String field = map.get("field");
        String keyword = map.get("keyword");
        Specification<OrganizationEntity> spec = new Specification<OrganizationEntity>() {
            @Override
            public Predicate toPredicate(Root<OrganizationEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
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
        return organizationRepository.findAll(spec, pageAble);
    }

    @Override
    public OrganizationEntity add(OrganizationEntity organizationEntity) {
        return organizationRepository.save(organizationEntity);
    }

    @Override
    public OrganizationEntity delete(int id) {
        OrganizationEntity entity = organizationRepository.findOne(id);
        if (entity != null) {
            organizationRepository.delete(id);
            return entity;
        }
        return null;
    }

    @Override
    public OrganizationEntity update(OrganizationEntity organizationEntity) {
        ModelMapper mapper = new ModelMapper();
        OrganizationEntity entity = mapper.map(organizationEntity, OrganizationEntity.class);
        return organizationRepository.save(entity);
    }

    @Override
    public List<OrganizationEntity> getAll() {
        ModelMapper mapper = new ModelMapper();
        Specification<OrganizationEntity> spec = new Specification<OrganizationEntity>() {
            @Override
            public Predicate toPredicate(Root<OrganizationEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predict = criteriaBuilder.conjunction();
                return predict;
            }
        };
        List<OrganizationEntity> list = organizationRepository.findAll(spec);
        return list;
    }

    @Override
    public OrganizationEntity findOne(int id) {
        return organizationRepository.findOne(id);
    }
}
