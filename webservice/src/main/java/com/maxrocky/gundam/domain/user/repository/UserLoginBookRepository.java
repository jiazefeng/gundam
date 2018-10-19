package com.maxrocky.gundam.domain.user.repository;

import com.maxrocky.gundam.domain.user.model.UserLoginBook;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by lizhipeng on 2016/6/12.
 */
public interface UserLoginBookRepository extends BaseRepository<UserLoginBook> {
    /**
     * 添加登录信息
     */
    void AddUserloginbook(UserLoginBook userloginbook);

    /**
     * 根据tokenid查询用户id
     */
    String getUserIdBytokenId(String tokenId);

    /**
     * 根据用户id，判断是否登录
     */
    List<UserLoginBook> ifLogin(String userId);

    /**
     * 根据tokenId查询登录信息
     *
     * @param tokenId
     * @return
     */
    List<UserLoginBook> logout(String tokenId);
    //修改已删除用户的token
    int updateState(String userId);
}
