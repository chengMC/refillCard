package com.mc.refillCard.config.supplier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: MC
 * @Date2020-08-29
 */

@Component
@ConfigurationProperties(prefix = "xingzou")
public class XingZouApiProperties {

    /**
     * 行走PI 密钥
     */
    private static String appSecret;

    /**
     * 行走API 商户id
     */
    private static String merchantID;

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getMerchantID() {
        return merchantID;
    }

    public void setAppSecret(String appSecret) {
        XingZouApiProperties.appSecret = appSecret;
    }

    public void setMerchantID(String merchantID) {
        XingZouApiProperties.merchantID = merchantID;
    }

}
