package com.maxrocky.gundam.domain.user.dto;//package com.maxrocky.gundam.service.user.jsonDto;

import com.maxrocky.gundam.page.PageInfoTools;

import java.util.Date;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/24.
 */
public class UserInfoDTO extends PageInfoTools {
    /*用户ID*/
    private String Id;
    /*用户头像*/
    private String uLogo;
    /*用户名*/
    private String uName;
    /*服务小区*/
    private String village;
    /*职务*/
    private String post;
    /*角色*/
    private String role;
    /*电话*/
    private String mobile;
    /*密码*/
    private String pwd;
    /*性别*/
    private int sex;
    /*出生日期*/
    private Date birthday;
    /*添加小区集合*/
    private List villageList;

    /*标识是否是超级管理员*/
    private boolean ifadmin;
    /**
     * 新密码
     */
    private String newPwd;
    private int degree;//等级


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getuLogo() {
        return uLogo;
    }

    public void setuLogo(String uLogo) {
        this.uLogo = uLogo;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List getVillageList() {
        return villageList;
    }

    public void setVillageList(List villageList) {
        this.villageList = villageList;
    }

    public boolean getIfadmin() {
        return ifadmin;
    }

    public void setIfadmin(boolean ifadmin) {
        this.ifadmin = ifadmin;
    }
    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
