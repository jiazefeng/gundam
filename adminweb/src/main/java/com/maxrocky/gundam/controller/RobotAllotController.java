package com.maxrocky.gundam.controller;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.HttpClient;
import com.maxrocky.gundam.domain.robot.dto.RobotStrategyDto;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.strategy.dto.StrategyDTO;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.service.robotAllot.inf.RobotAllotService;
import com.maxrocky.gundam.service.strategy.inf.StrategyService;
import com.maxrocky.gundam.service.user.inf.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 机器人分配
 * Created by jiazefeng on 23/6/15.
 */
@RestController
@RequestMapping(value = "/robotAllot")
public class RobotAllotController {
    @Autowired
    private RobotAllotService robotAllotService;
    @Autowired
    private UserService userService;


    /**
     * 机器人分配
     *
     * @param robotStrategy
     * @Author JZF
     */
    @RequestMapping(value = "/addRobotAllot", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult addRobotAllot(@RequestBody RobotStrategy robotStrategy, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        RobotStrategy robotStrategy1 = robotAllotService.addRobotStrategy(robotStrategy);
        if (robotStrategy1 != null) {
            result.addAttribute("success", "分配方案成功");
            List<RobotStrategyDto> robotStrategyDtoList = robotAllotService.searchStrategyListById(robotStrategy.getRobotId());
            List<RobotStrategyDto> robotStrategyDtoList1 = robotAllotService.searchStrategyListByRId(robotStrategy.getRobotId());
            result.addAttribute("offRobotStrategyDtoList", robotStrategyDtoList);
            result.addAttribute("noRobotStrategyDtoList", robotStrategyDtoList1);
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotStrategy1.getRobotId() + "/refresh");
        } else {
            result.addAttribute("error", "分配方案失败");
        }
        return new SuccessApiResult(result);
    }

    /**
     * 通过ID查询机器人执行方案的信息
     *
     * @Author jzf
     */
    @RequestMapping(value = "/checkRobotStrategyById/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult searchStrategyById(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return robotAllotService.getRobotStrategyById(id, villageId);
    }

    /**
     * 通过ID查询机器人全部的方案信息
     *
     * @Author jzf
     */
    @RequestMapping(value = "/strategyListById/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult strategyListById(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return robotAllotService.searchStrategyById(id);
    }
    /**
     * 通过ID查询机器人在当前时间未执行的方案信息
     *
     * @Author jzf
     */
    @RequestMapping(value = "/searchStrategyListById/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult searchStrategyListById(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return robotAllotService.searchStrategyListByRobotId(id);
    }

    /**
     * 删除分配的策略信息
     *
     * @Author jzf
     */
    @RequestMapping(value = "/deleteRobotStrategy", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult deleteRobotStrategy(@RequestBody RobotStrategy robotStrategy, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        ModelMap result = new ModelMap();
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (robotAllotService.deleteRobotStrategy(robotStrategy)) {
            result.addAttribute("success","删除成功");
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotStrategy.getRobotId() + "/refresh");
        } else {
            result.addAttribute("error","删除失败");
        }
        return  new SuccessApiResult(result);
    }
}
