package com.humy.mycat.controller;

import com.humy.mycat.annotation.CurrentUser;
import com.humy.mycat.annotation.Logging;
import com.humy.mycat.constant.Header;
import com.humy.mycat.dto.in.Login;
import com.humy.mycat.dto.out.Result;
import com.humy.mycat.entity.User;
import com.humy.mycat.service.UserService;
import com.humy.mycat.dto.vo.ClientToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MarkerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 09:58
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Logging
public class UserController {

    private static final String EMPTY_ACCOUNT_PWD = "The account number or password is empty";

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
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
    public Result<ClientToken> login(@RequestBody Login login, HttpServletRequest request) {
        if (StringUtils.isBlank(login.getTelNumber()) || StringUtils.isBlank(login.getPassword()))
            return Result.unauthorized(EMPTY_ACCOUNT_PWD);
        String remoteAddr = request.getRemoteAddr();
        String userAgent = request.getHeader(Header.USER_AGENT);
        login.setAddress(remoteAddr);
        login.setUserAgent(userAgent);
        User user = userService.findUserByTelNumberAndPassword(login.getTelNumber(), login.getPassword());
        if (user == null) {
            log.info(MarkerFactory.getMarker("loginInfo"), "login failed:{}", login);
        }
        ClientToken token = userService.login(user, userAgent);
        if (token == null) {
            return Result.unauthorized(EMPTY_ACCOUNT_PWD);
        }
        return Result.success(token);
    }

    @DeleteMapping("logout")
    public Result<Boolean> logout(@ApiIgnore @CurrentUser User user, @ApiIgnore @RequestHeader(Header.DEVICE_ID) String deviceId) {
        if (user == null) {
            return Result.badRequest(StringUtils.EMPTY);
        }
        boolean ok = userService.logout(user.getId(), deviceId);
        return Result.success(ok);
    }

    @PutMapping("pwd")
    public Result<Boolean> changePassword(@RequestBody Login login) {
        if (login.getUserId() == null || login.getPassword() == null || login.getNewPassword() == null) {
            return Result.badRequest(StringUtils.EMPTY);
        }
        boolean ok = userService.changePassword(login);
        return Result.success(ok);
    }

    @GetMapping("refresh")
    public Result<ClientToken> refreshToken(@ApiIgnore @CurrentUser User user, HttpServletRequest request) {
        if (user == null) {
            return Result.unauthorized(StringUtils.EMPTY);
        }
        String userAgent = request.getHeader(Header.USER_AGENT);
        String deviceId = request.getHeader(Header.DEVICE_ID);
        Login login = new Login();
        login.setUserAgent(userAgent);
        ClientToken token = userService.refreshToken(user, deviceId, userAgent);
        if (token == null) {
            return Result.unauthorized(EMPTY_ACCOUNT_PWD);
        }
        return Result.success(token);
    }
}
