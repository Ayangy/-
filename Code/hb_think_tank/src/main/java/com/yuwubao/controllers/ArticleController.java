package com.yuwubao.controllers;

import com.yuwubao.entities.ArticleEntity;
import com.yuwubao.services.ArticleService;
import com.yuwubao.services.impl.ArticleSearchService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/20.
 */
@RestController
@RequestMapping("/article")
@Transactional
@CrossOrigin
public class ArticleController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 查询文章
     * @param index  第几页
     * @param size  每页几条
     * @param field  查询字段
     * @param keyword  查询值
     * @param beginTime  开始时间
     * @param endTime  结束时间
     * @param articleType  文章分类Id
     * @param organizationId  机构Id
     *
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<ArticleEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                @RequestParam(defaultValue = "10", required = false)int size,
                                @RequestParam(required = false, defaultValue = "")String field,
                                @RequestParam(required = false, defaultValue = "")String keyword,
                                @RequestParam(required = false, defaultValue = "")String beginTime,
                                @RequestParam(required = false, defaultValue = "")String endTime,
                                @RequestParam(required = false, defaultValue = "0")int articleType,
                                @RequestParam(required = false, defaultValue = "0")int organizationId){
        RestApiResponse<Page<ArticleEntity>> result = new RestApiResponse<Page<ArticleEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("field", field);
            map.put("keyword", keyword);
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<ArticleEntity> list = articleService.findAll(map, pageAble, articleType, organizationId);
            if (list.getContent().size() == 0) {
                result.failedApiResponse(Const.SUCCESS, "暂无数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, list, "获取列表成功");
        } catch (Exception e) {
            logger.warn("文章列表查询异常", e);
            result.failedApiResponse(Const.FAILED, "文章列表查询异常");
        }
        return result;
    }

    /**
     * 新增文章
     */
    @PostMapping("/add")
    public RestApiResponse<ArticleEntity> add(@RequestBody ArticleEntity articleEntity) {
        RestApiResponse<ArticleEntity> result = new RestApiResponse<ArticleEntity>();
        try {
            articleEntity.setAddTime(new Date());
            ArticleEntity article = articleService.add(articleEntity);
            if (article == null) {
                result.failedApiResponse(Const.FAILED, "添加失败");
                return result;
            }
            articleSearchService.createDoc(articleEntity);
            result.successResponse(Const.SUCCESS, article, "添加成功");
        } catch (Exception e) {
            logger.warn("新增文章异常", e);
            result.failedApiResponse(Const.FAILED, "新增文章异常");
        }
        return result;
    }

    /**
     *  删除文章
     * @param id 文章Id
     *
     */
    @DeleteMapping("/delete")
    public RestApiResponse<ArticleEntity> delete(@RequestParam(required = true) int id) {
        RestApiResponse<ArticleEntity> result = new RestApiResponse<ArticleEntity>();
        try {
            ArticleEntity articleEntity = articleService.delete(id);
            if (articleEntity == null) {
                result.failedApiResponse(Const.FAILED, "删除失败,文章不存在");
                return result;
            }
            articleSearchService.delete(String.valueOf(id));
            result.successResponse(Const.SUCCESS, articleEntity, "删除成功");
        } catch (Exception e) {
            logger.warn("删除文章异常", e);
            result.failedApiResponse(Const.FAILED, "删除文章异常");
        }
        return result;
    }

    /**
     * 修改文章
     */
    @PutMapping("/update")
    public RestApiResponse<ArticleEntity> update(@RequestBody ArticleEntity articleEntity) {
        RestApiResponse<ArticleEntity> result = new RestApiResponse<ArticleEntity>();
        try {
            ArticleEntity article = articleService.update(articleEntity);
            if (article == null) {
                result.failedApiResponse(Const.FAILED, "修改失败，文章不存在");
                return result;
            }
            articleSearchService.update(String.valueOf(article.getId()),article);
            result.successResponse(Const.SUCCESS, article, "修改成功");
        } catch (Exception e) {
            logger.warn("修改文章异常", e);
            result.failedApiResponse(Const.FAILED, "修改文章异常");
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
     * 文章分类显示
     */
    @GetMapping("/articleSort")
    public RestApiResponse<List<ArticleEntity>> articleSort(@RequestParam int type,
                                                            @RequestParam int parentType) {
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        try {
            List<ArticleEntity> list = articleService.findByArticleSort(type, parentType);
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "暂无数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("文章分类查询异常", e);
            result.failedApiResponse(Const.FAILED, "文章分类查询异常");
        }
        return result;
    }
}
