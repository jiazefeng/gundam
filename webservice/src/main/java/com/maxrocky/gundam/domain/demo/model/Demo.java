package com.maxrocky.gundam.domain.demo.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by zhanghj on 2016/4/25.
 */
@Entity
@Table(name = "Demo")
public class Demo extends BaseVO {

    public Demo(){}

    public Demo(String demoName){
        this.create();
        this.demoName = demoName;
    }

    @Id
    @Column(name = "DEMO_ID", length = 100)
    private String demoId;//ID

    @Basic
    @Column(name = "DEMO_NAME", length = 200)
    private String demoName;//名称


    public String getDemoId() {
        return demoId;
    }

    public void setDemoId(String demoId) {
        this.demoId = demoId;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    /* create base data. */
    @Transient
    public void create(){
        this.demoId = "123";
        this.createBy = "Test";
        this.createOn = Calendar.getInstance();
        this.modifyBy = "Test";
        this.modifyOn = Calendar.getInstance();
    }

}
