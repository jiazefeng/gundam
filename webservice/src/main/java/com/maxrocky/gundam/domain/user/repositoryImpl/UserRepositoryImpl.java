package com.maxrocky.gundam.domain.user.repositoryImpl;


import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.user.repository.UserRepository;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<UserInfo> implements UserRepository {
    @Override
    public UserInfo login(UserInfo userInfo) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("uName", userInfo.getuName()));
        sql.add(Restrictions.eq("uPassword", userInfo.getuPassword()));
        sql.add(Restrictions.eq("uState", userInfo.getuState()));
        return this.findUniqueResult(sql);
    }

    //    @Autowired
//    private HibernateDaoImpl hibernateDao;
//    @Resource(name = "sessionFactory")
//    private SessionFactory sessionFactory;
//    public Session getSession(){
//        return sessionFactory.getCurrentSession();
//    }
//    public UserInfo login(UserInfo userInfo){
//        UserInfo result = null;
//        String hql = "FROM UserInfo WHERE uName = ? AND uPassword = ? AND uState = ?";
//        if (userInfo!=null) {
//           result = hibernateDao.findUniqueEntity(hql, userInfo.getuName(), userInfo.getuPassword(),userInfo.getuState());
//            return result;
//        }else{
//            return null;
//        }
//    }
    @Override
    public List<UserInfo> getUserList(String villageId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserInfo as u where 1 = 1 and uState = ?";
        params.add(1);
        if (villageId != null && !villageId.equals("")) {
            sql += " and u.uId in (select uv.userId from UserVillage uv where uv.villageId = ?)";
            params.add(villageId);
        }
        sql += " ORDER BY u.modifyDate DESC";
        List<UserInfo> UserInfo = this.findByQueryList(sql, new PageInfoTools(1, 10), params);
        return UserInfo;
    }

    @Override
    public List<UserInfo> getUserList(String villageId, UserInfo userInfo) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserInfo as u where uState = ? and uId <> ?";
        params.add(1);
        params.add(userInfo.getuId());
        if (villageId != null && !villageId.equals("")) {
            sql += " and u.uId in (select uv.userId from UserVillage uv where uv.villageId = ?)";
            params.add(villageId);
        }
        sql += " ORDER BY u.modifyDate DESC";
        List<UserInfo> UserInfo = this.findByQueryList(sql, new PageInfoTools(1, 10), params);
        return UserInfo;
    }

    @Override
    public List<Village> getVillageByUserId(String userId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Village as v where state = 1 and v.villageId in (select u.villageId from UserVillage u where u.userId = ? ORDER BY u.villageId ASC)";
        params.add(userId);
        List villageList = this.findByQueryList(sql, params);
        return villageList;
    }

    @Override
    public List<UserInfo> getUserListByItem(UserInfo userInfo, String villageId, int index) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserInfo as u where 1 = 1 and uState = ?";
        params.add(userInfo.getuState());
        if (userInfo.getuName() != null && !userInfo.getuName().equals("")) {
            sql += " and u.uName = '" + userInfo.getuName() + "'";
        }
        if (villageId != null && !villageId.equals("")) {
            sql += " and u.uId in (select uv.userId from UserVillage uv where uv.villageId = '" + villageId + "')";
        }
        sql += " ORDER BY u.modifyDate DESC";
        List<UserInfo> UserInfo = this.findByQueryList(sql, new PageInfoTools(index, 10), params);
        return UserInfo;
    }

    @Override
    public List<UserInfo> getUserListByItem(UserInfo userInfo, String villageId, int index, String userId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserInfo as u where uState = ? uId <> ?";
        params.add(userInfo.getuState());
        params.add(userId);
        if (userInfo.getuName() != null && !userInfo.getuName().equals("")) {
            sql += " and u.uName = '" + userInfo.getuName() + "'";
        }
        if (villageId != null && !villageId.equals("")) {
            sql += " and u.uId in (select uv.userId from UserVillage uv where uv.villageId = '" + villageId + "')";
        }
        sql += " ORDER BY u.modifyDate DESC";
        List<UserInfo> UserInfo = this.findByQueryList(sql, new PageInfoTools(index, 10), params);
        return UserInfo;
    }

    @Override
    public int getCount(String villagveId) {
//        String sql = "select count(0) from UserInfo where uState = ?";
        List<Object> params = new ArrayList<Object>();
        String sql = "select count(0) from UserInfo as u where 1 = 1 and uState = ?";
        params.add(1);
        if (villagveId != null && !villagveId.equals("")) {
            sql += " and u.uId in (select uv.userId from UserVillage uv where uv.villageId = ?)";
            params.add(villagveId);
        }
        return this.findByCriteriaForPageCount(sql, params);
    }

    @Override
    public int getCountByItem(UserInfo userInfo, String villageId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "select count(0) from UserInfo as u where 1 = 1 and uState = ?";
        params.add(userInfo.getuState());
        if (userInfo.getuName() != null && !userInfo.getuName().equals("")) {
            sql += " and u.uName = ?";
            params.add(userInfo.getuName());
        }
        if (villageId != null && !villageId.equals("")) {
            sql += " and u.uId in (select uv.userId from UserVillage uv where uv.villageId = ?)";
            params.add(villageId);
        }
        return this.findByCriteriaForPageCount(sql, params);
    }

    @Override
    public boolean addUser(UserInfo userInfo) {
        this.save(userInfo);
        return true;
    }

    @Override
    public UserInfo getUserByName(String userName) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserInfo where uName = ?";
        params.add(userName);
        return (UserInfo) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public UserInfo getUserById(String userId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserInfo where uId = ?";
        params.add(userId);
        return (UserInfo) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public boolean deleteUser(String userId) {
        UserInfo userInfo = new UserInfo();
        if (userId != null && !userId.equals("")) {
            userInfo = getUserById(userId);
        }
        if (userInfo != null) {
            userInfo.setuState(0);
            this.update(userInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UserInfo userInfo) {
        if (userInfo != null) {
            this.update(userInfo);
            return true;
        }
        return false;
    }

    @Override
    public List<UserInfo> getAdmin(String roleId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserInfo as u where 1 = 1 and uState = ? and roleId = ?";
        params.add(1);
        params.add(roleId);
        List<UserInfo> UserInfo = this.findByQueryList(sql, params);
        return UserInfo;
    }


}
