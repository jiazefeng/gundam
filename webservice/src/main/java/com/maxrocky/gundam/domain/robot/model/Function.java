package com.maxrocky.gundam.domain.robot.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * 功能信息
 * Created by jiazefeng on 2016/07/11.
 */
@Entity
@Table(name = "function")
public class Function extends BaseVO{
    private String fId;
    private String fName;
    @Id
    @Column(name = "function_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }
    @Basic
    @Column(name = "function_name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }
}
