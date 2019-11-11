package com.humy.mycat.service;

import com.humy.mycat.dto.in.Login;
import com.humy.mycat.entity.User;
import com.humy.mycat.vo.ClientToken;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:58
 * @Description:
 */
public interface UserService {

    User addUser(User user);

    User getUserById(Long Id);

    ClientToken login(User user, Login login);

    boolean logout(Long userId, String deviceId);

    boolean changePassword(Login login);

    User findUserByTelNumberAndPassword(String telNumber, String pwd);
}