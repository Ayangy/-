package com.yuwubao.services;

import com.yuwubao.entities.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/23.
 */
public interface OrganizationService {
    Page<OrganizationEntity> findAll(Map<String, String> map, Pageable pageAble);

    OrganizationEntity add(OrganizationEntity organizationEntity);

    OrganizationEntity delete(int id);

    OrganizationEntity update(OrganizationEntity organizationEntity);


    List<OrganizationEntity> getAll();

    OrganizationEntity findOne(int id);

    List<OrganizationEntity> finByLetter(String letter, int type);

    List<OrganizationEntity> finByNameAndTypeAndShield(String query, int type, int shield);

    OrganizationEntity findByName(String name);

    List<OrganizationEntity> findByCondition(Map<String, String> map, int type);
}
