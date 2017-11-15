package com.yuwubao.controllers;

import com.yuwubao.entities.OrganizationEntity;
import com.yuwubao.services.OrganizationService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/10/23.
 */
@RestController
@RequestMapping("/organization")
@Transactional
@CrossOrigin
public class OrganizationController {

    private final static Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    /**
     * 查询机构列表
     * @param index  第几页
     * @param size  每页几条
     * @param field  查询字段
     * @param keyword  查询值
     * @return
     */
    @GetMapping("/findAll")
    public RestApiResponse<Page<OrganizationEntity>> findAll(@RequestParam(defaultValue = "1", required = false)int index,
                                @RequestParam(defaultValue = "10", required = false)int size,
                                @RequestParam(required = false, defaultValue = "")String field,
                                @RequestParam(required = false, defaultValue = "")String keyword){
        RestApiResponse<Page<OrganizationEntity>> result = new RestApiResponse<Page<OrganizationEntity>>();
        try {
            Map<String, String> map = new HashMap();
            map.put("field", field);
            map.put("keyword", keyword);
            Pageable pageAble = new PageRequest(index - 1, size);
            Page<OrganizationEntity> list = organizationService.findAll(map, pageAble);
            result.successResponse(Const.SUCCESS, list, "机构列表查询成功");
        } catch (Exception e) {
            logger.warn("机构列表查询异常", e);
            result.failedApiResponse(Const.FAILED, "机构列表查询异常");
        }
        return result;
    }

    /**
     * 新增机构
     */
    @PostMapping("/add")
    public RestApiResponse<OrganizationEntity> add(@RequestBody OrganizationEntity organizationEntity) {
        RestApiResponse<OrganizationEntity> result = new RestApiResponse<OrganizationEntity>();
        try {
            OrganizationEntity organization = organizationService.add(organizationEntity);
            if (organization == null) {
                result.failedApiResponse(Const.FAILED, "添加机构失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, organization, "添加机构成功");
        } catch (Exception e) {
            logger.warn("新增机构异常", e);
            result.failedApiResponse(Const.FAILED, "新增机构异常");
        }
        return result;
    }

    /**
     * 删除机构
     * @param id  机构Id
     * @return
     */
    @DeleteMapping("/delete")
    public RestApiResponse<OrganizationEntity> delete(@RequestParam(required = true) int id) {
        RestApiResponse<OrganizationEntity> result = new RestApiResponse<OrganizationEntity>();
        try {
            OrganizationEntity organizationEntity = organizationService.delete(id);

            if (organizationEntity == null) {
                result.failedApiResponse(Const.FAILED, "该机构不存在，删除失败");
                return result;
            }
            result.successResponse(Const.SUCCESS, organizationEntity, "删除机构成功");
        } catch (Exception e) {
            logger.warn("删除机构异常", e);
            result.failedApiResponse(Const.FAILED, "删除机构异常");
        }
        return result;
    }

    /**
     * 修改机构
     */
    @PutMapping("/update")
    public RestApiResponse<OrganizationEntity> update(@RequestBody OrganizationEntity organizationEntity) {
        RestApiResponse<OrganizationEntity> result = new RestApiResponse<OrganizationEntity>();
        try {
            OrganizationEntity organization = organizationService.findOne(organizationEntity.getId());
            if (organization == null) {
                result.failedApiResponse(Const.FAILED, "修改失败，机构不存在");
                return result;
            }
            OrganizationEntity entity = organizationService.update(organizationEntity);
            result.successResponse(Const.SUCCESS, entity, "修改机构成功");
        } catch (Exception e) {
            logger.warn("修改机构异常", e);
            result.failedApiResponse(Const.FAILED, "修改机构异常");
        }
        return result;

    }

    /**
     * 无分页机构列表
     */
    @GetMapping("/getAll")
    public RestApiResponse<List<OrganizationEntity>> getAll() {
        RestApiResponse<List<OrganizationEntity>> result = new RestApiResponse<>();
        try {
            List<OrganizationEntity > list =  organizationService.getAll();
            if (list.size() == 0) {
                result.failedApiResponse(Const.FAILED, "机构列表为空");
                return result;
            }
            result.failedApiResponse(Const.SUCCESS, "获取列表成功");
            result.setResult(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }





    /**
     * 获取机构简介
     * @param id  机构Id
     * @return
     */
    @GetMapping("/getOrganization")
    public RestApiResponse<OrganizationEntity> getOrganizationEntity(@RequestParam int id) {
        RestApiResponse<OrganizationEntity> result = new RestApiResponse<OrganizationEntity>();
        try {
            OrganizationEntity entity = organizationService.findOne(id);
            if (entity == null) {
                result.failedApiResponse(Const.FAILED, "当前机构不存在");
                return result;
            }
            result.successResponse(Const.SUCCESS, entity);
        } catch (Exception e) {
            logger.warn("获取机构简介异常：", e);
            result.failedApiResponse(Const.FAILED, "获取机构简介异常");
        }
        return result;
    }
}
