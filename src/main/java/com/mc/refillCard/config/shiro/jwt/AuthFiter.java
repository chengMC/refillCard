package com.mc.refillCard.config.shiro.jwt;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mc.refillCard.common.UserConstants;
import com.mc.refillCard.util.JwtUtil;
import com.mc.refillCard.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthFiter extends AuthenticatingFilter {

    /**
     * 生成自定义token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return new AuthToken(token);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("Origin"));
            httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        httpRequest.getSession();
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        AuthToken jwtToken = (AuthToken)this.createToken(request,response);
        if(jwtToken != null){
            String token = jwtToken.getToken();

            try {
                // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                // 如果没有抛出异常则代表登入成功，返回true
                getSubject(request, response).login(jwtToken);
            }catch (AuthenticationException e){

//                获取异常并输出
                this.customResponse(e.getMessage(),response);
                return false;
            }
            //获取token中的userId
            String userId = JwtUtil.getUserId(token);
            //更新token时间

            log.info("更新token时间！！！！！");
            RedisUtil.stringSet(userId, token);
//            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//            httpServletResponse.setHeader("token", refreshToken);
//            httpServletResponse.setHeader("Access-Control-Expose-Headers", "token");

            return true;
        }else{
            this.customResponse("身份验证失败，请重新登录", response);
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    /**
     * 更新token
     */
    private String refreshToken(String token){

        String sign = null;
        DecodedJWT jwt = JWT.decode(token);
        //获取过期时间
        Date exDate = jwt.getExpiresAt();

        //比较过期时间
        boolean refesh = (exDate.getTime() - System.currentTimeMillis()) < UserConstants.USED_TIME;
        if(refesh){
            //获取token中的数据
            String userId = JwtUtil.getUserId(token);
            sign = JwtUtil.sign(userId);
        }

       return sign;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //处理登录失败的异常
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        this.customResponse(throwable.getMessage(), response);
        return false;
    }

    private void customResponse(String msg, ServletResponse response){
        Map<String,Object> map = new HashMap<>();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding("utf-8");
        map.put("code",403);
        map.put("message",msg);
        String resultJson= JSONUtil.toJsonStr(map);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(resultJson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
