package com.yuwubao.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yangyu on 2017/12/26.
 */
@Entity
@Table(name = "questionnaire")
public class QuestionnaireEntity {

    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 前端用户id
     */
    private int clientUserId;

    /**
     * 前端用户名
     */
    private String clientUserName;

    /**
     * 文章id
     */
    private int articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 调查问卷结果Url
     */
    private String QuestionnaireResultUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(int clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getQuestionnaireResultUrl() {
        return QuestionnaireResultUrl;
    }

    public void setQuestionnaireResultUrl(String questionnaireResultUrl) {
        QuestionnaireResultUrl = questionnaireResultUrl;
    }
}
