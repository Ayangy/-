package com.yuwubao.controllers;

import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import com.yuwubao.util.ThinkTankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by yangyu on 2017/11/21.
 */
@RestController
@RequestMapping("/data")
@Transactional
@CrossOrigin
public class DataController {

    private final static Logger logger = LoggerFactory.getLogger(DataController.class);

    @Value("${backupDataConfig}")
    private String backupDataConfig;

    @Value("${recoverDataConfig}")
    private String recoverDataConfig;

    /**
     * 数据库备份
     * @return
     */
    @PostMapping("/backup")
    public RestApiResponse<Boolean> backup() {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            ThinkTankUtil.backup("d:\\think_tank.sql", backupDataConfig);
            result.successResponse(Const.SUCCESS, true, "数据库备份成功");
        } catch (IOException e) {
            logger.warn("数据库备份失败", e);
            result.failedApiResponse(Const.FAILED, "数据库备份失败");
        }
        return result;
    }

    /**
     * 数据库恢复
     * @return
     */
    @PostMapping("/recover")
    public RestApiResponse<Boolean> recover() {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            ThinkTankUtil.recover("d:\\think_tank.sql", recoverDataConfig);
            result.successResponse(Const.SUCCESS, true, "数据库恢复成功");
        } catch (IOException e) {
            logger.warn("数据库恢复失败", e);
            result.failedApiResponse(Const.FAILED, "数据库恢复失败");
        }
        return result;
    }

}
