package com.yuwubao.services;

import com.yuwubao.entities.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface RoleService {
    RoleEntity add(RoleEntity roleEntity);

    RoleEntity delete(int id);

    RoleEntity update(RoleEntity roleEntity);

    Page<RoleEntity> findAll(Map<String, String> map, Pageable pageAble);

    List<RoleEntity> getAll();

    RoleEntity findOne(int id);

    RoleEntity findByName(String name);
}
