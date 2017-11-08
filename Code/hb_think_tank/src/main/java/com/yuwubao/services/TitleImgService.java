package com.yuwubao.services;

import com.yuwubao.entities.TitleImgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/8.
 */
public interface TitleImgService {
    Page<TitleImgEntity> findAll(Map<String, String> map, Pageable pageAble, Integer state);

    TitleImgEntity add(TitleImgEntity titleImgEntity);

    TitleImgEntity delete(int id);

    TitleImgEntity findById(int id);

    TitleImgEntity update(TitleImgEntity titleImgEntity);

    List<TitleImgEntity> findByState(int state);
}
