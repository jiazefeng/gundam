package com.maxrocky.gundam.domain.user.repository;

import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by lizhipeng on 2016/6/7.
 */
public interface UserVillageRepository extends BaseRepository<UserVillage> {
    public boolean addUserVillage(List<UserVillage> userVillages);
    public boolean addUserVillage(UserVillage userVillage);
    /**
     * 通过 用户ID 查询 用户所有的小区信息
     *
     * @param userId
     * @return
     */
    public List<UserVillage> getUserVillageList(String userId);
    /**
     * 删除用户下所有的小区
     *
     * @param userVillageList
     * @return
     */
    public boolean deleteUserVillage(List<UserVillage> userVillageList);
}
