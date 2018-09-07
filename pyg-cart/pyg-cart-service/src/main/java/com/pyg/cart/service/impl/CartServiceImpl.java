package com.pyg.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.cart.service.CartService;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbOrderItem;
import com.pyg.vo.Cart;
import com.pyg.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 购物车服务实现类
 *
 * @author AK
 * @date 2018/8/25 21:19
 * @since 1.0.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;

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
    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //1.根据商品SKU ID查询SKU商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        if(item==null){
            throw new RuntimeException("商品不存在");
        }
        if(!item.getStatus().equals("1")){
            throw new RuntimeException("商品状态无效");
        }

        //2.获取商家ID
        String sellerId = item.getSellerId();
        //3.根据商家ID判断购物车列表中是否存在该商家的购物车
        Cart cart = searchCartBySellerId(cartList, sellerId);

        //4.如果购物车列表中不存在该商家的购物车
        if(cart == null) {
            //4.1 新建购物车对象
            cart = new Cart();
            cart.setSellerId(item.getSellerId());
            cart.setSellerName(item.getSeller());
            List<CartItem> orderItemList = new ArrayList<>();
            CartItem orderItem = createOrderItem(item, num);
            orderItemList.add(orderItem);
            cart.setOrderItemList(orderItemList);
            //4.2 将新建的购物车对象添加到购物车列表
            cartList.add(cart);
        }

        //5.如果购物车列表中存在该商家的购物车
        else {
            // 查询购物车明细列表中是否存在该商品
            CartItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
            //5.1. 如果没有，新增购物车明细
            if(orderItem == null) {
                orderItem = createOrderItem(item, num);
                cart.getOrderItemList().add(orderItem);
            }

            //5.2. 如果有，在原购物车明细上添加数量，更改金额
            else {
                orderItem.setNum(orderItem.getNum() + num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getNum() * orderItem.getPrice().doubleValue()));
                if(orderItem.getNum() <= 0) {
                    cart.getOrderItemList().remove(orderItem);
                }
                if(cart.getOrderItemList().size() == 0) {
                    cartList.remove(cart);
                }
            }

        }
        return cartList;
    }

    /**
     * description: 从redis中查询购物车
     *
     * @param username
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年08月26日 10:09:27
     */
    @Override
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if(cartList == null) {
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    /**
     * description: 将购物车保存到redis
     *
     * @param username
     * @param cartList
     * @return void
     * @author AK
     * @date  2018年08月26日 10:09:39
     */
    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username, cartList);
    }

    /**
     * description: 合并购物车
     *
     * @param cartList1 cookie购物车
     * @param cartList2 redis购物车
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年08月26日 10:27:02
     */
    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
        for(Cart cart: cartList2){
            for(TbOrderItem orderItem:cart.getOrderItemList()){
                cartList1 = addGoodsToCartList(cartList1, orderItem.getItemId(), orderItem.getNum());
            }
        }
        return cartList1;
    }

    /**
     * description: 从购物车中查询需要下单的商品
     *
     * @param cartList 购物车
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年09月03日 16:19:29
     */
    @Override
    public List<Cart> findOrderCartList(List<Cart> cartList) {
        if(cartList != null) {
            for (int i = 0; i < cartList.size(); i++) {
                Cart cart = cartList.get(i);
                List<CartItem> orderItemList = cart.getOrderItemList();
                for (int j = 0; j < orderItemList.size(); j++) {
                    CartItem orderItem = orderItemList.get(j);
                    if(!orderItem.isCartStatus()) {
                        orderItemList.remove(orderItem);
                        j--;
                    }
                }
                if(orderItemList.size() == 0) {
                    cartList.remove(cart);
                    i--;
                }
            }
        } else {
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    /**
     * description: 从购物车中更新商品选中状态
     *
     * @param cartList 购物车
     * @param status 选中状态
     * @param itemId skuid
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年09月06日 21:01:11
     */
    @Override
    public List<Cart> updateStatus(List<Cart> cartList, boolean status, Long itemId) {
        // 根据商品SKU ID查询SKU商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        // 获取商家ID
        String sellerId = item.getSellerId();
        // 根据商家ID获取该商家的购物车
        Cart cart = searchCartBySellerId(cartList, sellerId);
        // 根据itemId获取该购物车明细
        CartItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
        // 根据传入的状态更新此购物车明细
        orderItem.setCartStatus(status);
        // 返回购物车列表
        return cartList;
    }

    /**
     * description: 根据商品ID查询商品明细
     *
     * @param orderItemList 商品明细列表
     * @param itemId 商品id
     * @return com.pyg.pojo.TbOrderItem
     * @author AK
     * @date  2018年08月26日 08:28:00
     */
    private CartItem searchOrderItemByItemId(List<CartItem> orderItemList, Long itemId) {
        for (CartItem orderItem : orderItemList) {
            if(orderItem.getItemId().longValue() == itemId.longValue()) {
                return orderItem;
            }
        }
        return null;
    }

    /**
     * description: 根据商家ID查询购物车对象
     *
     * @param cartList 购物车列表
     * @param sellerId 商家id
     * @return com.pyg.vo.Cart
     * @author AK
     * @date  2018年08月26日 08:22:11
     */
    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        for (Cart cart : cartList) {
            if(cart.getSellerId().equals(sellerId)) {
                return cart;
            }
        }
        return null;
    }

    /**
     * description: 创建购物车明细
     *
     * @param item 商品
     * @param num 数量
     * @return com.pyg.pojo.TbOrderItem
     * @author AK
     * @date  2018年08月26日 08:22:33
     */
    private CartItem createOrderItem(TbItem item, Integer num) {
        if(num <= 0) {
            throw new RuntimeException("数量非法");
        }
        CartItem orderItem = new CartItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setTitle(item.getTitle());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setPrice(item.getPrice());
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue() * num));
        orderItem.setCartStatus(true);
        return orderItem;
    }
}