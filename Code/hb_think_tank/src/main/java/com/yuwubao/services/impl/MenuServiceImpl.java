package com.yuwubao.services.impl;

import com.yuwubao.entities.MenuEntity;
import com.yuwubao.entities.repository.MenuRepository;
import com.yuwubao.services.MenuService;
import com.yuwubao.services.contracts.Menu;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyu on 2017/10/17.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findAll() {
        List<MenuEntity> menuEntityList = menuRepository.findAll();
        List<Menu> list = new ArrayList<Menu>();
        ModelMapper mapper = new ModelMapper();
        for (MenuEntity menu : menuEntityList) {
            Menu m = mapper.map(menu, Menu.class);
            list.add(m);
        }
        return list;
    }
}
