package com.mc.refillCard.config.supplier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: MC
 * @Date2020-08-29
 */

@Component
@ConfigurationProperties(prefix = "phonebill")
public class PhoneBillApiProperties {

    /**
     * 话费API 密钥
     */
    private static String appSecret;

    /**
     * 话费API 商户号
     */
    private static String merchantID;

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getMerchantID() {
        return merchantID;
    }

    public void setAppSecret(String appSecret) {
        PhoneBillApiProperties.appSecret = appSecret;
    }

    public void setMerchantID(String merchantID) {
        PhoneBillApiProperties.merchantID = merchantID;
    }
}
