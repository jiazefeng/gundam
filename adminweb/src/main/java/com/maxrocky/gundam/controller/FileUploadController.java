package com.maxrocky.gundam.controller;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.DateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhipeng on 2016/6/6.
 */
@RestController
@RequestMapping(value = "/file")
public class FileUploadController {

    @RequestMapping(value = "/upload/{imgType}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String upload(HttpServletRequest req,@PathVariable("imgType") String imgType){
        Map<String,Object> result = new HashMap<String, Object>();

        MultipartHttpServletRequest mReq  =  null;
        MultipartFile file = null;
        String fileName = "";
        String realPaht = req.getSession().getServletContext().getRealPath("/");
        String returnPath = "http://admapi.robot.maxrocky.com/static/"+imgType+"/";
//        String returnPath = "http://localhost:8080/static/"+imgType+"/";
            try {
            mReq = (MultipartHttpServletRequest)req;
            file = mReq.getFile("file");
            fileName = file.getOriginalFilename();
            if(!StringUtils.isEmpty(fileName)){
                String oosFilePath1 = "";
                //把读到的图片流保存成一个image
                BufferedImage image = ImageIO.read(file.getInputStream());
                //根据时间戳生成图片名称
                oosFilePath1 = DateUtils.getNow("yyyyMMddhhmmss");
                fileName = oosFilePath1;
                String path = realPaht+"/static/"+imgType+"/"+ oosFilePath1 + ".jpg";
                //将图片写进image
                ImageIO.write(image, "png", new File(path));
                return returnPath+oosFilePath1+".jpg";
            } else {
                throw new IOException("文件名为空!");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("state", "文件上传失败!");
            result.put("url","");
            result.put("title", "");
            result.put("original", "");
            System.out.println("文件 "+fileName+" 上传失败!");
        }
        return "";
    }
}
