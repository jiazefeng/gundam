package com.maxrocky.gundam.service.demo.inf;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.page.PageInfoTools;
import com.maxrocky.gundam.domain.demo.dto.DO0001.DemoJsonDTO;
import com.maxrocky.gundam.domain.demo.dto.DO0003.ModifyDemoJsonDTO;

/**
* Created by Tom on 2016/5/9 16:20.
* Describe:The demo service.
*/
public interface DemoService {

    /**
     * Code:DO0001
     * Type:UI Method
     * Describe:In object way to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param demoJsonDTO The create demo's json data.
     */
    ApiResult createDemo(DemoJsonDTO demoJsonDTO);

    /**
     * Code:DO0002
     * Type:UI Method
     * Describe:In ModelMap way to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name The demo's name.
     */
    ApiResult createDemo(String name);

    /**
     * Code:DO0003
     * Type:UI Method
     * Describe:In object way to modify data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param demoJsonDTO The modify demo's json data.
     */
    ApiResult modifyDemo(ModifyDemoJsonDTO demoJsonDTO);

    /**
     * Code:DO0004
     * Type:UI Method
     * Describe:In ModelMap way to modify data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param id The demo's id.
     * @param name The demo's name.
     */
    ApiResult modifyDemo(String id, String name);


    /**
     * Code:DO0005
     * Type:UI Method
     * Describe:Return the data of object.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name The demo's name.
     */
    ApiResult getByName(String name);

    /**
     * Code:DO0006
     * Type:UI Method
     * Describe:Return the data of list.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    ApiResult getList();

    /**
     * Code:DO0007
     * Type:UI Method
     * Describe:Return the data of list according to name.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name 名称
     */
    ApiResult getList(String name);

    /**
     * Code:DO0008
     * Type:UI Method
     * Describe:Remove the data according to id.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param id ID
     */
    ApiResult remove(String id);

    /**
     * Code:DO0009
     * Type:UI Method
     * Describe:In ModelMap way by transaction to create data.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     * @param name The demo's name.
     */
    ApiResult transaction(String name);

    /**
     * Code:DO0010
     * Type:UI Method
     * Describe:Return count.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    ApiResult getCount();

    /**
     * Code:DO0011
     * Type:UI Method
     * Describe:Return the data of page list.
     * CreateBy:Tom
     * CreateOn:2016-05-09 07:55:37
     */
    ApiResult getList(PageInfoTools pageInfoTools);
}
