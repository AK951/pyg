package com.pyg.listener;

import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.*;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/22 11:08
 * @since 1.0.0
 */
public class IndexDeleteListener implements MessageListener {
    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                List<String> itemList = JSON.parseArray(text, String.class);
                solrTemplate.deleteById(itemList);
                solrTemplate.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}