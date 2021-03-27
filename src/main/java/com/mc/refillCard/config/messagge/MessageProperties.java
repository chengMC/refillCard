package com.mc.refillCard.config.messagge;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: MC
 * @Date2020-08-29
 */

@Component
@ConfigurationProperties(prefix = "messages")
public class MessageProperties {

    /**
     * 阿里云 accessKeyId
     */
    private static String accessKeyId;

    /**
     * 阿里云 accessKeySecret
     */
    private static String accessKeySecret;

    /**
     * 短信签名
     */
    private static String signName;

    /**
     * 短信模板
     */
    private static String templateCode;

    /**
     * 短信签名
     */
    private static String signName2;

    /**
     * 短信模板
     */
    private static String templateCode2;


    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        MessageProperties.accessKeyId = accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        MessageProperties.accessKeySecret = accessKeySecret;
    }

    public static String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        MessageProperties.signName = signName;
    }

    public static String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        MessageProperties.templateCode = templateCode;
    }

    public static String getSignName2() {
        return signName2;
    }

    public void setSignName2(String signName2) {
        MessageProperties.signName2 = signName2;
    }

    public static String getTemplateCode2() {
        return templateCode2;
    }

    public void setTemplateCode2(String templateCode2) {
        MessageProperties.templateCode2 = templateCode2;
    }
}
