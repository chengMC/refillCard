package com.mc.refillCard.config.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * jwt拦截器
 *
 * @Author: MC
 * @Date2020-08-28
 */

public class JwtInterceptor extends HandlerInterceptorAdapter {

//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object){
//
//        // 从 http 请求头中取出 token
//        String token = httpServletRequest.getHeader("token");
//        // 执行认证
//        if (StringUtils.isEmpty(token)) {
//            throw new RuntimeException("身份验证失败，请重新登录");
//        }
//        String redisKey = UserConstants.PREFIX_USER_TOKEN + token;
//        // 验证 token 是否过期
//        if (redisTemplate.opsForValue().getOperations().getExpire(redisKey) < 1) {
//            throw new RuntimeException("身份验证过期，请重新登录");
//        }
//        // 验证 token
//        String reqToken = redisTemplate.opsForValue().get(redisKey);
//        if (StringUtils.isEmpty(reqToken)) {
//            throw new RuntimeException("身份验证失败，请重新登录");
//        }
//        //token过期时间设置
//        redisTemplate.opsForValue().set(redisKey, token, UserConstants.USER_TOKEN_OUT, TimeUnit.DAYS);
//        return true;
//    }

}
