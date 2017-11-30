package com.yuwubao.services.impl;

import com.yuwubao.entities.TerritoryEntity;
import com.yuwubao.entities.repository.TerritoryRepository;
import com.yuwubao.services.TerritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyu on 2017/11/30.
 */
@Service
public class TerritoryServiceImpl implements TerritoryService {

    @Autowired
    private TerritoryRepository territoryRepository;

    @Override
    public List<TerritoryEntity> findAll() {
        return territoryRepository.findAll();
    }
}
