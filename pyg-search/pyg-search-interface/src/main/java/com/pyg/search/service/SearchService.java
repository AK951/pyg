package com.pyg.search.service;

import com.pyg.vo.PageResult;

import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/19 11:01
 * @since 1.0.0
 */
public interface SearchService {

    /**
     * description: 根据搜索条件查询商品
     *
     * @param searchMap 搜索条件
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author AK
     * @date  2018年08月19日 11:11:07
     */
    public Map<String, Object> search(Map searchMap);
}
