package com.pyg.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbUserMapper;
import com.pyg.pojo.TbUser;
import com.pyg.pojo.TbUserExample;
import com.pyg.pojo.TbUserExample.Criteria;
import com.pyg.user.service.UserService;
import com.pyg.vo.PageResult;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbUser>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public List<TbUser> findAll() {
		return userMapper.selectByExample(null);
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
		Page<TbUser> pageInfo = (Page<TbUser>) userMapper.selectByExample(null);
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
     * description: 增加
     *
     * @param user 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void add(TbUser user) {
		String newPwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(newPwd);
		Date date = new Date();
		user.setCreated(date);
		user.setUpdated(date);
		userMapper.insert(user);
	}
	
	/**
     * description: 修改
     *
     * @param user 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public void update(TbUser user){
		userMapper.updateByPrimaryKey(user);
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbUser
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public TbUser findOne(Long id){
		return userMapper.selectByPrimaryKey(id);
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
			userMapper.deleteByPrimaryKey(id);
		}		
	}
	
	/**
     * description: 查询+分页
     *     
     * @param page 当前页
     * @param rows 每页记录数
     * @param user 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@Override
	public PageResult findPage(Integer page, Integer rows, TbUser user) {
		PageHelper.startPage(page, rows);
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		if(user != null) {			
						if(user.getUsername()!=null && user.getUsername().length()>0){
				criteria.andUsernameLike("%"+user.getUsername()+"%");
			}
			if(user.getPassword()!=null && user.getPassword().length()>0){
				criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(user.getPhone()!=null && user.getPhone().length()>0){
				criteria.andPhoneLike("%"+user.getPhone()+"%");
			}
			if(user.getEmail()!=null && user.getEmail().length()>0){
				criteria.andEmailLike("%"+user.getEmail()+"%");
			}
			if(user.getSourceType()!=null && user.getSourceType().length()>0){
				criteria.andSourceTypeLike("%"+user.getSourceType()+"%");
			}
			if(user.getNickName()!=null && user.getNickName().length()>0){
				criteria.andNickNameLike("%"+user.getNickName()+"%");
			}
			if(user.getName()!=null && user.getName().length()>0){
				criteria.andNameLike("%"+user.getName()+"%");
			}
			if(user.getStatus()!=null && user.getStatus().length()>0){
				criteria.andStatusLike("%"+user.getStatus()+"%");
			}
			if(user.getHeadPic()!=null && user.getHeadPic().length()>0){
				criteria.andHeadPicLike("%"+user.getHeadPic()+"%");
			}
			if(user.getQq()!=null && user.getQq().length()>0){
				criteria.andQqLike("%"+user.getQq()+"%");
			}
			if(user.getIsMobileCheck()!=null && user.getIsMobileCheck().length()>0){
				criteria.andIsMobileCheckLike("%"+user.getIsMobileCheck()+"%");
			}
			if(user.getIsEmailCheck()!=null && user.getIsEmailCheck().length()>0){
				criteria.andIsEmailCheckLike("%"+user.getIsEmailCheck()+"%");
			}
			if(user.getSex()!=null && user.getSex().length()>0){
				criteria.andSexLike("%"+user.getSex()+"%");
			}
	
		}
		
		Page<TbUser> pageInfo = (Page<TbUser>) userMapper.selectByExample(example);		
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Value("${sign_name}")
	private String sign_name;
	@Value("${template_code}")
	private String template_code;
	@Autowired
	private ActiveMQQueue queue;

	/**
	 * description: 根据手机号发送验证码
	 *
	 * @param phone 手机号
	 * @return void
	 * @author AK
	 * @date  2018年08月25日 08:27:22
	 */
    @Override
    public void getSmsCode(String phone) {
		Map<String, String> map = new HashMap<>();
		map.put("phone", phone);
		map.put("sign_name", sign_name);
		map.put("template_code", template_code);
		long code = (long) (Math.random()*1000000);
		redisTemplate.boundHashOps("smsCode").put(phone, code);
		redisTemplate.boundHashOps("smsCode").expire(5, TimeUnit.MINUTES);
		map.put("code", code + "");
		jmsTemplate.convertAndSend(queue, map);
    }

	/**
	 * description: 根据手机号检测验证码
	 *
	 * @param phone 手机号
	 * @param code 验证码
	 * @return boolean
	 * @author AK
	 * @date  2018年08月25日 08:42:03
	 */
	@Override
	public boolean checkCode(String phone, String code) {
		String smsCode = "";
		if(redisTemplate.boundHashOps("smsCode").get(phone) != null) {
			smsCode = (long) redisTemplate.boundHashOps("smsCode").get(phone) + "";
		}
		return (smsCode+"").equals(code);
	}

	@Override
	public TbUser loadNickName(String userName) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<TbUser> tbUsers = userMapper.selectByExample(example);
		if (tbUsers!=null){
			return tbUsers.get(0);
		}
		return null;
	}

	@Override
	public void saveNickName(TbUser tbUser) {
		userMapper.updateByPrimaryKeySelective(tbUser);
	}

}
