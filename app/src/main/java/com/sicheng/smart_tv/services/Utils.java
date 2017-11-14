package com.sicheng.smart_tv.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

/**
 * Created by av on 2017/9/6.
 */

public class Utils {
    public static boolean isReachable(int timeout) {
        boolean isReachable = false;
        try {
            if (InetAddress.getByName("www.baidu.com").isReachable(timeout)) {
                isReachable = true;
            } else {
                isReachable = false;
            }
        } catch (IOException e) {
            isReachable = false;
        } finally {
            return isReachable;
        }
    }

    public static String inputStreamToString(InputStream is) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                byteArrayOutputStream.write(buffer, 0, len); // 把读取的内容写入到输出流中
            }
            is.close();
            String result = byteArrayOutputStream.toString();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
