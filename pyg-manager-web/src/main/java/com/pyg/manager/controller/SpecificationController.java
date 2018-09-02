package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.SpecificationService;
import com.pyg.pojo.TbSpecification;
import com.pyg.vo.InfoResult;
import com.pyg.vo.PageResult;
import com.pyg.vo.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/7 20:19
 * @since 1.0.0
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference(timeout=100000)
    private SpecificationService specificationService;

    /**
     * description: 分页返回全部列表
     *
     * @param page 当前页
     * @param rows 每页记录数
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月07日 21:19:03
     */
    @RequestMapping("/findPage/{page}/{rows}")
    public PageResult findPage(@PathVariable Integer page, @PathVariable Integer rows) {
        return specificationService.findPage(page,rows);
    }

    /**
     * description: 增加
     *
     * @param specification 规格包装类
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月07日 21:22:28
     */
    @RequestMapping("/add")
    public InfoResult add(@RequestBody Specification specification) {
        try {
            specificationService.add(specification);
            return new InfoResult(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false, "增加失败");
        }
    }

    /**
     * description: 根据id返回实体
     *
     * @param id 实体格id
     * @return com.pyg.vo.Specification
     * @author AK
     * @date  2018年08月07日 21:25:26
     */
    @RequestMapping("/findOne/{id}")
    public Specification findOne(@PathVariable Long id) {
        return specificationService.findOne(id);
    }

    /**
     * description: 修改
     *
     * @param specification 规格包装类
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月08日 11:17:18
     */
    @RequestMapping("/update")
    public InfoResult update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return new InfoResult(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false, "修改失败");
        }
    }

    /**
     * description: 批量删除
     *
     * @param ids 实体id
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月08日 11:18:28
     */
    @RequestMapping("/delete/{ids}")
    public InfoResult delete(@PathVariable Long[] ids) {
        try {
            specificationService.delete(ids);
            return new InfoResult(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false, "删除失败");
        }
    }

    /**
     * description: 查询+分页
     *
     * @param page 当前页
     * @param rows 每页记录数
     * @param specification 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月08日 11:17:21
     */
    @RequestMapping("/search/{page}/{rows}")
    public PageResult search(@PathVariable Integer page, @PathVariable Integer rows, @RequestBody TbSpecification specification) {
        return specificationService.findPage(page, rows, specification);
    }

    /**
     * description: 规格下拉列表数据
     *
     * @return java.util.List<java.util.Map>
     * @author AK
     * @date  2018年08月09日 17:18:03
     */
    @RequestMapping("/findSpecList")
    public List<Map> findSpecList() {
        return specificationService.selectOptionList();
    }
}