package com.maxrocky.gundam.domain.role.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by lizhipeng on 2016/5/23.
 */
public class RolesetmapPK implements Serializable {
    private String rolesetid;
    private String roleid;


    @Column(name = "Rolesetid", nullable = false, insertable = true, updatable = true, length = 50)
    @Id
    public String getRolesetid() {
        return rolesetid;
    }

    public void setRolesetid(String rolesetid) {
        this.rolesetid = rolesetid;
    }



    @Column(name = "Roleid", nullable = false, insertable = true, updatable = true, length = 50)
    @Id
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

        RolesetmapPK that = (RolesetmapPK) o;

        if (rolesetid != null ? !rolesetid.equals(that.rolesetid) : that.rolesetid != null) return false;
        return !(roleid != null ? !roleid.equals(that.roleid) : that.roleid != null);


    }

    @Override
    public int hashCode() {
        int result = rolesetid.hashCode();
        result = 31 * result + roleid.hashCode();
        return result;
    }
}
