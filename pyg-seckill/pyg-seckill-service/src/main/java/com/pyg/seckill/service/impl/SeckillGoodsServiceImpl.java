package com.pyg.seckill.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbSeckillGoodsMapper;
import com.pyg.pojo.TbSeckillGoods;
import com.pyg.pojo.TbSeckillGoodsExample;
import com.pyg.pojo.TbSeckillGoodsExample.Criteria;
import com.pyg.seckill.service.SeckillGoodsService;

import com.pyg.vo.PageResult;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbSeckillGoods>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbSeckillGoods> findAll() {
		return redisTemplate.boundHashOps(TbSeckillGoods.class.getName()).values();
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
		Page<TbSeckillGoods> pageInfo = (Page<TbSeckillGoods>) seckillGoodsMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param seckillGoods 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(TbSeckillGoods seckillGoods) {
		seckillGoodsMapper.insert(seckillGoods);		
	}
	
	/**
     * description: 修改
     *
     * @param seckillGoods 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(TbSeckillGoods seckillGoods){
		seckillGoodsMapper.updateByPrimaryKey(seckillGoods);
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbSeckillGoods
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public TbSeckillGoods findOne(Long id){
		return (TbSeckillGoods) redisTemplate.boundHashOps(TbSeckillGoods.class.getName()).get(id);
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
			seckillGoodsMapper.deleteByPrimaryKey(id);
		}		
	}
	
	/**
     * description: 查询+分页
     *     
     * @param page 当前页
     * @param rows 每页记录数
     * @param seckillGoods 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public PageResult findPage(Integer page, Integer rows, TbSeckillGoods seckillGoods) {
		PageHelper.startPage(page, rows);
		
		TbSeckillGoodsExample example = new TbSeckillGoodsExample();
		Criteria criteria = example.createCriteria();
		
		if(seckillGoods != null) {			
						if(seckillGoods.getTitle()!=null && seckillGoods.getTitle().length()>0){
				criteria.andTitleLike("%"+seckillGoods.getTitle()+"%");
			}
			if(seckillGoods.getSmallPic()!=null && seckillGoods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+seckillGoods.getSmallPic()+"%");
			}
			if(seckillGoods.getSellerId()!=null && seckillGoods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+seckillGoods.getSellerId()+"%");
			}
			if(seckillGoods.getStatus()!=null && seckillGoods.getStatus().length()>0){
				criteria.andStatusLike("%"+seckillGoods.getStatus()+"%");
			}
			if(seckillGoods.getIntroduction()!=null && seckillGoods.getIntroduction().length()>0){
				criteria.andIntroductionLike("%"+seckillGoods.getIntroduction()+"%");
			}
	
		}
		
		Page<TbSeckillGoods> pageInfo = (Page<TbSeckillGoods>) seckillGoodsMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}
	
}
