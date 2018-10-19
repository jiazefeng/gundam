package com.maxrocky.gundam.coreservice.controller;

import com.maxrocky.gundam.coreservice.common.ErrorApiResult;
import com.maxrocky.gundam.coreservice.common.SuccessApiResult;
import com.maxrocky.gundam.coreservice.managment.RobotSnapshotManagement;
import com.maxrocky.gundam.coreservice.model.biz.RobetUpStreamInfo;
import com.maxrocky.gundam.coreservice.model.biz.RobotDownStreamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by yuer5 on 16/6/25.
 */
@RestController
@RequestMapping(value="/device")
public class DeviceController {

    @Autowired
    private RobotSnapshotManagement manager;

    @RequestMapping(value="/sync", method= RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public RobotDownStreamInfo Sync(@RequestBody RobetUpStreamInfo reportInfo) {

        String robotid = reportInfo.getId().getRobetID();
//        robotid = "Robot-001";
        reportInfo.setReportTime(new Date());
        manager.UpdateReport(reportInfo);

        RobotDownStreamInfo downStream = manager.GetDownStream(robotid);
        return downStream;
    }

}
