package com.maxrocky.gundam.domain.video.dto;

import com.maxrocky.gundam.page.PageInfoTools;

import java.util.Date;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public class VideoInfoDTo extends PageInfoTools {
    /*
    视频ID
     */
    private String videoId;
    /**
     * 视频URL
     */
    private String videoUrl;
    /**
     * 视频名称
     */
    private String videoName;
    /**
     * 上传时间
     */
    private Date uplodDate;
    /**
     * 视频状态
     */
    private int status;
    /**
     * 机器人ID
     */
    private String robotId;
    /**
     * 机器人名称
     */
    private String robotName;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Date getUplodDate() {
        return uplodDate;
    }

    public void setUplodDate(Date uplodDate) {
        this.uplodDate = uplodDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }
}
