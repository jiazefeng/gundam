package com.maxrocky.gundam.service.login.impl;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.common.util.PasswordEncode;
import com.maxrocky.gundam.domain.role.model.Role;
import com.maxrocky.gundam.domain.role.model.Userroleauthority;
import com.maxrocky.gundam.domain.role.model.Viewmodel;
import com.maxrocky.gundam.domain.role.repository.RoleRepository;
import com.maxrocky.gundam.domain.role.repository.RoleRoleauthorityRepository;
import com.maxrocky.gundam.domain.user.dto.LoginDto;
import com.maxrocky.gundam.domain.user.dto.SecondViewModel;
import com.maxrocky.gundam.domain.user.dto.UserInfoDTO;
import com.maxrocky.gundam.domain.user.dto.ViewModelDto;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.user.model.UserLoginBook;
import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.domain.user.repository.UserLoginBookRepository;
import com.maxrocky.gundam.domain.user.repository.UserRepository;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.service.login.inf.LoginService;
import com.maxrocky.gundam.service.user.inf.ViewModelService;
import com.maxrocky.gundam.service.village.inf.VillageAndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ViewModelService viewModelService;
    @Autowired
    private VillageAndUserService villageAndUserService;
    @Autowired
    private UserLoginBookRepository userLoginBookRepository;
    @Autowired
    private RoleRoleauthorityRepository roleRoleauthorityRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ApiResult login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserInfo userInfo = new UserInfo();
        ModelMap result = new ModelMap();
        UserLoginBook userLoginBook = new UserLoginBook();
        if (loginDto != null) {
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setuName(loginDto.getUserName());
            userInfo1.setuPassword(PasswordEncode.digestPassword(loginDto.getPwd()));
            userInfo1.setuState(1);
            //校验用户名和密码
            userInfo = userRepository.login(userInfo1);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        if (userInfo != null) {
            userInfoDTO.setId(userInfo.getuId());
            userInfoDTO.setuName(userInfo.getuName());
            userInfoDTO.setuLogo(userInfo.getuLogo());
            Role role = roleRepository.searRoleInfo(userInfo.getuId());
            userInfoDTO.setDegree(role.getDegree());
//            List<UserLoginBook> oldLoginBook = userLoginBookRepository.ifLogin(userInfo.getuId());
//            if(oldLoginBook!=null&&oldLoginBook.size()>0) {
//                for (UserLoginBook userLoginBook1 :oldLoginBook) {
//                    userLoginBook1.setLoginType("2");
//                    userLoginBookRepository.update(userLoginBook1);
//                }
//            }
            userLoginBook.setTookenId(IdGen.uuid());
            userLoginBook.setLoginType("1");
            userLoginBook.setMakeDate(new Date(System.currentTimeMillis()));
            userLoginBook.setMakeTime(new Time(System.currentTimeMillis()));
            userLoginBook.setUserId(userInfo.getuId());
            userLoginBookRepository.AddUserloginbook(userLoginBook);
            Cookie cookie = new Cookie("vestaToken", userLoginBook.getTookenId());
            cookie.setPath("/");
            cookie.setDomain(request.getServerName());
            cookie.setMaxAge(3600 * 24 * 30);
            response.addCookie(cookie);
            response.setHeader("P3P", "CP='CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR'");
        }

        if (userInfo != null)

        {
            List<Village> villages = villageAndUserService.getSellerIdByUserId(userInfo.getuId());
            if (villages != null && villages.size() > 0) {
                result.addAttribute("villageList", villages);
                Cookie cookie = new Cookie("villageId", "");
                if (!userInfo.getRoleId().equals("000100000000")) {
                    cookie = new Cookie("villageId", villages.get(0).getVillageId());
                } else {
                    userInfoDTO.setIfadmin(true);
                }
                cookie.setPath("/");
                cookie.setDomain(request.getServerName());
                cookie.setMaxAge(3600 * 24 * 30);
                response.addCookie(cookie);
                response.setHeader("P3P", "CP='CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR'");
                List<ViewModelDto> list = new ArrayList<ViewModelDto>();
                List<Viewmodel> viewmodelList = viewModelService.getViewListByUserId(userInfo.getuId());
                Role role = roleRepository.searRoleInfo(userInfo.getuId());
                if (role.getDegree() > 2) {
                    //获取一级菜单
                    if (viewmodelList != null && viewmodelList.size() > 0) {
                        for (Viewmodel viewmodel : viewmodelList) {
                            ViewModelDto viewModelDto = new ViewModelDto();
                            viewModelDto.setMenuName(viewmodel.getMenuName());
                            viewModelDto.setRunscript(viewmodel.getRunscript());
                            viewModelDto.setIcon(viewmodel.getIcon());
                            //获取二级菜单
                            List<SecondViewModel> secanViewlist = viewModelService.getViewListOtherByUserId(userInfo.getuId(), viewmodel.getMenuId());
                            for (SecondViewModel secondViewModel : secanViewlist) {
                                if (secondViewModel.getMenuName().equals("小区配置管理")) {
                                    if (cookie.getName().equalsIgnoreCase("villageId")) {
                                        secondViewModel.setRunscript("/home/district/lookDistrict/" + cookie.getValue());
                                    }
                                }
                                List<Viewmodel> threeViewlist = viewModelService.getViewListThreeByUserId(userInfo.getuId(), secondViewModel.getMenuId());
                                secondViewModel.setThreeMenuList(threeViewlist);
                            }
                            viewModelDto.setChildMenuList(secanViewlist);
                            list.add(viewModelDto);
                        }
                    }
                } else {
                    //获取一级菜单
                    if (viewmodelList != null && viewmodelList.size() > 0) {
                        for (Viewmodel viewmodel : viewmodelList) {
                            ViewModelDto viewModelDto = new ViewModelDto();
                            viewModelDto.setMenuName(viewmodel.getMenuName());
                            viewModelDto.setRunscript(viewmodel.getRunscript());
                            viewModelDto.setIcon(viewmodel.getIcon());
                            //获取二级菜单
                            List<SecondViewModel> secanViewlist = viewModelService.getViewListOtherByUserId(userInfo.getuId(), viewmodel.getMenuId());
                            for (SecondViewModel secondViewModel : secanViewlist) {
                                List<Viewmodel> threeViewlist = viewModelService.getViewListThreeByUserId(userInfo.getuId(), secondViewModel.getMenuId());
                                secondViewModel.setThreeMenuList(threeViewlist);
                            }
                            viewModelDto.setChildMenuList(secanViewlist);
                            list.add(viewModelDto);
                        }
                    }
                }
                result.addAttribute("userInfo", userInfoDTO);
                result.addAttribute("token", userLoginBook.getTookenId());
                if (viewmodelList == null || viewmodelList.size() <= 0) {
                    result.addAttribute("error", "权限已被禁用");
                } else {
                    result.addAttribute("menulist", list);
                }
            } else {
                result.addAttribute("error", "用户名密码错误");
            }
        } else {
            result.addAttribute("error", "用户名密码错误");
        }
        return new SuccessApiResult(result);
    }
}
