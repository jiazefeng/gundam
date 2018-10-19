package com.maxrocky.gundam.service.user.inf;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.user.dto.UserInfoDTO;
import com.maxrocky.gundam.domain.user.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lizhipeng on 2016/6/1.
 */
public interface UserService {
    //默认查询用户列表
    public ApiResult getUserList(String vilageId,String uId,UserInfo userInfo);
    //通过条件搜索用户
    public ApiResult getUserByItem(UserInfoDTO userInfoDTO,UserInfo userInfo);
    //初始化添加用户界面
    public ApiResult initAddUser(UserInfo userInfo);
    //增加用户
    public ApiResult addUser(UserInfoDTO userInfoDTO);
    //删除用户
    public boolean deleteUser(String userID);
    //通过ID查询用户
    public ApiResult getUserById(String userId);
    //添加用户
    //初始化修改用户页面
    public ApiResult initUpdate(String userId,UserInfo userInfo);
    //修改用户
    public ApiResult updateUser(UserInfoDTO userInfoDTO);
    //修改用户密码
    public ApiResult updateUserPwd(UserInfoDTO userInfoDTO);
    //通过token查询用户
    public UserInfo GetUserInfoByTokenValue(String  vestatoken);
    //通过tokenId查询用户角色，信息
    public ApiResult GetInfoByTokenId(String tokenId,String villageId);
    //切换小区
    public ApiResult changeVillage(String villageId,HttpServletRequest request, HttpServletResponse response);
    //退出系统
    public void logout(String tokenId,HttpServletRequest request, HttpServletResponse response);
}
