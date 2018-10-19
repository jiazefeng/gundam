package com.maxrocky.gundam.domain.robot.repositoryImpl;

import com.maxrocky.gundam.domain.robot.model.RobotFunction;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.RobotFunctionRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxrocky on 2016/06/23.
 */
@Repository
public class RobotFunctionRepositoryImpl extends BaseRepositoryImpl<RobotFunction> implements RobotFunctionRepository {

    @Override
    public boolean addRobotFunction(List<RobotFunction> robotFunctionList) {
        if (robotFunctionList.size() > 0) {
            for (RobotFunction robotFunction : robotFunctionList) {
                this.save(robotFunction);
            }
            return true;
        }
        return false;
    }

    @Override
    public RobotFunction getFunctionId(String robotId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotFunction as rf where 1 = 1";
        if (robotId != null && !robotId.equals("")) {
            sql += " and rf.rId = ?";
            params.add(robotId);
        }

        return (RobotFunction) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public boolean deleteRobotFunction(List<RobotFunction> robotFunction) {
        if (robotFunction.size() > 0) {
            for (RobotFunction robotFunction1 : robotFunction) {
                this.deletePhysical(robotFunction1);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<RobotFunction> getByFunction(String robotId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotFunction as rf where rf.rId = ?";
        params.add(robotId);
        List<RobotFunction> Infos = this.findByQueryList(sql, params);
        return Infos;
    }
}
