package com.yuwubao.services.impl;

import com.yuwubao.entities.UserEntity;
import com.yuwubao.entities.repository.UserRepository;
import com.yuwubao.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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
 * Created by yangyu on 2017/10/17.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity add(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Page<UserEntity> findAll(Map<String, String> map, Pageable pageAble) {
        String field = map.get("field");
        String keyword = map.get("keyword");
        String start = map.get("beginTime");
        String end = map.get("endTime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Specification<UserEntity> spec = new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                Predicate predict = criteriaBuilder.conjunction();
                if (StringUtils.isNotBlank(field)) {
                    Path<String> exp1 = root.get(field);
                    if (StringUtils.isNotBlank(keyword)) {
                        predict.getExpressions().add(criteriaBuilder.like(exp1, "%" + keyword + "%"));
                    }
                }

                if (StringUtils.isNotBlank(start)) {
                    Path<Date> incept = root.get("createTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(incept, dateFormat.parse(start)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if (StringUtils.isNotBlank(end)) {
                    Path<Date> finish = root.get("createTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(finish, dateFormat.parse(end)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return predict;
            }
        };
       return userRepository.findAll(spec, pageAble);
    }

    @Override
    public UserEntity findByUsernameAndPassword(String username, String password, int type) {
        return userRepository.findByUsernameAndPasswordAndType(username, password, type);
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public UserEntity findByUsernameAndType(String username, int type) {
        return userRepository.findByUsernameAndType(username, type);
    }

    @Override
    public UserEntity findById(int userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public UserEntity delete(int id) {
        UserEntity user = userRepository.findOne(id);
        if (user == null) {
            return null;
        }
        userRepository.delete(id);
        return user;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        ModelMapper mapper = new ModelMapper();
        UserEntity entity = mapper.map(userEntity, UserEntity.class);
        return userRepository.save(entity);
    }

}
