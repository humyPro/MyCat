package com.humy.mycat.controller;

import com.humy.mycat.dto.out.Result;
import com.humy.mycat.entity.User;
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

    @PostMapping()
    public Result<User> addUser(@RequestBody User user) {
        return Result.success(null);
    }

    @GetMapping("{id}")
    public Result<User> getUser(@PathVariable("id") Long id) {
        return Result.success(null);
    }

}
