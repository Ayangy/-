package com.yuwubao.controllers;

import com.yuwubao.entities.OrganizationEntity;
import com.yuwubao.entities.VideoEntity;
import com.yuwubao.services.OrganizationService;
import com.yuwubao.services.VideoService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/3.
 */
@RestController
@RequestMapping("/videoNews")
@CrossOrigin
public class VideoController {

    private final static Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private OrganizationService organizationService;

    /**
     * 查询视频资讯
     * @param index 第几页
     * @param size  每页几条
     * @param field  查询字段
     * @param keyword  查询值
     * @param beginTime 开始时间
     * @param endTime  结束时间
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<VideoEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                                      @RequestParam(defaultValue = "10", required = false)int size,
                                                      @RequestParam(required = false, defaultValue = "")String field,
                                                      @RequestParam(required = false, defaultValue = "")String keyword,
                                                      @RequestParam(required = false, defaultValue = "")String beginTime,
                                                      @RequestParam(required = false, defaultValue = "")String endTime){
        RestApiResponse<Page<VideoEntity>> result = new RestApiResponse<Page<VideoEntity>>();
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<VideoEntity> list = videoService.findAll(map, pageAble);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("查询视频资讯列表异常", e);
            result.failedApiResponse(Const.FAILED, "视频资讯列表查询异常");
        }
        return result;
    }

    /**
     * 新增视频资讯
     */
    @PostMapping("/add")
    public RestApiResponse<VideoEntity> add(@RequestBody VideoEntity videoEntity) {
        RestApiResponse<VideoEntity> result = new RestApiResponse<VideoEntity>();
        try {
            OrganizationEntity organizationEntity = organizationService.findOne(videoEntity.getOrganizationId());
            if (organizationEntity != null) {
                result.failedApiResponse(Const.FAILED, "指定的机构不存在");
                return result;
            }
            VideoEntity video = videoService.add(videoEntity);
            if (video == null) {
                result.failedApiResponse(Const.FAILED, "添加失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, video, "添加成功");
        } catch (Exception e) {
            logger.warn("新增视频资讯异常:", e);
            result.failedApiResponse(Const.FAILED, "新增视频资讯异常");
        }
        return result;
    }

    /**
     * 删除视频资讯
     * @param id  视频资讯Id
     * @return
     */
    @DeleteMapping("/delete")
    public RestApiResponse<VideoEntity> delete(@RequestParam(required = true) int id) {
        RestApiResponse<VideoEntity> result = new RestApiResponse<VideoEntity>();
        try {
            VideoEntity videoEntity = videoService.delete(id);
            if (videoEntity == null) {
                result.failedApiResponse(Const.FAILED, "该视频资讯不存在，删除失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, videoEntity, "删除成功");
        } catch (Exception e) {
            logger.warn("删除视频资讯异常:", e);
            result.failedApiResponse(Const.FAILED, "删除视频资讯异常");
        }
        return result;
    }

    /**
     * 修改视频资讯
     */
    @PutMapping("/update")
    public RestApiResponse<VideoEntity> update(@RequestBody VideoEntity videoEntity) {
        RestApiResponse<VideoEntity> result = new RestApiResponse<VideoEntity>();
        try {
            OrganizationEntity organizationEntity = organizationService.findOne(videoEntity.getOrganizationId());
            if (organizationEntity != null) {
                result.failedApiResponse(Const.FAILED, "指定的机构不存在");
                return result;
            }
            VideoEntity entity = videoService.findOne(videoEntity.getId());
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "该视频资讯不存在，修改失败");
                return result;
            }
            VideoEntity video = videoService.update(videoEntity);
            result.successResponse(Const.SUCCESS, video, "修改成功");
        } catch (Exception e) {
            logger.warn("修改视频资讯异常", e);
            result.failedApiResponse(Const.FAILED, "修改视频资讯异常");
        }
        return result;
    }


}
