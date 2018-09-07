package com.pyg.order.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbOrderItemMapper;
import com.pyg.mapper.TbOrderMapper;
import com.pyg.mapper.TbPayLogMapper;
import com.pyg.mapper.TbSellerMapper;
import com.pyg.order.service.OrderService;
import com.alibaba.fastjson.JSON;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.pojo.TbOrderExample.Criteria;
import com.pyg.util.IdWorker;
import com.pyg.vo.Cart;
import com.pyg.vo.CartItem;
import com.pyg.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbOrderExample.Criteria;
import com.pyg.order.service.OrderService;

import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbSellerMapper sellerMapper;
    @Autowired
    private TbPayLogMapper payLogMapper;
	@Autowired
	private TbItemMapper itemMapper;

	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbOrder>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
    @Override
    public List<TbOrder> findAll() {
        return orderMapper.selectByExample(null);
    }

    /**
     * description: 分页返回当前用户的订单列表
     *
     * @param page 当前页面
     * @param rows 每页记录数
     * @param userId 用户id
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
    @Override
    public PageResult findPage(int page, int rows, String userId) {
        PageHelper.startPage(page, rows);
        TbOrderExample orderExample = new TbOrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId);
        orderExample.setOrderByClause("create_time DESC");
        Page<TbOrder> pageInfo = (Page<TbOrder>) orderMapper.selectByExample(orderExample);
        List<TbOrder> result = pageInfo.getResult();
        List<Order> orderList = new ArrayList<>();
        for (TbOrder tbOrder : result) {
            Order order = new Order();
            order.setSellerName(sellerMapper.selectByPrimaryKey(tbOrder.getSellerId()).getNickName());
            order.setOrder(tbOrder);
            TbOrderItemExample example = new TbOrderItemExample();
            example.createCriteria().andOrderIdEqualTo(tbOrder.getOrderId());
            order.setOrderItemList(orderItemMapper.selectByExample(example));
            orderList.add(order);
        }
        return new PageResult(pageInfo.getTotal(), orderList);
    }

    /**
     * description: 增加
     *
     * @param tbOrder 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
    @Override
    public void add(TbOrder tbOrder) {
        List<Cart> cartList = findOrderCartList(tbOrder.getUserId());
        List<String> orderIdList = new ArrayList(); //订单ID列表
        double total_money=0; //总金额 （元）
        for (Cart cart : cartList) {
            long orderId = idWorker.nextId();
            TbOrder order = new TbOrder();
            order.setOrderId(orderId);
            order.setPaymentType(tbOrder.getPaymentType());
            order.setStatus("1");
            Date date = new Date();
            order.setCreateTime(date);
            order.setUpdateTime(date);
            order.setUserId(tbOrder.getUserId());
            order.setReceiverAreaName(tbOrder.getReceiverAreaName());
            order.setReceiverMobile(tbOrder.getReceiverMobile());
            order.setReceiver(tbOrder.getReceiver());
            order.setSourceType(tbOrder.getSourceType());
            order.setSellerId(cart.getSellerId());
            double money = 0;
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                orderItem.setId(idWorker.nextId());
                orderItem.setOrderId(orderId);
                orderItem.setSellerId(cart.getSellerId());
                money += orderItem.getTotalFee().doubleValue();
                orderItemMapper.insert(orderItem);
            }
            order.setPayment(new BigDecimal(money));
            orderMapper.insert(order);
            orderIdList.add(orderId + ""); // 添加到订单列表
            total_money += money; // 累加到总金额
        }
        if("1".equals(tbOrder.getPaymentType())) {
            TbPayLog payLog = new TbPayLog();
            String outTradeNo = idWorker.nextId() + ""; // 支付订单号
            payLog.setOutTradeNo(outTradeNo); // 支付订单号
            payLog.setCreateTime(new Date()); // 创建时间
            //订单号列表，逗号分隔
            String ids = orderIdList.toString().replace("[", "").replace("]", "").replace(" ", "");
            payLog.setOrderList(ids); // 订单号列表，逗号分隔
            payLog.setPayType("1"); // 支付类型
            payLog.setTotalFee( (long)(total_money*100 ) ); // 总金额(分)
            payLog.setTradeState("0"); // 支付状态
            payLog.setUserId(tbOrder.getUserId()); // 用户ID
            payLogMapper.insert(payLog); // 插入到支付日志表
            redisTemplate.boundHashOps("payLog").put(tbOrder.getUserId(), payLog); // 放入缓存
        }
        deleteOrderCartList(tbOrder.getUserId());
    }

    /**
     * description: 修改
     *
     * @param order 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
    @Override
    public void update(TbOrder order){
        orderMapper.updateByPrimaryKey(order);
    }

    /**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbOrder
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
    @Override
    public TbOrder findOne(Long id){
        return orderMapper.selectByPrimaryKey(id);
    }

    /**
     * description: 批量删除
     *
     * @param ids 实体ids
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            orderMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * description: 查询+分页
     *
     * @param page 当前页
     * @param rows 每页记录数
     * @param order 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
    @Override
    public PageResult findPage(Integer page, Integer rows, TbOrder order) {
        PageHelper.startPage(page, rows);

        TbOrderExample example = new TbOrderExample();
        Criteria criteria = example.createCriteria();

        if(order != null) {
            if(order.getPaymentType()!=null && order.getPaymentType().length()>0){
                criteria.andPaymentTypeLike("%"+order.getPaymentType()+"%");
            }
            if(order.getPostFee()!=null && order.getPostFee().length()>0){
                criteria.andPostFeeLike("%"+order.getPostFee()+"%");
            }
            if(order.getStatus()!=null && order.getStatus().length()>0){
                criteria.andStatusLike("%"+order.getStatus()+"%");
            }
            if(order.getShippingName()!=null && order.getShippingName().length()>0){
                criteria.andShippingNameLike("%"+order.getShippingName()+"%");
            }
            if(order.getShippingCode()!=null && order.getShippingCode().length()>0){
                criteria.andShippingCodeLike("%"+order.getShippingCode()+"%");
            }
            if(order.getUserId()!=null && order.getUserId().length()>0){
                criteria.andUserIdLike("%"+order.getUserId()+"%");
            }
            if(order.getBuyerMessage()!=null && order.getBuyerMessage().length()>0){
                criteria.andBuyerMessageLike("%"+order.getBuyerMessage()+"%");
            }
            if(order.getBuyerNick()!=null && order.getBuyerNick().length()>0){
                criteria.andBuyerNickLike("%"+order.getBuyerNick()+"%");
            }
            if(order.getBuyerRate()!=null && order.getBuyerRate().length()>0){
                criteria.andBuyerRateLike("%"+order.getBuyerRate()+"%");
            }
            if(order.getReceiverAreaName()!=null && order.getReceiverAreaName().length()>0){
                criteria.andReceiverAreaNameLike("%"+order.getReceiverAreaName()+"%");
            }
            if(order.getReceiverMobile()!=null && order.getReceiverMobile().length()>0){
                criteria.andReceiverMobileLike("%"+order.getReceiverMobile()+"%");
            }
            if(order.getReceiverZipCode()!=null && order.getReceiverZipCode().length()>0){
                criteria.andReceiverZipCodeLike("%"+order.getReceiverZipCode()+"%");
            }
            if(order.getReceiver()!=null && order.getReceiver().length()>0){
                criteria.andReceiverLike("%"+order.getReceiver()+"%");
            }
            if(order.getInvoiceType()!=null && order.getInvoiceType().length()>0){
                criteria.andInvoiceTypeLike("%"+order.getInvoiceType()+"%");
            }
            if(order.getSourceType()!=null && order.getSourceType().length()>0){
                criteria.andSourceTypeLike("%"+order.getSourceType()+"%");
            }
            if(order.getSellerId()!=null && order.getSellerId().length()>0){
                criteria.andSellerIdLike("%"+order.getSellerId()+"%");
            }

        }

        Page<TbOrder> pageInfo = (Page<TbOrder>) orderMapper.selectByExample(example);
        return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
    }

    /**
     * description: 根据用户查询payLog
     *
     * @param userId 用户id
     * @return com.pyg.pojo.TbPayLog
     * @author AK
     * @date  2018年08月29日 09:09:00
     */
    @Override
    public TbPayLog searchPayLogFromRedis(String userId) {
        return  (TbPayLog) redisTemplate.boundHashOps("payLog").get(userId);
    }

    /**
     * description: 修改订单状态
     *
     * @param out_trade_no 支付订单号
     * @param transaction_id 微信返回的交易流水号
     * @return void
     * @author AK
     * @date  2018年08月29日 09:15:25
     */
    @Override
    public void updateOrderStatus(String out_trade_no, String transaction_id) {
        //1.修改支付日志状态
        TbPayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);
        payLog.setPayTime(new Date());
        payLog.setTradeState("1"); // 已支付
        payLog.setTransactionId(transaction_id); // 交易号
        payLogMapper.updateByPrimaryKey(payLog);
        //2.修改订单状态
        String[] orderIds = payLog.getOrderList().split(",");
        for (String orderId : orderIds) {
            TbOrder order = orderMapper.selectByPrimaryKey(Long.parseLong(orderId));
            if(order != null) {
                order.setStatus("2"); // 已付款
                order.setPaymentTime(new Date()); // 付款时间
                orderMapper.updateByPrimaryKey(order);
            }
        }
        //3.清除redis缓存数据
        redisTemplate.boundHashOps("payLog").delete(payLog.getUserId());

    }

    /**
     * description: 根据订单id返回订单包装类对象
     *
     * @param id 订单id
     * @return com.pyg.vo.Order
     * @author AK
     * @date  2018年09月06日 21:12:47
     */
	public Order findOrderById(Long id) {
		//获取订单
		TbOrder tbOrder = orderMapper.selectByPrimaryKey(id);
		TbOrderItemExample example = new TbOrderItemExample();
		TbOrderItemExample.Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(id);
		//查询订单明细
		List<TbOrderItem> orderItemList = orderItemMapper.selectByExample(example);
		//查询商家
		TbSeller seller = sellerMapper.selectByPrimaryKey(tbOrder.getSellerId());
		Order order = new Order();
		//设置订单
		order.setOrder(tbOrder);
		//设置订单明细
		order.setOrderItemList(orderItemList);
		//设置商家名称
		order.setSellerName(seller.getNickName());
		//返回order包装类对象
		return order;
	}


	public TbItem findSpecForItemId(Long itemId) {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}

    /**
     * description: 获取要下单的购物车明细
     *
     * @param username 登录用户名
     * @return java.util.List<com.pyg.vo.Cart>
     * @author AK
     * @date  2018年09月06日 21:14:30
     */
    private List<Cart> findOrderCartList(String username) {
        // 获取该用户的购物车
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if(cartList != null) {
            for (int i = 0; i < cartList.size(); i++) {
                Cart cart = cartList.get(i);
                List<CartItem> orderItemList = cart.getOrderItemList();
                for (int j = 0; j < orderItemList.size(); j++) {
                    CartItem orderItem = orderItemList.get(j);
                    // 去除没有选中的购物车明细
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
        // 返回全部选中的购物车列表
        return cartList;
    }

    /**
     * description: 完成订单后,删除redis中选中的明细
     *
     * @param username 登录用户名
     * @return void
     * @author AK
     * @date  2018年09月06日 21:14:11
     */
    private void deleteOrderCartList(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if(cartList != null) {
            for (int i = 0; i < cartList.size(); i++) {
                Cart cart = cartList.get(i);
                List<CartItem> orderItemList = cart.getOrderItemList();
                for (int j = 0; j < orderItemList.size(); j++) {
                    CartItem orderItem = orderItemList.get(j);
                    if(orderItem.isCartStatus()) {
                        orderItemList.remove(orderItem);
                        j--;
                    }
                }
                if(orderItemList.size() == 0) {
                    cartList.remove(cart);
                    i--;
                }
            }
            if(cartList != null) {
                redisTemplate.boundHashOps("cartList").put(username, cartList);
            } else {
                redisTemplate.boundHashOps("cartList").delete(username);
            }
        }
    }

}
