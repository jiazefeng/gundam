package com.maxrocky.gundam.service.user.impl;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.common.util.PasswordEncode;
import com.maxrocky.gundam.domain.role.model.Role;
import com.maxrocky.gundam.domain.role.model.Rolesetmap;
import com.maxrocky.gundam.domain.role.model.Userroleauthority;
import com.maxrocky.gundam.domain.role.model.Viewmodel;
import com.maxrocky.gundam.domain.role.repository.RoleRepository;
import com.maxrocky.gundam.domain.role.repository.RoleRoleauthorityRepository;
import com.maxrocky.gundam.domain.user.dto.SecondViewModel;
import com.maxrocky.gundam.domain.user.dto.UserInfoDTO;
import com.maxrocky.gundam.domain.user.dto.ViewModelDto;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.user.model.UserLoginBook;
import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.domain.user.repository.RoleSetRepository;
import com.maxrocky.gundam.domain.user.repository.UserLoginBookRepository;
import com.maxrocky.gundam.domain.user.repository.UserRepository;
import com.maxrocky.gundam.domain.user.repository.UserVillageRepository;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.domain.village.repository.VillageRepository;
import com.maxrocky.gundam.service.user.inf.UserService;
import com.maxrocky.gundam.service.user.inf.ViewModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/1.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VillageRepository villageRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleSetRepository roleSetRepository;
    @Autowired
    private UserVillageRepository userVillageRepository;
    @Autowired
    private RoleRoleauthorityRepository roleRoleauthorityRepository;
    @Autowired
    private UserLoginBookRepository userLoginBookRepository;
    @Autowired
    private ViewModelService viewModelService;

    @Override
    public ApiResult getUserList(String villageId, String uId, UserInfo userInfo1) {
        int count = userRepository.getCount(villageId);
        List<UserInfo> userInfoList = userRepository.getUserList(villageId, userInfo1);
        List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
        for (UserInfo userInfo : userInfoList) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(userInfo.getuId());
            userInfoDTO.setuName(userInfo.getuName());
            userInfoDTO.setMobile(userInfo.getMobile());
            userInfoDTO.setPost(userInfo.getPost());
            List<Village> list = userRepository.getVillageByUserId(userInfo.getuId());
            Role role = roleRepository.getRoleById(userInfo.getRoleId());
            userInfoDTO.setRole(role.getRoleName());
            if (role.getRoleId().equals("000100000000")) {
                userInfoDTO.setIfadmin(true);
            } else {
                userInfoDTO.setIfadmin(false);
            }
            if (list.size() > 0) {
                String villageName = "";
                for (Village village : list) {
                    villageName += village.getVillageName() + " ";
                }
                userInfoDTO.setVillage(villageName);
            }
            userInfoDTOs.add(userInfoDTO);
        }
        ModelMap result = new ModelMap();
        result.addAttribute("count", count);
        result.addAttribute("userInfoDTOs", userInfoDTOs);
//        result.addAttribute("villageList",villageList);
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult getUserByItem(UserInfoDTO userInfoDTO, UserInfo userInfo2) {

        UserInfo userInfo = new UserInfo();
        if (userInfoDTO != null) {
            userInfo.setuName(userInfoDTO.getuName());
        }
        //1是未删除状态
        userInfo.setuState(1);
        int count = userRepository.getCountByItem(userInfo, userInfoDTO.getVillage());
        List<UserInfo> userInfoList = userRepository.getUserListByItem(userInfo, userInfoDTO.getVillage(), userInfoDTO.getIndex(), userInfo2.getuId());
        List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
        for (UserInfo userInfo1 : userInfoList) {
            UserInfoDTO userInfoDTO1 = new UserInfoDTO();
            userInfoDTO1.setId(userInfo1.getuId());
            userInfoDTO1.setuName(userInfo1.getuName());
            userInfoDTO1.setMobile(userInfo1.getMobile());
            userInfoDTO1.setPost(userInfo1.getPost());
            List<Village> list = userRepository.getVillageByUserId(userInfo1.getuId());
            Role role = roleRepository.getRoleById(userInfo1.getRoleId());
            userInfoDTO1.setRole(role.getRoleName());
            if (list.size() > 0) {
                String villageName = "";
                for (Village village : list) {
                    villageName += village.getVillageName() + " ";
                }
                userInfoDTO1.setVillage(villageName);
            }
            userInfoDTOs.add(userInfoDTO1);
        }
//        List <Village> villageList = villageRepository.getVillage();
        ModelMap result = new ModelMap();
        result.addAttribute("count", count);
        result.addAttribute("page", userInfoDTO.getIndex());
        result.addAttribute("userInfoDTOs", userInfoDTOs);
//        result.addAttribute("villageList",villageList);
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult initAddUser(UserInfo userInfo) {
        List<Village> villageList = new ArrayList<Village>();
        List<Role> roleList = new ArrayList<Role>();
        if (userInfo.getRoleId().equals("000100000000")) {
            villageList = villageRepository.getAllVillage();
            roleList = roleRepository.getRoleList();
        } else {
            villageList = userRepository.getVillageByUserId(userInfo.getuId());
            Role role = roleRepository.getRoleById(userInfo.getRoleId());
            if (role.getDegree() != 1) {
                roleList = roleRepository.inquireRole(role.getDegree());
            }

        }

        ModelMap result = new ModelMap();
        result.addAttribute("villageList", villageList);
        result.addAttribute("roleList", roleList);
        result.addAttribute("sex", 0);
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult addUser(UserInfoDTO userInfoDTO) {
        ModelMap result = new ModelMap();
        UserInfo userResult = userRepository.getUserByName(userInfoDTO.getuName());
        if (userResult == null) {
            UserInfo userInfo = new UserInfo();
            if (userInfoDTO != null) {
                userInfo.setuId(IdGen.uuid());
                userInfo.setuName(userInfoDTO.getuName());
                userInfo.setuPassword(PasswordEncode.digestPassword(userInfoDTO.getPwd()));
                userInfo.setuLogo(userInfoDTO.getuLogo());
                userInfo.setuSex(userInfoDTO.getSex());
                userInfo.setBirthday(userInfoDTO.getBirthday());
                userInfo.setMobile(userInfoDTO.getMobile());
                userInfo.setPost(userInfoDTO.getPost());
                userInfo.setRoleId(userInfoDTO.getRole());
                //1代表用户可用
                userInfo.setuState(1);
                userInfo.setCreateDate(new java.util.Date());
                userInfo.setModifyDate(new java.util.Date());
            }
            //保存用户基本信息
            if (userRepository.addUser(userInfo)) {
                List<UserVillage> list = new ArrayList<UserVillage>();
                List<Village> villageList1 = new ArrayList<Village>();
                if (userInfoDTO.getRole().equals("000100000000")) {
                    villageList1 = villageRepository.getAllVillage();
                    for (Village village : villageList1) {
                        UserVillage userVillage = new UserVillage();
                        userVillage.setId(IdGen.uuid());
                        userVillage.setUserId(userInfo.getuId());
                        userVillage.setVillageId(village.getVillageId());
                        list.add(userVillage);
                    }
                }
                List<LinkedHashMap<String, String>> villageList = userInfoDTO.getVillageList();
                for (int i = 0; i < villageList.size(); i++) {
                    UserVillage userVillage = new UserVillage();
                    userVillage.setId(IdGen.uuid());
                    userVillage.setUserId(userInfo.getuId());
                    LinkedHashMap<String, String> map = villageList.get(i);
                    userVillage.setVillageId(map.get("id"));
                    list.add(userVillage);
                }
                if (userVillageRepository.addUserVillage(list)) {
                    //通过角色查询权限
                    Rolesetmap rolesetmap = roleSetRepository.getRolesetIdByRoleId(userInfoDTO.getRole());
                    //保存用户权限信息
                    Userroleauthority userroleauthority = new Userroleauthority();
                    userroleauthority.setRoleId(rolesetmap.getRolesetid());
                    userroleauthority.setUserId(userInfo.getuId());
//                    roleRoleauthorityRepository.AddRoleForUser(userroleauthority);
                    if (roleRoleauthorityRepository.addRoleForUser(userroleauthority)) {
                        result.addAttribute("success", "添加成功");
                    } else {
                        result.addAttribute("error", "添加失败");
                    }
                }
            }
        } else {
            result.addAttribute("error", "用户已存在");
        }
        return new SuccessApiResult(result);
    }

    @Override
    public boolean deleteUser(String userId) {
        if(userLoginBookRepository.updateState(userId)>=0) {
            return userRepository.deleteUser(userId);
        }
        return false;
    }

    @Override
    public ApiResult getUserById(String userId) {
        UserInfo userInfo = userRepository.getUserById(userId);
        ModelMap result = new ModelMap();
        if (userInfo != null) {
            List<Village> villageList = userRepository.getVillageByUserId(userId);
            Role role = roleRepository.getRoleById(userInfo.getRoleId());
            result.addAttribute("userInfo", userInfo);
            result.addAttribute("villageList", villageList);
            result.addAttribute("role", role);
        }
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult initUpdate(String userId, UserInfo userInfo1) {
        UserInfo userInfo = userRepository.getUserById(userId);
        ModelMap result = new ModelMap();
        if (userInfo != null) {
            List<Village> villageList = userRepository.getVillageByUserId(userId);
            Role role = roleRepository.getRoleById(userInfo.getRoleId());
            result.addAttribute("userInfo", userInfo);
            result.addAttribute("villageList", villageList);
            result.addAttribute("role", role);
        }
        List<Village> allVillageList = new ArrayList<Village>();
        List<Role> roleList = new ArrayList<Role>();
        if (userInfo1.getRoleId().equals("000100000000")) {
            allVillageList = villageRepository.getAllVillage();
            roleList = roleRepository.getRoleList();
        } else {
            allVillageList = userRepository.getVillageByUserId(userInfo1.getuId());
            Role role = roleRepository.getRoleById(userInfo1.getRoleId());
            if (role.getDegree() != 1) {
                roleList = roleRepository.inquireRole(role.getDegree());
            }
        }

        result.addAttribute("allRoleList", roleList);
        result.addAttribute("allVillageList", allVillageList);
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult updateUser(UserInfoDTO userInfoDTO) {
        ModelMap result = new ModelMap();
        UserInfo userInfo = userRepository.getUserById(userInfoDTO.getId());
        if (userInfo != null) {
            userInfo.setuId(userInfoDTO.getId());
            userInfo.setuLogo(userInfoDTO.getuLogo());
            userInfo.setuSex(userInfoDTO.getSex());
            userInfo.setBirthday(userInfoDTO.getBirthday());
            userInfo.setMobile(userInfoDTO.getMobile());
            userInfo.setRoleId(userInfoDTO.getRole());
            userInfo.setPost(userInfoDTO.getPost());
            userInfo.setuLogo(userInfoDTO.getuLogo());
            userInfo.setModifyDate(new java.util.Date());
        }
        if (userRepository.updateUser(userInfo)) {
            List<UserVillage> list = new ArrayList<UserVillage>();
            Rolesetmap rolesetmap = roleSetRepository.getRolesetIdByRoleId(userInfoDTO.getRole());
            Userroleauthority userroleauthority = roleRoleauthorityRepository.getRoleauthorityById(userInfoDTO.getId());
            if (userroleauthority != null) {
                userroleauthority.setRoleId(rolesetmap.getRolesetid());
                if (roleRoleauthorityRepository.updateRoleSet(userroleauthority)) {
                    List<UserVillage> userVillageList = userVillageRepository.getUserVillageList(userInfo.getuId());
                    if (userVillageList != null && userVillageList.size() > 0) {
                        if (userVillageRepository.deleteUserVillage(userVillageList)) {
                            List<LinkedHashMap<String, String>> villageList = userInfoDTO.getVillageList();
                            for (int i = 0; i < villageList.size(); i++) {
                                UserVillage userVillage = new UserVillage();
                                userVillage.setId(IdGen.uuid());
                                userVillage.setUserId(userInfo.getuId());
                                LinkedHashMap<String, String> map = villageList.get(i);
                                userVillage.setVillageId(map.get("id"));
                                list.add(userVillage);
                            }
                            if (userVillageRepository.addUserVillage(list)) {
                                result.addAttribute("success", "修改成功");
                            }
                        }
                    } else {
                        List<LinkedHashMap<String, String>> villageList = userInfoDTO.getVillageList();
                        for (int i = 0; i < villageList.size(); i++) {
                            UserVillage userVillage = new UserVillage();
                            userVillage.setId(IdGen.uuid());
                            userVillage.setUserId(userInfo.getuId());
                            LinkedHashMap<String, String> map = villageList.get(i);
                            userVillage.setVillageId(map.get("id"));
                            list.add(userVillage);
                        }
                        if (userVillageRepository.addUserVillage(list)) {
                            result.addAttribute("success", "修改成功");
                        }
                    }

                } else {
                    result.addAttribute("error", "修改失败");
                }
            }
        } else {
            result.addAttribute("error", "修改失败");
        }
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult updateUserPwd(UserInfoDTO userInfoDTO) {
        ModelMap result = new ModelMap();
        UserInfo userInfo = userRepository.getUserById(userInfoDTO.getId());
        if (userInfo != null) {
            if (!PasswordEncode.digestPassword(userInfoDTO.getPwd()).equals(userInfo.getuPassword())) {
                result.addAttribute("error", "请正确输入您的初始密码！");
            } else {
                userInfo.setuPassword(PasswordEncode.digestPassword(userInfoDTO.getNewPwd()));

                boolean f = userRepository.updateUser(userInfo);
                if (f) {
                    result.addAttribute("success", "修改成功！");
                } else {
                    result.addAttribute("error", "修改失败！");
                }
            }
        } else {
            result.addAttribute("error", "用户不存在！");
        }
        return new SuccessApiResult(result);
    }

    @Override
    public UserInfo GetUserInfoByTokenValue(String tokenId) {
        String userId = userLoginBookRepository.getUserIdBytokenId(tokenId);
        if (userId != null && !userId.equals("")) {
            UserInfo userInfo = userRepository.getUserById(userId);
            return userInfo;
        }
        return null;
    }

    @Override
    public ApiResult GetInfoByTokenId(String userId, String villageId) {
        List<ViewModelDto> list = new ArrayList<ViewModelDto>();
        ModelMap result = new ModelMap();

        if (userId != null) {
            UserInfo userInfo = userRepository.getUserById(userId);
            if (userInfo != null) {
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setId(userInfo.getuId());
                userInfoDTO.setuName(userInfo.getuName());
                userInfoDTO.setuLogo(userInfo.getuLogo());
                Role role = roleRepository.searRoleInfo(userInfo.getuId());
                userInfoDTO.setDegree(role.getDegree());
                List<Village> villageList = userRepository.getVillageByUserId(userId);
                result.addAttribute("villageList", villageList);
                if (userInfo.getRoleId().equals("000100000000")) {
                    userInfoDTO.setIfadmin(true);
                }
                result.addAttribute("userInfo", userInfoDTO);
            }
            List<Viewmodel> viewmodelList = viewModelService.getViewListByUserId(userId);
            Role role = roleRepository.searRoleInfo(userInfo.getuId());
            if (role.getDegree() > 2) {
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
                                secondViewModel.setRunscript("/home/district/lookDistrict/" + villageId);
                            }
                            List<Viewmodel> threeViewlist = viewModelService.getViewListThreeByUserId(userInfo.getuId(), secondViewModel.getMenuId());
                            secondViewModel.setThreeMenuList(threeViewlist);
                        }
                        viewModelDto.setChildMenuList(secanViewlist);
                        list.add(viewModelDto);
                    }
                }
            } else {
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

        }

        if (villageId != null && !villageId.equals(""))

        {
            List<Village> village = villageRepository.getVillage(villageId);
            result.addAttribute("village", village.get(0));
        }

        result.addAttribute("menulist", list);
        return new

                SuccessApiResult(result);

    }

    @Override
    public ApiResult changeVillage(String villageId, HttpServletRequest request, HttpServletResponse response) {
        List<Village> village = villageRepository.getVillage(villageId);
        Cookie cookie = new Cookie("villageId", villageId);
        cookie.setPath("/");
        cookie.setDomain(request.getServerName());
        cookie.setMaxAge(3600 * 24 * 30);
        response.addCookie(cookie);
        response.setHeader("P3P", "CP='CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR'");
        return new SuccessApiResult(village.get(0));
    }

    @Override
    public void logout(String tokenId, HttpServletRequest request, HttpServletResponse response) {
        List<UserLoginBook> oldLoginBook = userLoginBookRepository.logout(tokenId);
        if (oldLoginBook != null && oldLoginBook.size() > 0) {
            for (UserLoginBook userLoginBook : oldLoginBook) {
                userLoginBook.setLoginType("2");
                userLoginBookRepository.update(userLoginBook);
                Cookie cookies[] = request.getCookies();
                if (cookies != null) {
                    for (int i = 0; i < cookies.length; i++) {
                        if (cookies[i].getName().equals("vestaToken")) {
                            Cookie cookie = new Cookie("vestaToken", "");
                            cookie.setPath("/");
                            cookie.setDomain(request.getServerName());
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            response.setHeader("P3P", "CP='CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR'");
                        }
                    }
                }

            }
        }
    }
}
