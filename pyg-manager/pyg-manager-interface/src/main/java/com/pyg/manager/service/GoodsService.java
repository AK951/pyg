package com.pyg.manager.service;
import java.util.List;
import com.pyg.pojo.TbGoods;

import com.pyg.pojo.TbItem;
import com.pyg.vo.Goods;
import com.pyg.vo.PageResult;

/**
 * Description: 服务层接口
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
public interface GoodsService {

	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.Goods>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	List<TbGoods> findAll();
		
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
     * @param goods 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void add(Goods goods);
		
	/**
     * description: 修改
     *
     * @param goods 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	void update(Goods goods);
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbGoods
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	Goods findOne(Long id);
	
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
     * @param goods 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	PageResult findPage(Integer page, Integer rows, TbGoods goods);

	/**
	 * description: 更新商品状态
	 *
	 * @param status 商品状态 0:未审核 1:审核通过 2:驳回 3:关闭
	 * @param ids 商品id
	 * @return void
	 * @author AK
	 * @date  2018年08月16日 08:59:04
	 */
	void updateStatus(String status, Long[] ids);

	/**
	 * description: 商品上下架
	 *
	 * @param status 商品状态 0:上架 1:下架
	 * @param ids 商品id
	 * @return void
	 * @author AK
	 * @date  2018年08月16日 08:59:04
	 */
    void isMarkertable(String status, Long[] ids);

    /**
     * description: 根据商品id查询sku集合
     *
     * @param ids 商品id
     * @return java.util.List<com.pyg.pojo.TbItem>
     * @author AK
     * @date  2018年08月22日 11:35:24
     */
    List<TbItem> findItemList(Long[] ids);

	/**
	 * description: 根据商品id查询sku的id
	 *
	 * @param ids 商品id
	 * @return java.util.List<java.lang.String>
	 * @author AK
	 * @date  2018年08月22日 16:02:20
	 */
    List<String> findItemIds(Long[] ids);
}
