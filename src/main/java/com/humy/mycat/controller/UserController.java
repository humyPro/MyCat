package com.humy.mycat.controller;

import com.humy.mycat.dto.in.Login;
import com.humy.mycat.dto.out.Result;
import com.humy.mycat.entity.User;
import com.humy.mycat.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 09:58
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String EMPTY_ACCOUNT_PWD = "The account number or password is empty";

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public Result<User> addUser(@RequestBody User user) {
        User saved = userService.addUser(user);
        return Result.success(saved);
    }

    @GetMapping("{id}")
    public Result<User> getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @PostMapping("login")
    public Result<String> login(@RequestBody Login login) {
        if (StringUtils.isBlank(login.getTelNumber()) || StringUtils.isBlank(login.getPassword()))
            return Result.unauthorized(EMPTY_ACCOUNT_PWD);
        String token = userService.login(login);
        if (token == null) {
            return Result.unauthorized(EMPTY_ACCOUNT_PWD);
        }
        return Result.success(token);
    }

    @DeleteMapping("logout")
    public Result<Boolean> logout(Login login) {
        if (login.getUserId() == null) {
            return Result.failed("");
        }
        boolean logout = userService.logout(login.getUserId());
        return Result.success(logout);
    }

    public Result<Boolean> changePassword(Login login) {
        if (login.getUserId() == null || login.getPassword() == null || login.getNewPassword() == null) {
            return Result.failed("");
        }
        boolean ok = userService.changePassword(login);
        return Result.success(ok);
    }
}
