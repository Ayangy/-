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
    private String lead;

    /**
     * 单位专家
     */
    private String expert;

    /**
     * 单位设置
     */
    private String institutionSetting;



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

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getInstitutionSetting() {
        return institutionSetting;
    }

    public void setInstitutionSetting(String institutionSetting) {
        this.institutionSetting = institutionSetting;
    }
}
