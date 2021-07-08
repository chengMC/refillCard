package com.mc.refillCard.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author: MC
 * @Date2020-08-11
 */

@Configuration
public class BeanConfig {

    /**
     * 雪花算法生成id
     *
     * @Param:
     * @Return: cn.hutool.core.lang.Snowflake
     * @Author: mc
     * @Date: 2020/8/11 15:01
     */
    @Bean
    public Snowflake snowflake() {
        return IdUtil.getSnowflake(1, 1);
    }



}