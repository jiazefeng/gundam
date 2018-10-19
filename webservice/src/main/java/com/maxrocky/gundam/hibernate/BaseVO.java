package com.maxrocky.gundam.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Tom on 2016/5/9 15:02.
 * Describe:
 */
@MappedSuperclass
public class BaseVO implements Serializable {

    @Basic
    @Column(name = "CREATE_BY", length = 200)
    protected String createBy;

    @Basic
    @Column(name = "CREATE_ON")
    protected Calendar createOn;

    @Basic
    @Column(name = "MODIFY_BY", length = 200)
    protected String modifyBy;

    @Basic
    @Column(name = "MODIFY_ON")
    protected Calendar modifyOn;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Calendar getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Calendar createOn) {
        this.createOn = createOn;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Calendar getModifyOn() {
        return modifyOn;
    }

    public void setModifyOn(Calendar modifyOn) {
        this.modifyOn = modifyOn;
    }

}

