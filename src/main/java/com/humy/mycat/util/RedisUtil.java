package com.humy.mycat.util;

import com.alibaba.fastjson.JSON;
import com.humy.mycat.vo.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
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

    public ValueOperations<String, String> redis;

    private DefaultRedisScript<Long> getDelScript;

    public RedisUtil(StringRedisTemplate StringRedisTemplate) {
        this.stringRedisTemplate = StringRedisTemplate;
        redis = this.stringRedisTemplate.opsForValue();
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptText("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end");
        this.getDelScript = script;

    }

    public static final String CAT_Z_SET_KEY = "cat_z_set";

    public static final String CAT_PREFIX = "cat_id_";

    public static final String USER_PREFIX = "user_id_";

    public static final String TOKEN_PREFIX = "token_id_";

    public static final String TOKEN_LOCK_PREFIX = "token_lock_";

    public boolean setValue(String key, Object value) {
        try {
            redis.set(key, JSON.toJSONString(value));
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean setValue(String key, Object value, long expire) {
        try {
            redis.set(key, JSON.toJSONString(value), expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean setEmptyValue(String key) {
        try {
            redis.set(key, "", 10, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public <T> T getValue(String key, Class<T> clazz) {
        try {
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

//    public boolean setToken(Long userId, String token) {
//        try {
//            ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
//            redis.set(TOKEN_PREFIX + userId, token);
//            return true;
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//        }
//        return false;
//    }

    public boolean setToken(Token token) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(token.getUserId());
        try {
            redis.set(TOKEN_PREFIX + token.getUserId(), JSON.toJSONString(token));
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean setToken(Token token, Long expire) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(token.getUserId());
        Objects.requireNonNull(expire);
        try {
            redis.set(TOKEN_PREFIX + token.getUserId(), JSON.toJSONString(token), expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public Token getToken(Long userId) {
        Objects.requireNonNull(userId);
        String tokenStr = redis.get(TOKEN_PREFIX + userId);
        if (tokenStr == null) {
            return null;
        }
        return JSON.parseObject(tokenStr, Token.class);
    }

    public boolean removeToken(Long userId) {
        boolean ok = this.remove(TOKEN_PREFIX + userId);
        return ok;
    }

    public boolean lock(String prefix, String key, String value, long lockExpireTimeOut, long lockWaitTimeOut) {
        try {
            String realKey = prefix + key;
            long deadTimeLine = System.currentTimeMillis() + lockWaitTimeOut;
            for (; ; ) {
                Boolean ok = redis.setIfAbsent(realKey, value, lockExpireTimeOut, TimeUnit.MILLISECONDS);
                if (Boolean.TRUE.equals(ok)) {
                    return true;
                }
                lockWaitTimeOut = deadTimeLine - System.currentTimeMillis();
                if (lockWaitTimeOut <= 0L) {
                    return false;
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public boolean lock(String prefix, String key, String value) {
        return lock(prefix, key, value, 200, 200);
    }

    public boolean unlock(String prefix, String key, String value) {
        try {
            String realKey = prefix + key;
            Long res = stringRedisTemplate.execute(getDelScript, Collections.singletonList(realKey), value);
            if (res != null && res.equals(1L)) {
                return true;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }
}
