package com.mc.refillCard.common;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

/****
 * @Author: MC
 * @Date: 2020/5/18
 * @Description: controller层使用的公共静态变量
 */
@Component
public class CommonConst {
    /** 配置文件 */
    private static Properties props = new Properties();

    static {
        try {
            //加载配置文件
            InputStream is = CommonConst.class.getClassLoader().getResourceAsStream("application.yml");
            //创建Properties对象
            props.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//时间类型常量
    /** 年月日 */
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    /** 年月日 时分 */
    public static final SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /** 年月日 时分秒 */
    public static final SimpleDateFormat SDF2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /** 时分 */
    public static final SimpleDateFormat SDF3 = new SimpleDateFormat("HH:mm");

//session存储使用的常量
    /** 存储登录用户的key */
    public static final String SESSION_USER = "loginUser";
    public static final String SHIRO_SESSION_USER = "shiroLoginUser";

    public static final Integer FREE_SESSION_LIFE = 3600*24*7;
    public static final Integer TEST_FREE_SESSION_LIFE = 60000;
    public static final Long ONLINE_FREE_SESSION_LIFE = 30*60*1000L;

//魔法值类型常量
    /** 比较的基数 */
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;

    public static final Boolean TRUE_FLAG = true;
    public static final Boolean FALSE_FLAG = true;

    /** 初始用户的用户类型 final */
    public static final Integer REGISTER_TYPE = 4;
    public static final Integer BACK_REGISTER_TYPE = 0;
    public static final String INITIAL_PWD = "123456";


//路径常量
    /** 登录 */
    public static final String ON_LOGIN = "/login";
    /** 首页 */
    public static final String ON_Index = "/";
    /** 退出 */
    public static final String LOGIN_OUT = "/logout";

//正则匹配常量
    /** 拦截器不处理的url匹配表达式 */
    public static final String MIS_MATCH_INTERCEPTOR = ".*/*((.css)|(.js)|(.min)|(images)|(login)|(anon)).*";


}
