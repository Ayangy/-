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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端首页数据
 * Created by yangyu on 2017/11/6.
 */
@RestController
@RequestMapping("/frontEnd")
@CrossOrigin
public class FrontEndController {

    private final static Logger logger = LoggerFactory.getLogger(FrontEndController.class);

    private final static int shield = 0;//未屏蔽数据

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private VideoService videoService;

    /**
     * 获取未屏蔽的最新文章
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
            List<ArticleEntity> list = articleService.getHomeArticle(articleType, shield, index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("首页文章显示异常", e);
            result.failedApiResponse(Const.FAILED, "首页文章显示异常");
        }
        return result;
    }

    /**
     * 通过拼音首字母查询专家
     * @param letter 字母
     * @return
     */
    @GetMapping("/findExpertByLetter")
    public RestApiResponse<List<ExpertEntity>> findExpertByLetter(@RequestParam String letter) {
        RestApiResponse<List<ExpertEntity>> result = new RestApiResponse<List<ExpertEntity>>();
        try {
            List<ExpertEntity> list = expertService.findExpertByLetter(letter);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("通过字母查询专家异常", e);
            result.failedApiResponse(Const.FAILED, "通过首字母查询专家异常");
        }
        return result;
    }

    /**
     * 通过首字母查询未屏蔽机构
     * @param type  机构类型
     * @param letter  查询字母
     * @return
     */
    @GetMapping("/findOrganizationByLetter")
    public RestApiResponse<List<OrganizationEntity>> findByLetter(@RequestParam(defaultValue = "0", required = false) int type,
                                                                  @RequestParam String letter) {
        RestApiResponse<List<OrganizationEntity>> result = new RestApiResponse<List<OrganizationEntity>>();
        try {
            List<OrganizationEntity> list = organizationService.finByLetter(type, letter, shield);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("通过字母查询异常", e);
            result.failedApiResponse(Const.FAILED, "通过字母查询异常");
        }
        return result;
    }

    /**
     * 模糊查询未屏蔽机构名
     * @param query 机构名
     * @param type  机构类型
     * @return
     */
    @GetMapping("/findOrganizationByName")
    public RestApiResponse<List<OrganizationEntity>> findByName(@RequestParam String query,
                                                                @RequestParam(defaultValue = "0", required = false) int type) {
        RestApiResponse<List<OrganizationEntity>> result = new RestApiResponse<List<OrganizationEntity>>();
        try {
            List<OrganizationEntity> list= organizationService.finByNameAndTypeAndShield(query, type, shield);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("机构名模糊查询异常", e);
            result.failedApiResponse(Const.FAILED, "机构名模糊查询异常");
        }
        return result;
    }

    /**
     * 获取所有智库专家
     */
    @GetMapping("/getNotShieldExpert")
    public RestApiResponse<List<ExpertEntity>> findExpertAll() {
        RestApiResponse<List<ExpertEntity>> result = new RestApiResponse<List<ExpertEntity>>();
        try {
            List<ExpertEntity> list = expertService.findByShield(shield);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取所有智库专家异常", e);
            result.failedApiResponse(Const.FAILED, "获取所有智库专家异常");
        }
        return result;
    }

    /**
     * 获取最新的未屏蔽视频新闻
     * @param index  第几页
     * @param size 每页几条
     * @return
     */
    @GetMapping("/findVideoNews")
    public RestApiResponse<List<VideoEntity>> getVideoNews(@RequestParam(defaultValue = "0", required = false) int index,
                                                           @RequestParam(defaultValue = "1", required = false) int size) {
        RestApiResponse<List<VideoEntity>> result = new RestApiResponse<List<VideoEntity>>();
        try {
            List<VideoEntity> list = videoService.getNewsVideo(index, size, shield);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("视频新闻获取异常", e);
            result.failedApiResponse(Const.FAILED, "视频新闻获取异常");
        }
        return result;
    }
}

