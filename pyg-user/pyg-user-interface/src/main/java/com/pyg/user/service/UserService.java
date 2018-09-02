package com.pyg.user.service;

import com.pyg.pojo.TbUser;
import com.pyg.vo.PageResult;

import java.util.List;

/**
 * Description: 服务层接口
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
public interface UserService {

	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbUser>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	List<TbUser> findAll();
		
	/**
     * description: 分页返回全部列表
     *
     * @param page 当前页面
     * @param rows 每页记录数
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	PageResult findPage(int page, int rows);
		
	/**
     * description: 增加
     *
     * @param user 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void add(TbUser user);
		
	/**
     * description: 修改
     *
     * @param user 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void update(TbUser user);
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbUser
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	TbUser findOne(Long id);	
	
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
     * @param user 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	PageResult findPage(Integer page, Integer rows, TbUser user);

	/**
	 * description: 根据手机号发送验证码
	 *
	 * @param phone 手机号
	 * @return void
	 * @author AK
	 * @date  2018年08月25日 08:27:22
	 */
    void getSmsCode(String phone);

    /**
     * description: 根据手机号检测验证码
     *
     * @param phone 手机号
     * @param code 验证码
     * @return boolean
     * @author AK
     * @date  2018年08月25日 08:42:03
     */
	boolean checkCode(String phone, String code);
}
