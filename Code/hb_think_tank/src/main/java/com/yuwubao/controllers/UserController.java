package com.yuwubao.controllers;

import com.yuwubao.entities.UserEntity;
import com.yuwubao.entities.UserRoleEntity;
import com.yuwubao.services.UserRoleService;
import com.yuwubao.services.UserService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/17.
 */
@RestController
@RequestMapping("/sys/user")
@Transactional
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 查询用户
     * @param index 第几页
     * @param size  每页几条
     * @param field  查询字段
     * @param keyword  查询值
     * @param beginTime 开始时间
     * @param endTime  结束时间
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<UserEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                                    @RequestParam(defaultValue = "10", required = false)int size,
                                                    @RequestParam(required = false, defaultValue = "")String field,
                                                    @RequestParam(required = false, defaultValue = "")String keyword,
                                                    @RequestParam(required = false, defaultValue = "")String beginTime,
                                                    @RequestParam(required = false, defaultValue = "")String endTime){
        RestApiResponse<Page<UserEntity>> result = new RestApiResponse<Page<UserEntity>>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("field", field);
            map.put("keyword", keyword);
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<UserEntity> list = userService.findAll(map, pageAble);
            if (list.getContent().size() == 0) {
                result.failedApiResponse(Const.FAILED, "暂无数据");
                return result;
            }
            result.successResponse(Const.SUCCESS, list);
        } catch (Exception e) {
            logger.warn("查询用户列表异常", e);
            result.failedApiResponse(Const.FAILED, "用户列表查询异常");
        }
        return result;
    }

    /**
     * 新增用户
     */
    @PostMapping("/add")
    public RestApiResponse<UserEntity> add(@RequestBody UserEntity userEntity) {
        RestApiResponse<UserEntity> result = new RestApiResponse<UserEntity>();
        try {
            UserEntity entity = userService.findByUsername(userEntity.getUsername());
            if (entity != null) {
                result.failedApiResponse(Const.FAILED, "用户名已存在");
                return result;
            }
            userEntity.setCreateTime(new Date());
            UserEntity user = userService.add(userEntity);
            if (user == null) {
                result.failedApiResponse(Const.FAILED, "添加失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, user, "添加成功");
        } catch (Exception e) {
            logger.warn("新增用户异常:", e);
            result.failedApiResponse(Const.FAILED, "新增用户异常");
        }
        return result;
    }

    /**
     * 删除用户
     * @param id  用户Id
     * @return
     */
    @DeleteMapping("/delete")
    public RestApiResponse<UserEntity> delete(@RequestParam(required = true) int id) {
        RestApiResponse<UserEntity> result = new RestApiResponse<UserEntity>();
        try {
            UserEntity userEntity = userService.delete(id);
            if (userEntity == null) {
                result.failedApiResponse(Const.FAILED, "该用户不存在，删除失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, userEntity, "删除成功");
        } catch (Exception e) {
            logger.warn("删除用户异常:", e);
            result.failedApiResponse(Const.FAILED, "删除用户异常");
        }
        return result;
    }

    /**
     * 修改用户
     */
    @PutMapping("/update")
    public RestApiResponse<UserEntity> update(@RequestBody UserEntity userEntity) {
        RestApiResponse<UserEntity> result = new RestApiResponse<UserEntity>();
        try {
            UserEntity entity = userService.findById(userEntity.getId());
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "修改失败，用户不存在");
                return result;
            }
            UserEntity u = userService.findByUsername(userEntity.getUsername());
            if (u != null) {
                result.failedApiResponse(Const.FAILED, "修改失败，用户名已存在");
                return result;
            }
            UserEntity user = userService.update(userEntity);
            result.successResponse(Const.SUCCESS, user, "修改成功");
        } catch (Exception e) {
            logger.warn("修改用户异常", e);
            result.failedApiResponse(Const.FAILED, "修改用户异常");
        }
        return result;

    }

    /**
     * 给用户指定角色
     * @param userId  用户Id
     * @param roleId  角色Id
     * @return
     */
    @PostMapping("/updateAuthedUserRole")
    public RestApiResponse<UserRoleEntity> editUserRole(@RequestParam int userId,@RequestParam int roleId) {
        RestApiResponse<UserRoleEntity> result = new RestApiResponse<UserRoleEntity>();
        try {
            UserEntity user = userService.findById(userId);
            if (user == null) {
                result.failedApiResponse(Const.FAILED, "用户不存在，无法指定角色");
                return result;
            }
            UserRoleEntity entity = userRoleService.findByUserId(userId);
            if (entity == null) {
                UserRoleEntity userRole = new UserRoleEntity();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleService.save(userRole);
                result.successResponse(Const.SUCCESS, userRole, "指定用户角色成功");
                return result;
            }
            entity.setRoleId(roleId);
            UserRoleEntity userRoleEntity = userRoleService.save(entity);
            result.successResponse(Const.SUCCESS, userRoleEntity, "修改用户角色成功");

        } catch (Exception e) {
            logger.warn("指定用户角色异常：", e);
            result.failedApiResponse(Const.FAILED, "指定用户角色异常");
        }
        return result;
    }

}
