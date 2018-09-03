package com.pyg.manager.service;
import java.util.List;
import com.pyg.pojo.TbSeller;

import com.pyg.vo.PageResult;

/**
 * Description: 服务层接口
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
public interface SellerService {

	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbSeller>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	List<TbSeller> findAll();
		
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
     * @param seller 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void add(TbSeller seller);
		
	/**
     * description: 修改
     *
     * @param seller 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void update(TbSeller seller);
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbSeller
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	TbSeller findOne(String id);
	
	/**
     * description: 批量删除
     *
     * @param ids 实体ids
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void delete(String[] ids);

	/**
     * description: 查询+分页
     *     
     * @param page 当前页
     * @param rows 每页记录数
     * @param seller 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	PageResult findPage(Integer page, Integer rows, TbSeller seller);

	/**
	 * description: 更新商家状态
	 *
	 * @param sellerId 商家id
	 * @param status 状态码 0:未审核 1:审核通过 2:审核未通过 3:关闭商家
	 * @return void
	 * @author AK
	 * @date  2018年08月10日 21:06:38
	 */
	void updateStatus(String sellerId, String status);

    TbSeller findSellerForId(String name);

    TbSeller selectPsw(String name);

	void updatePsw(TbSeller seller);
}
