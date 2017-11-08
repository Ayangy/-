package com.yuwubao.controllers;

import com.yuwubao.entities.TitleImgEntity;
import com.yuwubao.services.TitleImgService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/8.
 */
@RestController
@RequestMapping("/titleImg")
public class TitleImgController {

    private final static Logger logger = LoggerFactory.getLogger(TitleImgController.class);

    @Autowired
    private TitleImgService titleImgService;

    /**
     * 查询展示图片列表
     * @param index 第几页
     * @param size  每页几条
     * @param beginTime 开始时间
     * @param endTime  结束时间
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<TitleImgEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                                         @RequestParam(defaultValue = "10", required = false)int size,
                                                         @RequestParam(required = false, defaultValue = "")String beginTime,
                                                         @RequestParam(required = false, defaultValue = "")String endTime,
                                                         @RequestParam(required = false, defaultValue = "")Integer state){
        RestApiResponse<Page<TitleImgEntity>> result = new RestApiResponse<Page<TitleImgEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<TitleImgEntity> list = titleImgService.findAll(map, pageAble, state);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("查询展示图片列表异常", e);
            result.failedApiResponse(Const.FAILED, "查询展示图片列表异常");
        }
        return result;
    }

    /**
     * 新增展示图片
     */
    @PostMapping("/add")
    public RestApiResponse<TitleImgEntity> add(@RequestBody TitleImgEntity titleImgEntity) {
        RestApiResponse<TitleImgEntity> result = new RestApiResponse<TitleImgEntity>();
        try {
            titleImgEntity.setAddTime(new Date());
            TitleImgEntity entity = titleImgService.add(titleImgEntity);
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "添加失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, entity, "添加成功");
        } catch (Exception e) {
            logger.warn("新增展示图片异常:", e);
            result.failedApiResponse(Const.FAILED, "新增展示图片异常");
        }
        return result;
    }

    /**
     * 删除展示图片
     * @param id  图片Id
     * @return
     */
    @DeleteMapping("/delete")
    public RestApiResponse<TitleImgEntity> delete(@RequestParam(required = true) int id) {
        RestApiResponse<TitleImgEntity> result = new RestApiResponse<TitleImgEntity>();
        try {
            TitleImgEntity titleImgEntity = titleImgService.delete(id);
            if (titleImgEntity == null) {
                result.failedApiResponse(Const.FAILED, "该图片不存在，删除失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, titleImgEntity, "删除成功");
        } catch (Exception e) {
            logger.warn("删除展示图片异常:", e);
            result.failedApiResponse(Const.FAILED, "删除展示图片异常");
        }
        return result;
    }

    /**
     * 修改展示图片
     */
    @PutMapping("/update")
    public RestApiResponse<TitleImgEntity> update(@RequestBody TitleImgEntity titleImgEntity) {
        RestApiResponse<TitleImgEntity> result = new RestApiResponse<TitleImgEntity>();
        try {
            TitleImgEntity entity = titleImgService.findById(titleImgEntity.getId());
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "修改失败，图片信息不存在");
                return result;
            }
            TitleImgEntity imgEntity = titleImgService.update(titleImgEntity);
            result.successResponse(Const.SUCCESS, imgEntity, "修改成功");
        } catch (Exception e) {
            logger.warn("修改展示图片异常", e);
            result.failedApiResponse(Const.FAILED, "修改展示图片异常");
        }
        return result;
    }

    /**
     * 获取展示图片
     */
    @GetMapping("/findByState")
    public RestApiResponse<List<TitleImgEntity>> findByState() {
        RestApiResponse<List<TitleImgEntity>> result = new RestApiResponse<List<TitleImgEntity>>();
        try {
            List<TitleImgEntity> list = titleImgService.findByState(1);
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "无展示图片");
                return result;
            }
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取展示图片异常", e);
            result.failedApiResponse(Const.FAILED, "获取展示图片异常");
        }
        return result;
    }

}
