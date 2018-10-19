package com.maxrocky.gundam.service.role.impl;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.common.util.SqlDateUtils;
import com.maxrocky.gundam.domain.role.dto.RoleDTO;
import com.maxrocky.gundam.domain.role.model.*;
import com.maxrocky.gundam.domain.role.repository.RoleButtonMapRepository;
import com.maxrocky.gundam.domain.role.repository.RoleRepository;
import com.maxrocky.gundam.domain.role.repository.ViewModelRepository;
import com.maxrocky.gundam.domain.user.dto.SecondViewModel;
import com.maxrocky.gundam.domain.user.dto.ViewModelDto;
import com.maxrocky.gundam.domain.user.repository.RoleSetMapRepository;
import com.maxrocky.gundam.domain.user.repository.RoleSetRepository;

import com.maxrocky.gundam.domain.user.repository.UserLoginBookRepository;
import com.maxrocky.gundam.page.PageInfoTools;
import com.maxrocky.gundam.service.role.inf.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/6.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ViewModelRepository viewModelRepository;
    @Autowired
    private RoleButtonMapRepository roleButtonMapRepository;
    @Autowired
    private RoleSetMapRepository roleSetMapRepository;
    @Autowired
    private RoleSetRepository roleSetRepository;
    @Autowired
    private UserLoginBookRepository userLoginBookRepository;
    @Override
    public ApiResult getRoleList(){
            //获取角色的总数
            int count = roleRepository.getRoleCount();
            List<Role> list = roleRepository.getRole();
            ModelMap result = new ModelMap();
            result.addAttribute("count", count);
            result.addAttribute("roleList", list);
            return new SuccessApiResult(result);

    }
    @Override
    public ApiResult initAddRole(){
        ModelMap result = new ModelMap();
        //获取一级菜单
        List<Viewmodel> list =  viewModelRepository.getViewModel();
        List<ViewModelDto> menuList = new ArrayList<ViewModelDto>();
        for (Viewmodel viewmodel : list){
            List<Viewmodel> list1 = viewModelRepository.getViewLisOthertBymenuId(viewmodel.getMenuId());
//            List<SecondViewModel> list2 = new ArrayList<SecondViewModel>();
//            for (Viewmodel viewmodel1 :list1){
//                SecondViewModel secondViewModel = new SecondViewModel();
//                secondViewModel.setMenuName(viewmodel1.getMenuName());
//                secondViewModel.setMenuId();
//            }
            ViewModelDto viewModelDto = new ViewModelDto();
            viewModelDto.setMenuName(viewmodel.getMenuName());
            viewModelDto.setMenuId(viewmodel.getMenuId());
            viewModelDto.setAllchildMenuList(list1);
            menuList.add(viewModelDto);
        }
        result.addAttribute("menuList",menuList);
        return new SuccessApiResult(result);
    }
    @Override
    public ApiResult addRole(String villageId,RoleDTO roleDTO,String userInfo) {
        ModelMap result = new ModelMap();
        if (roleDTO != null) {
            Roleset roleset = new Roleset();
            roleset.setSetId(IdGen.uuid());
            roleset.setMakeDate(SqlDateUtils.getDate());
            roleset.setModifyDate(SqlDateUtils.getDate());
            roleset.setDesc(roleDTO.getRoleName());
            roleset.setSetState("01");
            roleset.setCompanyId(villageId);
            roleset.setMakeTime(SqlDateUtils.getTime());
            roleset.setModifyTime(SqlDateUtils.getTime());
            roleset.setOperator(userInfo);
            if (roleSetRepository.addRoleSet(roleset)) {
                Role role = new Role();
                role.setRoleId(IdGen.uuid());
                role.setRoleName(roleDTO.getRoleName());
                role.setState(roleDTO.getState());
                role.setMakeDate(new java.util.Date());
                role.setModifyDate(new java.util.Date());
                role.setRoleSetId(roleset.getSetId());
                if(roleRepository.addRole(role)){
                List<LinkedHashMap<String,String>> viewModelList = roleDTO.getViewModelList();
                List<Rolebuttonmap> list = new ArrayList<Rolebuttonmap>();
                for (int i = 0;i<viewModelList.size();i++) {
                    Rolebuttonmap rolebuttonmap = new Rolebuttonmap();
                    rolebuttonmap.setRoleId(role.getRoleId());
                    rolebuttonmap.setMakeDate(new java.util.Date());
                    rolebuttonmap.setModifyDate(new java.util.Date());
                    rolebuttonmap.setButtonState("01");
                    rolebuttonmap.setButtonId("none");
                    LinkedHashMap<String,String> map = viewModelList.get(i);
                    rolebuttonmap.setMenuId(map.get("id"));
                    list.add(rolebuttonmap);
                }
                if (roleButtonMapRepository.addRoleButtonMap(list)) {
                        Rolesetmap rolesetmap = new Rolesetmap();
                        rolesetmap.setRoleid(role.getRoleId());
                        rolesetmap.setRolesetid(roleset.getSetId());
                        if(roleSetMapRepository.addRoleSetMap(rolesetmap)){
                            result.addAttribute("success","添加成功");
                        }
                    }else{
                    result.addAttribute("error","添加失败");
                }
                }else{
                    result.addAttribute("error","添加失败");
                }
            }else{
                result.addAttribute("error","添加失败");
            }
        }else{
            result.addAttribute("error","参数错误");
        }
        return new SuccessApiResult(result);
    }
    @Override
    public  ApiResult searchRole(PageInfoTools pageInfoTools){
        int count = roleRepository.getRoleCount();
        List<Role> roleList = roleRepository.searchRole(pageInfoTools.getIndex());
        ModelMap result = new ModelMap();
        result.addAttribute("roleList",roleList);
        result.addAttribute("page",pageInfoTools.getIndex());
        result.addAttribute("count",count);
        return new SuccessApiResult(result);
    }
    @Override
    public ApiResult getRoleById(String id){
        Role role = roleRepository.getRoleById(id);
        return new SuccessApiResult(role);
    }
    @Override
    public ApiResult updateRole(RoleDTO roleDTO){
        ModelMap result = new ModelMap();
        if(roleDTO!=null){
            Role role = roleRepository.getRoleById(roleDTO.getRoleId());
            role.setRoleName(roleDTO.getRoleName());
            role.setState(roleDTO.getState());
            if(roleRepository.updateRole(role)){
                if(roleDTO.getViewModelList()!=null){
                    roleButtonMapRepository.deleteRoleButtonMap(roleDTO.getRoleId());
                    List<LinkedHashMap<String,String>> viewModelList = roleDTO.getViewModelList();
                    List<Rolebuttonmap> list = new ArrayList<Rolebuttonmap>();
                    for (int i = 0;i<viewModelList.size();i++){
                        Rolebuttonmap rolebuttonmap = new Rolebuttonmap();
                        rolebuttonmap.setRoleId(role.getRoleId());
                        rolebuttonmap.setModifyDate(new java.util.Date());
                        rolebuttonmap.setButtonState("01");
                        rolebuttonmap.setButtonId("none");
                        LinkedHashMap<String,String> map = viewModelList.get(i);
                        rolebuttonmap.setMenuId(map.get("id"));
                        list.add(rolebuttonmap);
                    }
                    if(roleButtonMapRepository.addRoleButtonMap(list)){
                        result.addAttribute("success","授权成功");
                    }else {
                        result.addAttribute("error","授权失败");
                    }
                }else {
                    result.addAttribute("success", "修改成功");
                }
            }else{
                result.addAttribute("error","授权失败");
            }
        }else {
            result.addAttribute("error","参数错误");
        }
        return new SuccessApiResult(result);
    }
    @Override
    public ApiResult initAccredit(String id){
        Role role = roleRepository.getRoleById(id);
        ModelMap result = new ModelMap();
        //获取一级菜单
        List<Viewmodel> list =  viewModelRepository.getViewModel();
        List<ViewModelDto> menuList = new ArrayList<ViewModelDto>();
        for (Viewmodel viewmodel : list){
            List<Viewmodel> list1 = viewModelRepository.getViewLisOthertBymenuId(viewmodel.getMenuId());
            ViewModelDto viewModelDto = new ViewModelDto();
            viewModelDto.setMenuName(viewmodel.getMenuName());
            viewModelDto.setMenuId(viewmodel.getMenuId());
            viewModelDto.setAllchildMenuList(list1);
            menuList.add(viewModelDto);
        }
        List<Rolebuttonmap> rolebuttonmap =roleButtonMapRepository.getButtonList(id);
        result.addAttribute("buttonList",rolebuttonmap);
        result.addAttribute("menuList",menuList);
        result.addAttribute("role",role);
        return new SuccessApiResult(result);
    }
}
