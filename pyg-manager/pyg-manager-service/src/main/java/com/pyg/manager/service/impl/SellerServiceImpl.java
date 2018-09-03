package com.pyg.manager.service.impl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbSellerMapper;
import com.pyg.pojo.TbSeller;
import com.pyg.pojo.TbSellerExample;
import com.pyg.pojo.TbSellerExample.Criteria;
import com.pyg.manager.service.SellerService;

import com.pyg.vo.PageResult;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbSeller>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbSeller> findAll() {
		return sellerMapper.selectByExample(null);
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
		Page<TbSeller> pageInfo = (Page<TbSeller>) sellerMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param seller 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(TbSeller seller) {
		// 设置状态为未审核
		seller.setStatus("0");
		// 设置注册时间
		seller.setCreateTime(new Date());
		// 增加商家
		sellerMapper.insert(seller);
	}
	
	/**
     * description: 修改
     *
     * @param seller 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(TbSeller seller){
		sellerMapper.updateByPrimaryKey(seller);
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbSeller
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public TbSeller findOne(String id){
		return sellerMapper.selectByPrimaryKey(id);
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
	public void delete(String[] ids) {
		for(String id:ids){
			sellerMapper.deleteByPrimaryKey(id);
		}		
	}
	
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
	@Override
	public PageResult findPage(Integer page, Integer rows, TbSeller seller) {
		PageHelper.startPage(page, rows);
		
		TbSellerExample example = new TbSellerExample();
		Criteria criteria = example.createCriteria();
		
		if(seller != null) {			
						if(seller.getSellerId()!=null && seller.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+seller.getSellerId()+"%");
			}
			if(seller.getName()!=null && seller.getName().length()>0){
				criteria.andNameLike("%"+seller.getName()+"%");
			}
			if(seller.getNickName()!=null && seller.getNickName().length()>0){
				criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}
			if(seller.getPassword()!=null && seller.getPassword().length()>0){
				criteria.andPasswordLike("%"+seller.getPassword()+"%");
			}
			if(seller.getEmail()!=null && seller.getEmail().length()>0){
				criteria.andEmailLike("%"+seller.getEmail()+"%");
			}
			if(seller.getMobile()!=null && seller.getMobile().length()>0){
				criteria.andMobileLike("%"+seller.getMobile()+"%");
			}
			if(seller.getTelephone()!=null && seller.getTelephone().length()>0){
				criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
			}
			if(seller.getStatus()!=null && seller.getStatus().length()>0){
				criteria.andStatusLike("%"+seller.getStatus()+"%");
			}
			if(seller.getAddressDetail()!=null && seller.getAddressDetail().length()>0){
				criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
			}
			if(seller.getLinkmanName()!=null && seller.getLinkmanName().length()>0){
				criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
			}
			if(seller.getLinkmanQq()!=null && seller.getLinkmanQq().length()>0){
				criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
			}
			if(seller.getLinkmanMobile()!=null && seller.getLinkmanMobile().length()>0){
				criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
			}
			if(seller.getLinkmanEmail()!=null && seller.getLinkmanEmail().length()>0){
				criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
			}
			if(seller.getLicenseNumber()!=null && seller.getLicenseNumber().length()>0){
				criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
			}
			if(seller.getTaxNumber()!=null && seller.getTaxNumber().length()>0){
				criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
			}
			if(seller.getOrgNumber()!=null && seller.getOrgNumber().length()>0){
				criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
			}
			if(seller.getLogoPic()!=null && seller.getLogoPic().length()>0){
				criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
			}
			if(seller.getBrief()!=null && seller.getBrief().length()>0){
				criteria.andBriefLike("%"+seller.getBrief()+"%");
			}
			if(seller.getLegalPerson()!=null && seller.getLegalPerson().length()>0){
				criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
			}
			if(seller.getLegalPersonCardId()!=null && seller.getLegalPersonCardId().length()>0){
				criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
			}
			if(seller.getBankUser()!=null && seller.getBankUser().length()>0){
				criteria.andBankUserLike("%"+seller.getBankUser()+"%");
			}
			if(seller.getBankName()!=null && seller.getBankName().length()>0){
				criteria.andBankNameLike("%"+seller.getBankName()+"%");
			}
	
		}
		
		Page<TbSeller> pageInfo = (Page<TbSeller>) sellerMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
	 * description: 更新商家状态
	 *
	 * @param sellerId 商家id
	 * @param status 状态码 0:未审核 1:审核通过 2:审核未通过 3:关闭商家
	 * @return void
	 * @author AK
	 * @date  2018年08月10日 21:06:38
	 */
	@Override
	public void updateStatus(String sellerId, String status) {
		TbSeller seller = sellerMapper.selectByPrimaryKey(sellerId);
		seller.setStatus(status);
		sellerMapper.updateByPrimaryKey(seller);
	}

	@Override
	public TbSeller findSellerForId(String name) {
		TbSeller seller = sellerMapper.selectByPrimaryKey(name);
		return seller;

	}

	@Override
	public TbSeller selectPsw(String name) {
		TbSeller seller = sellerMapper.selectByPrimaryKey(name);
		return seller;
	}

	@Override
	public void updatePsw(TbSeller seller) {
		sellerMapper.updateByPrimaryKeySelective(seller);
	}

}
