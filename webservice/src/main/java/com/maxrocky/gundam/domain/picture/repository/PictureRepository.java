package com.maxrocky.gundam.domain.picture.repository;


import com.maxrocky.gundam.domain.picture.model.PictureInfo;

import java.util.List;

/**
 * Created by jiazefeng on 2016/5/27.
 */
public interface PictureRepository {
    /**
     * 检索图片信息
     *
     * @return
     */
    public List<PictureInfo> getPictureList();

    /**
     * 检索总条数
     *
     * @return
     */
    public int getCount();

    /**
     * 按条件检索图片信息
     *
     * @param pictureInfo
     * @return
     */
    public List<PictureInfo> getPictureList(PictureInfo pictureInfo, int startRow);

    /**
     * 按条件检索总条数
     *
     * @param pictureInfo
     * @return
     */
    public int getCount(PictureInfo pictureInfo);
    /**
     * 按ID 检索图片信息
     * @param id
     * @return
     */
    public PictureInfo findPictureInfoByID(String id);
    /**
     * 删除图片
     *
     * @param pictureId
     * @return
     */
    public boolean deletePicture(String pictureId);
}
