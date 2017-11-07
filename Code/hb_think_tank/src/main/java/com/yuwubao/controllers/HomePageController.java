package com.yuwubao.controllers;

import com.yuwubao.entities.ArticleEntity;
import com.yuwubao.services.ArticleService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前端首页数据
 * Created by yangyu on 2017/11/6.
 */
@RestController
@RequestMapping("/homePage")
public class HomePageController {

    private final static Logger logger = LoggerFactory.getLogger(HomePageController.class);

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章
     * @param articleType  文章类型
     * @param index  第几页
     * @param size  每页几条
     * @return
     */
    @GetMapping("/article")
    public RestApiResponse<List<ArticleEntity>> getHomeArticle(@RequestParam int articleType,
                                                               @RequestParam(defaultValue = "0", required = false) int index,
                                                               @RequestParam(defaultValue = "6", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getHomeArticle(articleType, index, size);
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "暂无数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("首页文章显示异常", e);
            result.failedApiResponse(Const.FAILED, "首页文章显示异常");
        }
        return result;
    }



}

