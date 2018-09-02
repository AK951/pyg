package com.pyg.manager.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbItemExample;
import com.pyg.pojo.TbItemExample.Criteria;
import com.pyg.manager.service.ItemService;

import com.pyg.vo.PageResult;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbItem>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbItem> findAll() {
		return itemMapper.selectByExample(null);
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
		Page<TbItem> pageInfo = (Page<TbItem>) itemMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param item 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(TbItem item) {
		itemMapper.insert(item);		
	}
	
	/**
     * description: 修改
     *
     * @param item 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(TbItem item){
		itemMapper.updateByPrimaryKey(item);
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbItem
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public TbItem findOne(Long id){
		return itemMapper.selectByPrimaryKey(id);
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
			itemMapper.deleteByPrimaryKey(id);
		}		
	}
	
	/**
     * description: 查询+分页
     *     
     * @param page 当前页
     * @param rows 每页记录数
     * @param item 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public PageResult findPage(Integer page, Integer rows, TbItem item) {
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		
		if(item != null) {			
						if(item.getTitle()!=null && item.getTitle().length()>0){
				criteria.andTitleLike("%"+item.getTitle()+"%");
			}
			if(item.getSellPoint()!=null && item.getSellPoint().length()>0){
				criteria.andSellPointLike("%"+item.getSellPoint()+"%");
			}
			if(item.getBarcode()!=null && item.getBarcode().length()>0){
				criteria.andBarcodeLike("%"+item.getBarcode()+"%");
			}
			if(item.getImage()!=null && item.getImage().length()>0){
				criteria.andImageLike("%"+item.getImage()+"%");
			}
			if(item.getStatus()!=null && item.getStatus().length()>0){
				criteria.andStatusLike("%"+item.getStatus()+"%");
			}
			if(item.getItemSn()!=null && item.getItemSn().length()>0){
				criteria.andItemSnLike("%"+item.getItemSn()+"%");
			}
			if(item.getIsDefault()!=null && item.getIsDefault().length()>0){
				criteria.andIsDefaultLike("%"+item.getIsDefault()+"%");
			}
			if(item.getSellerId()!=null && item.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+item.getSellerId()+"%");
			}
			if(item.getCartThumbnail()!=null && item.getCartThumbnail().length()>0){
				criteria.andCartThumbnailLike("%"+item.getCartThumbnail()+"%");
			}
			if(item.getCategory()!=null && item.getCategory().length()>0){
				criteria.andCategoryLike("%"+item.getCategory()+"%");
			}
			if(item.getBrand()!=null && item.getBrand().length()>0){
				criteria.andBrandLike("%"+item.getBrand()+"%");
			}
			if(item.getSpec()!=null && item.getSpec().length()>0){
				criteria.andSpecLike("%"+item.getSpec()+"%");
			}
			if(item.getSeller()!=null && item.getSeller().length()>0){
				criteria.andSellerLike("%"+item.getSeller()+"%");
			}
	
		}
		
		Page<TbItem> pageInfo = (Page<TbItem>) itemMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}
	
}
