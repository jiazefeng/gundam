package com.maxrocky.gundam.domain.picture.model;

import javax.persistence.*;

/**
 * 图片与机器人关联
 * Created by jiazefeng on 2016/5/27.
 */
@Entity
@Table(name = "picture_robot")
public class PictureRobot {
    /**
     * 图片机器人ID
     */
    private String pictureRobotId;
    /**
     * 图片ID
     */
    private String pictureId;
    /**
     * 机器人ID
     */
    private String robotId;

    @Id
    @Column(name = "robot_id", nullable = false, insertable = true, updatable = true, length = 64)
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Basic
    @Column(name = "picture_robot_id", nullable = true, insertable = true, updatable = true, length = 64)
    public String getPictureRobotId() {
        return pictureRobotId;
    }

    public void setPictureRobotId(String pictureRobotId) {
        this.pictureRobotId = pictureRobotId;
    }

    @Basic
    @Column(name = "picture_id", nullable = true, insertable = true, updatable = true, length = 64)
    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }
}
