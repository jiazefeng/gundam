package com.maxrocky.gundam.domain.robot.repositoryImpl;

import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maxrocky on 2016/06/23.
 */
@Repository
public class RobotStrategyRepositoryImpl extends BaseRepositoryImpl<RobotStrategy> implements RobotStratrgyRepository {
    @Override
    public boolean addRobotStrategy(List<RobotStrategy> robotStrategyList) {
        if (robotStrategyList.size() > 0) {
            for (RobotStrategy robotStrategy : robotStrategyList) {
                this.save(robotStrategy);
            }
            return true;
        }
        return false;
    }

    @Override
    public RobotStrategy getStrategyId(String robotId) {

        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where 1 = 1 AND r.robotId = ?";
        params.add(robotId);
        return (RobotStrategy)this.findObjectByQueryResult(sql, params);
    }


//    @Override
//    public RobotStrategy getStrategyByStrategyId(String strategyId) {
//        List<Object> params = new ArrayList<Object>();
//        String sql = "from RobotStrategy as rs where 1 = 1";
//        if (strategyId != null && !strategyId.equals("")) {
//            sql += " and rs.strategyId = ?";
//            params.add(strategyId);
//        }
//
//        return (RobotStrategy)this.findObjectByQueryResult(sql, params);
//    }

    @Override
    public boolean deleteRobotStrategy(RobotStrategy robotStrategy) {
        if(robotStrategy != null){
            this.deletePhysical(robotStrategy);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRobotStrategy(RobotStrategy robotStrategy) {
        if(robotStrategy != null){
            this.update(robotStrategy);
            return true;
        }
        return false;
    }

    @Override
    public List<RobotStrategy> getByStrategy(String strategyId) {

        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where r.strategyId = ?";
        params.add(strategyId);
        List<RobotStrategy> Infos = this.findByQueryList(sql, params);
        return Infos;
    }

    @Override
    public List<RobotStrategy> getByRobot(String robotId) {

        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where r.robotId = ?";
        params.add(robotId);
        List<RobotStrategy> Infos = this.findByQueryList(sql, params);
        return Infos;
    }

    @Override
    public List<RobotStrategy> getStrategyByRobotId(String robotId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where r.robotId = ? and r.executionState = ? ORDER BY r.startTime ASC";
        params.add(robotId);
        params.add(1);
        List<RobotStrategy> Infos = this.findByQueryList(sql, params);
        return Infos;
    }

    @Override
    public List<RobotStrategy> searStrategyByRobotId(String robotId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where r.robotId = ? and r.executionState = ? ORDER BY r.startTime ASC";
        params.add(robotId);
        params.add(0);
        List<RobotStrategy> Infos = this.findByQueryList(sql, params);
        return Infos;
    }

    @Override
    public List<RobotStrategy> searchStrategyList(String robotId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where r.robotId = ? AND r.executionState=? AND r.startTime >= ?  ORDER BY r.startTime ASC";
        params.add(robotId);
        params.add(0);
        params.add(new Time(new Date().getTime()));
        List<RobotStrategy> Infos = this.findByQueryList(sql, params);
        return Infos;
    }

    @Override
    public List<RobotStrategy> getRobotStrategy(RobotStrategy robotStrategy) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where r.robotId = ?  AND r.strategyId=?  AND r.startTime=? ";
        params.add(robotStrategy.getRobotId());
        params.add(robotStrategy.getStrategyId());
        params.add(robotStrategy.getStartTime());
        List<RobotStrategy> Infos = this.findByQueryList(sql, params);
        return Infos;
    }

    @Override
    public List<RobotStrategy> getByStrategyAndRobot(String strategyId, String robotId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotStrategy as r where r.strategyId = ? And r.robotId=?";
        params.add(strategyId);
        params.add(robotId);
        List<RobotStrategy> Infos = this.findByQueryList(sql, params);
        return Infos;
    }
    @Override
    public boolean addRobotStrategy(RobotStrategy robotStrategy){
        this.save(robotStrategy);
        return true;
    }

    @Override
    public RobotStrategy getById(String robotStrategyId){
        String sql = "from RobotStrategy as r where r.id = ?";
        List<Object> params = new ArrayList<Object>();

        params.add(robotStrategyId);
        return (RobotStrategy) this.findObjectByQueryResult(sql, params);
    }
    @Override
    public List<RobotStrategy> GetUnfinishedStrategy(String robotId){
        String sql = "from RobotStrategy as r where r.robotId = ? and (executionState = 0 or executionState = 2)";
        List<Object> params = new ArrayList<Object>();
        params.add(robotId);
        List<RobotStrategy> robotStrategies = this.findByQueryList(sql, params);
        return robotStrategies;
    }

}
