package com.pyg.user.service.impl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbAddressMapper;
import com.pyg.pojo.TbAddress;
import com.pyg.pojo.TbAddressExample;
import com.pyg.pojo.TbAddressExample.Criteria;
import com.pyg.user.service.AddressService;

import com.pyg.vo.PageResult;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private TbAddressMapper addressMapper;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbAddress>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbAddress> findAll() {
		return addressMapper.selectByExample(null);
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
		Page<TbAddress> pageInfo = (Page<TbAddress>) addressMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param address 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(TbAddress address) {
		address.setCreateDate(new Date());
		addressMapper.insert(address);
	}
	
	/**
     * description: 修改
     *
     * @param address 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(TbAddress address){
		addressMapper.updateByPrimaryKey(address);
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbAddress
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public TbAddress findOne(Long id){
		return addressMapper.selectByPrimaryKey(id);
	}

	/**
     * description: 批量删除
     *
     * @param id 实体id
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void delete(Long id) {
		addressMapper.deleteByPrimaryKey(id);
	}
	
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
	@Override
	public PageResult findPage(Integer page, Integer rows, TbAddress address) {
		PageHelper.startPage(page, rows);
		
		TbAddressExample example = new TbAddressExample();
		Criteria criteria = example.createCriteria();
		
		if(address != null) {			
						if(address.getUserId()!=null && address.getUserId().length()>0){
				criteria.andUserIdLike("%"+address.getUserId()+"%");
			}
			if(address.getProvinceId()!=null && address.getProvinceId().length()>0){
				criteria.andProvinceIdLike("%"+address.getProvinceId()+"%");
			}
			if(address.getCityId()!=null && address.getCityId().length()>0){
				criteria.andCityIdLike("%"+address.getCityId()+"%");
			}
			if(address.getTownId()!=null && address.getTownId().length()>0){
				criteria.andTownIdLike("%"+address.getTownId()+"%");
			}
			if(address.getMobile()!=null && address.getMobile().length()>0){
				criteria.andMobileLike("%"+address.getMobile()+"%");
			}
			if(address.getAddress()!=null && address.getAddress().length()>0){
				criteria.andAddressLike("%"+address.getAddress()+"%");
			}
			if(address.getContact()!=null && address.getContact().length()>0){
				criteria.andContactLike("%"+address.getContact()+"%");
			}
			if(address.getIsDefault()!=null && address.getIsDefault().length()>0){
				criteria.andIsDefaultLike("%"+address.getIsDefault()+"%");
			}
			if(address.getNotes()!=null && address.getNotes().length()>0){
				criteria.andNotesLike("%"+address.getNotes()+"%");
			}
			if(address.getAlias()!=null && address.getAlias().length()>0){
				criteria.andAliasLike("%"+address.getAlias()+"%");
			}
	
		}
		
		Page<TbAddress> pageInfo = (Page<TbAddress>) addressMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
	 * description: 根据用户id查询地址
	 *
	 * @param userId 用户id
	 * @return java.util.List<com.pyg.pojo.TbAddress>
	 * @author AK
	 * @date  2018年08月26日 20:20:55
	 */
    @Override
    public List<TbAddress> findListByUserId(String userId) {
    	TbAddressExample example = new TbAddressExample();
    	example.createCriteria().andUserIdEqualTo(userId);
        return addressMapper.selectByExample(example);
    }

	@Override
	public void setDefaultAddress(Long id, String userId) {
		TbAddressExample example = new TbAddressExample();
		example.createCriteria().andUserIdEqualTo(userId);
		// List<TbAddress> addressList = addressMapper.selectByExample(example);

		TbAddress tbAddress = new TbAddress();
		tbAddress.setIsDefault("0");
		addressMapper.updateByExampleSelective(tbAddress, example);

		TbAddress address = addressMapper.selectByPrimaryKey(id);
		address.setIsDefault("1");
		addressMapper.updateByPrimaryKey(address);
	}

}
