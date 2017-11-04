package com.yuwubao.controllers;

import com.yuwubao.entities.ArticleSortEntity;
import com.yuwubao.services.ArticleSortService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/24.
 */
@RestController
@RequestMapping("/articleSort")
@Transactional
public class ArticleSortController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleSortController.class);

    @Autowired
    private ArticleSortService articleSortService;

    /**
     * 查询文章分类
     * @param index
     * @param size
     * @param field
     * @param keyword
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/findAll")
    public ModelAndView findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                @RequestParam(defaultValue = "10", required = false)int size,
                                @RequestParam(required = false, defaultValue = "")String field,
                                @RequestParam(required = false, defaultValue = "")String keyword,
                                @RequestParam(required = false, defaultValue = "")String beginTime,
                                @RequestParam(required = false, defaultValue = "")String endTime){
        ModelAndView modelAndView = new ModelAndView("login");
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<ArticleSortEntity> list = articleSortService.findAll(map, pageAble);
            modelAndView.addObject("list", list);
        } catch (Exception e) {
            logger.warn("article list find failed", e);

        }
        return modelAndView;
    }

    /**
     * 新增文章分类
     */
    @PostMapping("/add")
    public RestApiResponse<ArticleSortEntity> add(@RequestBody ArticleSortEntity articleSortEntity) {
        RestApiResponse<ArticleSortEntity> result = new RestApiResponse<ArticleSortEntity>();
        try {
            ArticleSortEntity articleSort = articleSortService.add(articleSortEntity);
            if (articleSort == null) {
                result.failedApiResponse(Const.FAILED, "添加失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, articleSort, "添加成功");
        } catch (Exception e) {
            logger.warn("", e);
        }
        return result;
    }

    /**
     * 删除文章分类
     */
    @DeleteMapping("/delete")
    public RestApiResponse<ArticleSortEntity> delete(@RequestParam(required = true) int id) {
        RestApiResponse<ArticleSortEntity> result = new RestApiResponse<ArticleSortEntity>();
        try {
            ArticleSortEntity articleSort = articleSortService.delete(id);
            if (articleSort == null) {
                result.failedApiResponse(Const.FAILED, "删除失败,分类不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, articleSort, "删除成功");
        } catch (Exception e) {
            logger.warn("删除文章分类异常", e);
        }
        return result;
    }

    /**
     * 修改文章分类
     */
    @PutMapping("/update")
    public RestApiResponse<ArticleSortEntity> update(@RequestBody ArticleSortEntity articleSortEntity) {
        RestApiResponse<ArticleSortEntity> result = new RestApiResponse<ArticleSortEntity>();
        try {
            ArticleSortEntity articleSort = articleSortService.update(articleSortEntity);
            if (articleSort == null) {
                result.failedApiResponse(Const.FAILED, "修改失败，分类不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, articleSort, "修改成功");
        } catch (Exception e) {
            logger.warn("修改文章分类异常", e);
        }
        return result;

    }

    /**
     * 无分页文章分类列表
     */
    @GetMapping("getAll")
    public RestApiResponse<List<ArticleSortEntity>> getAll() {
        RestApiResponse<List<ArticleSortEntity>> result = new RestApiResponse<List<ArticleSortEntity>>();
        try {
            List<ArticleSortEntity> list = articleSortService.getAll();
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "无文章分类列表");
                return result;
            }
            result.failedApiResponse(Const.SUCCESS, "列表获取成功");
            result.setResult(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
