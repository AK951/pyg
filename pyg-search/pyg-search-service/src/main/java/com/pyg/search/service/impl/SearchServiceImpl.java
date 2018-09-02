package com.pyg.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/19 11:04
 * @since 1.0.0
 */
@Service(timeout=3000)
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * description: 根据搜索条件查询商品
     *
     * @param searchMap 搜索条件
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author AK
     * @date  2018年08月19日 11:11:34
     */
    @Override
    public Map<String, Object> search(Map searchMap) {
        // 创建封装条件参数的query对象
        HighlightQuery query = new SimpleHighlightQuery();

        // 1,主查询条件 （关键词搜索条件）
        // 获取参数值
        String keywords = (String) searchMap.get("keywords");
        // 初始化一个条件对象
        Criteria criteria = null;
        // 判断条件参数是否为空
        if (!StringUtils.isEmpty(keywords)) {
            // 创建criteria对象
            criteria = new Criteria("item_keywords").is(keywords);
        } else {
            // 查询所有
            criteria = new Criteria().expression("*:*");
        }
        query.addCriteria(criteria);

        // 2,分类查询参数
        String category = (String) searchMap.get("category");
        if(!StringUtils.isEmpty(category)) {
            Criteria criteria1 = new Criteria("item_category").is(category);
            FilterQuery filterQuery = new SimpleFilterQuery(criteria1);
            query.addFilterQuery(filterQuery);
        }

        // 3，品牌参数
        String brand = (String) searchMap.get("brand");
        if(!StringUtils.isEmpty(brand)) {
            Criteria criteria1 = new Criteria("item_brand").is(brand);
            FilterQuery filterQuery = new SimpleFilterQuery(criteria1);
            query.addFilterQuery(filterQuery);
        }

        // 4,规格属性参数
        // 获取规格属性参数 spec = {"网络制式"："","内存"：“”}
        Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
        if(searchMap != null) {
            for (String key : specMap.keySet()) {
                Criteria criteria1 = new Criteria("item_spec_" + key).is(specMap.get(key));
                FilterQuery filterQuery = new SimpleFilterQuery(criteria1);
                query.addFilterQuery(filterQuery);
            }
        }

        // 5,价格参数
        String price = (String) searchMap.get("price");
        if(!StringUtils.isEmpty(price)) {
            String[] split = price.split("-");
            if(!split[0].equals("0")) {
                Criteria criteria1 = new Criteria("item_price").greaterThanEqual(split[0]);
                FilterQuery filterQuery = new SimpleFilterQuery(criteria1);
                query.addFilterQuery(filterQuery);
            }
            if(!split[1].equals("*")) {
                Criteria criteria1 = new Criteria("item_price").lessThanEqual(split[1]);
                FilterQuery filterQuery = new SimpleFilterQuery(criteria1);
                query.addFilterQuery(filterQuery);
            }
        }

        // 6,排序
        String sortField = (String) searchMap.get("sortField");
        String sortValue = (String) searchMap.get("sort");
        if(!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortValue)) {
            if(sortValue.equals("ASC")) {
                Sort sort = new Sort(Sort.Direction.ASC, "item_" + sortField);
                query.addSort(sort);
            }
            if(sortValue.equals("DESC")) {
                Sort sort = new Sort(Sort.Direction.DESC, "item_" + sortField);
                query.addSort(sort);
            }
        }


        // 7,分页
        Integer pageNo= (Integer) searchMap.get("pageNo");//提取页码
        if(pageNo==null){
            pageNo=1;//默认第一页
        }
        Integer pageSize=(Integer) searchMap.get("pageSize");//每页记录数
        if(pageSize==null){
            pageSize=20;//默认20
        }
        query.setOffset((pageNo-1)*pageSize);//从第几条记录查询
        query.setRows(pageSize);

        // 8,高亮
        //创建高亮对象
        HighlightOptions hOptions = new HighlightOptions();
        //添加高亮字段
        hOptions.addField("item_title");
        //设置高亮前缀
        hOptions.setSimplePrefix("<font color='red'>");
        hOptions.setSimplePostfix("</font>");
        //把高亮添加到查询对象中
        query.setHighlightOptions(hOptions);

        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
        // 获取总记录数据
        long totalElements = page.getTotalElements();
        //获取总页码
        int totalPages = page.getTotalPages();
        // 获取总记录
        List<TbItem> list = page.getContent();

        //循环记录，获取高亮字段
        for (TbItem tbItem : list) {
            //获取高亮
            List<HighlightEntry.Highlight> highlights = page.getHighlights(tbItem);
            //判断高亮是否存在
            if(highlights!=null && highlights.size()>0) {
                //获取高亮字段
                String highLightTitle = highlights.get(0).getSnipplets().get(0);
                //把高亮字段添加到对象
                tbItem.setTitle(highLightTitle);
            }

        }

        Map<String,Object> map=new HashMap<>();
        map.put("total", totalElements);
        map.put("totalPages", totalPages);
        map.put("rows", page.getContent());
        return map;
    }
}