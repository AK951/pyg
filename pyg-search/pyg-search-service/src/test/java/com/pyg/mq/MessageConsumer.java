package com.pyg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/22 9:29
 * @since 1.0.0
 */
public class MessageConsumer {

    // 接收消息
    // 点对点模式
    @Test
    public void receiveMessage() throws Exception {
        String brokerURL = "tcp://192.168.25.128:61616";
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("myQueue");
        javax.jms.MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        System.out.println("接收到的消息:" + text);
                        System.out.println();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

    // 接收消息
    // 发布订阅模式
    @Test
    public void receiveMessageWithPs() throws Exception {
        String brokerURL = "tcp://192.168.25.128:61616";
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("myTopic");
        javax.jms.MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        System.out.println("接收到的消息:" + text);
                        System.out.println();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

}