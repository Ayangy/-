package com.yuwubao.services.impl;

import com.yuwubao.entities.CommentEntity;
import com.yuwubao.entities.repository.CommentRepository;
import com.yuwubao.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangyu on 2017/12/21.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public CommentEntity add(CommentEntity comment) {
        return commentRepository.save(comment);
    }
}
