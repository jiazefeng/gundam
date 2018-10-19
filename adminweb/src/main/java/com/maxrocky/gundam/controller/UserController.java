package com.maxrocky.gundam.controller;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.domain.role.model.Viewmodel;
import com.maxrocky.gundam.domain.user.dto.SecondViewModel;
import com.maxrocky.gundam.domain.user.dto.UserInfoDTO;
import com.maxrocky.gundam.domain.user.dto.ViewModelDto;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.service.user.inf.TokenService;
import com.maxrocky.gundam.service.user.inf.UserService;
import com.maxrocky.gundam.service.user.inf.ViewModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/24.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ViewModelService viewModelService;

    @RequestMapping(value = "/userList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getUserList(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return userService.getUserList(villageId, userInfo.getuId(), userInfo);
    }

    /**
     * 查询用户通过条件
     *
     * @Param userDto
     * @Author lzp
     */
    @RequestMapping(value = "/searchUserByItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult searchUserByItem(@RequestBody UserInfoDTO userInfoDTO, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        userInfoDTO.setVillage(villageId);
        return userService.getUserByItem(userInfoDTO,userInfo);
    }

    /**
     * 加载添加用户页面
     *
     * @Author lzp
     */
    @RequestMapping(value = "/initAddUser", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult initAddUser(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return userService.initAddUser(userInfo);
    }

    /**
     * 添加用户
     *
     * @param userInfoDTO
     * @Author lzp
     */
    @RequestMapping(value = "/addUser", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult addUser(@RequestBody UserInfoDTO userInfoDTO, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return userService.addUser(userInfoDTO);
    }

    /**
     * 删除用户
     *
     * @Author lzp
     */
    @RequestMapping(value = "/deleteUser/{userId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult deleteUser(@PathVariable("userId") String userId, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        ModelMap result = new ModelMap();
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (userService.deleteUser(userId)) {
            result.addAttribute("success", "删除成功");

        } else {
            result.addAttribute("error", "删除失败");
        }
        return new SuccessApiResult(result);
    }

    /*
    * 通过用户ID查看详情
    * @Param
    * */
    @RequestMapping(value = "/lookUser/{userId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getUserById(@PathVariable("userId") String userId, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (userId != null && !userId.equals("")) {
            return userService.getUserById(userId);
        } else {
            return new ErrorApiResult("失败");
        }
    }

    /*
    * 初始化修改用户界面
    * */
    @RequestMapping(value = "/initUpdateUser/{userId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult initUpdateUser(@PathVariable("userId") String userId, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (userId != null && !userId.equals("")) {
            return userService.initUpdate(userId, userInfo);
        }
        return new ErrorApiResult("失败");
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/updateUser", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult updateUser(@RequestBody UserInfoDTO userInfo, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        UserInfo userInfos = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfos == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (userInfo != null) {
            return userService.updateUser(userInfo);
        }
        return new ErrorApiResult("失败");
    }

    /**
     * 修改用户密码
     *
     * @return
     */
    @RequestMapping(value = "/updateUserPwd", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult updateUserPwd(@RequestBody UserInfoDTO userInfo, @CookieValue(value = "vestaToken", required = false) String tokenId) {

        UserInfo userInfos = userService.GetUserInfoByTokenValue(tokenId);
        ModelMap result = new ModelMap();
        if (userInfos == null) {
            result.addAttribute("error", "当前未登录！");
            return new SuccessApiResult(result);
        }
        String userId = tokenService.getUserIdBytokenId(tokenId);
        if (userInfo != null) {
            userInfo.setId(userId);
        }
        return userService.updateUserPwd(userInfo);
    }

    /**
     * 通过tokenId查询角色权限
     */
    @RequestMapping(value = "/getRoleByTokenId/{tokenId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getRoleByTokenId(@PathVariable("tokenId") String tokenId) {
        if (tokenId != null && !tokenId.equals("")) {
            String userId = tokenService.getUserIdBytokenId(tokenId);
            List<ViewModelDto> list = new ArrayList<ViewModelDto>();
            if (userId != null) {
                List<Viewmodel> viewmodelList = viewModelService.getViewListByUserId(userId);
                if (viewmodelList != null && viewmodelList.size() > 0) {
                    for (Viewmodel viewmodel : viewmodelList) {
                        ViewModelDto viewModelDto = new ViewModelDto();
                        viewModelDto.setMenuName(viewmodel.getMenuName());
                        viewModelDto.setRunscript(viewmodel.getRunscript());
                        //获取二级菜单
                        List<SecondViewModel> secanViewlist = viewModelService.getViewListOtherByUserId(userId, viewmodel.getMenuId());
                        viewModelDto.setChildMenuList(secanViewlist);
                        list.add(viewModelDto);
                    }
                }
            }
            ModelMap result = new ModelMap();
            result.addAttribute("menulist", list);
            return new SuccessApiResult(result);
        }
        return new ErrorApiResult("用户不存在");
    }

    /**
     * 通过tokenId查询角色权限
     */
    @RequestMapping(value = "/getRoleAndUserByTokenId", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getRoleAndUserByTokenId(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return userService.GetInfoByTokenId(userInfo.getuId(), villageId);
    }

    /**
     * 切换小区
     */
    @RequestMapping(value = "/changeVillage/{villageId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult changeVillage(@PathVariable("villageId") String villageId, @CookieValue(value = "vestaToken", required = false) String tokenId, HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return userService.changeVillage(villageId, request, response);
    }

    /**
     * 退出系统
     *
     * @return
     */
    @RequestMapping(value = "/logout", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult logout(@CookieValue(value = "vestaToken", required = false) String tokenId, HttpServletRequest request, HttpServletResponse response) {
        if (tokenId != null && !tokenId.equals("")) {
            userService.logout(tokenId, request, response);
        }
        return new SuccessApiResult("退出成功！");
    }
}
