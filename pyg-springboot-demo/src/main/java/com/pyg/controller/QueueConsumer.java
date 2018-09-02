package com.pyg.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/24 22:30
 * @since 1.0.0
 */
@Component
public class QueueConsumer {

    @JmsListener(destination = "myQueue")
    public void readMessage(String text) {
        System.out.println("接收到的消息:" + text);
    }

    @JmsListener(destination = "test_map")
    public void readMap(Map map) {
        System.out.println(map);
    }

}