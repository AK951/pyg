package com.pyg.content.service.impl;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbContentMapper;
import com.pyg.pojo.TbContent;
import com.pyg.pojo.TbContentExample;
import com.pyg.pojo.TbContentExample.Criteria;
import com.pyg.content.service.ContentService;

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
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbContent>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
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
		Page<TbContent> pageInfo = (Page<TbContent>) contentMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param content 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(TbContent content) {
		contentMapper.insert(content);
		// 清空缓存
		redisTemplate.boundHashOps("contentList").delete(content.getCategoryId());
	}
	
	/**
     * description: 修改
     *
     * @param content 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(TbContent content){
		// 清空缓存
		Long categoryId = content.getCategoryId();
		redisTemplate.boundHashOps("contentList").delete(categoryId);
		contentMapper.updateByPrimaryKey(content);
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbContent
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public TbContent findOne(Long id){
		return contentMapper.selectByPrimaryKey(id);
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
			// 清空缓存
			Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();
			redisTemplate.boundHashOps("contentList").delete(categoryId);
			contentMapper.deleteByPrimaryKey(id);
		}		
	}
	
	/**
     * description: 查询+分页
     *     
     * @param page 当前页
     * @param rows 每页记录数
     * @param content 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public PageResult findPage(Integer page, Integer rows, TbContent content) {
		PageHelper.startPage(page, rows);
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		
		if(content != null) {			
						if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
	
		}
		
		Page<TbContent> pageInfo = (Page<TbContent>) contentMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
	 * description: 根据广告类型id查询列表
	 *
	 * @param categoryId 广告类型id
	 * @return java.util.List<com.pyg.pojo.TbContent>
	 * @author AK
	 * @date  2018年08月17日 14:34:00
	 */
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {
    	List<TbContent> contentList = (List<TbContent>) redisTemplate.boundHashOps("contentList").get("categoryId");
    	if(contentList == null) {
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			criteria.andStatusEqualTo("1");
			example.setOrderByClause("sort_order");
			contentList = contentMapper.selectByExample(example);
			redisTemplate.boundHashOps("contentList").put(categoryId, contentList);
		}
		return contentList;

    }

}
