package com.maxrocky.gundam.controller;
//
//import com.maxrocky.gundam.common.result.ApiResult;
//import com.maxrocky.gundam.domain.role.dto.RoleDTO;
//import com.maxrocky.gundam.service.role.inf.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;

        import com.maxrocky.gundam.common.result.ApiResult;
        import com.maxrocky.gundam.common.result.SuccessApiResult;
        import com.maxrocky.gundam.domain.role.dto.RoleDTO;
        import com.maxrocky.gundam.domain.user.model.UserInfo;
        import com.maxrocky.gundam.page.PageInfoTools;
        import com.maxrocky.gundam.service.role.inf.RoleService;
        import com.maxrocky.gundam.service.user.inf.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

/**
 * Created by lizhipeng on 2016/6/6.
 */
@RestController
@RequestMapping(value = "/role")
//@SessionAttributes(types={String.class,UserInfo.class},value = {"VillageList","userInfo"})
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    /**
     * 初始化页面
     * @Author lzp
     * */
    @RequestMapping(value = "/roleList",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    public ApiResult roleList(@CookieValue(value = "vestaToken", required = false) String vestatoken){
       UserInfo userInfo = userService.GetUserInfoByTokenValue(vestatoken);
        if(userInfo==null){
            return new SuccessApiResult("当前未登陆");
        }
       return roleService.getRoleList();
    }
    /**
     * 初始化添加页面
     * @Author lzp
     * */
    @RequestMapping(value = "/initAddRole",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    public ApiResult initAddRole(@CookieValue(value = "vestaToken", required = false) String vestatoken){
        UserInfo userInfo = userService.GetUserInfoByTokenValue(vestatoken);
        if(userInfo==null){
            return new SuccessApiResult("当前未登陆");
        }
        return roleService.initAddRole();
    }
    /**
     * 查询角色（分页）
     * @Author lzp
     * */
    @RequestMapping(value = "/searchRole",produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
    public ApiResult searchRole(@RequestBody PageInfoTools pageInfoTools,@CookieValue(value = "vestaToken", required = false) String vestatoken){
        UserInfo userInfo = userService.GetUserInfoByTokenValue(vestatoken);
        if(userInfo==null){
            return new SuccessApiResult("当前未登陆");
        }
        return roleService.searchRole(pageInfoTools);
    }
    /**
     * 添加角色
     * @Author lzp
     * */
    @RequestMapping(value = "/addRole",produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
    public ApiResult addRole(@RequestBody RoleDTO roleDTO,@CookieValue(value = "vestaToken", required = false) String vestatoken,@CookieValue(value = "villageId", required = false) String villageId){
        UserInfo userInfo = userService.GetUserInfoByTokenValue(vestatoken);
        if(userInfo==null){
            return new SuccessApiResult("当前未登陆");
        }
        return roleService.addRole(villageId,roleDTO,userInfo.getuName());
    }
    /**
     * 初始化修改角色页面
     * @Param roleId
     * @Author lzp
     * */
    @RequestMapping(value = "/initEditRole/{roleId}",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    public ApiResult initEditRole(@PathVariable("roleId") String roleId,@CookieValue(value = "vestaToken", required = false) String vestatoken){
        UserInfo userInfo = userService.GetUserInfoByTokenValue(vestatoken);
        if(userInfo==null){
            return new SuccessApiResult("当前未登陆");
        }
        return roleService.getRoleById(roleId);
    }
    /**
     * 修改角色
     * @Param roleDTO
     * @Author lzp
     * */
    @RequestMapping(value = "/updateRole",produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
    public ApiResult updateRole(@RequestBody RoleDTO roleDTO,@CookieValue(value = "vestaToken", required = false) String vestatoken){
        UserInfo userInfo = userService.GetUserInfoByTokenValue(vestatoken);
        if(userInfo==null){
            return new SuccessApiResult("当前未登陆");
        }
        return roleService.updateRole(roleDTO);
    }
    @RequestMapping(value = "/initAccredit/{roleId}",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    public ApiResult initAccredit(@PathVariable("roleId") String roleId,@CookieValue(value = "vestaToken", required = false) String vestatoken){
        UserInfo userInfo = userService.GetUserInfoByTokenValue(vestatoken);
        if(userInfo==null){
            return new SuccessApiResult("当前未登陆");
        }
        return roleService.initAccredit(roleId);
    }
}
