package com.mc.refillCard.config.supplier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: MC
 * @Date2020-08-29
 */

@Component
@ConfigurationProperties(prefix = "jinglan")
public class JinglanApiProperties {

    /**
     * 净蓝API 密钥
     */
    private static String appSecret;

    /**
     * 净蓝API 商户号
     */
    private static String merAccount;

    public static String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        JinglanApiProperties.appSecret = appSecret;
    }

    public static String getMerAccount() {
        return merAccount;
    }

    public  void setMerAccount(String merAccount) {
        JinglanApiProperties.merAccount = merAccount;
    }
}
