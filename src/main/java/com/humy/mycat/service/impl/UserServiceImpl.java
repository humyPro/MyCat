package com.humy.mycat.service.impl;

import com.humy.mycat.config.AppConfig;
import com.humy.mycat.dto.in.Login;
import com.humy.mycat.entity.User;
import com.humy.mycat.interceptor.auth.Jwt;
import com.humy.mycat.repository.UserRepository;
import com.humy.mycat.service.UserService;
import com.humy.mycat.util.RedisUtil;
import com.humy.mycat.vo.ClientToken;
import com.humy.mycat.vo.Device;
import com.humy.mycat.vo.Token;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:58
 * @Description:
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RedisUtil redis;

    private AppConfig appConfig;

    @Override
    public User addUser(User user) {
        User save = userRepository.save(user);
        redis.setValue(RedisUtil.USER_PREFIX + save.getId(), save);
        return save;
    }

    @Override
    public User getUserById(Long id) {
        User redis_user = redis.getValue(RedisUtil.USER_PREFIX + id, User.class);
        if (redis_user == null) {
            Optional<User> byId = userRepository.findById(id);
            if (!byId.isPresent()) {
                redis.setEmptyValue(RedisUtil.USER_PREFIX + id);
                return null;
            }
            User user = byId.get();
            redis.setValue(RedisUtil.USER_PREFIX + id, user);
            return user;
        }
        return redis_user;
    }

    /**
     * login and get token
     *
     * @param user
     * @return token
     */
    @Override
    public ClientToken login(User user, Login login) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(login);
        long val = System.currentTimeMillis();
        try {
            boolean lock = redis.lock(RedisUtil.TOKEN_LOCK_PREFIX, String.valueOf(user.getId()), String.valueOf(val));
            if (!lock) {
                return null;
            }
            Token oldToken = redis.getToken(user.getId());
            long now = System.currentTimeMillis();
            String uuid = UUID.randomUUID().toString();
            if (oldToken != null) {
                //其他设备登录过
                List<Device> devices = oldToken.getDevices();
                devices = devices == null ? new LinkedList<>() : devices;
                if (devices.size() > appConfig.getMaxDeviceNum()) {
                    //如果超过了最大的设备数，是删除之前所有的设备还是删除登陆最早的设备呢？
                    devices = new LinkedList<>();
                }
                //没有达到最多设备的限制
                //重新生成一台设备
                Device device = new Device();
                device.setDeviceId(uuid);
                device.setAgent(login.getUserAgent());
                device.setLoginTime(now);
                devices.add(device);
                oldToken.setDevices(devices);
                boolean ok = redis.setToken(oldToken);
                if (ok) {
                    ClientToken clientToken = new ClientToken();
                    clientToken.setLoginTime(now);
                    clientToken.setUuid(uuid);
                    clientToken.setToken(oldToken.getTokenStr());
                    return clientToken;
                } else {
                    return null;
                }
            }
            //第一次登陆
            String tokenStr = Jwt.createJWT(user.getId());
            Device device = new Device();
            device.setDeviceId(uuid);
            device.setAgent(login.getUserAgent());
            device.setLoginTime(now);
            ArrayList<Device> devices = new ArrayList<>(1);
            devices.add(device);
            Token token = new Token();
            token.setDevices(devices);
            token.setTokenStr(tokenStr);
            token.setUserId(user.getId());
            boolean ok = redis.setToken(token);
            if (ok) {
                ClientToken clientToken = new ClientToken();
                clientToken.setLoginTime(now);
                clientToken.setUuid(uuid);
                clientToken.setToken(tokenStr);
                return clientToken;
            }
            return null;
        } finally {
            redis.unlock(RedisUtil.TOKEN_LOCK_PREFIX, String.valueOf(user.getId()), String.valueOf(val));
        }
    }

    @Override
    public boolean logout(Long userId, String deviceId) {
        String now = String.valueOf(System.currentTimeMillis());
        try {
            boolean lock = redis.lock(RedisUtil.TOKEN_LOCK_PREFIX, String.valueOf(userId), now);
            if (!lock) {
                return false;
            }
            Token token = redis.getToken(userId);
            List<Device> devices = token.getDevices();
            devices.removeIf(next -> deviceId.equals(next.getDeviceId()));
            if (devices.size() == 0) {
                return redis.removeToken(userId);
            }
            token.setDevices(devices);
            return redis.setToken(token);
        } finally {
            redis.unlock(RedisUtil.TOKEN_LOCK_PREFIX, String.valueOf(userId), now);
        }
    }

    @Override
    public boolean changePassword(Login login) {
        int effect = userRepository.changePassword(login.getUserId(), login.getPassword(), login.getNewPassword());
        String now = String.valueOf(System.currentTimeMillis());
        if (effect == 1) {
            try {
                //todo 如果删除失败..........
                redis.lock(RedisUtil.TOKEN_LOCK_PREFIX, String.valueOf(login.getUserId()), now, 1000, 2000);
                long start = System.currentTimeMillis();
                for (; ; ) {
                    boolean ok = redis.removeToken(login.getUserId());
                    if (ok || (System.currentTimeMillis() - start > 800)) {
                        break;
                    }
                }
            } finally {
                redis.unlock(RedisUtil.TOKEN_LOCK_PREFIX, String.valueOf(login.getUserId()), now);
            }
        }
        return effect == 1;
    }

    @Override
    public User findUserByTelNumberAndPassword(String telNumber, String pwd) {
        return userRepository.findUserByTelNumberAndPassword(telNumber, pwd);
    }
}
