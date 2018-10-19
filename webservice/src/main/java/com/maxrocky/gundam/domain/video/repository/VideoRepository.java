package com.maxrocky.gundam.domain.video.repository;


import com.maxrocky.gundam.domain.video.model.VideoInfo;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;


/**
 * Created by jiazefeng on 2016/5/26.
 */
public interface VideoRepository extends BaseRepository<VideoInfo> {
    /**
     * 按 视频id 检索视频信息
     *
     * @param videoId
     * @return
     */
    public VideoInfo findVideoById(String videoId);

    /**
     * 检索所有视频信息
     *
     * @return
     */
    public List<VideoInfo> getVideoList();

    /**
     * 检索总条数
     *
     * @return
     */
    public int getCount();

    /**
     * 按条件检索视频信息
     *
     * @param videoInfo
     * @return
     */
    public List<VideoInfo> getVideoList(VideoInfo videoInfo, int startRow);

    /**
     * 按条件检索总条数
     *
     * @param videoInfo
     * @return
     */
    public int getCount(VideoInfo videoInfo);
    /**
     * 删除视频信息
     *
     * @param id
     * @return
     */

    public boolean deleteVideo(String id);

}
