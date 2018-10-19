package com.maxrocky.gundam.domain.user.repositoryImpl;

import com.maxrocky.gundam.domain.user.model.UserLoginBook;
import com.maxrocky.gundam.domain.user.repository.UserLoginBookRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/12.
 */
@Repository
public class UserLoginBookRepositoryImpl extends BaseRepositoryImpl<UserLoginBook> implements UserLoginBookRepository {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void AddUserloginbook(UserLoginBook userloginbook)
    {
        this.save(userloginbook);
    }
    @Override
    public String getUserIdBytokenId(String tokenId){
        List<Object> params = new ArrayList<Object>();
        String sql = "from UserLoginBook where tookenId = ? and loginType = ?";
        params.add(tokenId);
        params.add("1");
        UserLoginBook userLoginBook = (UserLoginBook)this.findObjectByQueryResult(sql,params);
        return userLoginBook.getUserId();
    }
    @Override
    public List<UserLoginBook> ifLogin(String userId){
        String sql = "from UserLoginBook where userId = ? and loginType = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(userId);
        params.add("1");
        return this.findByQueryList(sql, params);

    }

    @Override
    public List<UserLoginBook> logout(String tokenId) {
        String sql = "from UserLoginBook where tookenId = ? and loginType = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(tokenId);
        params.add("1");
        return this.findByQueryList(sql, params);
    }
    @Override
    public int updateState(String userId){
        String sql = "update UserLoginBook set loginType = '2' where userId = '"+userId+"'";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return query.executeUpdate();
//        List<Object> params = new ArrayList<Object>();
//        params.add("2");
//        params.add(userId);
//       return Integer.parseInt(this.findObjectByQueryResult(sql, params).toString());
    }
}
