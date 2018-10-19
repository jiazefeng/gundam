package com.maxrocky.gundam.controller;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.page.PageInfoTools;
import com.maxrocky.gundam.service.demo.inf.DemoService;
import com.maxrocky.gundam.domain.demo.dto.DO0001.DemoJsonDTO;
import com.maxrocky.gundam.domain.demo.dto.DO0003.ModifyDemoJsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Tom on 2016/5/9 16:23.
 * Describe:The to demo.
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    /* demo service */
    @Autowired
    DemoService demoService;

    /**
     * Code:DO0001
     * Type:UI Method
     * Describe:In object way to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demoA"
                  , produces = "application/json;charset=UTF-8"
                  , method = RequestMethod.POST)
    public ApiResult createDemo(@RequestBody DemoJsonDTO demoJsonDTO){
        try {
            return demoService.createDemo(demoJsonDTO);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0002
     * Type:UI Method
     * Describe:In ModelMap way to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demoB"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.POST)
    public ApiResult createDemo(@RequestBody ModelMap modelMap){
        try {
            String name = (String)modelMap.get("name");
            return demoService.createDemo(name);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0003
     * Type:UI Method
     * Describe:In object way to modify data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demoA"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.PUT)
    public ApiResult modifyDemo(@RequestBody ModifyDemoJsonDTO demoJsonDTO){
        try {
            return demoService.modifyDemo(demoJsonDTO);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0004
     * Type:UI Method
     * Describe:In ModelMap way to modify data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demoB"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.PUT)
    public ApiResult modifyDemo(@RequestBody ModelMap modelMap){
        try {
            String id = (String)modelMap.get("id");
            String name = (String)modelMap.get("name");
            return demoService.modifyDemo(id, name);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0005
     * Type:UI Method
     * Describe:Return the data of object.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demo/{name}"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.GET)
    public ApiResult getDemoByName(@PathVariable("name") String name){
        try {
            return demoService.getByName(name);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0006
     * Type:UI Method
     * Describe:Return the data of list.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demoList"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.GET)
    public ApiResult getDemoList(){
        try {
            return demoService.getList();
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0007
     * Type:UI Method
     * Describe:Return the data of list according to name.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demoList/{name}"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.GET)
    public ApiResult getDemoList(@PathVariable("name") String name){
        try {
            return demoService.getList(name);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0008
     * Type:UI Method
     * Describe:Remove the data according to id.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demo/{id}"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.DELETE)
    public ApiResult remove(@PathVariable("id") String id){
        try {
            return demoService.remove(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0009
     * Type:UI Method
     * Describe:In ModelMap way by transaction to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/transaction"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.POST)
    public ApiResult transaction(@RequestBody ModelMap modelMap){
        try {
            String name = (String)modelMap.get("name");
            return demoService.transaction(name);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0010
     * Type:UI Method
     * Describe:Return count.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/count"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.GET)
    public ApiResult count(){
        try {
            return demoService.getCount();
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

    /**
     * Code:DO0011
     * Type:UI Method
     * Describe:Return the data of page list.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @RequestMapping(value = "/demoListOfPage"
            , produces = "application/json;charset=UTF-8"
            , method = RequestMethod.GET)
    public ApiResult getDemoListOfPage(PageInfoTools pageInfoTools){
        try {
            return demoService.getList(pageInfoTools);
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorApiResult("0x00000001");
        }
    }

}
