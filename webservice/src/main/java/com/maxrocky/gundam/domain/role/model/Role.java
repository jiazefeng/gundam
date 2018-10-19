package com.maxrocky.gundam.domain.role.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "role_role")
public class Role extends BaseVO {

    private String roleId;
    private String roleName;
    private String roleDescription;
    private Date makeDate;
    private Date modifyDate;
    private String operator;
    private String roleSetId;
    private String roleDesc;
    private int state;
    private int degree;//等级

    @Id
    @Column(name = "RoleId", nullable = false, insertable = true, updatable = true, length = 32)
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "RoleName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "RoleDescription", nullable = true, insertable = true, updatable = true, length = 200)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
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
    @Column(name = "ModifyDate", nullable = true, insertable = true, updatable = true)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Basic
    @Column(name = "Operator", nullable = true, insertable = true, updatable = true, length = 30)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


    @Basic
    @Column(name = "RoleSetId", nullable = false, insertable = true, updatable = true, length = 32)
    public String getRoleSetId() {
        return roleSetId;
    }

    public void setRoleSetId(String roleSetId) {
        this.roleSetId = roleSetId;
    }

    @Basic
    @Column(name = "roledesc", nullable = true, insertable = true, updatable = true, length = 200)
    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Basic
    @Column(name = "state", nullable = true, insertable = true, updatable = true, length = 2)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    @Basic
    @Column(name = "degree", nullable = true, insertable = true, updatable = true, length = 2)
    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
