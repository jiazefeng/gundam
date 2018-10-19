package com.maxrocky.gundam.domain.village.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "village")
public class Village extends BaseVO{
    /*小区ID*/
    private String villageId;
    /*小区名称*/
    private String villageName;
    /*联系人*/
    private String userName;
    /*联系电话*/
    private String mobile;
    /*地址ַ*/
    private String address;
    /*地图*/
    private String mapImg;
    /*机器人数量*/
    private int robotCount;
    /*线路数量*/
    private int lineCount;
    /*任务数量*/
    private int taskCount;
    /*状态̬*/
    private int state;
    /*创建时间*/

    private Date createDate;
    /*修改时间*/
    private  Date modifyDate;
    /**
     * 地图Id
     */
    private int mapId;
    @Id
    @Column(name = "VILLAGE_ID", nullable = false, insertable = true, updatable = true, length = 30)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }
    @Basic
    @Column(name = "VILLAGE_NAME", nullable = true, insertable = true, updatable = true, length = 50)
    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
    @Basic
    @Column(name = "USER_NAME", nullable = true, insertable = true, updatable = true, length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Basic
    @Column(name = "MOBILE", nullable = true, insertable = true, updatable = true, length = 50)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Basic
    @Column(name = "ADDRESS", nullable = true, insertable = true, updatable = true, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Basic
    @Column(name = "MAPIMG", nullable = true, insertable = true, updatable = true, length = 50)
    public String getMapImg() {
        return mapImg;
    }

    public void setMapImg(String mapImg) {
        this.mapImg = mapImg;
    }
    @Basic
    @Column(name = "ROBOT_COUNT", nullable = true, insertable = true, updatable = true)
    public int getRobotCount() {
        return robotCount;
    }

    public void setRobotCount(int robotCount) {
        this.robotCount = robotCount;
    }
    @Basic
    @Column(name = "LINE_COUNT", nullable = true, insertable = true, updatable = true)
    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
    @Basic
    @Column(name = "TASK_COUNT", nullable = true, insertable = true, updatable = true)
    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
    @Basic
    @Column(name = "STATE", nullable = true, insertable = true, updatable = true)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    @Basic
    @Column(name = "CREATE_DATE", nullable = true, insertable = true, updatable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Basic
    @Column(name = "MODIFY_DATE", nullable = true, insertable = true, updatable = true)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    @Basic
    @Column(name = "MAP_ID", nullable = false, insertable = true, updatable = true)
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
