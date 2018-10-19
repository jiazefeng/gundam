package com.maxrocky.gundam.domain.village.model;//package com.maxrocky.gundam.domain.village.model;
//
//import javax.persistence.*;
//
///**
// * Created by lizhipeng on 2016/5/31.
// */
//@Entity
//@Table(name = "user_village")
//public class VillageAndUser {
//   /*小区和用户关联ID*/
//    private String userVillageId;
//    /*小区Id*/
//    private String villageId;
//    /*用户Id*/
//    private String userId;
//    @Id
//    @Column(name = "user_village_id", nullable = false, insertable = true, updatable = true, length = 32)
//    public String getUserVillageId() {
//        return userVillageId;
//    }
//
//    public void setUserVillageId(String userVillageId) {
//        this.userVillageId = userVillageId;
//    }
//    @Basic
//    @Column(name = "village_id", nullable = true, insertable = true, updatable = true, length = 32)
//    public String getVillageId() {
//        return villageId;
//    }
//
//    public void setVillageId(String villageId) {
//        this.villageId = villageId;
//    }
//    @Basic
//    @Column(name = "user_id", nullable = true, insertable = true, updatable = true, length = 32)
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//}
