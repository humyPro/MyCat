package com.humy.mycat.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Milo Hu
 * @Date: 11/1/2019 13:21
 * @Description:
 */
@Component
@Lazy
@Slf4j
public class RedisUtil<T> {

    private RedisTemplate<String, T> redisTemplate;

    private StringRedisTemplate stringRedisTemplate;

    public RedisUtil(@Qualifier("redisTemplate") RedisTemplate<String, T> redisTemplate, StringRedisTemplate StringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = StringRedisTemplate;
    }

    public static final String CAT_Z_SET_KEY = "cat_z_set";

    public static final String CAT_PREFIX = "cat_id_";

    public void setValue(String key, T value) {
        try {
            ValueOperations<String, T> redis = redisTemplate.opsForValue();
            redis.set(key, value);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void setValue(String key, T value, long expire) {
        try {
            ValueOperations<String, T> redis = redisTemplate.opsForValue();
            redis.set(key, value, expire, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void setEmptyValue(String key) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            redis.set(key, "", 10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public T getValue(String key) {
        try {
            ValueOperations<String, T> redis = redisTemplate.opsForValue();
            return redis.get(key);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public boolean remove(String key) {
        try {
            Boolean hasKey = redisTemplate.hasKey(key);
            if (Boolean.TRUE.equals(hasKey))
                return Boolean.TRUE.equals(redisTemplate.delete(key));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean zSetAdd(String key, T value, double score) {
        ZSetOperations<String, T> redis = redisTemplate.opsForZSet();
        return Boolean.TRUE.equals(redis.add(key, value, score));
    }
}
