package com.maxrocky.gundam.common.util.oss;


import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HaiXiang Yu on 2015/9/21.
 */
public class ImageUtility {

    /**
     * 保存图片到本地
     * @throws IOException
     */
    public static void saveImageToDisk(MultipartFile multipartFile, String fileName) throws IOException {

        InputStream inputStream = multipartFile.getInputStream();
        byte[] data = new byte[1024];
        int len = 0;

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        while ((len = inputStream.read(data)) != -1) {
            fileOutputStream.write(data, 0, len);
        }

        if(inputStream != null){
            inputStream.close();
        }
        if(fileOutputStream != null){
            fileOutputStream.close();
        }

    }
}
