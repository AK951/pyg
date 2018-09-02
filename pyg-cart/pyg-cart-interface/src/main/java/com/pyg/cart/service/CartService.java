package com.pyg.cart.service;

import com.pyg.vo.Cart;

import java.util.List;

/**
 * Description: 购物车服务接口
 *
 * @author AK
 * @date 2018/8/25 21:17
 * @since 1.0.0
 */
public interface CartService {

    /**
     * description: 添加商品到购物车
     *
     * @param cartList 购物车
     * @param itemId 商品id
     * @param num 商品数量
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年08月25日 21:18:03
     */
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

    /**
     * description: 从redis中查询购物车
     *
     * @param username
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年08月26日 10:09:27
     */
    public List<Cart> findCartListFromRedis(String username);

    /**
     * description: 将购物车保存到redis
     *
     * @param username
     * @param cartList
     * @return void
     * @author AK
     * @date  2018年08月26日 10:09:39
     */
    public void saveCartListToRedis(String username,List<Cart> cartList);

    /**
     * description: 合并购物车
     *
     * @param cartList1
     * @param cartList2
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年08月26日 10:27:02
     */
    public List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);
}
