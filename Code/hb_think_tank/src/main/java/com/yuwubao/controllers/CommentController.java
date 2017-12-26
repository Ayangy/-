package com.yuwubao.controllers;

import com.yuwubao.entities.CommentEntity;
import com.yuwubao.services.CommentService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyu on 2017/12/26.
 */
@RestController
@RequestMapping("/comment")
@Transactional
@CrossOrigin
public class CommentController {

    private final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     * @param index  第几页
     * @param size  每页几条
     * @param articleTitle  文章标题
     * @param clientUserName  前端用户名
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<CommentEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                                        @RequestParam(defaultValue = "10", required = false)int size,
                                                        @RequestParam(required = false, defaultValue = "") String articleTitle,
                                                        @RequestParam(required = false, defaultValue = "")String clientUserName) {
        RestApiResponse<Page<CommentEntity>> result = new RestApiResponse<Page<CommentEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("articleTitle", articleTitle);
            map.put("clientUserName", clientUserName);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<CommentEntity> list = commentService.getAll(map, pageAble);
            result.successResponse(Const.SUCCESS, list, "获取列表成功");
        } catch (Exception e) {
            logger.warn("评论列表查询异常", e);
            result.failedApiResponse(Const.FAILED, "评论列表查询异常");
        }
        return result;
    }


}
