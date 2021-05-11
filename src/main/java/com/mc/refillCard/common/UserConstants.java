package com.mc.refillCard.common;

/****
 * @Author: MC
 * @Date: 2020/5/18
 * @Description: 用户静态变量
 */
public class UserConstants {

    public static final String CUSTOM_ROLE_SHIRO_CACHE = "customrole:userId:";

    /**
     *  登录后存入redis中的token的key
     */
    public static final String PREFIX_USER_TOKEN  = "login:prefix_user_token";

    /**
     *  登录后存入redis中的token的key
     */
    public static final String APP  = "-app";

    /**
     *  登录后存入redis中的用户信息
     */
    public static final String PREFIX_USER_INFO  = "login:prefix_user_info";
    /**
     *
     * web端发短信后存入redis中的token的key
     */
    public static final String PREFIX_WEB_MESSAGES = "prefix_web_messages";

    /**
     *
     * web端发短信后存入redis中的token过期天数
     */
    public static final Integer USER_TOKEN_OUT = 1;

    /**
     *
     * APP端发短信后存入redis中的token过期天数
     */
    public static final Integer USER_TOKEN_OUT_APP = 30;

    /**
     *  短信发送间隔单位分钟
     */
    public static final Integer SEND_MESSAGES = 1;

    /**
     *  短信验证码过期时间 单位分钟
     */
    public static final Integer MESSAGES_VERIFICATION_CODE = 30;

    //当前时间与过期时间差小于这个时间，token刷新
    public static final long USED_TIME = 1 * 1000 * 60;

    /**
     * 阿奇索秘钥
     */
    public static final String appSecret = "ak6mr75ettb7yktzv7spspwu322f374y";

}
