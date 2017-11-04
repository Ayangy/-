package com.yuwubao.services.impl;

import com.yuwubao.entities.RoleMenuEntity;
import com.yuwubao.entities.repository.RoleMenuRepository;
import com.yuwubao.services.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyu on 2017/10/17.
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Override
    public void deleteByRoleId(int roleId) {
        roleMenuRepository.deleteByRoleId(roleId);
    }

    @Override
    public void saveRoleMenus(List<RoleMenuEntity> roleMenus) {
        for (RoleMenuEntity rolemenu : roleMenus) {
            roleMenuRepository.save(rolemenu);
        }
    }

}
