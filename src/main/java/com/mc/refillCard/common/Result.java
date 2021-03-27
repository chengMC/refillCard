package com.mc.refillCard.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口返回类
 *
 * @author: macheng
 * @create: 2020-08-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    private Result(Integer code, String message) {
        this(code, message, null);
    }
    public static <T> Result<T> success() {
        return Result.<T>builder()
                .code(200)
                .message("success")
                .data(null).build();
    }

    public static <T> Result<T> success(String message) {
        return Result.<T>builder()
                .code(200)
                .message(message)
                .data(null).build();
    }

    public static <T> Result<T> success( T data) {
        return Result.<T>builder()
                .code(200)
                .message("success")
                .data(data).build();
    }
       public static <T> Result<T> success(String message, T data) {
            return Result.<T>builder()
                    .code(200)
                    .message(message)
                    .data(data).build();
        }

    public static <T> Result<T> fall() {
        return Result.<T>builder()
                .code(500)
                .message("fall")
                .data(null).build();
    }

    public static <T> Result<T> fall(String message) {
        return Result.<T>builder()
                .code(500)
                .message(message)
                .data(null).build();
    }

    public static <T> Result<T> fall(String message, T data) {
        return Result.<T>builder()
                .code(500)
                .message(message)
                .data(data).build();
    }
}