package com.maxrocky.gundam.controller;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.domain.map.dto.GraphDto;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.village.dto.VillageDTO;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.service.map.inf.MapService;
import com.maxrocky.gundam.service.user.inf.UserService;
import com.maxrocky.gundam.service.village.inf.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lizhipeng on 2016/5/24.
 */
@RestController
@RequestMapping(value = "/village")
public class VillageController {
    @Autowired
    private VillageService villageService;
    @Autowired
    private UserService userService;
    @Autowired
    private MapService mapService;

    /**
     * 默认查询小区
     *
     * @Author lzp
     */
    @RequestMapping(value = "/searchVillage", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getVillage(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return villageService.getVillage(villageId);
    }

    /**
     * 通过小区ID查询小区
     *
     * @Author lzp
     */

    @RequestMapping(value = "/searchVillageByVillageId/{villageId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult searchVillageByVillageId(@PathVariable("villageId") String villageId, @CookieValue(value = "vestaToken", required = false) String tokenId) throws Exception {
        if (villageId.equals("0")) {
            return new SuccessApiResult();
        }
        ModelMap result = new ModelMap();
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        VillageDTO villageDTO = villageService.findVillageById(villageId);
        if (villageDTO != null) {
            result.addAttribute(villageDTO);
        }
        GraphDto graphDto = mapService.GetGraphDto(villageId);
        if (graphDto != null) {
            result.addAttribute(graphDto);
        } else {
            graphDto = new GraphDto();
            result.addAttribute(graphDto);
        }

        return new SuccessApiResult(result);
    }

    /**
     * 查询小区通过条件
     *
     * @Param villageDTO
     * @Author lzp
     */
    @RequestMapping(value = "/searchVillageByItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult getVillageByItem(@RequestBody VillageDTO villageDTO, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        villageDTO.setDistrictId(villageId);
        return villageService.getVillageByItem(villageDTO);
    }

    /**
     * 添加小区
     *
     * @Param villageDTO
     * @Author lzp
     */
    @RequestMapping(value = "/addVillage", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult addVillage(@RequestBody VillageDTO villageDTO, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        Village village = new Village();
        if (villageDTO != null) {
            village.setVillageId(IdGen.uuid());
            village.setVillageName(villageDTO.getDistrictName());
            village.setUserName(villageDTO.getUser());
            village.setMobile(villageDTO.getPhone());
            village.setAddress(villageDTO.getAddress());
            village.setLineCount(0);
            village.setState(1);
            village.setRobotCount(0);
            village.setTaskCount(0);
            village.setCreateDate(new java.util.Date());
            village.setModifyDate(new java.util.Date());
        }
        return villageService.addVillage(village, userInfo, villageId);
    }

    /**
     * 修改小区
     *
     * @Param villageDTO
     * @Author lzp
     */
    @RequestMapping(value = "/updateVillage", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult updateVillage(@RequestBody VillageDTO villageDTO, @CookieValue(value = "vestaToken", required = false) String tokenId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        Village village = villageService.searchVillageById(villageDTO.getDistrictId());
        if (villageDTO != null) {
            village.setVillageName(villageDTO.getDistrictName());
            village.setUserName(villageDTO.getUser());
            village.setMobile(villageDTO.getPhone());
            village.setAddress(villageDTO.getAddress());
            village.setModifyDate(new java.util.Date());
        }
//        if (villageService.updateVillage(village,u) != null) {
        return villageService.updateVillage(village, userInfo.getRoleId());
//        } else {
//            return new ErrorApiResult("修改小区");
//        }
    }

    /**
     * 删除小区
     *
     * @Author lzp
     * @Param villageId
     */
    @RequestMapping(value = "/deleteVillage/{villageId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult deleteVillage(@PathVariable("villageId") String villageId, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        ModelMap result = new ModelMap();
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (villageService.deleteVillage(villageId)) {
            result.addAttribute("success", "删除成功");
            List<Village> villageList = villageService.searchVillageByUserId(userInfo.getuId());
            result.addAttribute("villageList", villageList);
        } else {
            result.addAttribute("error", "删除失败");
        }
        return new SuccessApiResult(result);
    }
}
