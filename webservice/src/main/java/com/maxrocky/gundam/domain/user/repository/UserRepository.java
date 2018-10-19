package com.maxrocky.gundam.domain.user.repository;

import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.hibernate.BaseRepository;
//import com.maxrocky.gundam.domain.village.model.Village;

import java.util.List;

/**
* Created by lizhipeng on 2016/5/31.
*/
public interface UserRepository extends BaseRepository<UserInfo> {
    //用户登录
    public UserInfo login(UserInfo userInfo);

    //默认查询方法
    public List<UserInfo> getUserList(String villageId);
    //默认查询方法
    public List<UserInfo> getUserList(String villageId,UserInfo userInfo);

    //通过用户ID查询小区
    public List<Village> getVillageByUserId(String userId);

    //通过条件查询用户列表
    public List<UserInfo> getUserListByItem(UserInfo userInfo, String villageId, int index);
    //通过条件查询用户列表
    public List<UserInfo> getUserListByItem(UserInfo userInfo, String villageId, int index,String userId);

    //查询用户总数量
    public int getCount(String villageId);

    //通过条件查询用户数量
    public int getCountByItem(UserInfo userInfo, String villageId);

    //添加用户
    public boolean addUser(UserInfo userInfo);

    //通过用户名查询用户
    public UserInfo getUserByName(String userName);

    //删除用户通过ID
    public boolean deleteUser(String userId);

    //通过用户Id查询用户
    public UserInfo getUserById(String userId);

    //修改用户信息
    public boolean updateUser(UserInfo userInfo);

    //查询所有管理员
    public List<UserInfo> getAdmin(String roleId);

}
