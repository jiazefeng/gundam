package com.maxrocky.gundam.domain.user.dto;

/**
* Created by lizhipeng on 2016/5/31.
*/
public class LoginDto {
    //用户名
    private String userName;
    //密码
    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
