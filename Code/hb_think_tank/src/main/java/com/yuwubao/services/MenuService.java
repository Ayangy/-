package com.yuwubao.services;

import com.yuwubao.services.contracts.Menu;

import java.util.List;

/**
 * Created by yangyu on 2017/10/17.
 */
public interface MenuService {
    List<Menu> findAll();
}
