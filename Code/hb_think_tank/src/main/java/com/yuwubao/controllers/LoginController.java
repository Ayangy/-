package com.yuwubao.controllers;

import com.yuwubao.entities.UserEntity;
import com.yuwubao.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangyu on 2017/10/19.
 */
@Controller
@Transactional
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 登陆页
     */
    @RequestMapping("/sys/toLogin")
    public String index() {
        return "login";
    }

    /**
     * 后台用户登陆
     */
    @RequestMapping(value = "/login")
    public String sysLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (!StringUtils.isNotBlank(username)) {
                model.addAttribute("msg", "账号不能为空");
                return "login";
            }
            if (!StringUtils.isNotBlank(password)) {
                model.addAttribute("msg", "密码不能为空");
                return "login";
            }
            UserEntity userEntity = userService.findByUsernameAndPassword(username, password);
            if (userEntity == null) {
                model.addAttribute("msg", "账号或密码错误");
                return "login";
            }
            model.addAttribute("user", userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

}
