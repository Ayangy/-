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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Override
    public List<OrganizationEntity> finByLetter(String letter, int type) {
        String sql = "select * from organization where get_first_pinyin_char(name) = ? AND type = ? AND shield = 0";
        RowMapper<OrganizationEntity> rowMapper = new BeanPropertyRowMapper<>(OrganizationEntity.class);
        List<OrganizationEntity> list = jdbcTemplate.query(sql, rowMapper, letter, type);
        return list;
    }

    @Override
    public List<OrganizationEntity> finByNameAndTypeAndShield(String query, int type, int shield){
        String sql = "SELECT * FROM organization where type=? AND shield = ?";
        if (StringUtils.isNotBlank(query)) {
            sql = sql + " and `name` LIKE  '%" + query + "%'";
        }
        RowMapper<OrganizationEntity> rowMapper = new BeanPropertyRowMapper<>(OrganizationEntity.class);
        List<OrganizationEntity> list = jdbcTemplate.query(sql, rowMapper, type, shield);
        return list;
    }

    @Override
    public List<OrganizationEntity> findByName(String name) {
        String sql = "SELECT * FROM organization where `name` = ?";
        RowMapper<OrganizationEntity> rowMapper = new BeanPropertyRowMapper<>(OrganizationEntity.class);
        List<OrganizationEntity> list = jdbcTemplate.query(sql, rowMapper, name);
        return list;
    }

    @Override
    public List<OrganizationEntity> findByCondition(Map<String, String> map, int organizationType, int type) {
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
                if (organizationType != 0) {
                    Path<Integer> path = root.get("organizationType");
                    predict.getExpressions().add(criteriaBuilder.equal(path, String.valueOf(organizationType)));
                }
                Path<Integer> path = root.get("type");
                predict.getExpressions().add(criteriaBuilder.equal(path, String.valueOf(type)));
                Path<Integer> path1 = root.get("shield");
                predict.getExpressions().add(criteriaBuilder.equal(path1, String.valueOf(0)));
                return predict;
            }
        };
        return organizationRepository.findAll(spec);
    }

    @Override
    public List<OrganizationEntity> findByShield(int shield) {
        List<OrganizationEntity> list = organizationRepository.findByShield(shield);
        return list;
    }

    @Override
    public OrganizationEntity findByType(int i) {
        return organizationRepository.findByType(i);
    }

    @Override
    public List<OrganizationEntity> findByShieldAndType(int shield, int type) {
        List<OrganizationEntity> list = organizationRepository.findByShieldAndType(shield, type);
        return list;
    }

    @Override
    public List<OrganizationEntity> findByOrganizationType(int type, int organizationType) {
        String sql = "select * from organization where type = ? AND organizationType = ? AND shield = 0";
        RowMapper<OrganizationEntity> rowMapper = new BeanPropertyRowMapper<>(OrganizationEntity.class);
        List<OrganizationEntity> list = jdbcTemplate.query(sql, rowMapper, type, organizationType);
        return list;
    }
}
