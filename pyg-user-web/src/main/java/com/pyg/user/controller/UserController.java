package com.pyg.user.controller;
import java.util.List;

import com.pyg.util.PhoneFormatCheckUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbUser;
import com.pyg.user.service.UserService;

import com.pyg.vo.PageResult;
import com.pyg.vo.InfoResult;
 
/**
 * Description: controller
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Reference(timeout=100000)
	private UserService userService;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbUser>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findAll")
	public List<TbUser> findAll(){			
		return userService.findAll();
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
		return userService.findPage(page, rows);
	}
	
	/**
     * description: 增加
     *
     * @param user 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/add/{code}")
	public InfoResult add(@RequestBody TbUser user, @PathVariable String code){
		try {
			if(!userService.checkCode(user.getPhone(), code)) {
				return new InfoResult(false, "验证码错误");
			}
			userService.add(user);
			return new InfoResult(true, "注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "注册失败");
		}
	}
	
	/**
     * description: 修改
     *
     * @param user 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/update")
	public InfoResult update(@RequestBody TbUser user){
		try {
			userService.update(user);
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
     * @return com.pyg.pojo.TbUser
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findOne/{id}")
	public TbUser findOne(@PathVariable Long id){
		return userService.findOne(id);		
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
			userService.delete(ids);
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
     * @param user 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/search/{page}/{rows}")
	public PageResult search(@PathVariable Integer page, @PathVariable Integer rows, @RequestBody TbUser user){
		return userService.findPage(page, rows, user);		
	}

	@RequestMapping("/getSmsCode/{phone}")
	public InfoResult getSmsCode(@PathVariable String phone) {
		try {
			if(!PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
				return new InfoResult(false, "手机号格式不正确");
			}
			userService.getSmsCode(phone);
			return new InfoResult(true, "发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "发送失败");
		}
	}
	
}
