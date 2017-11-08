package com.yuwubao.services.impl;

import com.yuwubao.entities.TitleImgEntity;
import com.yuwubao.entities.repository.TitleImgRepository;
import com.yuwubao.services.TitleImgService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/8.
 */
@Service
public class TitleImgServiceImpl implements TitleImgService {

    @Autowired
    private TitleImgRepository titleImgRepository;

    @Override
    public Page<TitleImgEntity> findAll(Map<String, String> map, Pageable pageAble, Integer state) {
        String start = map.get("beginTime");
        String end = map.get("endTime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Specification<TitleImgEntity> spec = new Specification<TitleImgEntity>() {
            @Override
            public Predicate toPredicate(Root<TitleImgEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                Predicate predict = criteriaBuilder.conjunction();
                if (state != null) {
                    Path<Integer> path = root.get("state");
                    predict.getExpressions().add(criteriaBuilder.equal(path, String.valueOf(state)));
                }
                if (StringUtils.isNotBlank(start)) {
                    Path<Date> incept = root.get("addTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(incept, dateFormat.parse(start)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if (StringUtils.isNotBlank(end)) {
                    Path<Date> finish = root.get("addTime");
                    try {
                        predict.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(finish, dateFormat.parse(end)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return predict;
            }
        };
        return titleImgRepository.findAll(spec, pageAble);
    }

    @Override
    public TitleImgEntity add(TitleImgEntity titleImgEntity) {
        return titleImgRepository.save(titleImgEntity);
    }

    @Override
    public TitleImgEntity delete(int id) {
        TitleImgEntity entity = titleImgRepository.findOne(id);
        if (entity != null) {
            titleImgRepository.delete(id);
            return entity;
        }
        return null;
    }

    @Override
    public TitleImgEntity findById(int id) {
        return titleImgRepository.findOne(id);
    }

    @Override
    public TitleImgEntity update(TitleImgEntity titleImgEntity) {
        ModelMapper mapper = new ModelMapper();
        TitleImgEntity entity = mapper.map(titleImgEntity, TitleImgEntity.class);
        return titleImgRepository.save(entity);
    }

    @Override
    public List<TitleImgEntity> findByState(int state) {
        List<TitleImgEntity> list = titleImgRepository.findByState(state);
        return list;
    }
}
