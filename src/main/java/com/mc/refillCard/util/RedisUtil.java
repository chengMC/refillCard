package com.mc.refillCard.util;

import com.mc.refillCard.common.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    private static StringRedisTemplate StringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate StringRedisTemplate) {
        RedisUtil.StringRedisTemplate = StringRedisTemplate;
    }

    //工具类是静态方法，用这样的方法将redisTemplate注入
    //@PostConstruct 这个注解也可以，具体使用方法自行网上查询
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return boolean
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            String redisKey = UserConstants.PREFIX_USER_TOKEN + key;
            return redisTemplate.opsForValue().getOperations().getExpire(redisKey) > 0 ;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public static String getStr(String key) {
        String redisKey = UserConstants.PREFIX_USER_TOKEN + key;
        return key == null ? null :StringRedisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 代替shiro的redis 缓存
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean stringSet(String key, Object value) {
        try {
            StringRedisTemplate.opsForValue().set(UserConstants.PREFIX_USER_TOKEN + key, String.valueOf(value), UserConstants.USER_TOKEN_OUT, TimeUnit.DAYS);
//            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
