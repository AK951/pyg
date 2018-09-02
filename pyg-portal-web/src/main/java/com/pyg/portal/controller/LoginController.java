package com.pyg.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/25 16:29
 * @since 1.0.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/showName")
    public Map showName(HttpServletRequest request) {
        String remoteUser = request.getRemoteUser();
        Map map = new HashMap();
        map.put("loginName", remoteUser);
        return map;
    }
}