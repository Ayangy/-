package com.yuwubao.entities.repository;

import com.yuwubao.entities.QuestionnaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yangyu on 2017/12/26.
 */
public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity, Integer>, JpaSpecificationExecutor<QuestionnaireEntity> {
    QuestionnaireEntity findByClientUserId(int clientUserId);
}
