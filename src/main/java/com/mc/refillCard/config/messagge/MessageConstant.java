package com.mc.refillCard.config.messagge;

/****
 * @Author: MC
 * @Date: 2020/3/31
 * @Description: 阿里短信验证部分配置
 */
public class MessageConstant {

    public static final String SEND_Sms_RESPONSE_CODE = "OK";
    //阿里验证码获取成功
    public static final int ALI_MSG_CODE_OK = 101;
    //阿里验证码获取失败
    public static final int ALI_MSG_CODE_ERROR = 100;
    //阿里验证未发送
    public static final int ALI_NO_SEND_MSG = 1000;
    //阿里服务器异常
    public static final int ALI_SERVER_ERROR = 1001;
    //阿里连接客户端异常
    public static final int ALI_CLIENT_ERROR = 1002;

    // 设置超时时间-可自行调整
    public final static String defaultConnectTimeout  = "sun.net.client.defaultConnectTimeout";
    public final static String defaultReadTimeout = "sun.net.client.defaultReadTimeout";
    public final static String Timeout = "10000";
    // 初始化ascClient需要的几个参数
    public final static String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
    public final static String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）





}
