package com.maxrocky.gundam.domain.role.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name ="role_viewmodel")
public class Viewmodel extends BaseVO {
    private String menuId;
    private String menuName;
    private String menuDescription;
    private String menuState;  //菜单状态01可用
    private String operator;
    private String runscript;  //执行脚本
    private String menulevel;  //菜单等级
    private String parantmenuid;  //父级菜单ID
    private String childFlag;   //表示子菜单
    private String menuorder;   //排序号
    private String owner ;      //所属
    private String icon;        //icon


    @Id
    @Column(name = "MenuId", nullable = false, insertable = true, updatable = true, length = 32)
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }


    @Basic
    @Column(name = "Owner", nullable = true, insertable = true, updatable = true, length = 30)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "MenuName", nullable = true, insertable = true, updatable = true, length = 30)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "MenuDescription", nullable = true, insertable = true, updatable = true, length = 50)
    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    @Basic
    @Column(name = "MenuState", nullable = true, insertable = true, updatable = true, length = 2)
    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
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
    @Column(name = "Runscript", nullable = true, insertable = true, updatable = true, length = 200)
    public String getRunscript() {
        return runscript;
    }

    public void setRunscript(String runscript) {
        this.runscript = runscript;
    }

    @Basic
    @Column(name = "Menulevel", nullable = true, insertable = true, updatable = true, length = 20)
    public String getMenulevel() {
        return menulevel;
    }

    public void setMenulevel(String menulevel) {
        this.menulevel = menulevel;
    }

    @Basic
    @Column(name = "Parantmenuid", nullable = true, insertable = true, updatable = true, length = 32)
    public String getParantmenuid() {
        return parantmenuid;
    }

    public void setParantmenuid(String parantmenuid) {
        this.parantmenuid = parantmenuid;
    }

    @Basic
    @Column(name = "ChildFlag", nullable = true, insertable = true, updatable = true, length = 2)
    public String getChildFlag() {
        return childFlag;
    }

    public void setChildFlag(String childFlag) {
        this.childFlag = childFlag;
    }

    @Basic
    @Column(name = "Menuorder", nullable = true, insertable = true, updatable = true, length = 4)
    public String getMenuorder() {
        return menuorder;
    }

    public void setMenuorder(String menuorder) {
        this.menuorder = menuorder;
    }

    @Basic
    @Column(name = "icon", nullable = true, insertable = true, updatable = true, length = 50)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
