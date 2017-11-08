package com.yuwubao.controllers;

import com.yuwubao.entities.RoleMenuEntity;
import com.yuwubao.services.MenuService;
import com.yuwubao.services.RoleMenuService;
import com.yuwubao.services.contracts.Menu;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyu on 2017/10/17.
 */
@RestController
@RequestMapping("/sys/menu")
@Transactional
@CrossOrigin
public class MenuController {

    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 查询所有菜单
     */
    @GetMapping("/findAll")
    public RestApiResponse<List<Menu>> findAll() {
        RestApiResponse<List<Menu>> result = new RestApiResponse<List<Menu>>();
        List<Menu> menus = new ArrayList<Menu>();
        try {
            List<Menu> all = menuService.findAll();
            List<Menu> sonMenu = new ArrayList<Menu>();
            for (Menu menu : all) {
                if (menu.getMenuId() == -1) {
                    menus.add(menu);
                } else {
                    sonMenu.add(menu);
                }
            }
            for (Menu menu : menus) {
                for (Menu entity : sonMenu) {
                    if (menu.getId() == entity.getMenuId()) {
                        menu.getList().add(entity);
                    }
                }
            }
            result.successResponse(Const.SUCCESS, menus, "菜单加载成功");
        } catch (Exception e) {
            logger.warn("菜单加载异常", e);
            result.failedApiResponse(Const.FAILED, "菜单加载异常");
        }
        return result;
    }

    /**
     * 编辑角色菜单
     */
    @PostMapping("/editRoleRight")
    public RestApiResponse<Boolean> editRoleRight(@RequestParam int roleId, @RequestBody List<Integer> menuIds) {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            roleMenuService.deleteByRoleId(roleId);
            List<RoleMenuEntity> roleMenus = new ArrayList<RoleMenuEntity>();
            for (Integer menuId: menuIds) {
                RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setRoleId(roleId);
                roleMenuEntity.setMenuId(menuId);
                roleMenus.add(roleMenuEntity);
            }
            roleMenuService.saveRoleMenus(roleMenus);
            result.successResponse(Const.SUCCESS, true, "角色菜单设置成功");
        } catch (Exception e) {
            logger.warn("编辑角色菜单异常：", e);
            result.failedApiResponse(Const.FAILED, "编辑角色菜单异常");
        }

        return result;
    }

}
