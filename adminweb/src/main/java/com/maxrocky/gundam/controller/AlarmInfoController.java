package com.maxrocky.gundam.controller;

/**
 * Created by jiazefeng on 2016/5/26.
 */

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.alarm.dto.AlarmInfoDto;
import com.maxrocky.gundam.service.alarm.inf.AlarmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;


@RestController
@RequestMapping(value = "/alarmInfo")
public class AlarmInfoController {
    @Autowired
    private AlarmInfoService alarmInfoServiceImpl;

    /**
     * 检索报警信息
     *
     * @return
     */
    @RequestMapping(value = "/alarmInfoList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getAlarmInfoList() {

        return alarmInfoServiceImpl.getAlarmInfoList();
    }

    /*
   * 按条件检索报警信息
   * @Param alarmInfoDto
   * @Author jzf
   * */
    @RequestMapping(value = "/alarmInfoByItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult getVillageByItem(@RequestBody AlarmInfoDto alarmInfoDto) throws Exception {

        return alarmInfoServiceImpl.getAlarmInfoList(alarmInfoDto);
    }

    /**
     * 导出EXCEL
     * <p>
     * //     * @param alarmInfoDto
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/exportExcels/{robotId}/{alarmDate}", method = RequestMethod.GET)
    public void exportExcels(@PathVariable("robotId") String robotId, @PathVariable("alarmDate") String alarmDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = "AlarmExcel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
        response.reset();
        response.setContentType("application/x-xls");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

        String title = "报警信息EXCEL文档";
        String[] headers = {"报警时间", "报警位置", "机器人", "事件", "状态", "级别"};

        AlarmInfoDto alarmInfoDto = new AlarmInfoDto();
        if (!robotId.equals("null")) {
            alarmInfoDto.setRobotId(robotId);
        }
        if (!alarmDate.equals("null")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            alarmInfoDto.setAlarmDate(sdf.parse(alarmDate));
        }


        ServletOutputStream out = response.getOutputStream();
        alarmInfoServiceImpl.exportExcel(title, headers, alarmInfoDto, out);
        //Object obj = null;
        // return new SuccessApiResult(obj);
    }
}
