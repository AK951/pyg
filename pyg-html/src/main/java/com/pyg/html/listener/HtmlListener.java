package com.pyg.html.listener;

import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.mapper.TbItemCatMapper;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbItemExample;
import com.pyg.util.FMUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/23 9:38
 * @since 1.0.0
 */
public class HtmlListener implements MessageListener {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Long[] ids = (Long[]) objectMessage.getObject();
                if(ids.length > 0) {
                    for (Long id : ids) {
                        Map<String, Object> map = new HashMap<>();
                        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
                        map.put("goods", tbGoods);
                        TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(tbGoods.getId());
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}