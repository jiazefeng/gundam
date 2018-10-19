package com.maxrocky.gundam.domain.video.model;

import javax.persistence.*;

/**
 * 视频与机器人关联表
 * Created by jiazefeng on 2016/5/26.
 */
@Entity
@Table(name = "video_robot")
public class VideoRobot {
    /**
     * 视频与机器人关联id
     */
    private String videoRobotId;
    /**
     * 视频ID
     */
    private String videoId;
    /**
     * 机器人ID
     */
    private String robotId;

    @Id
    @Column(name = "video_robot_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getVideoRobotId() {
        return videoRobotId;
    }

    public void setVideoRobotId(String videoRobotId) {
        this.videoRobotId = videoRobotId;
    }

    @Basic
    @Column(name = "video_id", nullable = true, insertable = true, updatable = true, length = 32)
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Basic
    @Column(name = "robot_id", nullable = true, insertable = true, updatable = true, length = 32)
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoRobot)) return false;

        VideoRobot that = (VideoRobot) o;

        if (getVideoRobotId() != null ? !getVideoRobotId().equals(that.getVideoRobotId()) : that.getVideoRobotId() != null)
            return false;
        if (getVideoId() != null ? !getVideoId().equals(that.getVideoId()) : that.getVideoId() != null) return false;
        return !(getRobotId() != null ? !getRobotId().equals(that.getRobotId()) : that.getRobotId() != null);

    }

    @Override
    public int hashCode() {
        int result = getVideoRobotId() != null ? getVideoRobotId().hashCode() : 0;
        result = 31 * result + (getVideoId() != null ? getVideoId().hashCode() : 0);
        result = 31 * result + (getRobotId() != null ? getRobotId().hashCode() : 0);
        return result;
    }
}
