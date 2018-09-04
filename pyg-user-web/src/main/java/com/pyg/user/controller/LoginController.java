package com.pyg.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbUser;
import com.pyg.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/10 11:30
 * @since 1.0.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private UserService userService;

    @RequestMapping("/showUser")
    public TbUser showName() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!StringUtils.isEmpty(name)) {
            return userService.findUserByUserName(name);
        }
        return null;
    }
}