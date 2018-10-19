package com.maxrocky.gundam.domain.picture.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * 图片信息
 * Created by jiazefeng on 2016/5/27.
 */
@Entity
@Table(name = "picture_info")
public class PictureInfo extends BaseVO {
    /**
     * 图片ID
     */
    private String pictureId;
    /**
     * 图片名称
     */
    private String pictureName;
    /**
     * 图片URL
     */
    private String pictureUrl;
    /**
     * 图片状态
     */
    private String status;
    /**
     * 上传时间
     */
    private Date uplodDate;
    /**
     * 机器人ID
     */
    private String robotId;
    @Id
    @Column(name = "picture_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }
    @Basic
    @Column(name = "picture_name", nullable = true, insertable = true, updatable = true, length = 64)
    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
    @Basic
    @Column(name = "picture_url", nullable = true, insertable = true, updatable = true, length = 100)
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true, length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof PictureInfo)) return false;

        PictureInfo that = (PictureInfo) o;

        if (getPictureId() != null ? !getPictureId().equals(that.getPictureId()) : that.getPictureId() != null)
            return false;
        if (getPictureName() != null ? !getPictureName().equals(that.getPictureName()) : that.getPictureName() != null)
            return false;
        if (getPictureUrl() != null ? !getPictureUrl().equals(that.getPictureUrl()) : that.getPictureUrl() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) return false;
        if (getUplodDate() != null ? !getUplodDate().equals(that.getUplodDate()) : that.getUplodDate() != null)
            return false;
        return !(getRobotId() != null ? !getRobotId().equals(that.getRobotId()) : that.getRobotId() != null);

    }

    @Override
    public int hashCode() {
        int result = getPictureId() != null ? getPictureId().hashCode() : 0;
        result = 31 * result + (getPictureName() != null ? getPictureName().hashCode() : 0);
        result = 31 * result + (getPictureUrl() != null ? getPictureUrl().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getUplodDate() != null ? getUplodDate().hashCode() : 0);
        result = 31 * result + (getRobotId() != null ? getRobotId().hashCode() : 0);
        return result;
    }
}
