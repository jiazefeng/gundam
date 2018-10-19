package com.maxrocky.gundam.service.login.inf;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.user.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Created by lizhipeng on 2016/5/31.
*/
public interface LoginService {
    public ApiResult login(LoginDto loginDto, HttpServletRequest request,HttpServletResponse response);
}
