package com.maxrocky.gundam.controller;


import com.maxrocky.gundam.common.util.HttpClient;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.service.strategy.inf.StrategyService;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.domain.strategy.dto.StrategyDTO;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.service.user.inf.UserService;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 策略管理
 * Created by jiazefeng on 16/6/15.
 */
@RestController
@RequestMapping(value = "/strategy")
public class StrategyController {
    @Autowired
    private UserService userService;
    @Autowired
    private StrategyService strategyService;

    /**
     * 添加方案
     *
     * @param strategyDTO
     * @Author JZF
     */
    @RequestMapping(value = "/addStrategy", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult addStrategy(@RequestBody StrategyDTO strategyDTO, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (villageId != null && !villageId.equals("")) {
            strategyDTO.setVillage(villageId);
        }
        ModelMap result = new ModelMap();
        Strategy strategy = strategyService.addStrategy(strategyDTO);
        if(strategy!=null){
            int count = strategyService.GetCountByVillageId(strategyDTO.getVillage());
            List<StrategyDTO> strategyDTOList = strategyService.getStrategyDTOList(strategyDTO.getVillage());
            result.addAttribute("strategyList", strategyDTOList);
            result.addAttribute("count", count);
            result.addAttribute("success", "添加成功");
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url+"adminapi/strategy/" + strategy.getsId() + "/refresh");
            System.out.println("s");
        }else {
            result.addAttribute("error", "添加失败");
        }
        return new SuccessApiResult(result);
    }


    /**
     * 初始化检索方案信息
     *
     * @param tokenId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/strategyList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getStrategyList(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return strategyService.getStrategyList(villageId);
    }


    /**
     * 按条件检索方案信息
     *
     * @param strategyDTO
     * @return
     */
    @RequestMapping(value = "/strategyListByItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult getStrategyListByItem(@RequestBody StrategyDTO strategyDTO, @CookieValue(value = "villageId", required = false) String villageId) {
        strategyDTO.setVillage(villageId);
        return strategyService.getStrategyList(strategyDTO);
    }

    /*
   * 通过ID查询方案信息
   * @Author jzf
   * */
    @RequestMapping(value = "/searchStrategyById/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult searchStrategyById(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return new SuccessApiResult(strategyService.searchStrategyById(id, villageId));
    }


    /**
     * 删除方案信息
     *
     * @Author jzf
     */
    @RequestMapping(value = "/deleteStrategy/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult deleteStrategy(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        ModelMap result = new ModelMap();
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        if (strategyService.deleteStrategy(id)) {
            result.addAttribute("success","删除成功");
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url+"adminapi/strategy/" + id + "/refresh");
        } else {
            result.addAttribute("error","删除失败");
        }
        return  new SuccessApiResult(result);
    }


    /**
     * 修改方案
     *
     * @param strategyDTO
     * @param tokenId
     * @return
     */
    @RequestMapping(value = "/updateStrategy", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult updateStrategy(@RequestBody StrategyDTO strategyDTO, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        UserInfo userInfos = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfos == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        if (strategyDTO != null) {
            Strategy strategy = strategyService.updateStrategy(strategyDTO);
            if(strategy!=null){
                 result.addAttribute("success", "修改成功");
                 List<StrategyDTO> strategyDTOList = strategyService.getStrategyDTOList(strategy.getVillageId());
                 result.addAttribute("strategyList", strategyDTOList);
                JSONObject jsonObject = HttpClient.httpGet(HttpClient.url+"adminapi/strategy/" + strategy.getsId() + "/refresh");
            }else{
                result.addAttribute("error", "修改失败");
            }
        } else {
            result.addAttribute("error","修改失败");
        }
        return new SuccessApiResult(result);
    }

    /**
     * 查询方案信息
     *
     * @param villageId
     * @return
     */
    @RequestMapping(value = "/findStrategy", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult findStrategy(@CookieValue(value = "villageId", required = false) String villageId) {
        return strategyService.getStrategy(villageId);
    }
}
