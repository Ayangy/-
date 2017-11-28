package com.yuwubao.controllers;

import com.yuwubao.entities.*;
import com.yuwubao.services.*;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import com.yuwubao.util.ThinkTankUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 获取未屏蔽的最新文章
     * @param textTypeId  文章类型
     * @param index  第几页
     * @param size  每页几条
     * @return
     */
    @GetMapping("/article")
    public RestApiResponse<List<ArticleEntity>> getHomeArticle(@RequestParam int textTypeId,
                                                               @RequestParam(defaultValue = "0", required = false) int index,
                                                               @RequestParam(defaultValue = "6", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getHomeArticle(textTypeId, shield, index, size);
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
    public RestApiResponse<Map<String, List<ExpertEntity>>> findExpertByLetter(@RequestParam(defaultValue = "", required = false) String letter,
                                                                               @RequestParam(defaultValue = "0", required = false) int type) {
        RestApiResponse<Map<String, List<ExpertEntity>>> result = new RestApiResponse<Map<String, List<ExpertEntity>>>();
        Map<String, List<ExpertEntity>> map = new HashMap<String, List<ExpertEntity>>();
        try {
            if (!StringUtils.isNotBlank(letter)) {
                String alphabet;
                for (char i = 'A' ; i<= 'Z'; i++ ){
                    alphabet = String.valueOf(i);
                    List<ExpertEntity> entityList = expertService.findExpertByLetter(alphabet, type);
                    map.put(alphabet,entityList);
                }
                result.successResponse(Const.SUCCESS, map);
                return result;
            }
            List<ExpertEntity> entities = expertService.findExpertByLetter(letter, type);
            map.put(letter, entities);
            result.successResponse(Const.SUCCESS, map);
        } catch (Exception e) {
            logger.warn("通过字母查询专家异常", e);
            result.failedApiResponse(Const.FAILED, "通过首字母查询专家异常");
        }
        return result;
    }

    /**
     * 查询专家详情
     * @param id  专家id
     * @return
     */
    @GetMapping("/queryAnExpert")
    public RestApiResponse<List<ExpertEntity>> queryAnExpert(@RequestParam int id){
        RestApiResponse<List<ExpertEntity>> result = new RestApiResponse<List<ExpertEntity>>();
        try {
            List<ExpertEntity> entity = expertService.queryAnExpert(id);
            if (entity.size() == 0) {
                result.failedApiResponse(Const.FAILED, "专家不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, entity);
        } catch (Exception e) {
            logger.warn("查询专家异常", e);
            result.failedApiResponse(Const.FAILED, "查询专家异常");
        }
        return result;
    }

    /**
     * 条件查询专家
     * @param field  查询字段
     * @param keyword  查询值
     * @return
     */
    @GetMapping("/findExpertByCondition")
    public RestApiResponse<Map<String, List<ExpertEntity>>> findExpertByCondition(@RequestParam(required = false, defaultValue = "")String field,
                                                       @RequestParam(required = false, defaultValue = "")String keyword){
        RestApiResponse<Map<String, List<ExpertEntity>>> result = new RestApiResponse<Map<String, List<ExpertEntity>>>();
        Map<String, List<ExpertEntity>> endResult = new HashMap<String, List<ExpertEntity>>();
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            List<ExpertEntity> list = expertService.findExpertByCondition(map);
            List<String> letter = new ArrayList<String>();
            for (ExpertEntity entity : list) {
                String substring = entity.getName().substring(0, 1);
                String pyIndexStr = ThinkTankUtil.getPYIndexStr(substring, true);
                letter.add(pyIndexStr);
            }

            for (String s : letter) {
                List<ExpertEntity> entities = new ArrayList<ExpertEntity>();
                for (ExpertEntity expertEntity: list) {
                    String substring = expertEntity.getName().substring(0, 1);
                    String pyIndexStr = ThinkTankUtil.getPYIndexStr(substring, true);
                    if (s.equals(pyIndexStr)) {
                        entities.add(expertEntity);
                    }
                }
                endResult.put(s, entities);
            }
            result.successResponse(Const.SUCCESS, endResult);
        } catch (Exception e) {
            logger.warn("查询专家列表异常", e);
            result.failedApiResponse(Const.FAILED, "专家列表查询异常");
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
    public RestApiResponse<Map<String, List<OrganizationEntity>>> findByLetter(@RequestParam(defaultValue = "0", required = false) int type,
                                                                  @RequestParam(defaultValue = "", required = false) String letter) {
        RestApiResponse<Map<String, List<OrganizationEntity>>> result = new RestApiResponse<Map<String, List<OrganizationEntity>>>();
        Map<String, List<OrganizationEntity>> map = new HashMap<String, List<OrganizationEntity>>();
        try {
            if (!StringUtils.isNotBlank(letter)) {
                String alphabet;
                for (char i = 'A' ; i<= 'Z'; i++ ){
                    alphabet = String.valueOf(i);
                    List<OrganizationEntity> list = organizationService.finByLetter(alphabet, type);
                    map.put(alphabet,list);
                }
                result.successResponse(Const.SUCCESS, map);
                return result;
            }
            List<OrganizationEntity> entities = organizationService.finByLetter(letter, type);
            map.put(letter, entities);
            result.successResponse(Const.SUCCESS, map);
        } catch (Exception e) {
            logger.warn("通过字母查询异常", e);
            result.failedApiResponse(Const.FAILED, "通过字母查询异常");
        }
        return result;
    }

    /**
     * 模糊查询未屏蔽机构名
     * @param field 查询字段
     * @param keyword  查询值
     * @param type  机构类型0(智库机构)，1(智库联盟)
     * @return
     */
    @GetMapping("/findOrganizationByName")
    public RestApiResponse<Map<String, List<OrganizationEntity>>> findByName(@RequestParam(required = false, defaultValue = "")String field,
                                                                @RequestParam(required = false, defaultValue = "")String keyword,
                                                                @RequestParam(defaultValue = "0", required = false) int type) {
        RestApiResponse<Map<String, List<OrganizationEntity>>> result = new RestApiResponse<Map<String, List<OrganizationEntity>>>();
        Map<String, List<OrganizationEntity>> endResult = new HashMap<String, List<OrganizationEntity>>();
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            List<OrganizationEntity> list = organizationService.findByCondition(map, type);
            List<String> letter = new ArrayList<String>();
            for (OrganizationEntity entity : list) {
                String substring = entity.getName().substring(0, 1);
                String pyIndexStr = ThinkTankUtil.getPYIndexStr(substring, true);
                letter.add(pyIndexStr);
            }

            for (String s : letter) {
                List<OrganizationEntity> entities = new ArrayList<OrganizationEntity>();
                for (OrganizationEntity organizationEntity: list) {
                    String substring = organizationEntity.getName().substring(0, 1);
                    String pyIndexStr = ThinkTankUtil.getPYIndexStr(substring, true);
                    if (s.equals(pyIndexStr)) {
                        entities.add(organizationEntity);
                    }
                }
                endResult.put(s, entities);
            }
            result.successResponse(Const.SUCCESS, endResult);
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

    /**
     * 分类查询未屏蔽文章
     * @param textTypeId  文章类型
     * @param parentId  父分类
     * @param index  第几页
     * @param size  每页几条
     * @return
     */
    @GetMapping("/articleSort")
    public RestApiResponse<List<ArticleEntity>> articleSort(@RequestParam int textTypeId, @RequestParam int parentId,
                                                            @RequestParam(defaultValue = "0", required = false) int index,
                                                            @RequestParam(defaultValue = "10", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.findByArticleSortAndShield(textTypeId, parentId,shield, index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("文章分类查询异常", e);
            result.failedApiResponse(Const.FAILED, "文章分类查询异常");
        }
        return result;
    }

    /**
     * 智库成果多条件查询
     * @param index  第几页
     * @param size  每页几条
     * @param field  查询字段
     * @param keyword  查询值
     * @param textTypeId  文章类型
     * @param parentId  父级文章类型
     * @param timeHorizon  时间范围 1(三天内),2(一周内),3(一月内),4(半年内),5(一年内)
     * @param sort  排序 0(降序),1(升序)
     * @return
     */
    @GetMapping("/findResultByCriteria")
    public RestApiResponse<List<ArticleEntity>> findResultByCriteria(@RequestParam(defaultValue = "0", required = false)int index,
                                                          @RequestParam(defaultValue = "10", required = false)int size,
                                                          @RequestParam(required = false, defaultValue = "")String field,
                                                          @RequestParam(required = false, defaultValue = "")String keyword,
                                                          @RequestParam(required = false, defaultValue = "0")int textTypeId,
                                                          @RequestParam(required = false, defaultValue = "0")int parentId,
                                                          @RequestParam(required = false, defaultValue = "0")int timeHorizon,
                                                          @RequestParam(required = false, defaultValue = "0")int sort) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("field", field);
            map.put("keyword", keyword);
            List<ArticleEntity> list = articleService.findByCriteria(map, textTypeId, parentId, timeHorizon, sort, index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("查询失败", e);
            result.failedApiResponse(Const.FAILED, "查询失败");
        }
        return result;
    }

    /**
     * 获取五个同类文章
     * @param textTypeId  文章类型id
     * @return
     */
    @GetMapping("/getKind")
    public RestApiResponse<List<ArticleEntity>> getKind(int textTypeId,
                                                        @RequestParam(defaultValue = "0", required = false) int index,
                                                        @RequestParam(defaultValue = "5", required = false) int size){
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getKind(textTypeId, index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取同类文章异常", e);
            result.failedApiResponse(Const.FAILED, "获取同类文章异常");
        }
        return result;
    }

    /**
     * 获取五个推荐文章
     * @param recommend 是否推荐 0(否)，1(是)
     * @return
     */
    @GetMapping("/getRecommendArticle")
    public RestApiResponse<List<ArticleEntity>> getRecommendArticle(@RequestParam(required = false, defaultValue = "1")int recommend,
                                                                    @RequestParam(defaultValue = "0", required = false) int index,
                                                                    @RequestParam(defaultValue = "5", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getRecommendArticle(recommend, index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取推荐文章异常", e);
            result.failedApiResponse(Const.FAILED, "获取推荐文章异常");
        }
        return result;
    }

    /**
     * 获取五个最新文章
     * @return
     */
    @GetMapping("/getNewestArticle")
    public RestApiResponse<List<ArticleEntity>> getNewestArticle(@RequestParam(defaultValue = "0", required = false) int index,
                                                                 @RequestParam(defaultValue = "5", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getNewestArticle(index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取最新文章异常", e);
            result.failedApiResponse(Const.FAILED, "获取最新文章异常");
        }
        return result;
    }

    /**
     * 注册用户
     */
    @PostMapping("/registeredUser")
    public RestApiResponse<UserEntity> registeredUser(@RequestBody UserEntity userEntity,
                                           @RequestParam(required = false, defaultValue = "1")int type) {
        RestApiResponse<UserEntity> result = new RestApiResponse<UserEntity>();
        try {
            if (!StringUtils.isNotBlank(userEntity.getUsername())) {
                result.failedApiResponse(Const.FAILED, "未设置用户名");
                return result;
            }
            if (!StringUtils.isNotBlank(userEntity.getPassword())) {
                result.failedApiResponse(Const.FAILED, "未设置密码");
                return result;
            }
            UserEntity entity = userService.findByUsernameAndType(userEntity.getUsername(), type);
            if (entity != null) {
                result.failedApiResponse(Const.FAILED, "用户名已存在");
                return result;
            }
            userEntity.setCreateTime(new Date());
            userEntity.setType(type);
            UserEntity user = userService.add(userEntity);
            if (user == null) {
                result.failedApiResponse(Const.FAILED, "添加失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, user, "注册成功");
        } catch (Exception e) {
            logger.warn("注册用户异常:", e);
            result.failedApiResponse(Const.FAILED, "注册用户异常");
        }
        return result;
    }
}

