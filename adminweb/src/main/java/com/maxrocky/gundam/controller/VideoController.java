package com.maxrocky.gundam.controller;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.domain.video.dto.VideoInfoDTo;
import com.maxrocky.gundam.service.video.inf.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 视频类
 * Created by jiazefeng on 2016/5/26.
 */
@RestController
@RequestMapping(value = "/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 初始化检索视频信息
     *
     * @return
     */
    @RequestMapping(value = "/videoList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getVideoList() {
        return videoService.getVideoList();
    }

    /**
     * 按条件检索视频信息
     *
     * @param videoInfoDTo
     * @return
     */
    @RequestMapping(value = "/videoListByItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult getVideoList(@RequestBody VideoInfoDTo videoInfoDTo) {
        return videoService.getVideoList(videoInfoDTo);
    }


    /**
     * 按 视频ID 检索视频信息
     * @param videoId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findVideoByVideoId/{videoId}",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    public ApiResult searchVillageByVillageId(@PathVariable("videoId") String videoId) throws Exception{
        return  new SuccessApiResult(videoService.findVideoById(videoId));
    }
    /*
 * 删除视频信息
 * @Param videoId
 * */
    @RequestMapping(value = "/deleteVideo/{videoId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult deleteVillage(@PathVariable("videoId") String videoId){
        if (videoService.deleteVideo(videoId)) {
            return new SuccessApiResult(videoId);
        } else {

            return new ErrorApiResult("0x00000001");
        }
    }

}
