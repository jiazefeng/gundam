package com.maxrocky.gundam.domain.user.dto;

import com.maxrocky.gundam.domain.role.model.Viewmodel;

import java.util.List;

/**
 * Created by lizhipeng on 2016/7/13.
 */
public class SecondViewModel {
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
    private String icon; //icon

    private List<Viewmodel> threeMenuList;//三级菜单集合

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRunscript() {
        return runscript;
    }

    public void setRunscript(String runscript) {
        this.runscript = runscript;
    }

    public String getMenulevel() {
        return menulevel;
    }

    public void setMenulevel(String menulevel) {
        this.menulevel = menulevel;
    }

    public String getParantmenuid() {
        return parantmenuid;
    }

    public void setParantmenuid(String parantmenuid) {
        this.parantmenuid = parantmenuid;
    }

    public String getChildFlag() {
        return childFlag;
    }

    public void setChildFlag(String childFlag) {
        this.childFlag = childFlag;
    }

    public String getMenuorder() {
        return menuorder;
    }

    public void setMenuorder(String menuorder) {
        this.menuorder = menuorder;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Viewmodel> getThreeMenuList() {
        return threeMenuList;
    }

    public void setThreeMenuList(List<Viewmodel> threeMenuList) {
        this.threeMenuList = threeMenuList;
    }
}
