package com.maxrocky.gundam.domain.map.repository;

import com.maxrocky.gundam.domain.map.model.BussinessMap;

import java.util.List;


/**
 * Created by yuer5 on 16/5/18.
 */
public interface BussinessMapRepository {
    boolean Add(BussinessMap object);

    void Update(BussinessMap object);

    void Del(int id);

    BussinessMap Get(int id);

    BussinessMap GetByVillageId(String villageId);
    List<BussinessMap> getBussinessMapByVillageId(String villageId);
}
