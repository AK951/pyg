package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.BrandService;
import com.pyg.pojo.TbBrand;
import com.pyg.vo.InfoResult;
import com.pyg.vo.PageResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * @author AK
 * @date 2018/8/6 20:19
 * @since 1.0.0
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference(timeout=100000)
    private BrandService brandService;

    /**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbBrand>
     * @author AK
     * @date  2018年08月09日 10:18:22
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    /**
     * description: 分页返回全部列表
     *
     * @param page 当前页面
     * @param rows 每页记录数
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月06日 21:50:03
     */
    @RequestMapping("/findPage/{page}/{rows}")
    public PageResult findPage(@PathVariable Integer page, @PathVariable Integer rows) {
        return brandService.findPage(page, rows);
    }

    /**
     * description: 增加
     *
     * @param brand 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月06日 21:54:13
     */
    @RequestMapping("/add")
    public InfoResult add(@RequestBody TbBrand brand) {
        try {
            brandService.add(brand);
            return new InfoResult(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false, "增加失败");
        }
    }

    /**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbBrand
     * @author AK
     * @date  2018年08月06日 21:55:22
     */
    @RequestMapping("/findOne/{id}")
    public TbBrand findOne(@PathVariable Long id) {
        return brandService.findOne(id);
    }

    /**
     * description: 修改
     *
     * @param brand 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:22:38
     */
    @RequestMapping("/update")
    public InfoResult update(@RequestBody TbBrand brand) {
        try {
            brandService.update(brand);
            return new InfoResult(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false,"修改失败");
        }
    }

    /**
     * description: 批量删除
     *
     * @param ids 实体ids
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月07日 08:25:09
     */
    @RequestMapping("/delete/{ids}")
    public InfoResult delete(@PathVariable Long[] ids) {
        try {
            brandService.delete(ids);
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
     * @param brand 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月07日 19:56:59
     */
    @RequestMapping("/search/{page}/{rows}")
    public PageResult search(@PathVariable Integer page, @PathVariable Integer rows, @RequestBody TbBrand brand) {
        return brandService.findPage(page, rows, brand);
    }

    @RequestMapping("/findBrandList")
    public List<Map> findBrandList() {
        return brandService.selectOptionList();
    }
}