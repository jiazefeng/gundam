package com.maxrocky.gundam.service.video.inf;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.video.dto.VideoInfoDTo;
import com.maxrocky.gundam.domain.video.model.VideoInfo;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public interface VideoService {
    /**
     * 检索全部视频信息
     *
     * @return
     */
    public ApiResult getVideoList();

    /**
     * 按检索条件进行检索视频信息
     *
     * @param videoInfoDTo
     * @return
     */
    public ApiResult getVideoList(VideoInfoDTo videoInfoDTo);

    /**
     * 按 视频ID 检索视频信息
     * @param videoId
     * @return
     */
    public VideoInfo findVideoById(String videoId);
    /**
     * 删除视频信息
     *
     * @param id
     * @return
     */
    public boolean deleteVideo(String id);

}
