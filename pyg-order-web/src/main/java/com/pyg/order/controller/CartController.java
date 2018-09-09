package com.pyg.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pyg.cart.service.CartService;
import com.pyg.util.CookieUtil;
import com.pyg.vo.Cart;
import com.pyg.vo.InfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/26 8:44
 * @since 1.0.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout=100000)
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * description: 购物车列表
     *
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年08月26日 08:49:38
     */
    @RequestMapping("/findCartList")
    public List<Cart> findCartList() {
        //得到登陆人账号,判断当前是否有人登陆
        String username = request.getRemoteUser();
        String cartListString = CookieUtil.getCookieValue(request, "cartList", "utf-8");
        if(StringUtils.isEmpty(cartListString)) {
            cartListString = "[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
        if(StringUtils.isEmpty(username)) {
            return cartList_cookie;
        } else {
            List<Cart> cartList_redis = cartService.findCartListFromRedis(username);
            if(cartList_cookie.size() > 0) {
                cartList_redis = cartService.mergeCartList(cartList_redis, cartList_cookie);
                CookieUtil.deleteCookie(request, response, "cartList");
                cartService.saveCartListToRedis(username, cartList_redis);
            }
            return cartList_redis;
        }
    }

    @RequestMapping("/findOrderCartList")
    public List<Cart> findOrderCartList() {
        //得到登陆人账号,判断当前是否有人登陆
        List<Cart> cartList = findCartList();
        return cartService.findOrderCartList(cartList);
    }

}