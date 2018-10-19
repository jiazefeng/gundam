package com.maxrocky.gundam.service.village.inf;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.village.dto.VillageDTO;
import com.maxrocky.gundam.domain.village.model.Village;

import java.util.List;

/**
 * Created by lizhipeng on 2016/5/24.
 */
public interface VillageService {
    //默认查询方法
    public ApiResult getVillage(String villageId);

    /**
     * 检索小区详细信息
     *
     * @param villageId
     * @return
     */
    public List<VillageDTO> getVillageDtoList(String villageId);

    //条件查询方法
    public ApiResult getVillageByItem(VillageDTO villageDTO);

    //插入小区
    public ApiResult addVillage(Village village,UserInfo userInfo,String villageId);

    //删除小区
    public boolean deleteVillage(String vId);

    //通过ID查询小区信息
    public Village searchVillageById(String id);

    //修改小区信息
    public ApiResult updateVillage(Village village,String roleId);

    //通过ID查询小区信息
    public VillageDTO findVillageById(String villageId);
    //通过用户id查询小区
    public List<Village> searchVillageByUserId(String userId);
}
