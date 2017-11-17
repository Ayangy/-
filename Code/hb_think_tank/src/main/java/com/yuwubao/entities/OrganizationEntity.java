package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/10/23.
 * 机构实体
 */
@Entity
@Table(name = "organization")
public class OrganizationEntity {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 机构名
     */
    private String name;

    /**
     * 单位简介
     */
    private String intro;

    /**
     * 单位领导
     */
    private int lead;

    /**
     * 单位专家
     */
    private int expert;

    /**
     * 单位设置
     */
    private String institutionSetting;

    /**
     *  机构分类0(智库机构)，1(智库联盟)
     */
    private int type;

    /**
     * 是否屏蔽0(不屏蔽),1(屏蔽)
     */
    private int shield;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getLead() {
        return lead;
    }

    public void setLead(int lead) {
        this.lead = lead;
    }

    public int getExpert() {
        return expert;
    }

    public void setExpert(int expert) {
        this.expert = expert;
    }

    public String getInstitutionSetting() {
        return institutionSetting;
    }

    public void setInstitutionSetting(String institutionSetting) {
        this.institutionSetting = institutionSetting;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }
}
