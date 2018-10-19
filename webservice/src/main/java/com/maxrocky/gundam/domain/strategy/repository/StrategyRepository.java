package com.maxrocky.gundam.domain.strategy.repository;

import com.maxrocky.gundam.domain.strategy.model.Strategy;

import java.util.List;

/**
 * Created by JIAZEFENG on 16/6/15.
 */
public interface StrategyRepository {
    /**
     * 分页查询方案列表
     *
     * @return
     */
    public List<Strategy> getStrategyList();
    /**
     * 查询方案列表
     *
     * @return
     */
    public List<Strategy> getStrategy();

    /**
     * 查询总条数
     *
     * @return
     */
    public int getStrategyTotal();

    /**
     * 按条件检索方案列表
     *
     * @param startRow
     * @return
     */
    public List<Strategy> getStrategyList(Strategy strategy,String villageId, int startRow);
    /**
     * 按条件检索方案列表
     *
     * @param id
     * @return
     */
    public List<Strategy> getStrategyList(String id);

    /**
     * 按小区id检索方案列表
     *
     * @param id
     * @return
     */
    public List<Strategy> getStrategyListByVillage(String id);

    /**
     * 按条件查询总条数
     *
     * @return
     */
    public int getStrategyTotal(Strategy strategy);
    /**
     * 按小区ID查询总条数
     *
     * @return
     */
    public int getStrategyTotalByVillageId(String villageId);

    /**
     * 根据id 查询方案信息
     *
     * @param id
     * @return
     */
    public Strategy getStrategyById(String id);
    /**
     * 根据id 查询方案信息
     *
     * @param id
     * @return
     */
    public Strategy getSchemeById(String id);

    /**
     * 删除方案信息
     *
     * @param id
     * @return
     */
    public boolean deleteStrategy(String id);

    //添加方案
    public boolean addStrategy(Strategy strategy);
    //修改方案
    public boolean updateStrategy(Strategy strategy);

    /**
     * 根据小区id得到地图id
     * @param villageId
     * @return
     */
    public int  getMapIdByVillageId(String villageId);

    void Create(Strategy entity);

    void Update(Strategy entity);

    void Delete(String key);

    void BatchDelete(int mapId);

    Strategy Retrive(int key);

    List<Strategy> List(int mapId);

}
