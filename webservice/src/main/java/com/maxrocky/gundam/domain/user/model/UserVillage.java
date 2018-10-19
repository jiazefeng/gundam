package com.maxrocky.gundam.domain.user.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * Created by lizhipeng on 2016/5/23.
 */
@Entity
@Table(name = "user_villagesetmap")
public class UserVillage extends BaseVO {
   private String id;
    private String villageId;
    private String userId;
    @Id
    @Column(name = "USER_VILLAGE_ID", nullable = false, insertable = true, updatable = true, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Basic
    @Column(name = "VILLAGE_ID", nullable = true, insertable = true, updatable = true, length = 100)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }
    @Basic
    @Column(name = "USER_ID", nullable = true, insertable = true, updatable = true, length = 100)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
