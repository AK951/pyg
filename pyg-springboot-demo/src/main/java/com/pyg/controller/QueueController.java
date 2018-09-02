package com.pyg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/24 22:25
 * @since 1.0.0
 */
@RestController
public class QueueController {

    @Autowired
    private JmsTemplate jmsMessagingTemplate;

    @RequestMapping("/send/{text}")
    public void send(@PathVariable String text) {
        jmsMessagingTemplate.convertAndSend("myQueue", text);
    }

    @RequestMapping("/sendmap")
    public void sendmap() {
        Map map = new HashMap();
        map.put("phone", "13512345678");
        map.put("content", "恭喜中50元话费大奖");
        jmsMessagingTemplate.convertAndSend("test_map", map);
    }

    @RequestMapping("/sendSms")
    public void sendSms() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "17666578866");
        map.put("sign_name", "周怡斌");
        map.put("template_code", "SMS_142381138");
        map.put("code", "666666");
        jmsMessagingTemplate.convertAndSend("sms", map);
    }

}