package com.mc.refillCard.config.fulu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: MC
 * @Date2020-08-29
 */

@Component
@ConfigurationProperties(prefix = "shushan")
public class ShuShanApiProperties {

    /**
     * 蜀山API 密钥
     */
    private static String appSecret;

    /**
     * 蜀山API 商户id
     */
    private static String merchantID;

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getMerchantID() {
        return merchantID;
    }

    public void setAppSecret(String appSecret) {
        ShuShanApiProperties.appSecret = appSecret;
    }

    public void setMerchantID(String merchantID) {
        ShuShanApiProperties.merchantID = merchantID;
    }

}
