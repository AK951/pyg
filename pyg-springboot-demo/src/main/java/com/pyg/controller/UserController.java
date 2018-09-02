package com.pyg.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/23 16:21
 * @since 1.0.0
 */
@RestController
public class UserController {

    @RequestMapping("/hello")
    public String showHello() {
        return "hello,spring boot";
    }

    @RequestMapping("/info")
    public String showInfo() {
        return "赵老师爱吃鱼";
    }
}