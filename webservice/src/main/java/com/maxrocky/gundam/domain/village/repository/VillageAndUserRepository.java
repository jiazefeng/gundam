package com.maxrocky.gundam.domain.village.repository;

import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
public interface VillageAndUserRepository extends BaseRepository<UserVillage> {
    //根据用户Id查询所关联小区的Id
    List<UserVillage> getSellerIdByUserId(String id);

    //根据 小区ID 和 用户id 查询所关联的信息
    public UserVillage getUserVillageById(String vId);
    public List<UserVillage> getUserVillageByVillageId(String vId);

    //删除用户所关联的小区信息
    public boolean deleteUserVillage(UserVillage userVillage);
}
