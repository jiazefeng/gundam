package com.maxrocky.gundam.domain.role.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "role_roleanthority")
//@IdClass(UserroleauthorityPK.class)
public class Userroleauthority extends BaseVO {
    private String userId;
    private String roleId;

    @Id
    @Column(name = "UserId", nullable = false, insertable = true, updatable = true, length = 50)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "RoleId", nullable = false, insertable = true, updatable = true, length = 50)
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Userroleauthority that = (Userroleauthority) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
