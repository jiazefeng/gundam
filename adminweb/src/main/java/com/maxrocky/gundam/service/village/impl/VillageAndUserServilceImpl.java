package com.maxrocky.gundam.service.village.impl;

import com.maxrocky.gundam.domain.user.model.UserVillage;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.domain.village.repository.VillageAndUserRepository;
import com.maxrocky.gundam.domain.village.repository.VillageRepository;
import com.maxrocky.gundam.service.village.inf.VillageAndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
@Service
public class VillageAndUserServilceImpl implements VillageAndUserService {
    @Autowired
    private VillageAndUserRepository villageAndUserRepository;
    @Autowired
    private VillageRepository villageRepository;
    @Override
    public List<Village> getSellerIdByUserId(String id) {
        List<UserVillage> villagesList = this.villageAndUserRepository.getSellerIdByUserId(id);
        List<Village> villageList = new ArrayList<Village>();
        for(UserVillage userVillage :villagesList){
            Village villages = villageRepository.searchVillageById(userVillage.getVillageId());
            villageList.add(villages);
        }
        return villageList;
    }
}
