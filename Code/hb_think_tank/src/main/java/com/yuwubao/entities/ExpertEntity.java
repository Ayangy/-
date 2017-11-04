package com.yuwubao.entities;

import javax.persistence.*;

/**
 * Created by yangyu on 2017/11/3.
 * 智库专家实体
 */
@Entity
@Table( name = "zk_expert")
public class ExpertEntity {
    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 专家照片
     */
    private String img;

    /**
     * 专家姓名
     */
    private String name;

    /**
     * 专家职位
     */
    private String position;

    /**
     *  所属机构Id
     */
    private int organizationId;

    /**
     *  个人简历
     */
    private String resume;

    /**
     * 研究领域
     */
    private String research;

    /**
     * 教育背景
     */
    private String educational;

    /**
     * 内部报告
     */
    private String report;

    /**
     * 学术论文
     */
    private String academicPaper;

    /**
     * 学术著作
     */
    private String composition;

    /**
     * 研究项目
     */
    private String researchProject;

    /**
     * 学术兼职
     */
    private String partTimeJob;

    /**
     * 社会荣誉
     */
    private String socialPrestige;

    /**
     * 研究成果
     */
    private String researchResult;

    /**
     * 联系方式
     */
    private String contactWay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public String getEducational() {
        return educational;
    }

    public void setEducational(String educational) {
        this.educational = educational;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getAcademicPaper() {
        return academicPaper;
    }

    public void setAcademicPaper(String academicPaper) {
        this.academicPaper = academicPaper;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getResearchProject() {
        return researchProject;
    }

    public void setResearchProject(String researchProject) {
        this.researchProject = researchProject;
    }

    public String getPartTimeJob() {
        return partTimeJob;
    }

    public void setPartTimeJob(String partTimeJob) {
        this.partTimeJob = partTimeJob;
    }

    public String getSocialPrestige() {
        return socialPrestige;
    }

    public void setSocialPrestige(String socialPrestige) {
        this.socialPrestige = socialPrestige;
    }

    public String getResearchResult() {
        return researchResult;
    }

    public void setResearchResult(String researchResult) {
        this.researchResult = researchResult;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
}
