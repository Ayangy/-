package com.yuwubao.services;

import com.yuwubao.entities.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yangyu on 2017/12/21.
 */
public interface CommentService {
    CommentEntity add(CommentEntity comment);

    Page<CommentEntity> findAll(Pageable pageAble, int articleId);
}
