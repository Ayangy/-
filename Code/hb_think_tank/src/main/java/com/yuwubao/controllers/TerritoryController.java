package com.yuwubao.controllers;

import com.yuwubao.entities.TerritoryEntity;
import com.yuwubao.services.TerritoryService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangyu on 2017/11/30.
 */
@RestController
@RequestMapping("/territory")
@Transactional
@CrossOrigin
public class TerritoryController {

    private final static Logger logger = LoggerFactory.getLogger(TerritoryController.class);

    @Autowired
    private TerritoryService territoryService;

    /**
     * 获取地域列表
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<List<TerritoryEntity>> findAll() {
        RestApiResponse<List<TerritoryEntity>> result = new RestApiResponse<List<TerritoryEntity>>();
        try {
            List<TerritoryEntity> list = territoryService.findAll();
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("地域列表查询异常", e);
            result.failedApiResponse(Const.FAILED, "地域列表查询异常");
        }
        return result;
    }


}
