package com.yuwubao.controllers;

import com.yuwubao.entities.*;
import com.yuwubao.services.*;
import com.yuwubao.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * 前端接口
 * Created by yangyu on 2017/11/6.
 */
@RestController
@RequestMapping("/frontEnd")
@CrossOrigin
public class FrontEndController {

    private final static Logger logger = LoggerFactory.getLogger(FrontEndController.class);

    private final static int shield = 0;//未屏蔽数据

    @Value("${resourcesPath}")
    private String resourcesPath;

    @Value("${serverIp}")
    private String serverIp;

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

    @Autowired
    private BlogrollService blogrollService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private QuestionnaireService questionnaireService;

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
    public RestApiResponse<Map<String, List<ExpertEntity>>> findExpertByLetter(@RequestParam(defaultValue = "", required = false) String letter) {
        RestApiResponse<Map<String, List<ExpertEntity>>> result = new RestApiResponse<Map<String, List<ExpertEntity>>>();
        Map<String, List<ExpertEntity>> map = new HashMap<String, List<ExpertEntity>>();
        try {
            if (!StringUtils.isNotBlank(letter)) {
                String alphabet;
                for (char i = 'A' ; i<= 'Z'; i++ ){
                    alphabet = String.valueOf(i);
                    List<ExpertEntity> entityList = expertService.findExpertByLetter(alphabet);
                    map.put(alphabet,entityList);
                }
                result.successResponse(Const.SUCCESS, map);
                return result;
            }
            List<ExpertEntity> entities = expertService.findExpertByLetter(letter);
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
     * @param fieldType 0党建、1社会、2生态、3政治、4经济、5文化、6热点专题、7国际关系
     * @return
     */
    @GetMapping("/findExpertByCondition")
    public RestApiResponse<Map<String, List<ExpertEntity>>> findExpertByCondition(@RequestParam(required = false, defaultValue = "")String field,
                                                                                  @RequestParam(required = false, defaultValue = "")String keyword,
                                                                                  @RequestParam(required = false, defaultValue = "")int fieldType){
        RestApiResponse<Map<String, List<ExpertEntity>>> result = new RestApiResponse<Map<String, List<ExpertEntity>>>();
        Map<String, List<ExpertEntity>> endResult = new HashMap<String, List<ExpertEntity>>();
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            List<ExpertEntity> list = expertService.findExpertByCondition(map, fieldType);
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
    public RestApiResponse<Map<String, List<OrganizationEntity>>> findByLetter(@RequestParam(defaultValue = "1", required = false) int type,
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
     * 条件查询未屏蔽机构名
     * @param field 查询字段
     * @param keyword  查询值
     * @param organizationType  1(大学),2(党校),3(政研室),4(科研机构)
     * @param type  0(智库机构),1(智库联盟)
     * @return
     */
    @GetMapping("/findOrganizationByCondition")
    public RestApiResponse<Map<String, List<OrganizationEntity>>> findOrganizationByCondition(@RequestParam(required = false, defaultValue = "")String field,
                                                                                              @RequestParam(required = false, defaultValue = "")String keyword,
                                                                                              @RequestParam(defaultValue = "0", required = false) int organizationType,
                                                                                              @RequestParam(defaultValue = "0", required = false) int type) {
        RestApiResponse<Map<String, List<OrganizationEntity>>> result = new RestApiResponse<Map<String, List<OrganizationEntity>>>();
        Map<String, List<OrganizationEntity>> endResult = new HashMap<String, List<OrganizationEntity>>();
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            List<OrganizationEntity> list = organizationService.findByCondition(map, organizationType, type);
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
     * 获取本机构
     *
     */
    @GetMapping("/getThisInstitution")
    public RestApiResponse<OrganizationEntity> getThisInstitution(){
        RestApiResponse<OrganizationEntity> result = new RestApiResponse<OrganizationEntity>();
        try {
            OrganizationEntity organizationEntity = organizationService.findByType(0);
            if (organizationEntity == null) {
                result.failedApiResponse(Const.FAILED, "暂未添加本机构数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, organizationEntity);
        } catch (Exception e) {
            logger.warn("获取本机构数据异常", e);
            result.failedApiResponse(Const.FAILED, "获取本机构数据异常");
        }
        return result;
    }

    /**
     * 获取当前机构最新一条公告
     * @param id  机构id
     * @return
     */
    @GetMapping("/getOrganizationNotice")
    public RestApiResponse<ArticleEntity> getOrganizationNotice(@RequestParam int id,
                                                                @RequestParam int textTypeId) {
        RestApiResponse<ArticleEntity> result = new RestApiResponse<ArticleEntity>();
        try {
            ArticleEntity articleEntity = articleService.getAnOrganizationNotice(id, textTypeId);
            result.successResponse(Const.SUCCESS, articleEntity);
        } catch (Exception e) {
            logger.warn("获取最新公告异常", e);
            result.failedApiResponse(Const.FAILED, "获取最新公告异常");
        }
        return result;
    }

    /**
     *  获取当前机构的活动动态
     *  @param id   机构id
     * @return
     */
    @GetMapping("/getOrganizationActivity")
    public RestApiResponse<List<ArticleEntity>> get(@RequestParam int id,@RequestParam int textTypeId,
                                                    @RequestParam(defaultValue = "0", required = false) int index,
                                                    @RequestParam(defaultValue = "4", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> articleEntityList = articleService.getOrganizationActivity(id, textTypeId, index, size);
            result.successResponse(Const.SUCCESS, articleEntityList);
        } catch (Exception e) {
            logger.warn("获取机构活动异常", e);
            result.failedApiResponse(Const.FAILED, "获取机构活动异常");
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

    @GetMapping("/getVideoDetails")
    public RestApiResponse<VideoEntity> getVideoDetails(@RequestParam int id) {
        RestApiResponse<VideoEntity> result = new RestApiResponse<VideoEntity>();
        try {
            VideoEntity entity = videoService.findOne(id);
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "音视频不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, entity);
        } catch (Exception e) {
            logger.warn("查询视频详情异常", e);
            result.failedApiResponse(Const.FAILED, "查询视频详情异常");
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
     * @param keyword  查询值
     * @param parentId  文章类型
     * @param sort  排序 0(降序),1(升序)
     * @return
     */
    @GetMapping("/findResultByCriteria")
    public RestApiResponse<List<ArticleEntity>> findResultByCriteria(@RequestParam(defaultValue = "0", required = false)int index,
                                                          @RequestParam(defaultValue = "10", required = false)int size,
                                                          @RequestParam(required = false, defaultValue = "")String keyword,
                                                          @RequestParam(required = false, defaultValue = "28")int parentId,
                                                          @RequestParam(required = false, defaultValue = "0")int sort) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("keyword", keyword);
            List<ArticleEntity> list = articleService.findByCriteria(map, parentId, sort, index, size);
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
     * 未屏蔽机构列表
     */
    @GetMapping("/getOrganizationList")
    public RestApiResponse<List<OrganizationEntity>> getOrganizationList(@RequestParam(required = false, defaultValue = "1")int type) {
        RestApiResponse<List<OrganizationEntity>> result = new RestApiResponse<>();
        try {
            List<OrganizationEntity > list =  organizationService.findByShieldAndType(0,type);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取机构列表失败", e);
            result.failedApiResponse(Const.FAILED, "获取机构列表失败");
        }
        return result;
    }


    /**
     * 获取所有文章
     * @param parentId
     * @return
     */
    @GetMapping("/getArticleList")
    public RestApiResponse<List<ArticleEntity>> getAchievementList(@RequestParam int parentId) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<>();
        try {
            List<ArticleEntity> entityList = articleService.findByTextTypeIdAndShield(parentId, 0);
            result.successResponse(Const.SUCCESS, entityList);
        } catch (Exception e) {
            logger.warn("获取文章列表失败", e);
            result.failedApiResponse(Const.FAILED, "获取文章列表失败");
        }
        return result;
    }

    /**
     * 获取指定机构文章
     * @param textTypeId  文章类型id
     * @param organizationId 机构id
     * @param index  第几页
     * @param size  每页几条
     * @return
     */
    @GetMapping("/getInstitutionArticle")
    public RestApiResponse<List<ArticleEntity>> getInstitutionArticle(@RequestParam int textTypeId, @RequestParam int organizationId,
                                                                      @RequestParam(defaultValue = "0", required = false) int index,
                                                                      @RequestParam(defaultValue = "5", required = false) int size) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<>();
        try {
            List<ArticleEntity> entityList = articleService.findOrganizationArticle(textTypeId, organizationId, index, size, 0);
            result.successResponse(Const.SUCCESS, entityList);
        } catch (Exception e) {
            logger.warn("获取文章列表失败", e);
            result.failedApiResponse(Const.FAILED, "获取文章列表失败");
        }
        return result;
    }

    /**
     * 获取指定联盟详情
     * @param organizationId  机构id
     * @return
     */
    @GetMapping("/findInstitutionByid")
    public RestApiResponse<OrganizationEntity> getInstitution(@RequestParam int organizationId) {
        RestApiResponse<OrganizationEntity> result = new RestApiResponse<OrganizationEntity>();
        try {
            OrganizationEntity organizationEntity = organizationService.findOne(organizationId);
            result.successResponse(Const.SUCCESS, organizationEntity);
        } catch (Exception e) {
            logger.warn("获取机构详情失败", e);
            result.failedApiResponse(Const.FAILED, "获取机构详情失败");
        }
        return result;
    }

    /**
     * 数据库查询
     * @param index  第几页
     * @param size  每页几条
     * @param keyword  查询值
     * @param beginTime  开始时间
     * @param endTime  结束时间
     * @return
     */
    @GetMapping("/getDatabase")
    public RestApiResponse<List<ArticleEntity>> getDatabase(@RequestParam(defaultValue = "0", required = false)int index,
                                                      @RequestParam(defaultValue = "10", required = false)int size,
                                                      @RequestParam(required = false, defaultValue = "")String keyword,
                                                      @RequestParam(required = false, defaultValue = "")String beginTime,
                                                      @RequestParam(required = false, defaultValue = "")String endTime) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.getDatabase(index,size, keyword, beginTime, endTime);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取数据库列表异常", e);
            result.failedApiResponse(Const.FAILED, "获取数据库列表异常");
        }
        return result;
    }

    /**
     * 获取合作单位列表
     * @param type  0(共建机构),1(友情链接),2(合作媒体),3(特别成员单位)
     * @return
     */
    @GetMapping("/getBlogroll")
    public RestApiResponse<List<BlogrollEntity>> getBlogroll(@RequestParam int type) {
        RestApiResponse<List<BlogrollEntity>> result = new RestApiResponse<List<BlogrollEntity>>();
        try {
            List<BlogrollEntity> list = blogrollService.findByType(type);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取合作单位列表异常", e);
            result.failedApiResponse(Const.FAILED, "获取合作单位列表异常");
        }
        return result;

    }

    /**
     * 条件查询音视频
     * @param index
     * @param size
     * @param query
     * @return
     */
    @GetMapping("/findByString")
    public RestApiResponse<List<VideoEntity>> findByString(@RequestParam(defaultValue = "0", required = false)int index,
                                                           @RequestParam(defaultValue = "10", required = false)int size,
                                                           @RequestParam(defaultValue = "", required = false)String query) {
        RestApiResponse<List<VideoEntity>> result = new RestApiResponse<List<VideoEntity>>();
        try {
            List<VideoEntity> list = videoService.findByString(query, index, size);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("条件查询音视频异常", e);
            result.failedApiResponse(Const.FAILED, "条件查询音视频异常");
        }
        return result;

    }

    /**
     *  验证邮箱是否有效
     * @param email  邮箱地址
     * @return
     */
    @PostMapping("/checkEmail")
    public RestApiResponse<Boolean> checkEmail(@RequestParam(defaultValue = "", required = false) String email) {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            if (!StringUtils.isNotBlank(email)) {
                result.failedApiResponse(Const.FAILED, "邮箱不能为空");
                return result;
            }
            boolean emailState = CheckEmailValidityUtil.isEmailValid(email);
            result.successResponse(Const.SUCCESS, emailState);
        } catch (Exception e) {
            logger.warn("检查邮箱异常", e);
            result.failedApiResponse(Const.FAILED, "检查邮箱异常");
        }
        return result;
    }

    /**
     * 获取验证码
     * @throws Exception
     */
    @PostMapping("/getCaptcha")
    public RestApiResponse<Map<String, Object>> getCaptcha(){
        RestApiResponse<Map<String, Object>> result = new RestApiResponse<Map<String, Object>>();
        try {
            //利用图片工具生成图片
            //第一个参数是生成的验证码，第二个参数是生成的图片
            Object[] objs = VerifyCodeUtil.createImage();
            String captcha = (String)objs[0];

            // 图片验证码
            ByteArrayOutputStream outputStream = null;
            BufferedImage image = (BufferedImage) objs[1];

            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);

            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", "data:image/png;base64," + encoder.encode(outputStream.toByteArray()));
            map.put("captcha", captcha);
            result.successResponse(Const.SUCCESS, map);
        } catch (Exception e) {
            logger.warn("获取验证码异常", e);
            result.failedApiResponse(Const.FAILED, "获取验证码异常");
        }
        return result;
    }

    /**
     * 发表评论
     * @param clientUserId  前端用户id
     * @param articleId  文章id
     * @param content  评论内容
     * @return
     */
    @PostMapping("/makeComments")
    public RestApiResponse<CommentEntity> makeComments(@RequestParam(required = false, defaultValue = "0") int clientUserId,
                                                       @RequestParam int articleId,
                                                       @RequestParam(required = false, defaultValue = "") String content) {
        RestApiResponse<CommentEntity> result = new RestApiResponse<CommentEntity>();
        try {
            if (clientUserId == 0) {
                result.failedApiResponse(Const.FAILED, "您未登录，发布评论失败");
                return result;
            }
            if (!StringUtils.isNotBlank(content)) {
                result.failedApiResponse(Const.FAILED, "评论不能为空");
                return result;
            }
            ClientUserEntity clientUserEntity = clientUserService.findOne(clientUserId);
            if (clientUserEntity == null) {
                result.failedApiResponse(Const.FAILED, "用户不存在,评论失败");
                return result;
            }
            ArticleEntity articleEntity = articleService.findById(articleId);
            if (articleEntity == null) {
                result.failedApiResponse(Const.FAILED, "文章不存在,评论失败");
                return result;
            }
            CommentEntity comment = new CommentEntity();
            comment.setClientUserId(clientUserId);
            comment.setArticleId(articleId);
            comment.setContent(content);
            comment.setAddTime(new Date());
            comment.setClientUserName(clientUserEntity.getName());
            comment.setArticleTitle(articleEntity.getTitle());
            CommentEntity commentEntity = commentService.add(comment);
            if (commentEntity == null) {
                result.failedApiResponse(Const.FAILED, "发表评论失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, commentEntity);
        } catch (Exception e) {
            logger.warn("添加评论异常", e);
            result.failedApiResponse(Const.FAILED, "添加评论异常");
        }
        return result;
    }

    /**
     * 获取当前文章的评论列表
     * @param index  第几页
     * @param size  每页几条
     * @param articleId  文章id
     * @return
     */
    @GetMapping("/getCommentList")
    public RestApiResponse<Page<CommentEntity>> getCommentList(@RequestParam(defaultValue = "1", required = false) int index,
                                                               @RequestParam(defaultValue = "10", required = false) int size,
                                                               @RequestParam int articleId) {
        RestApiResponse<Page<CommentEntity>> result = new RestApiResponse<Page<CommentEntity>>();
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
            Pageable pageAble = new PageRequest(index-1, size, sort);
            Page<CommentEntity> list = commentService.findAll(pageAble, articleId);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取评论列表异常", e);
            result.failedApiResponse(Const.FAILED, "获取评论列表异常");
        }
        return result;
    }

    /**
     * 提交调查问卷
     * @param clientUserId  前端用户id
     * @param articleId  文章id
     * @param questionnaireResultUrl  问卷Url
     * @return
     */
    @PostMapping("/submitQuestionnaire")
    public RestApiResponse<QuestionnaireEntity> submitQuestionnaire(@RequestParam(required = false, defaultValue = "0") int clientUserId,
                                                                    @RequestParam int articleId,
                                                                    @RequestParam(required = false, defaultValue = "") String questionnaireResultUrl) {
        RestApiResponse<QuestionnaireEntity> result = new RestApiResponse<QuestionnaireEntity>();
        try {
            if (clientUserId == 0) {
                result.failedApiResponse(Const.FAILED, "您未登录,请登录再提交");
                return result;
            }
            ClientUserEntity clientUserEntity = clientUserService.findOne(clientUserId);
            if (clientUserEntity == null) {
                result.failedApiResponse(Const.FAILED, "用户不存在,提交失败");
                return result;
            }
            QuestionnaireEntity questionnaire = questionnaireService.findByClientUserId(clientUserId);
            if (questionnaire != null) {
                result.failedApiResponse(Const.FAILED, "您已提交问卷");
                return result;
            }
            ArticleEntity articleEntity = articleService.findById(articleId);
            if (articleEntity == null) {
                result.failedApiResponse(Const.FAILED, "此调研话题不存在,提交失败");
                return result;
            }
            if (!StringUtils.isNotBlank(questionnaireResultUrl)) {
                result.failedApiResponse(Const.FAILED, "提交失败");
                return result;
            }
            QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
            questionnaireEntity.setClientUserId(clientUserId);
            questionnaireEntity.setClientUserName(clientUserEntity.getName());
            questionnaireEntity.setArticleId(articleId);
            questionnaireEntity.setArticleTitle(articleEntity.getTitle());
            questionnaireEntity.setQuestionnaireResultUrl(questionnaireResultUrl);
            questionnaireEntity.setSubmitTime(new Date());
            QuestionnaireEntity entity = questionnaireService.save(questionnaireEntity);
            result.successResponse(Const.SUCCESS, entity);
        } catch (Exception e) {
            logger.warn("提交异常", e);
            result.failedApiResponse(Const.FAILED, "提交异常");
        }
        return result;
    }

    /**
     * 条件查询文献库
     * @param index  第几页
     * @param size  每页几条
     * @param literatureType  文献类型
     * @return
     */
    @GetMapping("/findLiteratureList")
    public RestApiResponse<List<ArticleEntity>> getLiteratureList(@RequestParam(required = false, defaultValue = "0")int index,
                                                                  @RequestParam(required = false, defaultValue = "10")int size,
                                                                  @RequestParam(required = false, defaultValue = "0") int literatureType) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.findLiteratureList(index,size, literatureType);
            result.successResponse(Const.SUCCESS,list);
        } catch (Exception e) {
            logger.warn("获取文献列表异常", e);
            result.failedApiResponse(Const.FAILED, "获取文献列表异常");
        }
        return result;
    }
}


