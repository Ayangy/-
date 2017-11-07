package com.yuwubao.controllers;

import com.yuwubao.entities.ArticleEntity;
import com.yuwubao.entities.ExpertEntity;
import com.yuwubao.entities.OrganizationEntity;
import com.yuwubao.entities.VideoEntity;
import com.yuwubao.services.ArticleService;
import com.yuwubao.services.ExpertService;
import com.yuwubao.services.OrganizationService;
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

    @Autowired
    private  ExpertService expertService;

    @Autowired
    private OrganizationService organizationService;

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
                                                               @RequestParam(defaultValue = "10", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getHomeArticle(articleType, index, size);
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "暂无数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("首页文章显示异常：", e);
            result.failedApiResponse(Const.FAILED, "首页文章显示异常");
        }
        return result;
    }

    /**
     * 获取视频新闻
     * @param index  第几页
     * @param size 每页几条
     * @return
     */
    @GetMapping("/videoNews")
    public RestApiResponse<List<VideoEntity>> getVideoNews(@RequestParam(defaultValue = "0", required = false) int index,
                                                           @RequestParam(defaultValue = "1", required = false) int size) {
        RestApiResponse<List<VideoEntity>> result = new RestApiResponse<List<VideoEntity>>();
        try {
            List<VideoEntity> list = VideoService.getNewsVideo(index, size);
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "暂无数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
           logger.warn("视频新闻获取异常", e);
           result.failedApiResponse(Const.FAILED, "视频新闻获取异常");
        }
        return result;
    }

    /**
     * 获取文章详情
     * @param id  文章id
     * @return
     */
    @GetMapping("/articleDetails")
    public RestApiResponse<ArticleEntity> findArticleById(@RequestParam int id) {
        RestApiResponse<ArticleEntity> result = new RestApiResponse<ArticleEntity>();
        try {
            ArticleEntity articleEntity = articleService.findById(id);
            if (articleEntity == null) {
                result.failedApiResponse(Const.FAILED, "文章不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, articleEntity);
        } catch (Exception e) {
            logger.warn("文章获取异常", e);
            result.failedApiResponse(Const.FAILED, "文章获取异常");
        }
        return result;
    }

    /**
     * 获取智库专家详情
     * @param id  智库专家id
     * @return
     */
    @GetMapping("/expertDetails")
    public RestApiResponse<ExpertEntity> findExpertById(@RequestParam int id) {
        RestApiResponse<ExpertEntity> result= new RestApiResponse<ExpertEntity>();
        try {
            ExpertEntity expertEntity = expertService.findById(id);
            if (expertEntity == null) {
                result.failedApiResponse(Const.FAILED, "智库专家不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, expertEntity);
        } catch (Exception e) {
            logger.warn("获取智库专家异常", e);
            result.failedApiResponse(Const.FAILED, "获取智库专家异常");
        }
        return result;
    }

    /**
     * 获取所有智库专家
     */
    @GetMapping("/getExpertAll")
    public RestApiResponse<List<ExpertEntity>> findExpertAll() {
        RestApiResponse<List<ExpertEntity>> result = new RestApiResponse<List<ExpertEntity>>();
        try {
            List<ExpertEntity> list = expertService.getAll();
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "暂无数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取所有智库专家异常", e);
            result.failedApiResponse(Const.FAILED, "获取所有智库专家异常");
        }
        return result;
    }

    /**
     * 获取机构简介
     * @param id  机构Id
     * @return
     */
    @GetMapping("/getOrganization")
     public RestApiResponse<OrganizationEntity> getOrganizationEntity(@RequestParam int id) {
        RestApiResponse<OrganizationEntity> result = new RestApiResponse<OrganizationEntity>();
        try {
            OrganizationEntity entity = organizationService.findOne(id);
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "当前机构不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, entity);
        } catch (Exception e) {
            logger.warn("获取机构简介异常：", e);
            result.failedApiResponse(Const.FAILED, "获取机构简介异常");
        }
        return result;
    }
}

