package com.pyg.fm.utils;

import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.mapper.TbItemCatMapper;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.*;
import com.pyg.util.FMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 初始化静态页面
 *
 * @author AK
 * @date 2018/8/22 20:36
 * @since 1.0.0
 */
@Component
public class PygHtmlUtils {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;

    public void genHtml() {
        try {
            TbGoodsExample goodsExample = new TbGoodsExample();
            TbGoodsExample.Criteria goodsCriteria = goodsExample.createCriteria();
            goodsCriteria.andAuditStatusEqualTo("1");
            goodsCriteria.andIsMarketableEqualTo("0");
            List<TbGoods> goodsList = goodsMapper.selectByExample(goodsExample);
            for (TbGoods tbGoods : goodsList) {
                TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(tbGoods.getId());
                Map<String, Object> map = new HashMap<>();
                map.put("goods", tbGoods);
                map.put("goodsDesc", goodsDesc);
                map.put("cat1", itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName());
                map.put("cat2", itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName());
                map.put("cat3", itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName());

                TbItemExample itemExample = new TbItemExample();
                itemExample.createCriteria().andGoodsIdEqualTo(tbGoods.getId());
                List<TbItem> itemList = itemMapper.selectByExample(itemExample);
                map.put("itemList", itemList);

                FMUtils fmUtils = new FMUtils();
                fmUtils.ouputFile("item.ftl", tbGoods.getId() + ".html", map);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/*.xml");
        PygHtmlUtils pygHtmlUtils = applicationContext.getBean(PygHtmlUtils.class);
        pygHtmlUtils.genHtml();
    }
}