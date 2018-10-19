package com.maxrocky.gundam.domain.role.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by lizhipeng on 2016/5/23.
 */
public class RolebuttonmapPK implements Serializable {
    private String roleId;
    private String buttonId;
    private String menuId;
    @Column(name = "RoleId", nullable = false, insertable = true, updatable = true, length = 32)
    @Id
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Column(name = "ButtonId", nullable = false, insertable = true, updatable = true, length = 32)
    @Id
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolebuttonmapPK that = (RolebuttonmapPK) o;

        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (buttonId != null ? !buttonId.equals(that.buttonId) : that.buttonId != null) return false;

        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (buttonId != null ? buttonId.hashCode() : 0);

        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        return result;
    }
}
