package com.maxrocky.gundam.service.user.impl;

import com.maxrocky.gundam.domain.user.repository.UserLoginBookRepository;
import com.maxrocky.gundam.service.user.inf.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lizhipeng on 2016/6/12.
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private UserLoginBookRepository userLoginBookRepository;
    @Override
    public String getUserIdBytokenId(String tokenId){
       return userLoginBookRepository.getUserIdBytokenId(tokenId);
    }
}
