package com.pyg.vo;

import com.pyg.pojo.TbOrder;
import com.pyg.pojo.TbOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/28 8:42
 * @since 1.0.0
 */
public class Order implements Serializable {
    private String sellerName; //商家名称
    private TbOrder order; //订单
    private List<TbOrderItem> orderItemList; //订单明细

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public TbOrder getOrder() {
        return order;
    }

    public void setOrder(TbOrder order) {
        this.order = order;
    }

    public List<TbOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<TbOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}