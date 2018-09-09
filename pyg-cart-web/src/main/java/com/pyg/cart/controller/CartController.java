package com.pyg.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pyg.cart.service.CartService;
import com.pyg.util.CookieUtil;
import com.pyg.vo.Cart;
import com.pyg.vo.InfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * description: 添加商品到购物车
     *
     * @param itemId 商品id
     * @param num 商品数量
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年09月03日 14:50:56
     */
    @CrossOrigin(origins="*",allowCredentials="true")
    @RequestMapping("/addGoodsToCartList/{itemId}/{num}")
    public InfoResult addGoodsToCartList(@PathVariable Long itemId, @PathVariable Integer num) {

        //得到登陆人账号,判断当前是否有人登陆
        String username = request.getRemoteUser();
        List<Cart> cartList = findCartList();
        cartList = cartService.addGoodsToCartList(cartList, itemId, num);
        try {
            if(StringUtils.isEmpty(username)) {
                CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList),3600*24,"UTF-8");
                return new InfoResult(true, "添加成功");
            } else {
                cartService.saveCartListToRedis(username, cartList);
                return new InfoResult(true, "添加成功");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new InfoResult(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false, "添加失败");
        }

    }

    /**
     * description: 从购物车中更新商品选中状态
     *
     * @param cartList 购物车
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年09月06日 21:06:55
     */
    @RequestMapping("/updateStatus")
    public InfoResult updateStatus(@RequestBody List<Cart> cartList) {
        //得到登陆人账号,判断当前是否有人登陆
        String username = request.getRemoteUser();
        try {
            if(StringUtils.isEmpty(username)) {
                CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList),3600*24,"UTF-8");
                return new InfoResult(true, "添加成功");
            } else {
                cartService.saveCartListToRedis(username, cartList);
                return new InfoResult(true, "添加成功");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new InfoResult(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false, "添加失败");
        }
    }

    /**
     * description: 从购物车中查询需要下单的商品
     *
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年09月06日 21:06:47
     */
    @RequestMapping("/findOrderCartList")
    public List<Cart> findOrderCartList() {
        List<Cart> cartList = findCartList();
        return cartService.findOrderCartList(cartList);
    }

}