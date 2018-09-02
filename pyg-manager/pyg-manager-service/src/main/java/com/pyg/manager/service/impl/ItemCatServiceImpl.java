package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.ItemCatService;
import com.pyg.mapper.TbItemCatMapper;
import com.pyg.pojo.TbItemCat;
import com.pyg.pojo.TbItemCatExample;
import com.pyg.pojo.TbItemCatExample.Criteria;
import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbItemCat>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
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
	@Override
	public PageResult findPage(int page, int rows) {
		PageHelper.startPage(page, rows);		
		Page<TbItemCat> pageInfo = (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param itemCat 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);		
	}
	
	/**
     * description: 修改
     *
     * @param itemCat 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKey(itemCat);
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbItemCat
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public TbItemCat findOne(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
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
			// 检查当前实体下面是否有子节点,有则不能删除
			int num = findByParentId(id).size();
			if(num > 0) {
				throw new RuntimeException("不能删除有字节点的分类");
			}
			itemCatMapper.deleteByPrimaryKey(id);
		}		
	}
	
	/**
     * description: 查询+分页
     *     
     * @param page 当前页
     * @param rows 每页记录数
     * @param itemCat 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public PageResult findPage(Integer page, Integer rows, TbItemCat itemCat) {
		PageHelper.startPage(page, rows);
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		if(itemCat != null) {			
						if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
		
		Page<TbItemCat> pageInfo = (Page<TbItemCat>) itemCatMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
	 * description: 根据父id返回实体
	 *
	 * @param parentId 父id
	 * @return java.util.List<com.pyg.pojo.TbItemCat>
	 * @author AK
	 * @date  2018年08月09日 20:03:10
	 */
    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
    	TbItemCatExample example = new TbItemCatExample();
    	example.createCriteria().andParentIdEqualTo(parentId);
        return itemCatMapper.selectByExample(example);
    }

}
