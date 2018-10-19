package com.maxrocky.gundam.controller;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.util.HttpClient;
import com.maxrocky.gundam.domain.user.dto.LoginDto;
import com.maxrocky.gundam.service.login.inf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lizhipeng on 2016/5/31.
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @RequestMapping(value = "/login",produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
    public ApiResult login(@RequestBody LoginDto loginDto,HttpServletRequest request, HttpServletResponse response) throws Exception{
       return loginService.login(loginDto,request,response);
    }
}
