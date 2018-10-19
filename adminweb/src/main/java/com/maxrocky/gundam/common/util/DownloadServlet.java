package com.maxrocky.gundam.common.util;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件下载工具类
 * <p>
 * Created by jiazefeng on 2016/6/6.
 *
 * @version v1.0
 */
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //private static final String SERVERURL = "http://img.uhuojia.com/";// 服务器路径

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        File file = null;
        String fileName = null;

        String filePath = req.getParameter("filepath");// 获取要下载的服务器地址
        if (filePath != null && !filePath.equals("")) {
            fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

            byte[] btImg = getImageFromNetByUrl(filePath);// 获取字节流

            String localPath = getServletContext().getRealPath("/") + "images/";// 本地服务器地址

            if (null != btImg && btImg.length > 0) {
                System.out.println("读取到：" + btImg.length + " 字节");

                file = writeImageToDisk(btImg, fileName, localPath);
            } else {
                System.out.println("没有从该连接获得内容");
            }
            // 开始从本地服务器上下载
            if (file.exists()) {
                resp.reset();
                // 设置相应类型application/octet-stream
                resp.setContentType("application/x-msdownload");
                // 设置头信息
                resp.setHeader("Content-Disposition", "attachment;filename=\""
                        + fileName + "\"");
                InputStream inputStream = new FileInputStream(file);
                ServletOutputStream ouputStream = resp.getOutputStream();
                byte b[] = new byte[1024];
                int n;
                while ((n = inputStream.read(b)) != -1) {
                    ouputStream.write(b, 0, n);
                }
                // 关闭流、释放资源
                ouputStream.flush();
                ouputStream.close();
                inputStream.close();
                // 删除临时保存到本地服务器上的文件
                file.delete();

            } else {
                req.setAttribute("errorResult", "文件不存在下载失败！");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * 将图片写入到本地服务器
     *
     * @param img      图片数据流
     * @param fileName 文件保存时的名称
     * @param localPath 本地服务器地址
     * @return
     */
    public static File writeImageToDisk(byte[] img, String fileName, String localPath) {
        File file = null;
        try {
            file = new File(localPath + fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

}
