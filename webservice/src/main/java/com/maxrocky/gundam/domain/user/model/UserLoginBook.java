package com.maxrocky.gundam.domain.user.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by lizhipeng on 2016/6/12.
 */
@Entity
@Table(name = "user_loginbook")
public class UserLoginBook extends BaseVO {
    private String tookenId;
    private String unionId;
    private String loginType;
    private Date makeDate;
    private Time makeTime;
    private String userId;
    @Id
    @Column(name = "TookenId", nullable = false, insertable = true, updatable = true, length = 50)
    public String getTookenId() {
        return tookenId;
    }

    public void setTookenId(String tookenId) {
        this.tookenId = tookenId;
    }
    @Basic
    @Column(name = "UnionId", nullable = true, insertable = true, updatable = true, length = 50)
    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
    @Basic
    @Column(name = "LoginType", nullable = false, insertable = true, updatable = true, length = 20)
    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    @Basic
    @Column(name = "MakeDate", nullable = true, insertable = true, updatable = true)
    public Date getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(Date makeDate) {
        this.makeDate = makeDate;
    }
    @Basic
    @Column(name = "MakeTime", nullable = true, insertable = true, updatable = true)
    public Time getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Time makeTime) {
        this.makeTime = makeTime;
    }
    @Basic
    @Column(name = "UserId", nullable = true, insertable = true, updatable = true, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLoginBook that = (UserLoginBook) o;

        if (tookenId != null ? !tookenId.equals(that.tookenId) : that.tookenId != null) return false;
        if (loginType != null ? !loginType.equals(that.loginType) : that.loginType != null) return false;
        if (makeDate != null ? !makeDate.equals(that.makeDate) : that.makeDate != null) return false;
        if (makeTime != null ? !makeTime.equals(that.makeTime) : that.makeTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tookenId != null ? tookenId.hashCode() : 0;
        result = 31 * result + (loginType != null ? loginType.hashCode() : 0);
        result = 31 * result + (makeDate != null ? makeDate.hashCode() : 0);
        result = 31 * result + (makeTime != null ? makeTime.hashCode() : 0);
        return result;
    }

}
