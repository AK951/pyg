package com.pyg.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.cart.service.CartService;
import com.pyg.order.service.OrderService;
import com.pyg.pojo.TbOrder;
import com.pyg.vo.Cart;
import com.pyg.vo.InfoResult;
import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: controller
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Reference(timeout=100000)
	private OrderService orderService;
	@Reference(timeout=100000)
	private CartService cartService;
	@Autowired
	private HttpServletRequest request;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbOrder>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findAll")
	public List<TbOrder> findAll(){			
		return orderService.findAll();
	}
	
	/**
     * description: 增加
     *
     * @param order 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/add")
	public InfoResult add(@RequestBody TbOrder order){
		String username = request.getRemoteUser();
		order.setUserId(username);
		order.setSourceType("2");
		try {
			orderService.add(order);
			return new InfoResult(true, "创建成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "创建失败");
		}
	}
	
	/**
     * description: 修改
     *
     * @param order 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/update")
	public InfoResult update(@RequestBody TbOrder order){
		try {
			orderService.update(order);
			return new InfoResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "修改失败");
		}
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbOrder
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findOne/{id}")
	public TbOrder findOne(@PathVariable Long id){
		return orderService.findOne(id);		
	}
	
	/**
     * description: 批量删除
     *
     * @param ids 实体ids
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/delete/{ids}")
	public InfoResult delete(@PathVariable Long[] ids){
		try {
			orderService.delete(ids);
			return new InfoResult(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "删除失败");
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
	@RequestMapping("/search/{page}/{rows}")
	public PageResult search(@PathVariable Integer page, @PathVariable Integer rows, @RequestBody TbOrder order){
		return orderService.findPage(page, rows, order);		
	}

}
