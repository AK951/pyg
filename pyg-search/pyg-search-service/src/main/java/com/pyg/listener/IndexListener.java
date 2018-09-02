package com.pyg.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pyg.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/22 11:08
 * @since 1.0.0
 */
public class IndexListener implements MessageListener {
    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                List<TbItem> itemList = JSON.parseArray(text, TbItem.class);
                solrTemplate.saveBeans(itemList);
                solrTemplate.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}