package com.maxrocky.gundam.domain.video.repositoryImpl;


import com.maxrocky.gundam.domain.video.model.VideoInfo;
import com.maxrocky.gundam.domain.video.repository.VideoRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiazefeng on 2016/5/26.
 */
@Repository
public class VideoRepositoryImpl extends BaseRepositoryImpl<VideoInfo> implements VideoRepository {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public VideoInfo findVideoById(String videoId) {
        List<Object> params = new ArrayList<Object>();
        String hql = null;
        if (videoId != null && !videoId.equals("")) {
            hql = " from VideoInfo where videoId = ?";
        }
        params.add(videoId);
        return this.findByQueryResult(hql, params);
    }

    @Override
    public List<VideoInfo> getVideoList() {
        List<Object> params = new ArrayList<Object>();
        String hql = "from VideoInfo where status = ? ORDER BY uplodDate DESC";
        params.add(1);
        return this.findByQueryList(hql,new PageInfoTools(), params);
    }

    @Override
    public int getCount() {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("status", 1));
        return this.findByCriteriaForPageCount(sql);

    }

    @Override
    public List<VideoInfo> getVideoList(VideoInfo videoInfo, int startRow) {
        List<Object> params = new ArrayList<Object>();
        String hql = "from VideoInfo as v where v.status = ?";
        params.add(1);

        if (videoInfo.getRobotId() != null && !videoInfo.getRobotId().equals("")) {
            hql += " and v.robotId = '" + videoInfo.getRobotId() + "'";
        }
        if (videoInfo.getUplodDate() != null && !videoInfo.getUplodDate().equals("")) {
            hql += " and v.uplodDate >= '" + sdf.format(videoInfo.getUplodDate()) + "'";
        }
        if (videoInfo.getVideoId() != null && !videoInfo.getVideoId().equals("")) {
            hql += " and v.videoId = '" + videoInfo.getVideoId() + "'";
        }
        hql += " ORDER BY uplodDate DESC";

        List<VideoInfo> videoInfoList = this.findByQueryList(hql, new PageInfoTools(startRow, 10), params);

        return videoInfoList;
    }

    @Override
    public int getCount(VideoInfo videoInfo) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("status", 1));
        if (videoInfo.getRobotId() != null && !videoInfo.getRobotId().equals("")) {
            sql.add(Restrictions.eq("robotId", videoInfo.getRobotId()));
        }
        if (videoInfo.getUplodDate() != null && !videoInfo.getUplodDate().equals("")) {
            sql.add(Restrictions.ge("uplodDate", videoInfo.getUplodDate()));
        }
        if (videoInfo.getVideoId() != null && !videoInfo.getVideoId().equals("")) {
            sql.add(Restrictions.eq("videoId", videoInfo.getVideoId()));
        }
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public boolean deleteVideo(String id) {
        VideoInfo videoInfo = new VideoInfo();
        if (id != null && !id.equals("")) {
            videoInfo = findVideoById(id);
        }
        if (videoInfo != null) {
            videoInfo.setStatus(0);
            this.update(videoInfo);
            return true;
        } else {
            return false;
        }
    }


}
