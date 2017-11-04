package com.yuwubao.entities.repository;

import com.yuwubao.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/10/23.
 */
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer>, JpaSpecificationExecutor<OrganizationEntity> {
}
