package com.mc.refillCard.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class AccountUtils {

    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");

    public static String createAccountSecret(String username,String source){
        //指定加密使用的盐值
        Object salt = username;
        //加密的次数,可以进行多次的加密操作
        int hashIterations = 1024;
        //通过SimpleHash 来进行加密操作
        SimpleHash hash = new Md5Hash(source, salt, hashIterations);
        return hash.toString();
    }

    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = CHINA_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 阿奇索签名验证
     *
     * @param params
     * @param appSecret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getAqusuoSign(Map<String, String> params, String appSecret)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(appSecret);
        for (String key : keys) {
            String value = params.get(key);
            query.append(key).append(value);
        }
        query.append(appSecret);

        byte[] md5byte = encryptMD5(query.toString());

        return byte2hex(md5byte);
    }

    /**
     * 蜀山签名
     *
     * @param params
     * @param appSecret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getShuShanSign(Map<String, Object> params,String appSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            String value = String.valueOf(params.get(key));
            query.append(value);
        }
        query.append(appSecret);

        byte[] md5byte = encryptMD5(query.toString());

        return  byte2hex(md5byte);
    }


    //参数签名
    public static String Sign(Map<String, String> params,String appSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(appSecret);
        for (String key : keys) {
            String value = params.get(key);
            query.append(key).append(value);
        }
        query.append(appSecret);

        byte[] md5byte = encryptMD5(query.toString());

        return  byte2hex(md5byte);
    }

    // byte数组转成16进制字符串
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toLowerCase());
        }
        return sign.toString();
    }

    // Md5摘要
    public static byte[] encryptMD5(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return md5.digest(data.getBytes("UTF-8"));
    }


    public static String findNumber(String s){
        String result = "";
        for(int i = 0; i < s.length(); i++){
            int code = s.codePointAt(i);
            if(code >= 48 && code <= 57){
                result += (char)code;
            }
        }
        return result;
    }

}
