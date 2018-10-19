package com.maxrocky.gundam.domain.role.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "role_roleset")
public class Roleset extends BaseVO {
    private String setId;
    private String setState;
    private Date makeDate;
    private Time makeTime;
    private Date modifyDate;
    private Time modifyTime;
    private String operator;
    private String desc;
    private String companyId ;

    @Basic
    @Column(name = "CompanyId", nullable = false, insertable = true, updatable = true, length = 50)
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Id
    @Column(name = "SetId", nullable = false, insertable = true, updatable = true, length = 50)
    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    @Basic
    @Column(name = "SetState", nullable = false, insertable = true, updatable = true, length = 2)
    public String getSetState() {
        return setState;
    }

    public void setSetState(String setState) {
        this.setState = setState;
    }

    @Basic
    @Column(name = "MakeDate", nullable = false, insertable = true, updatable = true)
    public Date getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(Date makeDate) {
        this.makeDate = makeDate;
    }

    @Basic
    @Column(name = "MakeTime", nullable = false, insertable = true, updatable = true)
    public Time getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Time makeTime) {
        this.makeTime = makeTime;
    }

    @Basic
    @Column(name = "ModifyDate", nullable = false, insertable = true, updatable = true)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Basic
    @Column(name = "ModifyTime", nullable = false, insertable = true, updatable = true)
    public Time getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Time modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "Operator", nullable = false, insertable = true, updatable = true, length = 30)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "roledesc", nullable = true, insertable = true, updatable = true, length = 200)
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Roleset roleset = (Roleset) o;

        if (setId != null ? !setId.equals(roleset.setId) : roleset.setId != null) return false;
        if (setState != null ? !setState.equals(roleset.setState) : roleset.setState != null) return false;
        if (makeDate != null ? !makeDate.equals(roleset.makeDate) : roleset.makeDate != null) return false;
        if (makeTime != null ? !makeTime.equals(roleset.makeTime) : roleset.makeTime != null) return false;
        if (modifyDate != null ? !modifyDate.equals(roleset.modifyDate) : roleset.modifyDate != null) return false;
        if (modifyTime != null ? !modifyTime.equals(roleset.modifyTime) : roleset.modifyTime != null) return false;
        if (operator != null ? !operator.equals(roleset.operator) : roleset.operator != null) return false;
        if (desc != null ? !desc.equals(roleset.operator) : roleset.desc != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = setId != null ? setId.hashCode() : 0;
        result = 31 * result + (setState != null ? setState.hashCode() : 0);
        result = 31 * result + (makeDate != null ? makeDate.hashCode() : 0);
        result = 31 * result + (makeTime != null ? makeTime.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
