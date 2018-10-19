package com.maxrocky.gundam.coreservice.controller;

import com.maxrocky.gundam.coreservice.common.ErrorApiResult;
import com.maxrocky.gundam.coreservice.common.SuccessApiResult;
import com.maxrocky.gundam.coreservice.core.RobotSnapshot;
import com.maxrocky.gundam.coreservice.managment.RobotSnapshotManagement;
import com.maxrocky.gundam.coreservice.model.biz.RobetUpStreamInfo;
import com.maxrocky.gundam.coreservice.model.biz.UpStreamGps;
import com.maxrocky.gundam.coreservice.model.biz.UpStreamId;
import com.maxrocky.gundam.coreservice.model.biz.UpStreamStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by yuer5 on 16/7/21.
 */
@RestController
@RequestMapping(value="/test")
public class TestController {

    @Autowired
    private RobotSnapshotManagement manager;

    // - for test
    @RequestMapping(value="/sync/{id}", method= RequestMethod.GET)
    @ResponseBody
    public RobetUpStreamInfo upStreamInfo(@PathVariable String id) {

        RobetUpStreamInfo ret = new RobetUpStreamInfo();
        UpStreamGps gps = new UpStreamGps();
        gps.setX(3);
        gps.setY(10);
        gps.setZ(0);
        gps.setStarNum(4);
        ret.setGps(gps);
        ret.setPose(1.041);
        UpStreamId idinfo = new UpStreamId();
        idinfo.setEndpointKeyHash("M349HBABEFJ");
        idinfo.setRobetID("Robot-001");

        ret.setId(idinfo);
        ret.setReportTime(new Date());
        UpStreamStatus status = new UpStreamStatus();
        status.setAlert(1);
        status.setPower(85);
        ret.setStatus(status);
        return ret;
    }


    @RequestMapping(value="/AddImmediatelyTaskForTest/{strategyId}/{robotId}", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap AddImmediatelyTaskForTest(@PathVariable String strategyId, @PathVariable String robotId) {
        try {
            manager.AddImmediatelyTaskForTest(strategyId, robotId);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }

    }

    // - for test
    @RequestMapping(value="/redis/clear", method= RequestMethod.GET)
    @ResponseBody
    public RobotSnapshot test(@PathVariable String id) {


        RobotSnapshot snapshot = manager.GetSnapshot(id);

        return snapshot;
    }

    @RequestMapping(value="/redis/{id}", method= RequestMethod.GET)
    @ResponseBody
    public RobotSnapshot redis(@PathVariable String id) {

//        RobotSnapshot snapshot = manager.GetSnapshot("Robot-001");
//        RobotSnapshot snapshot = manager.GetSnapshot("b05b4072e2be4eee875bbfa6eeb76fda");
        RobotSnapshot snapshot = manager.GetSnapshot(id);

        return snapshot;
    }

//    AddImmediatelyTaskForTest
}
