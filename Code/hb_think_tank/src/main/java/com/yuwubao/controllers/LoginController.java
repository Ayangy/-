package com.yuwubao.controllers;

import com.yuwubao.entities.ClientUserEntity;
import com.yuwubao.entities.UserEntity;
import com.yuwubao.entities.vo.UserVo;
import com.yuwubao.services.ClientUserService;
import com.yuwubao.services.UserService;
import com.yuwubao.util.Const;
import com.yuwubao.util.MD5;
import com.yuwubao.util.RestApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ClientUserService clientUserService;

    /**
     * 后台用户登陆
     */
    @PostMapping("sys/login")
    public RestApiResponse<UserEntity> sysLogin(@RequestBody UserEntity user,
                                                @RequestParam(required = false, defaultValue = "0")int type) {
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
            UserEntity userEntity = userService.findByUsernameAndPassword(username, password, type);
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

    /**
     * 前端用户登录
     * @param userVo
     * @return
     */
    @PostMapping("/login")
    public RestApiResponse<ClientUserEntity> login(@RequestBody UserVo userVo) {
        RestApiResponse<ClientUserEntity> result = new RestApiResponse<ClientUserEntity>();
        try {
            String username = userVo.getUsername();
            String pwd = MD5.md5(userVo.getPassword());
            ClientUserEntity clientUserEntity;
            if (userVo.getUsername().matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
                clientUserEntity = clientUserService.findByEmailAndPwd(username, pwd);
            }else {
                clientUserEntity = clientUserService.findByNameAndPwd(username, pwd);
            }
            if (clientUserEntity == null) {
                result.failedApiResponse(Const.FAILED, "账号或密码错误");
                return result;
            }
            if(clientUserEntity.getStatus() == 0){
                result.failedApiResponse(Const.FAILED, "账号未激活");
                return result;
            }
            result.successResponse(Const.SUCCESS, clientUserEntity);
        } catch (Exception e) {
            logger.warn("登录异常", e);
            result.failedApiResponse(Const.FAILED, "登录异常");
        }
        return result;
    }

}
