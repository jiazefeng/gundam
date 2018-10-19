package com.maxrocky.gundam.domain.village.repository;


import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.hibernate.BaseRepository;


import java.util.List;

/**
 * Created by lizhipeng on 2016/5/24.
 */
public interface VillageRepository extends BaseRepository<Village> {
    //默认查询小区
    public List<Village> getVillage(String villageId);
    //通过条件查询小区
    public List<Village> getVillageByItem(Village village, int startRow);
    //获取小区总数量
    public int getCount(String villageId);
    //通过条件查询数量
    public int getCount(Village village);
    //添加小区
    public boolean addVillage(Village village);
    //删除小区
    public boolean deleteVillage(String Id);
    public Village searchVillageById(String id);
    //修改小区
    public boolean updateVillage(Village village);
    //查询全部小区（不分页）
    public List<Village> getAllVillage();
}
