package com.yuwubao.services.impl;

import com.yuwubao.entities.RoleEntity;
import com.yuwubao.entities.repository.RoleRepository;
import com.yuwubao.services.RoleService;
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
 * Created by yangyu on 2017/10/17.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity add(RoleEntity roleEntity) {
        RoleEntity role = roleRepository.save(roleEntity);
        return role;
    }

    @Override
    public RoleEntity delete(int id) {
        RoleEntity role = roleRepository.findOne(id);
        if (role == null) {
            return null;
        }
        roleRepository.delete(id);
        return role;
    }

    @Override
    public RoleEntity update(RoleEntity roleEntity) {
        ModelMapper mapper = new ModelMapper();
        RoleEntity entity = mapper.map(roleEntity, RoleEntity.class);
        return roleRepository.save(entity);
    }

    @Override
    public Page<RoleEntity> findAll(Map<String, String> map, Pageable pageAble) {
        String field = map.get("field");
        String keyword = map.get("keyword");
        Specification<RoleEntity> spec = new Specification<RoleEntity>() {
            @Override
            public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
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
        return roleRepository.findAll(spec, pageAble);
    }

    @Override
    public List<RoleEntity> getAll() {
        List<RoleEntity> list = roleRepository.findAll();
        return list;
    }

    @Override
    public RoleEntity findOne(int id) {
        return roleRepository.findOne(id);
    }

    @Override
    public RoleEntity findByName(String name) {
        RoleEntity entity = roleRepository.findByName(name);
        return entity;
    }
}
