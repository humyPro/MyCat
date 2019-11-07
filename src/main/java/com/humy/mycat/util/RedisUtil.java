package com.humy.mycat.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
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
public class RedisUtil {

    private StringRedisTemplate stringRedisTemplate;

    public RedisUtil(StringRedisTemplate StringRedisTemplate) {
        this.stringRedisTemplate = StringRedisTemplate;
    }

    public static final String CAT_Z_SET_KEY = "cat_z_set";

    public static final String CAT_PREFIX = "cat_id_";

    public static final String USER_PREFIX = "user_id_";

    public static final String TOKEN_PREFIX = "token_id";

    public boolean setValue(String key, Object value) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            redis.set(key, JSON.toJSONString(value));
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean setValue(String key, Object value, long expire) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            redis.set(key, JSON.toJSONString(value), expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean setEmptyValue(String key) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            redis.set(key, "", 10, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public <T> T getValue(String key, Class<T> clazz) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            String str = redis.get(key);
            if (str != null) {
                return JSON.parseObject(str, clazz);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public boolean remove(String key) {
        try {
            Boolean hasKey = stringRedisTemplate.hasKey(key);
            if (Boolean.TRUE.equals(hasKey))
                return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public <T> boolean zSetAdd(String key, T value, double score) {
        try {
            ZSetOperations<String, String> redis = stringRedisTemplate.opsForZSet();
            return Boolean.TRUE.equals(redis.add(key, JSON.toJSONString(value), score));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean setToken(Long userId, String token) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            redis.set(TOKEN_PREFIX + userId, token);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean setToken(Long userId, String token, Long expire) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            redis.set(TOKEN_PREFIX + userId, token, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public String getToken(Long userId) {
        try {
            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
            return redis.get(TOKEN_PREFIX + userId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public boolean removeToken(Long userId) {
        boolean ok = this.remove(TOKEN_PREFIX + userId);
        return ok;
    }
}
