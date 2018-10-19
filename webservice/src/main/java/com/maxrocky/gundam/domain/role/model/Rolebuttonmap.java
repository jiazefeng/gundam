package com.maxrocky.gundam.domain.role.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "role_rolebuttonmap")
@IdClass(RolebuttonmapPK.class)
public class Rolebuttonmap extends BaseVO {
    private String roleId;
    private String buttonId;
    private String menuId;
    private String buttonState;
    private Date makeDate;
    private Date modifyDate;

    @Id
    @Column(name = "RoleId", nullable = false, insertable = true, updatable = true, length = 32)
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "ButtonId", nullable = false, insertable = true, updatable = true, length = 32)
    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    @Id
    @Column(name = "MenuId", nullable = true, insertable = true, updatable = true, length = 32)
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "ButtonState", nullable = true, insertable = true, updatable = true, length = 2)
    public String getButtonState() {
        return buttonState;
    }

    public void setButtonState(String buttonState) {
        this.buttonState = buttonState;
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
    @Column(name = "modifyDate", nullable = true, insertable = true, updatable = true)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rolebuttonmap that = (Rolebuttonmap) o;

        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (buttonId != null ? !buttonId.equals(that.buttonId) : that.buttonId != null) return false;
        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (buttonState != null ? !buttonState.equals(that.buttonState) : that.buttonState != null) return false;
        if (makeDate != null ? !makeDate.equals(that.makeDate) : that.makeDate != null) return false;
        if (modifyDate != null ? !modifyDate.equals(that.modifyDate) : that.modifyDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (buttonId != null ? buttonId.hashCode() : 0);
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        result = 31 * result + (buttonState != null ? buttonState.hashCode() : 0);
        result = 31 * result + (makeDate != null ? makeDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        return result;
    }
}
