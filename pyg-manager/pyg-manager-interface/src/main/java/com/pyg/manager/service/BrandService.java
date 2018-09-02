package com.pyg.manager.service;

import com.pyg.pojo.TbBrand;
import com.pyg.vo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * @author AK
 * @date 2018/8/6 20:43
 * @since 1.0.0
 */
public interface BrandService {

    /**
     * description: 查询所有品牌数据
     *
     * @return java.util.List<com.pyg.pojo.TbBrand>
     * @author AK
     * @date  2018年08月06日 20:43:41
     */
    List<TbBrand> findAll();

    /**
     * description: 分页查询品牌列表
     *
     * @param page 当前页面
     * @param rows 每页记录数
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月06日 21:32:38
     */
    PageResult findPage(Integer page, Integer rows);

    /**
     * description: 增加品牌
     *
     * @param brand 品牌
     * @return void
     * @author AK
     * @date  2018年08月06日 21:21:16
     */
    void add(TbBrand brand);

    /**
     * description: 根据id查询品牌
     *
     * @param id 品牌id
     * @return com.pyg.pojo.TbBrand
     * @author AK
     * @date  2018年08月06日 21:22:07
     */
    TbBrand findOne(Long id);

    /**
     * description: 更新品牌
     *
     * @param brand 品牌
     * @return void
     * @author AK
     * @date  2018年08月06日 21:27:23
     */
    void update(TbBrand brand);

    /**
     * description: 根据id批量删除品牌
     *
     * @param ids 品牌id数组
     * @return void
     * @author AK
     * @date  2018年08月06日 21:25:08
     */
    void delete(Long[] ids);

    /**
     * description: 条件查询并分页
     *
     * @param page 当前页面
     * @param rows 每页记录数
     * @param brand 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月07日 10:51:25
     */
    PageResult findPage(Integer page, Integer rows, TbBrand brand);

    /**
     * description: 品牌下拉框数据
     *
     * @return java.util.List<java.util.Map>
     * @author AK
     * @date  2018年08月09日 15:37:48
     */
    List<Map> selectOptionList();
}