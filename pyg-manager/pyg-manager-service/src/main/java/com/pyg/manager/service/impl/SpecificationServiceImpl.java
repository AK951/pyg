package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.SpecificationService;
import com.pyg.mapper.TbSpecificationMapper;
import com.pyg.mapper.TbSpecificationOptionMapper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationExample;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.vo.PageResult;
import com.pyg.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Description: 服务实现层
 *
 * @author AK
 * @date 2018/8/7 17:25
 * @since 1.0.0
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * description: 分页查询规格
     *
     * @param page 当前页
     * @param rows 每页记录数
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月07日 17:24:45
     */
    @Override
    public PageResult findPage(Integer page, Integer rows) {
        PageHelper.startPage(page ,rows);
        Page<TbSpecification> pageInfo = (Page<TbSpecification>) specificationMapper.selectByExample(null);
        return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
    }

    /**
     * description: 增加规格
     *
     * @param specification 规格包装类
     * @return void
     * @author AK
     * @date  2018年08月07日 20:41:52
     */
    @Override
    public void add(Specification specification) {
        // 增加规格
        TbSpecification spec = specification.getSpecification();
        specificationMapper.insert(spec);
        // 增加规格选项
        List<TbSpecificationOption> list = specification.getSpecificationOptionList();
        if(list != null && list.size() > 0) {
            for (TbSpecificationOption option : list) {
                option.setSpecId(spec.getId());
                specificationOptionMapper.insert(option);
            }
        }
    }

    /**
     * description: 根据id查询规格
     *
     * @param id 规格id
     * @return com.pyg.vo.Specification
     * @author AK
     * @date  2018年08月07日 20:42:09
     */
    @Override
    public Specification findOne(Long id) {
        // 查询规格
        TbSpecification spec = specificationMapper.selectByPrimaryKey(id);
        // 查询规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(spec.getId());
        // 封装规格包装类
        List<TbSpecificationOption> list = specificationOptionMapper.selectByExample(example);
        Specification specification = new Specification();
        specification.setSpecification(spec);
        specification.setSpecificationOptionList(list);
        return specification;
    }

    /**
     * description: 根据修改后的规格更新规格
     *
     * @param specification 规格包装类
     * @return void
     * @author AK
     * @date  2018年08月07日 20:42:23
     */
    @Override
    public void update(Specification specification) {
        // 更新规格
        TbSpecification spec = specification.getSpecification();
        specificationMapper.updateByPrimaryKey(spec);
        // 更新规格选项
        // 先删除原有的规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(spec.getId());
        specificationOptionMapper.deleteByExample(example);
        // 在添加更新后的规格选项
        List<TbSpecificationOption> list = specification.getSpecificationOptionList();
        if(list != null && list.size() > 0) {
            for (TbSpecificationOption option : list) {
                option.setSpecId(spec.getId());
                specificationOptionMapper.insert(option);
            }
        }
    }

    /**
     * description: 条件分页查询规格信息
     *
     * @param page 当前页
     * @param rows 每页记录数
     * @param specification 规格包装类
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月07日 20:43:02
     */
    @Override
    public PageResult findPage(Integer page, Integer rows, TbSpecification specification) {
        // 判断查询条件
        TbSpecificationExample example = null;
        if(!StringUtils.isEmpty(specification.getSpecName())) {
            example = new TbSpecificationExample();
            example.createCriteria().andSpecNameLike(specification.getSpecName());
        }
        // 分页查询
        PageHelper.startPage(page ,rows);
        Page<TbSpecification> pageInfo = (Page<TbSpecification>) specificationMapper.selectByExample(example);
        return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
    }

    /**
     * description: 根据id批量删除规格
     *
     * @param ids 规格id数组
     * @return void
     * @author AK
     * @date  2018年08月07日 21:10:14
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            // 根据id删除规格
            specificationMapper.deleteByPrimaryKey(id);
            // 根据规格id删除规格选项
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            example.createCriteria().andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }

    /**
     * description: 规格下拉框数据
     *
     * @return java.util.List<java.util.Map>
     * @author AK
     * @date  2018年08月09日 17:14:06
     */
    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}