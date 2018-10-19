package com.maxrocky.gundam.domain.user.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "user_userinfo")
public class UserInfo extends BaseVO {
    /*用户ID*/
    private String uId;
    /*用户密码*/
    private String uPassword;
    /*用户头像*/
    private String uLogo;
    /*用户性别*/
    private int uSex;
    /*用户生日*/
    private Date birthday;
    /*用户电话*/
    private String mobile;
    /*用户名*/
    private String uName;
    /*真实姓名*/
    private String uRealName;
    /*用户状态*/
    private int uState;
    /*职务*/
    private String post;
    /*创建时间*/
    private Date createDate;
    /*修改时间*/
    private Date modifyDate;
    /*权限ID*/
    private String roleId;
    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 100)
    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }
    @Basic
    @Column(name = "logo", nullable = true, insertable = true, updatable = true, length = 200)
    public String getuLogo() {
        return uLogo;
    }

    public void setuLogo(String uLogo) {
        this.uLogo = uLogo;
    }
    @Basic
    @Column(name = "sex", nullable = true, insertable = true, updatable = true)
    public int getuSex() {
        return uSex;
    }

    public void setuSex(int uSex) {
        this.uSex = uSex;
    }
    @Basic
    @Column(name = "birthday", nullable = true, insertable = true, updatable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    @Basic
    @Column(name = "mobile", nullable = true, insertable = true, updatable = true, length = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Basic
    @Column(name = "user_name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
    @Basic
    @Column(name = "real_name", nullable = true, insertable = true, updatable = true, length = 30)
    public String getuRealName() {
        return uRealName;
    }

    public void setuRealName(String uRealName) {
        this.uRealName = uRealName;
    }
    @Basic
    @Column(name = "user_state", nullable = true, insertable = true, updatable = true)
    public int getuState() {
        return uState;
    }

    public void setuState(int uState) {
        this.uState = uState;
    }
    @Basic
    @Column(name = "post", nullable = true, insertable = true, updatable = true)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    @Basic
    @Column(name = "create_date", nullable = true, insertable = true, updatable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Basic
    @Column(name = "modify_date", nullable = true, insertable = true, updatable = true)
    public Date getModifyDate() {
        return modifyDate;
    }
    @Basic
    @Column(name = "role_id", nullable = false, insertable = true, updatable = true)
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
