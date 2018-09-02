package com.pyg.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbContent;
import com.pyg.content.service.ContentService;

import com.pyg.vo.PageResult;
import com.pyg.vo.InfoResult;
 
/**
 * Description: controller
 * @author AK
 * @date 2018/8/9 10:00
 * @since 1.0.0
 */
@RestController
@RequestMapping("/content")
public class ContentController {

	@Reference(timeout=100000)
	private ContentService contentService;
	
	/**
     * description: 返回全部列表
     *
     * @return java.util.List<com.pyg.pojo.TbContent>
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findAll")
	public List<TbContent> findAll(){			
		return contentService.findAll();
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
	@RequestMapping("/findPage/{page}/{rows}")
	public PageResult findPage(@PathVariable Integer page, @PathVariable Integer rows){
		return contentService.findPage(page, rows);
	}
	
	/**
     * description: 增加
     *
     * @param content 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/add")
	public InfoResult add(@RequestBody TbContent content){
		try {
			contentService.add(content);
			return new InfoResult(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "增加失败");
		}
	}
	
	/**
     * description: 修改
     *
     * @param content 实体
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/update")
	public InfoResult update(@RequestBody TbContent content){
		try {
			contentService.update(content);
			return new InfoResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new InfoResult(false, "修改失败");
		}
	}	
	
	/**
     * description: 根据id返回实体
     *
     * @param id 实体id
     * @return com.pyg.pojo.TbContent
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/findOne/{id}")
	public TbContent findOne(@PathVariable Long id){
		return contentService.findOne(id);		
	}
	
	/**
     * description: 批量删除
     *
     * @param ids 实体ids
     * @return com.pyg.vo.InfoResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/delete/{ids}")
	public InfoResult delete(@PathVariable Long[] ids){
		try {
			contentService.delete(ids);
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
     * @param content 查询条件
     * @return com.pyg.vo.PageResult
     * @author AK
     * @date  2018年08月09日 10:00:00
     */
	@RequestMapping("/search/{page}/{rows}")
	public PageResult search(@PathVariable Integer page, @PathVariable Integer rows, @RequestBody TbContent content){
		return contentService.findPage(page, rows, content);		
	}
	
}
