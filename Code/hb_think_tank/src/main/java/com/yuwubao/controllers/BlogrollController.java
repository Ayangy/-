package com.yuwubao.controllers;

import com.yuwubao.entities.BlogrollEntity;
import com.yuwubao.services.BlogrollService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.apache.commons.lang3.StringUtils;
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
 * Created by yangyu on 2017/12/10.
 */
@RestController
@RequestMapping("/blogroll")
@Transactional
@CrossOrigin
public class BlogrollController {

    private final static Logger logger = LoggerFactory.getLogger(BlogrollController.class);

    @Autowired
    private BlogrollService blogrollService;

    /**
     * 查询合作单位
     * @param index  第几页
     * @param size  每页几条
     * @param field  查询字段
     * @param keyword  查询值
     *
     *
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<BlogrollEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                                         @RequestParam(defaultValue = "10", required = false)int size,
                                                         @RequestParam(required = false, defaultValue = "")String field,
                                                         @RequestParam(required = false, defaultValue = "")String keyword){
        RestApiResponse<Page<BlogrollEntity>> result = new RestApiResponse<Page<BlogrollEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("field", field);
            map.put("keyword", keyword);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<BlogrollEntity> list = blogrollService.findAll(map, pageAble);
            result.successResponse(Const.SUCCESS, list, "获取列表成功");
        } catch (Exception e) {
            logger.warn("合作单位列表查询异常", e);
            result.failedApiResponse(Const.FAILED, "合作单位列表查询异常");
        }
        return result;
    }

    /**
     * 新增合作单位
     */
    @PostMapping("/add")
    public RestApiResponse<BlogrollEntity> add(@RequestBody BlogrollEntity blogrollEntity) {
        RestApiResponse<BlogrollEntity> result = new RestApiResponse<BlogrollEntity>();
        try {
            if (blogrollEntity.getName() == null) {
                result.failedApiResponse(Const.FAILED, "合作单位名不能为空");
                return result;
            }
            BlogrollEntity entity = blogrollService.findByName(blogrollEntity.getName());
            if (entity != null) {
                result.failedApiResponse(Const.FAILED, "合作单位已添加");
                return result;
            }
            BlogrollEntity blogroll = blogrollService.add(blogrollEntity);
            result.successResponse(Const.SUCCESS, blogroll, "添加成功");
        } catch (Exception e) {
            logger.warn("合作单位新增异常", e);
            result.failedApiResponse(Const.FAILED, "合作单位新增异常");
        }
        return result;
    }

    /**
     *  删除合作单位
     * @param id 合作单位Id
     *
     */
    @DeleteMapping("/delete")
    public RestApiResponse<BlogrollEntity> delete(@RequestParam int id) {
        RestApiResponse<BlogrollEntity> result = new RestApiResponse<BlogrollEntity>();
        try {
            BlogrollEntity blogrollEntity = blogrollService.delete(id);
            if (blogrollEntity == null) {
                result.failedApiResponse(Const.FAILED, "删除失败,合作单位不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, blogrollEntity, "删除成功");
        } catch (Exception e) {
            logger.warn("删除合作单位异常", e);
            result.failedApiResponse(Const.FAILED, "删除合作单位异常");
        }
        return result;
    }

    /**
     * 修改合作单位
     */
    @PutMapping("/update")
    public RestApiResponse<BlogrollEntity> update(@RequestBody BlogrollEntity blogrollEntity) {
        RestApiResponse<BlogrollEntity> result = new RestApiResponse<BlogrollEntity>();
        try {
            if (!StringUtils.isNotBlank(blogrollEntity.getName())) {
                result.failedApiResponse(Const.FAILED, "单位名不能为空");
                return result;
            }
            BlogrollEntity oldBlogroll = blogrollService.findOne(blogrollEntity.getId());
            if (oldBlogroll == null) {
                result.failedApiResponse(Const.FAILED, "修改失败，合作单位不存在");
                return result;
            }
            if (!oldBlogroll.getName().equals(blogrollEntity.getName())) {
                BlogrollEntity byName = blogrollService.findByName(blogrollEntity.getName());
                if (byName != null) {
                    result.failedApiResponse(Const.FAILED, "单位名已存在");
                    return result;
                }
            }
            BlogrollEntity entity =  blogrollService.add(blogrollEntity);
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "修改失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, entity, "修改成功");
        } catch (Exception e) {
            logger.warn("修改合作单位异常", e);
            result.failedApiResponse(Const.FAILED, "修改合作单位异常");
        }
        return result;

    }
}
