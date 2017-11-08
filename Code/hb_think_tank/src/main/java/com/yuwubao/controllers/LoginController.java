package com.yuwubao.controllers;

import com.yuwubao.entities.UserEntity;
import com.yuwubao.services.UserService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangyu on 2017/10/19.
 */
@RestController
@Transactional
@CrossOrigin
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 登陆页
     */
    /*@RequestMapping("/sys/toLogin")
    public String index() {
        return "login";
    }*/

    /**
     * 后台用户登陆
     */
    @PostMapping("/login")
    public RestApiResponse<UserEntity> sysLogin(@RequestBody UserEntity user) {
        RestApiResponse<UserEntity> result = new RestApiResponse<UserEntity>();
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            if (!StringUtils.isNotBlank(username)) {
                result.failedApiResponse(Const.FAILED, "账号不能为空");
                return result;
            }
            if (!StringUtils.isNotBlank(password)) {
                result.failedApiResponse(Const.FAILED, "密码不能为空");
                return result;
            }
            UserEntity userEntity = userService.findByUsernameAndPassword(username, password);
            if (userEntity == null) {
                result.failedApiResponse(Const.FAILED, "账号或密码错误");
                return result;
            }
            result.successResponse(Const.SUCCESS, userEntity, "登陆成功");
        } catch (Exception e) {
            logger.warn("登陆异常:", e);
            result.failedApiResponse(Const.FAILED, "登陆异常");
        }
        return result;
    }

}
