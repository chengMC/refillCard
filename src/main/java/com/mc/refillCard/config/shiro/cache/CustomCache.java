package com.mc.refillCard.config.shiro.cache;


import com.mc.refillCard.common.UserConstants;
import com.mc.refillCard.util.JwtUtil;
import com.mc.refillCard.util.RedisUtil;
import com.mc.refillCard.vo.UserVo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.Set;

/**
 * 重写Shiro的Cache保存读取
 * @author dolyw.com
 * @date 2018/9/4 17:31
 */
public class CustomCache<K,V> implements Cache<K,V> {

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        String userId;
        if (key instanceof PrincipalCollection) {
            UserVo user = (UserVo) ((PrincipalCollection)key).getPrimaryPrincipal();
            userId= JwtUtil.getUserId(user.getToken());
        } else {
            userId = key.toString();
        }
        return UserConstants.CUSTOM_ROLE_SHIRO_CACHE + userId;
    }

    /**
     * 获取缓存
     */
    @Override
    public V get(K key) throws CacheException {
        if (!RedisUtil.hasKey(this.getKey(key))) {
            return null;
        }

        Object o = RedisUtil.get(this.getKey(key));
        V v = (V)o;

        return (V)o;
    }

    /**
     * 保存缓存
     */
    @Override
    public V put(K key, V value) throws CacheException {
        RedisUtil.stringSet(this.getKey(key), value);
        return value;
    }

    /**
     * 移除缓存
     */
    @Override
    public V remove(K key) throws CacheException {
        if (!RedisUtil.hasKey(this.getKey(key))) {
            return null;
        }
        RedisUtil.del(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {

    }

    /**
     * 缓存的个数
     */
    @Override
    public Set<K> keys() {
        return null;
    }

    /**
     * 获取所有的key
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection<V> values() {
        return null;
    }

}
