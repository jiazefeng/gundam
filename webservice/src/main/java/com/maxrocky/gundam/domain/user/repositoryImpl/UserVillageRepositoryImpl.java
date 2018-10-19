package com.maxrocky.gundam.domain.user.repositoryImpl;

import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.domain.user.repository.UserVillageRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/7.
 */
@Repository
public class UserVillageRepositoryImpl extends BaseRepositoryImpl<UserVillage> implements UserVillageRepository {

    @Override
    public boolean addUserVillage(List<UserVillage> userVillage){
        if(userVillage.size()>0){
            for(UserVillage userVillage1 : userVillage) {
                this.save(userVillage1);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addUserVillage(UserVillage userVillage) {
        this.save(userVillage);
        return true;
    }

    @Override
    public List<UserVillage> getUserVillageList(String userId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserVillage uv where uv.userId = ?";
        params.add(userId);
        List<UserVillage> list = this.findByQueryList(sql, params);
        return list;
    }

    @Override
    public boolean deleteUserVillage(List<UserVillage> userVillageList) {
        if (userVillageList.size() > 0) {
            for (UserVillage userVillage : userVillageList) {
                this.deletePhysical(userVillage);
            }
            return true;
        }
        return false;
    }
}
