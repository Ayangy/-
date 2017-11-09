package com.yuwubao.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yangyu on 2017/11/3.
 * 智库视频资讯
 */
@Entity
@Table( name = "video_news")
public class VideoEntity {

    /**
     * 主键、自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频详情内容
     */
    private String content;

    /**
     * 视频url
     */
    private String videoUrl;

    /**
     *
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否屏蔽0(未屏蔽), 1(屏蔽)
     */
    private int shield;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
