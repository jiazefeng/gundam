package com.maxrocky.gundam.service.picture.impl;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.domain.picture.dto.PictureInfoDto;
import com.maxrocky.gundam.domain.picture.model.PictureInfo;
import com.maxrocky.gundam.domain.picture.repository.PictureRepository;
import com.maxrocky.gundam.domain.robot.dto.RobotInfoDTo;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.service.picture.inf.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiazefeng on 2016/5/27.
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private RobotRepository robotRepository;

    @Override
    public ApiResult getPictureList() {

        int total = pictureRepository.getCount();

        List<PictureInfo> pictureInfoList = pictureRepository.getPictureList();
        List<PictureInfoDto> pictureInfoDtos = new ArrayList<PictureInfoDto>();

        for (PictureInfo pictureInfo : pictureInfoList) {
            PictureInfoDto pictureInfoDto = new PictureInfoDto();
            pictureInfoDto.setPictureId(pictureInfo.getPictureId());
            pictureInfoDto.setPictureName(pictureInfo.getPictureName());
            pictureInfoDto.setPictureUrl(pictureInfo.getPictureUrl());
            pictureInfoDto.setUplodDate(pictureInfo.getUplodDate());
            List<RobotInfo> robotInfoList = robotRepository.getRobotByPicId(pictureInfo.getPictureId());
            if (robotInfoList.size() > 0) {
                String robotName = "";
                for (RobotInfo robotInfo : robotInfoList) {
                    robotName = robotInfo.getRobotName();
                }
                pictureInfoDto.setRobotName(robotName);
            }
            pictureInfoDtos.add(pictureInfoDto);

        }

        List<RobotInfo> robotInfos = robotRepository.getRobotList();
        List<RobotInfoDTo> robotDTos = new ArrayList<RobotInfoDTo>();
        for (RobotInfo robotInfo : robotInfos) {
            RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());

            robotDTos.add(robotInfoDTo);
        }

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pictureList", pictureInfoDtos);
        modelMap.addAttribute("robotList", robotDTos);
        modelMap.addAttribute("count", total);
        return new SuccessApiResult(modelMap);
    }

    @Override
    public ApiResult getPictureList(PictureInfoDto pictureInfoDto) {

        PictureInfo pictureInfo = new PictureInfo();
        pictureInfo.setRobotId(pictureInfoDto.getRobotId());
        pictureInfo.setUplodDate(pictureInfoDto.getUplodDate());
        pictureInfo.setPictureId(pictureInfoDto.getPictureId());

        int total = pictureRepository.getCount(pictureInfo);
        //pictureInfoDto.setTotal(total);

        List<PictureInfo> pictureInfoList = pictureRepository.getPictureList(pictureInfo, pictureInfoDto.getIndex());
        List<PictureInfoDto> pictureInfoDtos = new ArrayList<PictureInfoDto>();

        for (PictureInfo pictureInfo1 : pictureInfoList) {
            PictureInfoDto pictureInfoDto1 = new PictureInfoDto();
            pictureInfoDto1.setPictureId(pictureInfo1.getPictureId());
            pictureInfoDto1.setPictureName(pictureInfo1.getPictureName());
            pictureInfoDto1.setPictureUrl(pictureInfo1.getPictureUrl());
            pictureInfoDto1.setUplodDate(pictureInfo1.getUplodDate());
            List<RobotInfo> robotInfoList = robotRepository.getRobotByPicId(pictureInfo1.getPictureId());
            if (robotInfoList.size() > 0) {
                String robotName = "";
                for (RobotInfo robotInfo : robotInfoList) {
                    robotName = robotInfo.getRobotName();
                }
                pictureInfoDto1.setRobotName(robotName);
            }
            pictureInfoDtos.add(pictureInfoDto1);

        }

        List<RobotInfo> robotInfos = robotRepository.getRobotList();
        List<RobotInfoDTo> robotDTos = new ArrayList<RobotInfoDTo>();
        for (RobotInfo robotInfo : robotInfos) {
            RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());

            robotDTos.add(robotInfoDTo);
        }


        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("pictureList", pictureInfoDtos);
        modelMap.addAttribute("robotList", robotDTos);
        modelMap.addAttribute("count", total);
        modelMap.addAttribute("page", pictureInfoDto.getIndex());
        return new SuccessApiResult(modelMap);
    }
    @Override
    public boolean deletePicture(String pictureId) {
        return pictureRepository.deletePicture(pictureId);
    }
}
