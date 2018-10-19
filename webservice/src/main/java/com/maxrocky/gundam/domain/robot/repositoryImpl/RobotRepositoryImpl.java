package com.maxrocky.gundam.domain.robot.repositoryImpl;

import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxrocky on 2016/5/26.
 */
@Repository
public class RobotRepositoryImpl extends BaseRepositoryImpl<RobotInfo> implements RobotRepository {

    @Override
    public boolean addRobot(RobotInfo robotInfo) {
        if (robotInfo != null) {
            this.save(robotInfo);
            return true;
        }
        return false;
    }

    @Override
    public List<RobotInfo> getRobotList() {
        List<Object> params = new ArrayList<Object>();
        String hql = " from RobotInfo WHERE status = 1";
        return this.findByQueryList(hql, params);
    }

    @Override
    public List<RobotInfo> getRobotList(String alarmId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotInfo as r where r.status = 1";
        if (alarmId != null && !alarmId.equals("")) {
            sql += " and r.robotId in (select a.robotId from AlarmRobot a where a.alarmId = ?)";
            params.add(alarmId);
        }

        return this.findByQueryList(sql, params);
    }

    @Override
    public List<RobotInfo> getRobotListByVieoId(String videoId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotInfo as r where r.status = 1";
        if (videoId != null && !videoId.equals("")) {
            sql = sql + " and r.robotId in (select v.robotId from VideoRobot v where v.videoId = ?)";
            params.add(videoId);
        }

        return this.findByQueryList(sql, params);
    }

    @Override
    public List<RobotInfo> getRobotByPicId(String pictureId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotInfo as r where r.status = 1";
        if (pictureId != null && !pictureId.equals("")) {
            sql += " and r.robotId in (select p.robotId from PictureRobot p where p.pictureId = ?)";
            params.add(pictureId);
        }

        List<RobotInfo> robotInfos = this.findByQueryList(sql, params);
        return robotInfos;
    }


    @Override
    public List<RobotInfo> getRobotByVillageId(String villageId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from RobotInfo as r where r.status = ? and r.villageId = ? ORDER BY r.modifyOn DESC";
        params.add(1);
        params.add(villageId);
        List<RobotInfo> robotInfos = this.findByQueryList(sql, new PageInfoTools(), params);
        return robotInfos;
    }

    @Override
    public int getRobotTotalByVillageId(String villageId) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("status", 1));
        if (villageId != null && !villageId.equals("")) {
            sql.add(Restrictions.eq("villageId", villageId));
        }
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public List<RobotInfo> getStrategyListByItem(RobotInfo robotInfo, int startRow) {
        String sql = "from RobotInfo as r where r.status=?";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        if (robotInfo.getVillageId() != null && !robotInfo.getVillageId().equals("")) {
            sql += " AND r.villageId =?";
            params.add(robotInfo.getVillageId());
        }
        sql += " ORDER BY r.modifyOn DESC";
        List<RobotInfo> robotInfoList = findByQueryList(sql, new PageInfoTools(startRow, 10), params);
        return robotInfoList;
    }

    @Override
    public RobotInfo getRobotInfoById(String id) {
        String sql = "from RobotInfo as r where r.status = ? AND r.robotId = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        params.add(id);
        return (RobotInfo) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public boolean updateRobot(RobotInfo robotInfo) {
        if (robotInfo != null) {
            this.update(robotInfo);
            return true;
        }
        return false;
    }
}
