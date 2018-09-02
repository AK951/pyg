package com.pyg.listener;

import com.pyg.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/24 23:17
 * @since 1.0.0
 */
@Component
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;

    @JmsListener(destination = "sms")
    public void receiveSms(Map<String, String> smsMap) {
        try {
            smsUtils.sendSms(smsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}