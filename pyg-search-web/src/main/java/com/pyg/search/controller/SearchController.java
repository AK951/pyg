package com.pyg.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.search.service.SearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/19 11:23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/itemsearch")
public class SearchController {

    @Reference(timeout=100000)
    private SearchService searchService;

    @RequestMapping("/search")
    public Map<String, Object> search(@RequestBody Map searchMap) {
        return searchService.search(searchMap);
    }

}