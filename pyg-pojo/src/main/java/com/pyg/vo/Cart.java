package com.pyg.vo;

import com.pyg.pojo.TbOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 购物车实体类
 *
 * @author AK
 * @date 2018/8/25 20:58
 * @since 1.0.0
 */
public class Cart implements Serializable {
    private String sellerId; //商家ID
    private String sellerName; //商家名称
    private boolean cartStatus;
    private Long cartNum;
    private List<CartItem> orderItemList; //购物车明细

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public boolean isCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(boolean cartStatus) {
        this.cartStatus = cartStatus;
    }

    public Long getCartNum() {
        return cartNum;
    }

    public void setCartNum(Long cartNum) {
        this.cartNum = cartNum;
    }

    public List<CartItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<CartItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}