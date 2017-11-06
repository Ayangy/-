package com.yuwubao.services.impl;

import com.yuwubao.entities.VideoEntity;
import com.yuwubao.entities.repository.VideoRepository;
import com.yuwubao.services.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/3.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<VideoEntity> findAll(Map<String, String> map, Pageable pageAble) {
        String field = map.get("field");
        String keyword = map.get("keyword");
        String start = map.get("beginTime");
        String end = map.get("endTime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Specification<VideoEntity> spec = new Specification<VideoEntity>() {
            @Override
            public Predicate toPredicate(Root<VideoEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                Predicate predict = criteriaBuilder.conjunction();
                if (StringUtils.isNotBlank(field)) {
                    Path<String> exp1 = root.get(field);
                    if (StringUtils.isNotBlank(keyword)) {
                        predict.getExpressions().add(criteriaBuilder.like(exp1, "%" + keyword + "%"));
                    }
                }

                if (StringUtils.isNotBlank(start)) {
                    Path<Date> incept = root.get("createTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(incept, dateFormat.parse(start)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if (StringUtils.isNotBlank(end)) {
                    Path<Date> finish = root.get("createTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(finish, dateFormat.parse(end)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return predict;
            }
        };
        return videoRepository.findAll(spec, pageAble);
    }

    @Override
    public VideoEntity add(VideoEntity videoEntity) {
        return videoRepository.save(videoEntity);
    }

    @Override
    public VideoEntity delete(int id) {
        VideoEntity entity = videoRepository.findOne(id);
        if (entity == null) {
            return null;
        }
        videoRepository.delete(id);
        return entity;
    }

    @Override
    public VideoEntity findOne(int id) {
        return videoRepository.findOne(id);
    }

    @Override
    public VideoEntity update(VideoEntity videoEntity) {
        ModelMapper mapper = new ModelMapper();
        VideoEntity entity = mapper.map(videoEntity, VideoEntity.class);
        return  videoRepository.save(entity);
    }

    @Override
    public VideoEntity getNewsVideo(){
        String sql = "select * from video_news order by createTime desc limit 0, 1";
        RowMapper<VideoEntity> rowMapper = new BeanPropertyRowMapper<>(VideoEntity.class);
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }
}
