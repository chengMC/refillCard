package com.mc.refillCard.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: MC
 * @Date2020-12-21
 */

public class FileUtils {

    /**
     * 请求网络文件转化成文件
     *
     * @param file  文件
     * @param urlStr 网络请求地址
     * @throws IOException
     */
    public static void requestFileStreamChangeFile(File file, String urlStr) throws IOException {
        URL url = new URL(urlStr);
        //请求文件地址
        URLConnection uc = url.openConnection();
        //获取文件流
        InputStream inputStream = uc.getInputStream();
        //文件流住转化文件对象
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        inputStream.close();
    }

}
