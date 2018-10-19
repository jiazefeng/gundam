package com.maxrocky.gundam.domain.role.model;//package com.maxrocky.gundam.domain.role.model;
//
//import javax.persistence.*;
//
///**
// * Created by lizhipeng on 2016/5/23.
// */
//@Entity
//@Table(name = "role_viewbutton")
//public class Viewoperator {
//    private String buttonId;
//    private String buttonName;
//    private String buttonDescription;
//    private String buttonState;
//    private String operator;
//
//    @Id
//    @Column(name = "ButtonId", nullable = false, insertable = true, updatable = true, length = 30)
//    public String getButtonId() {
//        return buttonId;
//    }
//
//    public void setButtonId(String buttonId) {
//        this.buttonId = buttonId;
//    }
//
//    @Basic
//    @Column(name = "ButtonName", nullable = true, insertable = true, updatable = true, length = 30)
//    public String getButtonName() {
//        return buttonName;
//    }
//
//    public void setButtonName(String buttonName) {
//        this.buttonName = buttonName;
//    }
//
//    @Basic
//    @Column(name = "ButtonDescription", nullable = true, insertable = true, updatable = true, length = 50)
//    public String getButtonDescription() {
//        return buttonDescription;
//    }
//
//    public void setButtonDescription(String buttonDescription) {
//        this.buttonDescription = buttonDescription;
//    }
//
//    @Basic
//    @Column(name = "ButtonState", nullable = true, insertable = true, updatable = true, length = 2)
//    public String getButtonState() {
//        return buttonState;
//    }
//
//    public void setButtonState(String buttonState) {
//        this.buttonState = buttonState;
//    }
//
//    @Basic
//    @Column(name = "Operator", nullable = true, insertable = true, updatable = true, length = 30)
//    public String getOperator() {
//        return operator;
//    }
//
//    public void setOperator(String operator) {
//        this.operator = operator;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Viewoperator that = (Viewoperator) o;
//
//        if (buttonId != null ? !buttonId.equals(that.buttonId) : that.buttonId != null) return false;
//        if (buttonName != null ? !buttonName.equals(that.buttonName) : that.buttonName != null) return false;
//        if (buttonDescription != null ? !buttonDescription.equals(that.buttonDescription) : that.buttonDescription != null)
//            return false;
//        if (buttonState != null ? !buttonState.equals(that.buttonState) : that.buttonState != null) return false;
//        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = buttonId != null ? buttonId.hashCode() : 0;
//        result = 31 * result + (buttonName != null ? buttonName.hashCode() : 0);
//        result = 31 * result + (buttonDescription != null ? buttonDescription.hashCode() : 0);
//        result = 31 * result + (buttonState != null ? buttonState.hashCode() : 0);
//        result = 31 * result + (operator != null ? operator.hashCode() : 0);
//        return result;
//    }
//}
