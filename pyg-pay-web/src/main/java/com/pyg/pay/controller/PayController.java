package com.pyg.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.order.service.OrderService;
import com.pyg.pay.service.PayService;
import com.pyg.pojo.TbPayLog;
import com.pyg.vo.InfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/28 0:13
 * @since 1.0.0
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference(timeout = 10000000)
    private PayService payService;
    @Reference
    private OrderService orderService;
    @Autowired
    private HttpServletRequest request;

    /**
     * description: 生成支付二维码
     *
     * @return java.util.Map
     * @author AK
     * @date  2018年08月28日 00:15:32
     */
    @RequestMapping("/createNative")
    public Map createNative() {
        //获取当前用户
        String userId = request.getRemoteUser();
        //到redis查询支付日志
        TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
        //判断支付日志存在
        if(payLog!=null){
            return payService.createNative(payLog.getOutTradeNo(),payLog.getTotalFee() + "");
        } else {
            return new HashMap();
        }
    }

    /**
     * description: 查询订单状态
     *
     * @param out_trade_no 订单号
     * @return Result
     * @author AK
     * @date  2018年08月28日 00:30:00
     */
    @RequestMapping("/queryPayStatus/{out_trade_no}")
    public InfoResult queryPayStatus(@PathVariable String out_trade_no){
        InfoResult result=null;
        int x=0;
        while(true){
            //调用查询接口
            Map map = payService.queryPayStatus(out_trade_no);
            if(map==null){//出错
                result = new InfoResult(false, "支付出错");
                break;
            }
            if(map.get("trade_state").equals("SUCCESS")) { // 如果成功
                //修改订单状态
                result = new InfoResult(true, "支付成功");
                orderService.updateOrderStatus(out_trade_no, (String) map.get("transaction_id"));
                break;
            }
            try {
                Thread.sleep(3000);//间隔三秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //为了不让循环无休止地运行，我们定义一个循环变量，如果这个变量超过了这个值则退出循环，设置时间为5分钟
            x++;
            if(x >= 100){
                result=new InfoResult(false, "二维码超时");
                break;
            }
        }
        return result;
    }

}