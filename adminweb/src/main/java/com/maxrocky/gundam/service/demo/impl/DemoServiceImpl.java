package com.maxrocky.gundam.service.demo.impl;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.domain.demo.model.Demo;
import com.maxrocky.gundam.domain.demo.repository.DemoRepository;
import com.maxrocky.gundam.page.PageInfoTools;
import com.maxrocky.gundam.service.demo.inf.DemoService;
import com.maxrocky.gundam.domain.demo.dto.DO0001.DemoJsonDTO;
import com.maxrocky.gundam.domain.demo.dto.DO0003.ModifyDemoJsonDTO;
import com.maxrocky.gundam.domain.demo.dto.DO0005.DemoDetailJsonDTO;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
* Created by Tom on 2016/5/9 16:21.
* Describe:The demo service.
*/
@Service
public class DemoServiceImpl implements DemoService {

    /* demo repository */
    @Autowired
    DemoRepository demoRepository;
    /* mapper */
    @Autowired
    MapperFacade mapper;

    /**
     * Code:DO0001
     * Type:UI Method
     * Describe:In object way to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param demoJsonDTO The create demo's json data.
     */
    @Override
    public ApiResult createDemo(DemoJsonDTO demoJsonDTO) {
        demoRepository.save(new Demo(demoJsonDTO.getName()));
        return new SuccessApiResult();
    }

    /**
     * Code:DO0002
     * Type:UI Method
     * Describe:In ModelMap way to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name The demo's name.
     */
    @Override
    public ApiResult createDemo(String name) {
        demoRepository.save(new Demo(name));
        return new SuccessApiResult();
    }

    /**
     * Code:DO0003
     * Type:UI Method
     * Describe:In object way to modify data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param demoJsonDTO The modify demo's json data.
     */
    @Override
    public ApiResult modifyDemo(ModifyDemoJsonDTO demoJsonDTO) {

        Demo demo = demoRepository.get(demoJsonDTO.getId());
        if(demo == null){
            return new ErrorApiResult("0x00010001");
        }

        demo.setDemoName(demoJsonDTO.getName());
        demo.setModifyOn(Calendar.getInstance());
        demoRepository.update(demo);
        return new SuccessApiResult();
    }

    /**
     * Code:DO0004
     * Type:UI Method
     * Describe:In ModelMap way to modify data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param id The demo's id.
     * @param name The demo's name.
     */
    @Override
    public ApiResult modifyDemo(String id, String name) {
        Demo demo = demoRepository.get(id);
        if(demo == null){
            return new ErrorApiResult("0x00010001");
        }

        demo.setDemoName(name);
        demo.setModifyOn(Calendar.getInstance());
        demoRepository.update(demo);
        return new SuccessApiResult();
    }

    /**
     * Code:DO0005
     * Type:UI Method
     * Describe:Return the data of object.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name 名称
     */
    @Override
    public ApiResult getByName(String name) {
        Demo demo = demoRepository.getByName(name);
        if(demo == null){
            return new ErrorApiResult("0x00010002");
        }
        DemoDetailJsonDTO demoDetailJsonDTO = mapper.map(demo, DemoDetailJsonDTO.class);
        return new SuccessApiResult(demoDetailJsonDTO);
    }

    /**
     * Code:DO0006
     * Type:UI Method
     * Describe:Return the data of list.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @Override
    public ApiResult getList() {

        List<DemoDetailJsonDTO> demoDetailList = new ArrayList<DemoDetailJsonDTO>();
        List<Demo> demoList = demoRepository.getAll();
        for (Demo demo : demoList){
            demoDetailList.add(mapper.map(demo, DemoDetailJsonDTO.class));
        }

        return new SuccessApiResult(demoDetailList);
    }

    /**
     * Code:DO0007
     * Type:UI Method
     * Describe:Return the data of list according to name.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name 名称
     */
    @Override
    public ApiResult getList(String name) {
        List<DemoDetailJsonDTO> demoDetailList = new ArrayList<DemoDetailJsonDTO>();
        List<Demo> demoList = demoRepository.getList(name);
        for (Demo demo : demoList){
            demoDetailList.add(mapper.map(demo, DemoDetailJsonDTO.class));
        }

        return new SuccessApiResult(demoDetailList);
    }

    /**
     * Code:DO0008
     * Type:UI Method
     * Describe:Remove the data according to id.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param id ID
     */
    @Override
    public ApiResult remove(String id) {

        Demo demo = demoRepository.get(id);
        if(demo != null){
            demoRepository.deletePhysical(demo);
        }

        return new SuccessApiResult();
    }

    /**
     * Code:DO0009
     * Type:UI Method
     * Describe:In ModelMap way by transaction to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name The demo's name.
     */
    @Override
    public ApiResult transaction(String name) {
        demoRepository.save(new Demo(name));

//        TestEntity testEntity = new TestEntity(name);
//        testEntity.setTestId(null);//set id is null for transaction.
//        testRepository.save(testEntity);

        return new SuccessApiResult();
    }

    /**
     * Code:DO0010
     * Type:UI Method
     * Describe:Return count.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @Override
    public ApiResult getCount() {

        int count = demoRepository.getCount();

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("count", count);

        return new SuccessApiResult(modelMap);
    }

    /**
     * Code:DO0011
     * Type:UI Method
     * Describe:Return the data of page list.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    @Override
    public ApiResult getList(PageInfoTools pageInfoTools) {
        List<DemoDetailJsonDTO> demoDetailList = new ArrayList<DemoDetailJsonDTO>();
        List<Demo> demoList = demoRepository.getList(pageInfoTools);
        for (Demo demo : demoList){
            demoDetailList.add(mapper.map(demo, DemoDetailJsonDTO.class));
        }

        return new SuccessApiResult(demoDetailList);
    }

}
