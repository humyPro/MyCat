package com.humy.mycat.service;

import com.humy.mycat.dto.in.Login;
import com.humy.mycat.entity.User;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:58
 * @Description:
 */
public interface UserService {

    User addUser(User user);

    User getUserById(Long Id);

    String login(Login login);

    boolean logout(Long id);

    boolean changePassword(Login login);
}
