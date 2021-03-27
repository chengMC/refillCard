package com.mc.refillCard.config.advice;

import cn.hutool.json.JSONObject;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MC
 * @date 2019/4/28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object bindExceptionHandler(MethodArgumentNotValidException e) {
        JSONObject jsonObject = new JSONObject();
        String msg =  e.getBindingResult().getFieldError().getDefaultMessage();

        if (msg == null || msg.equals("")) {
            msg = "系统异常，请稍后再试";
        }
        logger.error("ExceptionTest Exception:",e);
        jsonObject.put("code", 500);
        jsonObject.put("message", msg);
        return jsonObject;
    }

    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e) {
        JSONObject jsonObject = new JSONObject();
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "系统异常，请稍后再试";
        }else{
            if(msg.indexOf("Subject does not have")>-1){
                msg = "您没有权限操作";
            }
        }

        logger.error("ExceptionTest Exception:",e);
        jsonObject.put("code", 500);
        jsonObject.put("message", msg);
        return jsonObject;
    }

    @ExceptionHandler(RuntimeException.class)
    public Object doHandleRuntimeException(RuntimeException  e) {
        JSONObject jsonObject = new JSONObject();
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "系统异常，请稍后再试";
        }
        logger.error("ExceptionTest Exception:",e);
        jsonObject.put("code", 500);
        jsonObject.put("message", msg);
        return jsonObject;
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        JSONObject jsonObject = new JSONObject();
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "系统异常，请稍后再试";
        }
        logger.error("ExceptionTest Exception:",e);
        jsonObject.put("code", 500);
        jsonObject.put("message", msg);
        return jsonObject;
    }

}
