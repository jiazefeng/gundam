package com.maxrocky.gundam.service.alarm.impl;

/**
 * Created by jiazefeng on 2016/5/26.
 */

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.ExportExcel;
import com.maxrocky.gundam.domain.alarm.dto.AlarmInfoDto;
import com.maxrocky.gundam.domain.alarm.dto.ExportExcelDto;
import com.maxrocky.gundam.domain.alarm.model.AlarmInfo;
import com.maxrocky.gundam.domain.alarm.repository.AlarmInfoRepository;
import com.maxrocky.gundam.domain.robot.dto.RobotInfoDTo;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.service.alarm.inf.AlarmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmInfoServiceImpl implements AlarmInfoService {
    @Autowired
    private AlarmInfoRepository alarmInfoRepositoryImpl;
    @Autowired
    private RobotRepository robotRepositoryImpl;

    @Override
    public ApiResult getAlarmInfoList() {
        int cont = alarmInfoRepositoryImpl.getAlarmInfoCount();
        List<AlarmInfo> alarmInfoList = alarmInfoRepositoryImpl.getAlarmInfoList();
        List<AlarmInfoDto> alarmInfoDTOs = new ArrayList<AlarmInfoDto>();
        for (AlarmInfo alarmInfo : alarmInfoList) {
            AlarmInfoDto alarmInfoDto = new AlarmInfoDto();
            alarmInfoDto.setAlarmId(alarmInfo.getAlarmId());
            alarmInfoDto.setAlarmDate(alarmInfo.getAlarmDate());
            alarmInfoDto.setAlarmPosition(alarmInfo.getAlarmPosition());
            alarmInfoDto.setEvent(alarmInfo.getEvent());
            alarmInfoDto.setLevel(alarmInfo.getLevel());
            alarmInfoDto.setStatus(alarmInfo.getStatus());

            List<RobotInfo> list = robotRepositoryImpl.getRobotList(alarmInfo.getAlarmId());
            if (list.size() > 0) {
                String robotName = "";
                for (RobotInfo robot : list) {
                    robotName = robot.getRobotName();
                }
                alarmInfoDto.setRobotName(robotName);
            }
            alarmInfoDTOs.add(alarmInfoDto);
        }
        List<RobotInfo> robotInfos = robotRepositoryImpl.getRobotList();
        List<RobotInfoDTo> robotDTos = new ArrayList<RobotInfoDTo>();
        for (RobotInfo robotInfo : robotInfos) {
            RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());

            robotDTos.add(robotInfoDTo);
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("count", cont);
        modelMap.addAttribute("alarmInfoList", alarmInfoDTOs);
        modelMap.addAttribute("robotList", robotDTos);
        return new SuccessApiResult(modelMap);
    }

    @Override
    public ApiResult getAlarmInfoList(AlarmInfoDto alarmInfoDto) {
        AlarmInfo alarmInfo = new AlarmInfo();
        alarmInfo.setRobotId(alarmInfoDto.getRobotId());
        alarmInfo.setAlarmDate(alarmInfoDto.getAlarmDate());

        int cont = alarmInfoRepositoryImpl.getCount(alarmInfo);

        //alarmInfoDto.setTotal(cont);

        List<AlarmInfo> alarmInfoList = alarmInfoRepositoryImpl.getAlarmInfoList(alarmInfo, alarmInfoDto.getIndex());

        List<AlarmInfoDto> alarmInfoDTOs = new ArrayList<AlarmInfoDto>();

        for (AlarmInfo alarmInfo1 : alarmInfoList) {

            AlarmInfoDto alarmInfoDto1 = new AlarmInfoDto();

            alarmInfoDto1.setAlarmId(alarmInfo1.getAlarmId());
            alarmInfoDto1.setAlarmDate(alarmInfo1.getAlarmDate());
            alarmInfoDto1.setAlarmPosition(alarmInfo1.getAlarmPosition());
            alarmInfoDto1.setEvent(alarmInfo1.getEvent());
            alarmInfoDto1.setLevel(alarmInfo1.getLevel());
            alarmInfoDto1.setStatus(alarmInfo1.getStatus());

            List<RobotInfo> list = robotRepositoryImpl.getRobotList(alarmInfo1.getAlarmId());
            if (list.size() > 0) {
                String robotName = "";
                for (RobotInfo robot : list) {
                    robotName = robot.getRobotName();
                }
                alarmInfoDto1.setRobotName(robotName);
            }
            alarmInfoDTOs.add(alarmInfoDto1);
        }

        List<RobotInfo> robotInfos = robotRepositoryImpl.getRobotList();
        List<RobotInfoDTo> robotDTos = new ArrayList<RobotInfoDTo>();
        for (RobotInfo robotInfo : robotInfos) {
            RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());

            robotDTos.add(robotInfoDTo);
        }

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("count", cont);
        modelMap.addAttribute("alarmInfoList", alarmInfoDTOs);
        modelMap.addAttribute("robotList", robotDTos);
        modelMap.addAttribute("page", alarmInfoDto.getIndex());
        return new SuccessApiResult(modelMap);
    }

    @Override
    public List<ExportExcelDto> exportAlarmInfo(AlarmInfoDto alarmInfoDto) {

        AlarmInfo alarmInfo = new AlarmInfo();
        alarmInfo.setRobotId(alarmInfoDto.getRobotId());
        alarmInfo.setAlarmDate(alarmInfoDto.getAlarmDate());

        List<AlarmInfo> alarmInfoList = alarmInfoRepositoryImpl.getAlarmInfoList(alarmInfo);

        List<ExportExcelDto> excelByAlarmInfoDtos = new ArrayList<ExportExcelDto>();

        for (AlarmInfo alarmInfo1 : alarmInfoList) {

            ExportExcelDto excelByAlarmInfoDto = new ExportExcelDto();
            excelByAlarmInfoDto.setAlarmDate(alarmInfo1.getAlarmDate());
            excelByAlarmInfoDto.setAlarmPosition(alarmInfo1.getAlarmPosition());
            excelByAlarmInfoDto.setEvent(alarmInfo1.getEvent());
            if (alarmInfo1.getStatus() == 1) {
                excelByAlarmInfoDto.setStatus("已处理");
            } else {
                excelByAlarmInfoDto.setStatus("报警，未处理！");
            }
            if (alarmInfo1.getLevel() == 1) {
                excelByAlarmInfoDto.setLevel("高");
            } else {
                excelByAlarmInfoDto.setLevel("一般");
            }

            List<RobotInfo> list = robotRepositoryImpl.getRobotList(alarmInfo1.getAlarmId());
            if (list.size() > 0) {
                String robotName = "";
                for (RobotInfo robot : list) {
                    robotName = robot.getRobotName();
                }
                excelByAlarmInfoDto.setRobotName(robotName);
            }
            excelByAlarmInfoDtos.add(excelByAlarmInfoDto);
        }

        return excelByAlarmInfoDtos;
    }


    @Override
    public void exportExcel(String title, String[] headers, AlarmInfoDto alarmInfoDto, OutputStream out) throws Exception {
        List<ExportExcelDto> excelByAlarmInfoDtos = this.exportAlarmInfo(alarmInfoDto);
        // 导出数据
        ExportExcel<ExportExcelDto> ex = new ExportExcel<ExportExcelDto>();

        List<ExportExcelDto> excelByAlarmInfoDtoList = new ArrayList<ExportExcelDto>();

        if (excelByAlarmInfoDtos != null && excelByAlarmInfoDtos.size() > 0) {
            for (ExportExcelDto excelByAlarmInfoDto : excelByAlarmInfoDtos) {

                ExportExcelDto exportExcelByAlarmInfoDto = new ExportExcelDto();

                exportExcelByAlarmInfoDto.setAlarmDate(excelByAlarmInfoDto.getAlarmDate());
                exportExcelByAlarmInfoDto.setAlarmPosition(excelByAlarmInfoDto.getAlarmPosition());
                exportExcelByAlarmInfoDto.setRobotName(excelByAlarmInfoDto.getRobotName());
                exportExcelByAlarmInfoDto.setEvent(excelByAlarmInfoDto.getEvent());
                exportExcelByAlarmInfoDto.setStatus(excelByAlarmInfoDto.getStatus());
                exportExcelByAlarmInfoDto.setLevel(excelByAlarmInfoDto.getLevel());
                excelByAlarmInfoDtoList.add(exportExcelByAlarmInfoDto);
            }
        }
        ex.exportExcel(title,headers, excelByAlarmInfoDtoList, out,"yyyy-MM-dd");
//            out = new FileOutputStream("d:\\" + System.getProperties()
//                    + "报警信息.xls");
        System.out.println("excel导出成功！");
    }

}
