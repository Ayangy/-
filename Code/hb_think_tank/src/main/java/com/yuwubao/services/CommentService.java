package com.yuwubao.services;

import com.yuwubao.entities.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by yangyu on 2017/12/21.
 */
public interface CommentService {
    CommentEntity add(CommentEntity comment);

    Page<CommentEntity> findAll(Pageable pageAble, int articleId);

    Page<CommentEntity> getAll(Map<String, String> map, Pageable pageAble);
}
