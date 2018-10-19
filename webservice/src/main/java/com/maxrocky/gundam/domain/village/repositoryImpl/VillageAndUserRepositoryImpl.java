package com.maxrocky.gundam.domain.village.repositoryImpl;

import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.domain.village.repository.VillageAndUserRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
@Repository
public class VillageAndUserRepositoryImpl extends BaseRepositoryImpl<UserVillage> implements VillageAndUserRepository {
    @Override
    public List<UserVillage> getSellerIdByUserId(String id) {
        String sql = "from UserVillage where userId=? ORDER BY villageId ASC";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        return this.findByQueryList(sql, params);
//        List<Criterion> sql = new ArrayList<Criterion>();
//        sql.add(Restrictions.eq("userId", id));
//        return this.findByCriteria(sql);
    }

    @Override
    public UserVillage getUserVillageById(String vId) {
        String sql = "from UserVillage where villageId=?";
        List<Object> params = new ArrayList<Object>();
        params.add(vId);
        return (UserVillage) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public List<UserVillage> getUserVillageByVillageId(String vId) {
        String sql = "from UserVillage where villageId=?";
        List<Object> params = new ArrayList<Object>();
        params.add(vId);
        return this.findByQueryList(sql, params);
    }

    @Override
    public boolean deleteUserVillage(UserVillage userVillage) {
        if (userVillage != null) {
            this.deletePhysical(userVillage);
            return true;
        }
        return false;
    }

}
