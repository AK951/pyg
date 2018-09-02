package com.pyg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/22 9:00
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring/spring-mq.xml")
public class SpringProducer {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ActiveMQQueue queue;
    @Autowired
    private ActiveMQTopic topic;

    // 点对点模式
    @Test
    public void sendMessage() throws Exception {
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring整合测试2");
            }
        });
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