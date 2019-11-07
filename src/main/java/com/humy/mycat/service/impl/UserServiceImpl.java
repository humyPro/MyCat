package com.humy.mycat.service.impl;

import com.humy.mycat.dto.in.Login;
import com.humy.mycat.entity.User;
import com.humy.mycat.interceptor.auth.Jwt;
import com.humy.mycat.repository.UserRepository;
import com.humy.mycat.service.UserService;
import com.humy.mycat.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:58
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RedisUtil redis;

    public UserServiceImpl(UserRepository userRepository, RedisUtil redisUtil) {
        this.userRepository = userRepository;
        this.redis = redisUtil;
    }

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
        return null;
    }

    /**
     * login and get token
     *
     * @param login login name and password
     * @return token
     */
    @Override
    public String login(Login login) {
        User user = userRepository.findUserByTelNumberAndPassword(login.getTelNumber(), login.getPassword());
        if (user == null)
            return null;
        String token = Jwt.createJWT(user);
        if (token != null) {
            redis.setToken(user.getId(), token);
        }
        return token;
    }

    public boolean logout(Long id) {
        return redis.removeToken(id);
    }

    @Override
    public boolean changePassword(Login login) {
        int effect = userRepository.changePassword(login);
        if (effect == 1) {
            this.logout(login.getUserId());
        }
        return effect == 1;
    }
}
