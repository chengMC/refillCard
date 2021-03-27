package com.mc.refillCard.config.fulu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: MC
 * @Date2020-08-29
 */

@Component
@ConfigurationProperties(prefix = "fuli")
public class FuliProperties {

    /**
     * 福禄 accessKeyId
     */
    private static String appKey;

    /**
     * 福禄 accessKeySecret
     */
    private static String sysSecret;

    private static String url;

    public static String getAppKey() {
        return appKey;
    }

    public static String getSysSecret() {
        return sysSecret;
    }

    public static String getUrl() {
        return url;
    }

    public void setAppKey(String appKey) {
        FuliProperties.appKey = appKey;
    }

    public void setSysSecret(String sysSecret) {
        FuliProperties.sysSecret = sysSecret;
    }

    public void setUrl(String url) {
        FuliProperties.url = url;
    }
}
