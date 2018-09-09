package com.pyg.vo;

import com.pyg.pojo.TbOrderItem;

import java.io.Serializable;

/**
 * Description: 购物车明细
 *
 * @author AK
 * @date 2018/9/3 11:08
 * @since 1.0.0
 */
public class CartItem extends TbOrderItem implements Serializable {
    private boolean cartStatus; // 购物车明细是否选中,默认加入购物车的时候选中

    public boolean isCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(boolean cartStatus) {
        this.cartStatus = cartStatus;
    }
}