package com.pyg.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/showName")
    public Map showName() throws Exception {
        String name = request.getRemoteUser();
        Map map = new HashMap();
        map.put("loginName", name);
        return map;
    }

    @RequestMapping("/logout")
    public void logout() throws Exception {
        request.getSession().invalidate();
        response.sendRedirect("http://192.168.25.128:9100/cas/logout?service=http://localhost:9107/cart.html");
    }
}