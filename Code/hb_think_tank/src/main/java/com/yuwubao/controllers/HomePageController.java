package com.yuwubao.controllers;

import com.yuwubao.entities.ArticleEntity;
import com.yuwubao.entities.VideoEntity;
import com.yuwubao.services.ArticleService;
import com.yuwubao.services.VideoService;
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

    @Autowired
    private VideoService VideoService;

    /**
     * 首页新闻
     * @param articleType 新闻类型
     * @return
     */
    @GetMapping("/slideshow")
    public RestApiResponse<List<ArticleEntity>> getHomeArticle(@RequestParam int articleType,
                                                               @RequestParam(defaultValue = "0", required = false) int index,
                                                               @RequestParam(defaultValue = "10", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getHomeArticle(articleType, index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("首页文章显示异常：", e);
            result.failedApiResponse(Const.FAILED, "首页文章显示异常");
        }
        return result;
    }

    /**
     * 首页视频新闻
     */
    @GetMapping("/videoNews")
    public RestApiResponse<VideoEntity> getVideoNews() {
        RestApiResponse<VideoEntity> result = new RestApiResponse<VideoEntity>();
        try {
            VideoEntity videoEntity = VideoService.getNewsVideo();
            result.successResponse(Const.SUCCESS, videoEntity);
        } catch (Exception e) {
           logger.warn("视频新闻获取异常", e);
           result.failedApiResponse(Const.FAILED, "视频新闻获取异常");
        }
        return result;
    }



}
