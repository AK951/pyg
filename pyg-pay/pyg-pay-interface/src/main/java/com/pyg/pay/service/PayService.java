package com.pyg.pay.service;

import java.util.Map;

/**
 * Description: 微信支付接口
 *
 * @author AK
 * @date 2018/8/28 0:00
 * @since 1.0.0
 */
public interface PayService {

    /**
     * description: 生成微信支付二维码
     *
     * @param out_trade_no 订单号
     * @param total_fee 金额(分)
     * @return java.util.Map
     * @author AK
     * @date  2018年08月28日 00:01:15
     */
    Map createNative(String out_trade_no, String total_fee);

    /**
     * description: 查询支付状态
     *
     * @param out_trade_no 订单号
     * @return java.util.Map
     * @author AK
     * @date  2018年08月28日 00:27:45
     */
    Map queryPayStatus(String out_trade_no);
}