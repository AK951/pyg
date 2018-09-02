package com.pyg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/22 9:00
 * @since 1.0.0
 */
public class MessageProducer {

    // 发布消息
    // 点对点模式
    @Test
    public void sendMessage() throws Exception {
        String brokerURL = "tcp://192.168.25.128:61616";
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queuq = session.createQueue("myQueue");
        javax.jms.MessageProducer producer = session.createProducer(queuq);
        TextMessage message = new ActiveMQTextMessage();
        message.setText("queue消息测试");
        producer.send(message);
        producer.close();
        session.close();
        connection.close();
    }

    // 发布消息
    // 发布订阅模式
    @Test
    public void sendMessageWithPs() throws Exception {
        String brokerURL = "tcp://192.168.25.128:61616";
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("myTopic");
        javax.jms.MessageProducer producer = session.createProducer(topic);
        TextMessage message = new ActiveMQTextMessage();
        message.setText("topic消息测试");
        producer.send(message);
        producer.close();
        session.close();
        connection.close();
    }

}