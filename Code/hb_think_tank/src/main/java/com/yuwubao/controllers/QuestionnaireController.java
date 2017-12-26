package com.yuwubao.controllers;

import com.yuwubao.entities.QuestionnaireEntity;
import com.yuwubao.services.QuestionnaireService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyu on 2017/12/26.
 */
@RestController
@RequestMapping("/questionnaire")
@Transactional
@CrossOrigin
public class QuestionnaireController {

    private final static Logger logger = LoggerFactory.getLogger(QuestionnaireController.class);

    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 查询问卷列表
     * @param index  第几页
     * @param size  每页几条
     * @param articleTitle 文章标题
     * @param clientUserName  前端用户名
     * @param beginTime  开始时间
     * @param endTime  结束时间
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<QuestionnaireEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                                              @RequestParam(defaultValue = "10", required = false)int size,
                                                              @RequestParam(required = false, defaultValue = "") String articleTitle,
                                                              @RequestParam(required = false, defaultValue = "")String clientUserName,
                                                              @RequestParam(required = false, defaultValue = "")String beginTime,
                                                              @RequestParam(required = false, defaultValue = "")String endTime) {
        RestApiResponse<Page<QuestionnaireEntity>> result = new RestApiResponse<Page<QuestionnaireEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("articleTitle", articleTitle);
            map.put("clientUserName", clientUserName);
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<QuestionnaireEntity> list = questionnaireService.findAll(map, pageAble);
            result.successResponse(Const.SUCCESS, list, "获取列表成功");
        } catch (Exception e) {
            logger.warn("问卷列表查询异常", e);
            result.failedApiResponse(Const.FAILED, "问卷列表查询异常");
        }
        return result;
    }

}
