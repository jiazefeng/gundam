package com.maxrocky.gundam.service.picture.inf;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.picture.dto.PictureInfoDto;

/**
 * Created by jiazefeng on 2016/5/27.
 */
public interface PictureService {
    /**
     * 检索图片信息
     *
     * @return
     */
    public ApiResult getPictureList();

    /**
     * 按条件检索图片信息
     *
     * @param pictureInfoDto
     * @return
     */
    public ApiResult getPictureList(PictureInfoDto pictureInfoDto);
    /**
     * 删除图片
     *
     * @param pictureId
     * @return
     */
    public boolean deletePicture(String pictureId);
}
