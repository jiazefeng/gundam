package com.maxrocky.gundam.service.video.impl;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.domain.robot.dto.RobotInfoDTo;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.domain.video.dto.VideoInfoDTo;
import com.maxrocky.gundam.domain.video.model.VideoInfo;
import com.maxrocky.gundam.domain.video.repository.VideoRepository;
import com.maxrocky.gundam.service.video.inf.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiazefeng on 2016/5/26.
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoRepository videoRepositoryImpl;
    @Autowired
    private RobotRepository robotRepositoryImpl;

    @Override
    public ApiResult getVideoList() {

        List<VideoInfo> videoInfoList = videoRepositoryImpl.getVideoList();
        List<VideoInfoDTo> videoInfoDTos = new ArrayList<VideoInfoDTo>();
        for (VideoInfo videoInfo : videoInfoList) {
            VideoInfoDTo videoInfoDTo = new VideoInfoDTo();
            videoInfoDTo.setVideoId(videoInfo.getVideoId());
            videoInfoDTo.setVideoName(videoInfo.getVideoName());
            videoInfoDTo.setVideoUrl(videoInfo.getVideoUrl());
            videoInfoDTo.setUplodDate(videoInfo.getUplodDate());
            videoInfoDTo.setStatus(videoInfo.getStatus());
            videoInfoDTo.setRobotId(videoInfo.getRobotId());
            List<RobotInfo> robotInfoList = robotRepositoryImpl.getRobotList(videoInfo.getVideoId());
            if (robotInfoList.size() > 0) {
                String robotName = "";
                for (RobotInfo robotInfo : robotInfoList) {
                    robotName = robotInfo.getRobotName();
                }
                videoInfoDTo.setRobotName(robotName);
            }
            videoInfoDTos.add(videoInfoDTo);

        }

        List<RobotInfo> robotInfos = robotRepositoryImpl.getRobotList();
        List<RobotInfoDTo> robotDTos = new ArrayList<RobotInfoDTo>();
        for (RobotInfo robotInfo : robotInfos) {
            RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());

            robotDTos.add(robotInfoDTo);
        }
        int total = videoRepositoryImpl.getCount();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("videoList", videoInfoDTos);
        modelMap.addAttribute("robotList", robotDTos);
        modelMap.addAttribute("count", total);
        return new SuccessApiResult(modelMap);
    }

    @Override
    public ApiResult getVideoList(VideoInfoDTo videoInfoDTo) {

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setRobotId(videoInfoDTo.getRobotId());
        videoInfo.setUplodDate(videoInfoDTo.getUplodDate());
        videoInfo.setVideoId(videoInfoDTo.getVideoId());

        int total = videoRepositoryImpl.getCount(videoInfo);
        //videoInfoDTo.setTotal(total);

        List<VideoInfo> videoInfoList = videoRepositoryImpl.getVideoList(videoInfo, videoInfoDTo.getIndex());

        List<VideoInfoDTo> videoInfoDTos = new ArrayList<VideoInfoDTo>();
        for (VideoInfo videoInfo1 : videoInfoList) {

            VideoInfoDTo videoInfoDTo1 = new VideoInfoDTo();

            videoInfoDTo1.setVideoId(videoInfo1.getVideoId());
            videoInfoDTo1.setVideoName(videoInfo1.getVideoName());
            videoInfoDTo1.setVideoUrl(videoInfo1.getVideoUrl());
            videoInfoDTo1.setUplodDate(videoInfo1.getUplodDate());
            videoInfoDTo1.setStatus(videoInfo1.getStatus());
            videoInfoDTo1.setRobotId(videoInfo1.getRobotId());

            List<RobotInfo> robotInfoList = robotRepositoryImpl.getRobotList(videoInfo1.getVideoId());

            if (robotInfoList.size() > 0) {
                String robotName = "";
                for (RobotInfo robotInfo : robotInfoList) {
                    robotName = robotInfo.getRobotName();
                }
                videoInfoDTo1.setRobotName(robotName);
            }

            videoInfoDTos.add(videoInfoDTo1);
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
        modelMap.addAttribute("videoList", videoInfoDTos);
        modelMap.addAttribute("robotList", robotDTos);
        modelMap.addAttribute("count", total);
        modelMap.addAttribute("page", videoInfoDTo.getIndex());

        return new SuccessApiResult(modelMap);
    }

    @Override
    public VideoInfo findVideoById(String videoId) {

        return videoRepositoryImpl.findVideoById(videoId);
    }

    @Override
    public boolean deleteVideo(String id) {
       return videoRepositoryImpl.deleteVideo(id);
    }
}
