package com.maxrocky.gundam.service.strategy.inf;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.strategy.dto.StrategyDTO;
import com.maxrocky.gundam.domain.strategy.model.Strategy;

import java.util.List;

/**
 * Created by jiazefeng on 16/6/15.
 */
public interface StrategyService {
    /**
     * 查询方案列表
     *
     * @return
     */
    public ApiResult getStrategyList(String villageId);

    /**
     * 检索方案信息
     *
     * @param villageId
     * @return
     */
    public List<StrategyDTO> getStrategyDTOList(String villageId);

    /**
     * 查询方案信息
     *
     * @return
     */
    public ApiResult getStrategy(String villageId);

    /**
     * 按条件检索方案信息
     *
     * @param strategyDTO
     * @return
     */
    public ApiResult getStrategyList(StrategyDTO strategyDTO);

    //通过ID查询方案信息
    public ApiResult searchStrategyById(String id, String villageId);

    /**
     * 删除方案信息
     *
     * @param id
     * @return
     */
    public boolean deleteStrategy(String id);

    /**
     * 添加方案
     *
     * @param strategyDTO
     * @return
     */
    public Strategy addStrategy(StrategyDTO strategyDTO);

    /**
     * 修改方案
     *
     * @param strategyDTO
     * @return
     */
    public Strategy updateStrategy(StrategyDTO strategyDTO);

    Strategy GetStrategy(String strategyId);

    void SaveStrategy(Strategy strategy);

    List<Strategy> GetStrategyList(int mapId);

    void DeleteStrategyById(String strategyId);

    void DelteStrategyByMap(int mapId);
    /**
     * 通过小区Id查询策略的总数
     * */
    int GetCountByVillageId(String villageId);

}
