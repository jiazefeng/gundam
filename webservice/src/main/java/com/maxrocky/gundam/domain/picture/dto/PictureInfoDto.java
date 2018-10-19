package com.maxrocky.gundam.domain.picture.dto;

import com.maxrocky.gundam.page.PageInfoTools;

import java.util.Date;

/**
 * Created by jiazefeng on 2016/5/27.
 */
public class PictureInfoDto  extends PageInfoTools {
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
     * 上传时间
     */
    private Date uplodDate;
    /**
     * 机器人ID
     */
    private String robotId;
    /**
     * 机器人名称
     */
    private String robotName;

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getUplodDate() {
        return uplodDate;
    }

    public void setUplodDate(Date uplodDate) {
        this.uplodDate = uplodDate;
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
