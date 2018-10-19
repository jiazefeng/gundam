package com.maxrocky.gundam.domain.role.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "role_rolesetmap")
@IdClass(RolesetmapPK.class)
public class Rolesetmap extends BaseVO{
    private String rolesetid;
    private String roleid;

    @Id
    @Column(name = "Rolesetid", nullable = false, insertable = true, updatable = true, length = 50)
    public String getRolesetid() {
        return rolesetid;
    }

    public void setRolesetid(String rolesetid) {
        this.rolesetid = rolesetid;
    }


    @Id
    @Column(name = "Roleid", nullable = false, insertable = true, updatable = true, length = 50)
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rolesetmap that = (Rolesetmap) o;

        if (rolesetid != null ? !rolesetid.equals(that.rolesetid) : that.rolesetid != null) return false;
        return !(roleid != null ? !roleid.equals(that.roleid) : that.roleid != null);

    }

    @Override
    public int hashCode() {
        int result = rolesetid != null ? rolesetid.hashCode() : 0;
        result = 31 * result + (roleid != null ? roleid.hashCode() : 0);
        return result;
    }
}
