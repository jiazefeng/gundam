package com.maxrocky.gundam.domain.video.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * 视频信息
 * Created by jiazefeng on 2016/5/26.
 */
@Entity
@Table(name = "video_info")
public class VideoInfo extends BaseVO {
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

    @Id
    @Column(name = "video_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Basic
    @Column(name = "video_url", nullable = true, insertable = true, updatable = true, length = 100)
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Basic
    @Column(name = "video_name", nullable = true, insertable = true, updatable = true, length = 64)
    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Basic
    @Column(name = "uplod_date", nullable = true, insertable = true, updatable = true)
    public Date getUplodDate() {
        return uplodDate;
    }

    public void setUplodDate(Date uplodDate) {
        this.uplodDate = uplodDate;
    }

    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true, length = 6)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "robot_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoInfo)) return false;

        VideoInfo videoInfo = (VideoInfo) o;

        if (getStatus() != videoInfo.getStatus()) return false;
        if (getVideoId() != null ? !getVideoId().equals(videoInfo.getVideoId()) : videoInfo.getVideoId() != null)
            return false;
        if (getVideoUrl() != null ? !getVideoUrl().equals(videoInfo.getVideoUrl()) : videoInfo.getVideoUrl() != null)
            return false;
        if (getVideoName() != null ? !getVideoName().equals(videoInfo.getVideoName()) : videoInfo.getVideoName() != null)
            return false;
        if (getUplodDate() != null ? !getUplodDate().equals(videoInfo.getUplodDate()) : videoInfo.getUplodDate() != null)
            return false;
        return !(getRobotId() != null ? !getRobotId().equals(videoInfo.getRobotId()) : videoInfo.getRobotId() != null);

    }

    @Override
    public int hashCode() {
        int result = getVideoId() != null ? getVideoId().hashCode() : 0;
        result = 31 * result + (getVideoUrl() != null ? getVideoUrl().hashCode() : 0);
        result = 31 * result + (getVideoName() != null ? getVideoName().hashCode() : 0);
        result = 31 * result + (getUplodDate() != null ? getUplodDate().hashCode() : 0);
        result = 31 * result + getStatus();
        result = 31 * result + (getRobotId() != null ? getRobotId().hashCode() : 0);
        return result;
    }
}
