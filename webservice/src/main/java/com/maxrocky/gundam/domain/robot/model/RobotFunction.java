package com.maxrocky.gundam.domain.robot.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * Created by jiazefeng on 2016/07/11.
 */
@Entity
@Table(name = "robot_function")
public class RobotFunction extends BaseVO{
    private String id;
    private String rId;
    private String fId;
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Basic
    @Column(name = "robot_id", nullable = true, insertable = true, updatable = true, length = 32)
    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }
    @Basic
    @Column(name = "function_id", nullable = true, insertable = true, updatable = true, length = 32)
    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }
}
