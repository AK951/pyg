package com.pyg.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbAddress;
import com.pyg.user.service.AddressService;
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
@RequestMapping("/address")
public class AddressController {

	@Reference(timeout=100000)
	private AddressService addressService;
	@Autowired
	private HttpServletRequest request;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbAddress>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findAll")
	public List<TbAddress> findAll(){			
		return addressService.findAll();
	}
	
	
	/**
     * description: 分页返回全部列表
     *
     * @param page 当前页面
     * @param rows 每页记录数
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findPage/{page}/{rows}")
	public PageResult findPage(@PathVariable Integer page, @PathVariable Integer rows){
		return addressService.findPage(page, rows);
	}
	
	/**
     * description: 增加
     *
     * @param address 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/add")
	public InfoResult add(@RequestBody TbAddress address){
		try {
			String userId = request.getRemoteUser();
			address.setUserId(userId);
			addressService.add(address);
			return new InfoResult(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "增加失败");
		}
	}
	
	/**
     * description: 修改
     *
     * @param address 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/update")
	public InfoResult update(@RequestBody TbAddress address){
		try {
			addressService.update(address);
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
     * @return com.pyg.pojo.TbAddress
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findOne/{id}")
	public TbAddress findOne(@PathVariable Long id){
		return addressService.findOne(id);		
	}
	
	/**
     * description: 批量删除
     *
     * @param id 实体ids
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/delete/{id}")
	public InfoResult delete(@PathVariable Long id){
		try {
			addressService.delete(id);
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
     * @param address 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/search/{page}/{rows}")
	public PageResult search(@PathVariable Integer page, @PathVariable Integer rows, @RequestBody TbAddress address){
		return addressService.findPage(page, rows, address);		
	}

	/**
	 * description: 根据用户返回地址列表
	 *
	 * @return java.util.List<com.pyg.pojo.TbAddress>
	 * @author AK
	 * @date  2018年08月29日 14:28:04
	 */
	@RequestMapping("/findListByLoginUser")
	public List<TbAddress> findListByLoginUser() {
		String userId = request.getRemoteUser();
		return addressService.findListByUserId(userId);
	}

	/**
	 * description: 设为默认地址
	 *
	 * @param id 地址id
	 * @return com.pyg.vo.InfoResult
	 * @author AK
	 * @date  2018年08月29日 14:28:00
	 */
	@RequestMapping("/setDefaultAddress/{id}")
	public InfoResult setDefaultAddress(@PathVariable Long id) {
		try {
			String userId = request.getRemoteUser();
			addressService.setDefaultAddress(id, userId);
			return new InfoResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "操作失败");
		}
	}

}
