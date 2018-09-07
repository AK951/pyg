package com.pyg.order.service;
import java.util.List;

import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbOrder;

import com.pyg.pojo.TbPayLog;
import com.pyg.vo.Order;
import com.pyg.vo.PageResult;

/**
 * Description: 服务层接口
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
public interface OrderService {

	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbOrder>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	List<TbOrder> findAll();
		
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
	PageResult findPage(int page, int rows, String userId);
		
	/**
     * description: 增加
     *
     * @param order 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void add(TbOrder order);
		
	/**
     * description: 修改
     *
     * @param order 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void update(TbOrder order);
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbOrder
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	TbOrder findOne(Long id);	
	
	/**
     * description: 批量删除
     *
     * @param ids 实体ids
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void delete(Long[] ids);

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
	PageResult findPage(Integer page, Integer rows, TbOrder order);

	/**
	 * description: 根据用户查询payLog
	 *
	 * @param userId 用户id
	 * @return com.pyg.pojo.TbPayLog
	 * @author AK
	 * @date  2018年08月29日 09:09:00
	 */
	TbPayLog searchPayLogFromRedis(String userId);

	/**
	 * description: 修改订单状态
	 *
	 * @param out_trade_no 支付订单号
	 * @param transaction_id 微信返回的交易流水号
	 * @return void
	 * @author AK
	 * @date  2018年08月29日 09:15:25
	 */
	void updateOrderStatus(String out_trade_no,String transaction_id);

	/**
	 * description: 根据订单id返回订单包装类对象
	 *
	 * @param id 订单id
	 * @return com.pyg.vo.Order
	 * @author AK
	 * @date  2018年09月06日 21:12:47
	 */
    Order findOrderById(Long id);

	TbItem findSpecForItemId(Long itemId);
}
