package com.humy.mycat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 16:29
 * @Description:
 */
@RestController
public class BaseController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
