package com.pyg.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.content.service.ContentService;
import com.pyg.pojo.TbContent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/17 14:44
 * @since 1.0.0
 */
@RestController
@RequestMapping("/ad")
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/findListByCategoryId/{categoryId}")
    public List<TbContent> findListByCategoryId(@PathVariable Long categoryId) {
        return contentService.findByCategoryId(categoryId);
    }
}