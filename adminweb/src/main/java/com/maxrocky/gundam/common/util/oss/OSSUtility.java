package com.maxrocky.gundam.common.util.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.*;
import com.maxrocky.gundam.common.util.AppConfig;
import com.maxrocky.gundam.common.util.DateUtils;
import com.maxrocky.gundam.common.util.IdGen;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by HaiXiang Yu on 2015/9/20.
 * OSS上传图片
 */
public class OSSUtility {

    /**
     * 创建一个容器
     */
    public static void createBucket(){

        try {

            OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);

            //检查容器是否存在
            Boolean exist = ossClient.doesBucketExist(AppConfig.BucketName);
            //如果不存在则创建
            if(!exist){
                ossClient.createBucket(AppConfig.BucketName);
            }

            setBucketPublicACl(CannedAccessControlList.PublicRead);
        }
        catch (ServiceException e){
            e.getErrorCode();
            e.getErrorMessage();
            e.printStackTrace();
        }

    }

    /**
     * 删除Bucket以及下面所有Object
     */
    public void deleteBucket(){

        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = ossClient.listObjects(AppConfig.BucketName);
        //删除下面所有文件
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            ossClient.deleteObject(AppConfig.BucketName, objectSummary.getKey());
        }
        //删除Bucket
        ossClient.deleteBucket(AppConfig.BucketName);
    }

    /**
     * 设置Bucket权限
     * @param cannedAccessControlList
     *
     * public-read-write: 任何人（包括匿名访问）都可以对该bucket中的object进行上传、下载和删除操作；所有这些操作产生的费用由该bucket的创建者承担，请慎用该权限。
     * public-read: 只有该bucket的创建者可以对该bucket内的Object进行写操作（包括上传和删除）；任何人（包括匿名访问）可以对该bucket中的object进行读操作。
     * private: 只有该bucket的创建者才可以访问此Bukcet。其他人禁止对此Bucket做任何操作。
     */
    public static void setBucketPublicACl(CannedAccessControlList cannedAccessControlList){
        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);

        //设置权限
        ossClient.setBucketAcl(AppConfig.BucketName, cannedAccessControlList);
    }

    /**
     * 上传文件
     * @param key 上传到OSS起的名
     * @param filePath 本地文件路径+名称
     */
    public static void uploadFile(String key, String filePath) throws FileNotFoundException {

        System.out.println("--------------------start uploadFile---------------------");
        System.out.println("--------------------key is (" + key + ")---------------------");
        System.out.println("--------------------filePath is (" + filePath + ")---------------------");
        System.out.println("--------------------AppConfig.OSSEndpoint is (" + AppConfig.OSSEndpoint + ")---------------------");
        System.out.println("--------------------AppConfig.AccessId is (" + AppConfig.AccessId + ")---------------------");
        System.out.println("--------------------AppConfig.AccessKey is (" + AppConfig.AccessKey + ")---------------------");

        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);

        createBucket();

        // 获取指定文件的输入流
        File file = new File(filePath);
        InputStream content = new FileInputStream(file);

        // 创建上传Object的Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();

        // 必须设置ContentLength
        objectMetadata.setContentLength(file.length());

        //设置文件类型，暂时没有发现必要
        objectMetadata.setContentType("image/jpeg");

        // 上传Object.
        PutObjectResult result = ossClient.putObject(AppConfig.BucketName, key, content, objectMetadata);

        System.out.println("--------------------end uploadFile---------------------");

    }

    /**
     * 上传文件
     * @param key 上传到OSS起的名
     * @param file 文件
     */
    public static void uploadFile(String key, File file) throws FileNotFoundException {

        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);

        createBucket();

        // 获取指定文件的输入流
        InputStream content = new FileInputStream(file);

        // 创建上传Object的Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();

        // 必须设置ContentLength
        objectMetadata.setContentLength(file.length());

        //设置文件类型，暂时没有发现必要
        objectMetadata.setContentType("image/jpeg");

        // 上传Object.
        PutObjectResult result = ossClient.putObject(AppConfig.BucketName, key, content, objectMetadata);

        System.out.println("--------------------end uploadFile---------------------");

    }

    /**
     * 上传文件
     * @param key 文件名称
     * @param inputStream 文件流
     */
    public static void uploadFile(String key,InputStream inputStream) throws IOException {

        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);

        createBucket();

        // 创建上传Object的Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();

        // 必须设置ContentLength
        objectMetadata.setContentLength(inputStream.available());

        //设置文件类型，暂时没有发现必要
        objectMetadata.setContentType("image/jpeg");

        // 上传Object.
        PutObjectResult result = ossClient.putObject(AppConfig.BucketName, key, inputStream, objectMetadata);
    }

    /**
     * 上传文件
     * @param ossFolderPath OSS文件夹路径
     * @param multipartFile 文件
     */
    public static String uploadFile(String ossFolderPath,MultipartFile multipartFile) throws IOException {

        if(multipartFile == null){
            throw new IOException("上传文件为空。");
        }
        if(multipartFile.getSize() == 0){
            throw new IOException("上传文件为空。");
        }

        String ossFilePath = ossFolderPath + IdGen.getDateTimeId(DateUtils.FORMAT_LONG_Number);
        uploadFile(ossFilePath, multipartFile.getInputStream());
        return ossFilePath;
    }

    /**
     * 删除文件
     * @param key
     */
    public static void delete(String key){
        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);
        ossClient.deleteObject(AppConfig.BucketName, key);
    }

    /**
     * 判断文件存在
     * @param key
     */
    public static Boolean doesObjectExist(String key){
        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);
        GetObjectRequest getObjectRequest = new GetObjectRequest(AppConfig.BucketName, key);
        getObjectRequest.setRange(0, 100);

        try {
            OSSObject object = ossClient.getObject(getObjectRequest);
        }
        catch (OSSException ossException){
            ossException.printStackTrace();
            return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     *  下载文件
     * @param key  上传到OSS起的名
     * @param filePath 文件下载到本地保存的路径
     */
    private static void downloadFile(String key, String filePath){
        OSSClient ossClient = new OSSClient(AppConfig.OSSEndpoint, AppConfig.AccessId, AppConfig.AccessKey);
        //下载文件
        ossClient.getObject(new GetObjectRequest(AppConfig.BucketName,key),new File(filePath));
    }
}
