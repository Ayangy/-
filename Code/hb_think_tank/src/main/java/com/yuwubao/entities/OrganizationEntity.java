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
     * 单位设置
     */
    private String institutionSetting;

    /**
     * 联系我们
     */
    private String contactUs;

    /**
     *  机构分类0(智库机构)，1(智库联盟)
     */
    private int type;

    /**
     * 是否屏蔽0(不屏蔽),1(屏蔽)
     */
    private int shield;

    /**
     * 地域名
     */
    private String territoryName;

    /**
     * 机构类型1（大学），2（政府）
     */
    private int organizationType;

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

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public int getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(int organizationType) {
        this.organizationType = organizationType;
    }

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }
}
