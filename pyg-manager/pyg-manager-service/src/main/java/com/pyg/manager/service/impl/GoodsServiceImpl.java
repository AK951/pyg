package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.GoodsService;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.pojo.TbGoodsExample.Criteria;
import com.pyg.vo.Goods;
import com.pyg.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	@Autowired
	private TbBrandMapper brandMapper;
	@Autowired
	private TbSellerMapper sellerMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private TbItemMapper itemMapper;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.Goods>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbGoods> findAll() {
		// return goodsMapper.selectByExample(null);
		return null;
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
		Page<TbGoods> pageInfo = (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param goods 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(Goods goods) {
		// 插入goods表
		TbGoods tbGoods = goods.getGoods();
		goodsMapper.insertSelective(tbGoods);
		// 插入goodsDesc表
		TbGoodsDesc goodsDesc = goods.getGoodsDesc();
		goodsDesc.setGoodsId(tbGoods.getId());
		goodsDescMapper.insert(goodsDesc);
		// 插入item表
		String brandName = brandMapper.selectByPrimaryKey(tbGoods.getBrandId()).getName();
		String sellerName = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId()).getNickName();
		String categoryName = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();

		if("1".equals(tbGoods.getIsEnableSpec())) {
			List<TbItem> itemList = goods.getItemList();
			for (TbItem item : itemList) {
				String title = tbGoods.getGoodsName();
				Map<String, Object> specMap = JSON.parseObject(item.getSpec());
				for(String key:specMap.keySet()) {
					title += " " + specMap.get(key);
				}
				// 设置标题
				item.setTitle(title);
				setItemValue(goods, item, brandName, sellerName, categoryName);
				itemMapper.insert(item);
			}
		} else {
			TbItem item = new TbItem();
			item.setTitle(tbGoods.getGoodsName());
			item.setPrice(tbGoods.getPrice());
			item.setStatus("1");
			item.setIsDefault("1");//是否默认
			item.setNum(99999);//库存数量
			item.setSpec("{}");
			setItemValue(goods, item, brandName, sellerName, categoryName);
			itemMapper.insert(item);
		}

	}

	private void setItemValue(Goods goods, TbItem item, String brandName, String sellerName, String categoryName) {
		item.setSellerId(goods.getGoods().getSellerId());
		item.setCategoryid(goods.getGoods().getCategory3Id());
		item.setGoodsId(goods.getGoods().getId());
		// 设置卖点
		item.setSellPoint(goods.getGoods().getCaption());
		item.setCreateTime(new Date());
		item.setUpdateTime(new Date());

		item.setBrand(brandName);
		item.setSeller(sellerName);
		item.setCategory(categoryName);

		List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
		if(imageList.size() > 0) {
			item.setImage((String) imageList.get(0).get("url"));
		}
	}
	
	/**
     * description: 修改
     *
     * @param goods 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(Goods goods){
		goodsMapper.updateByPrimaryKey(goods.getGoods());
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
		TbItemExample example = new TbItemExample();
		example.createCriteria().andGoodsIdEqualTo(goods.getGoods().getId());
		itemMapper.deleteByExample(example);
		// 插入item表
		TbGoods tbGoods = goods.getGoods();
		String brandName = brandMapper.selectByPrimaryKey(tbGoods.getBrandId()).getName();
		String sellerName = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId()).getNickName();
		String categoryName = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();

		if("1".equals(tbGoods.getIsEnableSpec())) {
			List<TbItem> itemList = goods.getItemList();
			for (TbItem item : itemList) {
				String title = tbGoods.getGoodsName();
				Map<String, Object> specMap = JSON.parseObject(item.getSpec());
				for(String key:specMap.keySet()) {
					title += " " + specMap.get(key);
				}
				// 设置标题
				item.setTitle(title);
				setItemValue(goods, item, brandName, sellerName, categoryName);
				itemMapper.insert(item);
			}
		} else {
			TbItem item = new TbItem();
			item.setTitle(tbGoods.getGoodsName());
			item.setPrice(tbGoods.getPrice());
			item.setStatus("1");
			item.setIsDefault("1");//是否默认
			item.setNum(99999);//库存数量
			item.setSpec("{}");
			setItemValue(goods, item, brandName, sellerName, categoryName);
			itemMapper.insert(item);
		}
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.Goods
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public Goods findOne(Long id){
		Goods goods = new Goods();
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goods.setGoods(tbGoods);
		goods.setGoodsDesc(tbGoodsDesc);
		TbItemExample example = new TbItemExample();
		example.createCriteria().andGoodsIdEqualTo(id);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		goods.setItemList(itemList);
		return goods;
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
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsDelete("1");
			tbGoods.setAuditStatus("3");
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}
	
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
	@Override
	public PageResult findPage(Integer page, Integer rows, TbGoods goods) {
		PageHelper.startPage(page, rows);
		
		TbGoodsExample example = new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		
		if(goods != null) {
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> pageInfo = (Page<TbGoods>) goodsMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
	 * description: 更新商品状态
	 *
	 * @param status 商品状态 0:未审核 1:审核通过 2:驳回 3:关闭
	 * @param ids 商品id
	 * @return void
	 * @author AK
	 * @date  2018年08月16日 08:59:04
	 */
    @Override
    public void updateStatus(String status, Long[] ids) {
		for (Long id : ids) {
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
    }

	/**
	 * description: 商品上下架
	 *
	 * @param status 商品状态 0:上架 1:下架
	 * @param ids 商品id
	 * @return void
	 * @author AK
	 * @date  2018年08月16日 08:59:04
	 */
	@Override
	public void isMarkertable(String status, Long[] ids) {
		for (Long id : ids) {
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsMarketable(status);
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

	/**
	 * description: 根据商品id查询sku集合
	 *
	 * @param ids 商品id
	 * @return java.util.List<com.pyg.pojo.TbItem>
	 * @author AK
	 * @date  2018年08月22日 11:35:24
	 */
    @Override
    public List<TbItem> findItemList(Long[] ids) {
    	List<TbItem> itemList = new ArrayList<>();
		for (Long id : ids) {
			TbItemExample example = new TbItemExample();
			example.createCriteria().andGoodsIdEqualTo(id);
			List<TbItem> tbItems = itemMapper.selectByExample(example);
			itemList.addAll(tbItems);
		}
    	return itemList;
    }

    /**
     * description: 根据商品id查询sku的id
     *
     * @param ids 商品id
     * @return java.util.List<java.lang.String>
     * @author AK
     * @date  2018年08月22日 16:02:20
     */
    @Override
    public List<String> findItemIds(Long[] ids) {
		List<String> itemList = new ArrayList<>();
		for (Long id : ids) {
			TbItemExample example = new TbItemExample();
			example.createCriteria().andGoodsIdEqualTo(id);
			List<TbItem> tbItems = itemMapper.selectByExample(example);
			for (TbItem tbItem : tbItems) {
				itemList.add(tbItem.getId()+"");
			}
		}
    	return itemList;
    }

}
