package com.sicheng.smart_tv.services;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by av on 2017/9/6.
 */

public class NetworkService {
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
}
