package com.mc.refillCard.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author hu
 * @date 2020/11/17 上午9:09
 */

@Component
public class JwtUtil {

    /**
     * jwt 密钥
     */
    private static String secret;
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret =  secret;
    }

    /** 七天 */
    public static final int ONE_DAY = 1000 * 60 * 60 * 24 * 7 ;

    /**
     * 生成签名，五分钟后过期
     * @param userId
     * @return
     */
    public static String sign(String userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + ONE_DAY);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    // 将 user id 保存到 token 里面
                    .withAudience(userId)
                    // 五分钟后token过期
                    .withExpiresAt(date)
                    // token 的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("身份验证已过期，请重新登录");
        }
    }

    /**
     * 根据token获取userId
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        } catch (JWTDecodeException e) {
            throw new RuntimeException("身份验证无效，请重新登录");
        }
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static boolean checkSign(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            jwt.getClaims();
            return true;
        } catch (JWTVerificationException exception) {
            throw new IncorrectCredentialsException("身份验证无效，请重新登录");
        }
    }

}
