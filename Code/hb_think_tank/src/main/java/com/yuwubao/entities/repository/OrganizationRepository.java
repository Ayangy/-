package com.yuwubao.entities.repository;

import com.yuwubao.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by yangyu on 2017/10/23.
 */
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer>, JpaSpecificationExecutor<OrganizationEntity> {
    OrganizationEntity findByName(String name);

    List<OrganizationEntity> findByShield(int shield);

    OrganizationEntity findByType(int i);

    List<OrganizationEntity> findByShieldAndType(int shield, int type);
}
