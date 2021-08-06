package com.anzhen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Classname : MyServiceConfig
 * @Date : 2021.5.3 9:23
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@Component
public class MyServiceConfig {
    @Value("${server.port}")
    private int serverPort;

    public String getUrl() {
        InetAddress address = null;
        try {
            // 获取本机地址
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 返回  主机和端口
        return "http://"+address.getHostAddress() +":"+this.serverPort;
    }
}
