package com.maxrocky.gundam.controller;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.domain.picture.dto.PictureInfoDto;
import com.maxrocky.gundam.service.picture.inf.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 图片类
 * Created by jiazefeng on 2016/5/27.
 */
@RestController
@RequestMapping(value = "/picture")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    /**
     * 初始化检索图片信息
     *
     * @return
     */
    @RequestMapping(value = "/pictureList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getVideoList() {
        return pictureService.getPictureList();
    }

    /**
     * 按条件检索图片信息
     *
     * @param pictureInfoDto
     * @return
     */
    @RequestMapping(value = "/pictureListByItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult getVideoList(@RequestBody PictureInfoDto pictureInfoDto) {

        return pictureService.getPictureList(pictureInfoDto);
    }
    /*
  * 删除图片信息
  * @Param pictureId
  * */
    @RequestMapping(value = "/deletePicture/{pictureId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult deleteVillage(@PathVariable("pictureId") String pictureId) {
        if (pictureService.deletePicture(pictureId)) {
            return new SuccessApiResult(pictureId);
        } else {

            return new ErrorApiResult("0x00000001");
        }
    }
}
