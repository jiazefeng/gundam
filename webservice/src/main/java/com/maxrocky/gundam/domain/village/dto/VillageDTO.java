package com.maxrocky.gundam.domain.village.dto;//package com.maxrocky.gundam.service.village.jsonDto;


import com.maxrocky.gundam.page.PageInfoTools;

/**
 * Created by lizhipeng on 2016/5/26.
 * VillageDTO
 */
public class VillageDTO extends PageInfoTools {
    //小区ID
    private String districtId;
    //小区名称
    private String districtName;
    //管理员姓名
    private String user;
    //电话
    private String phone;
    //地址
    private String address;
    /*地图*/
    private String mapImg;
    /*机器人数量*/
    private int robotCount;
    /*方案数量*/
    private int strategyCount;
    /*任务组数量*/
    private int taskCount;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMapImg() {
        return mapImg;
    }

    public void setMapImg(String mapImg) {
        this.mapImg = mapImg;
    }

    public int getRobotCount() {
        return robotCount;
    }

    public void setRobotCount(int robotCount) {
        this.robotCount = robotCount;
    }

    public int getStrategyCount() {
        return strategyCount;
    }

    public void setStrategyCount(int strategyCount) {
        this.strategyCount = strategyCount;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
