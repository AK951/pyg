package com.pyg.user.service;
import java.util.List;
import com.pyg.pojo.TbAddress;

import com.pyg.vo.PageResult;

/**
 * Description: 服务层接口
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
public interface AddressService {

	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbAddress>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	List<TbAddress> findAll();
		
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
     * @param address 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void add(TbAddress address);
		
	/**
     * description: 修改
     *
     * @param address 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void update(TbAddress address);
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbAddress
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	TbAddress findOne(Long id);	
	
	/**
     * description: 删除
     *
     * @param id 实体id
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void delete(Long id);

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
	PageResult findPage(Integer page, Integer rows, TbAddress address);

	/**
	 * description: 根据用户id查询地址
	 *
	 * @param userId 用户id
	 * @return java.util.List<com.pyg.pojo.TbAddress>
	 * @author AK
	 * @date  2018年08月26日 20:20:55
	 */
	public List<TbAddress> findListByUserId(String userId );



	void setDefaultAddress(Long id, String userId);
}
