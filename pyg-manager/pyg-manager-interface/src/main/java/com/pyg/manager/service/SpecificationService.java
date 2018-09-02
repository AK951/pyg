package com.pyg.manager.service;

import com.pyg.pojo.TbSpecification;
import com.pyg.vo.PageResult;
import com.pyg.vo.Specification;

import java.util.List;
import java.util.Map;

/**
 * Description: 服务层接口
 *
 * @author AK
 * @date 2018/8/7 17:23
 * @since 1.0.0
 */
public interface SpecificationService {

    /**
     * description: 分页查询规格
     *
     * @param page 当前页
     * @param rows 每页记录数
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月07日 17:24:45
     */
    PageResult findPage(Integer page, Integer rows);

    /**
     * description: 增加规格
     *
     * @param specification 规格包装类
     * @return void
     * @author AK
     * @date  2018年08月07日 20:41:52
     */
    void add(Specification specification);

    /**
     * description: 根据id查询规格
     *
     * @param id 规格id
     * @return com.pyg.vo.Specification
     * @author AK
     * @date  2018年08月07日 20:42:09
     */
    Specification findOne(Long id);

    /**
     * description: 根据修改后的规格更新规格
     *
     * @param specification 规格包装类
     * @return void
     * @author AK
     * @date  2018年08月07日 20:42:23
     */
    void update(Specification specification);

    /**
     * description: 根据id批量删除规格
     *
     * @param ids 规格id数组
     * @return void
     * @author AK
     * @date  2018年08月07日 21:10:14
     */
    void delete(Long[] ids);

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
    PageResult findPage(Integer page, Integer rows, TbSpecification specification);

    /**
     * description: 规格下拉框数据
     *
     * @return java.util.List<java.util.Map>
     * @author AK
     * @date  2018年08月09日 17:14:06
     */
    List<Map> selectOptionList();
}
