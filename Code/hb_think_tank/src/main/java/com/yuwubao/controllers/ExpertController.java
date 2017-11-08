package com.yuwubao.controllers;

import com.yuwubao.entities.ExpertEntity;
import com.yuwubao.services.ExpertService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/3.
 */
@RestController
@RequestMapping("/expert")
@CrossOrigin
public class ExpertController {

    private final static Logger logger = LoggerFactory.getLogger(ExpertController.class);

    @Autowired
    private ExpertService expertService;

    /**
     * 查询专家列表
     * @param index 第几页
     * @param size  每页几条
     * @param field  查询字段
     * @param keyword  查询值
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<ExpertEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                                       @RequestParam(defaultValue = "10", required = false)int size,
                                                       @RequestParam(required = false, defaultValue = "")String field,
                                                       @RequestParam(required = false, defaultValue = "")String keyword){
        RestApiResponse<Page<ExpertEntity>> result = new RestApiResponse<Page<ExpertEntity>>();
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<ExpertEntity> list = expertService.findAll(map, pageAble);
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("查询专家列表异常", e);
            result.failedApiResponse(Const.FAILED, "专家列表查询异常");
        }
        return result;
    }

    /**
     * 新增专家
     */
    @PostMapping("/add")
    public RestApiResponse<ExpertEntity> add(@RequestBody ExpertEntity expertEntity) {
        RestApiResponse<ExpertEntity> result = new RestApiResponse<ExpertEntity>();
        try {
            ExpertEntity expert = expertService.add(expertEntity);
            if (expert == null) {
                result.failedApiResponse(Const.FAILED, "添加失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, expert, "添加成功");
        } catch (Exception e) {
            logger.warn("新增专家异常:", e);
            result.failedApiResponse(Const.FAILED, "新增专家异常");
        }
        return result;
    }

    /**
     * 删除专家
     * @param id  专家Id
     * @return
     */
    @DeleteMapping("/delete")
    public RestApiResponse<ExpertEntity> delete(@RequestParam(required = true) int id) {
        RestApiResponse<ExpertEntity> result = new RestApiResponse<ExpertEntity>();
        try {
            ExpertEntity expertEntity = expertService.delete(id);
            if (expertEntity == null) {
                result.failedApiResponse(Const.FAILED, "专家不存在，删除失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, expertEntity, "删除成功");
        } catch (Exception e) {
            logger.warn("删除专家异常:", e);
            result.failedApiResponse(Const.FAILED, "删除专家异常");
        }
        return result;
    }

    /**
     * 修改专家
     */
    @PutMapping("/update")
    public RestApiResponse<ExpertEntity> update(@RequestBody ExpertEntity expertEntity) {
        RestApiResponse<ExpertEntity> result = new RestApiResponse<ExpertEntity>();
        try {
            ExpertEntity entity = expertService.findOne(expertEntity.getId());
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "该专家不存在，修改失败");
                return result;
            }
            ExpertEntity expert = expertService.update(expertEntity);
            result.successResponse(Const.SUCCESS, expert, "修改成功");
        } catch (Exception e) {
            logger.warn("修改专家异常", e);
            result.failedApiResponse(Const.FAILED, "修改专家异常");
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
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("获取所有智库专家异常", e);
            result.failedApiResponse(Const.FAILED, "获取所有智库专家异常");
        }
        return result;
    }

}
