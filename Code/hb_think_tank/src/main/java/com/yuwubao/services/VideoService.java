package com.yuwubao.services;

import com.yuwubao.entities.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/3.
 */
public interface VideoService {
    Page<VideoEntity> findAll(Map<String, String> map, Pageable pageAble);

    VideoEntity add(VideoEntity videoEntity);

    VideoEntity delete(int id);

    VideoEntity findOne(int id);

    VideoEntity update(VideoEntity videoEntity);

    List<VideoEntity> getNewsVideo(int index, int size);
}
