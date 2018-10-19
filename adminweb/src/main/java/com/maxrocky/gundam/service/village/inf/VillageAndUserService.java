package com.maxrocky.gundam.service.village.inf;


import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.domain.village.model.Village;

import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
public interface VillageAndUserService {
    //根据用户Id查询所关联小区的Id
    List<Village> getSellerIdByUserId(String id);
}
