package com.pyg.user.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Description: 自定义json转换类
 *
 * @author AK
 * @date 2018/9/3 23:33
 * @since 1.0.0
 */
public class LongToStringAdapter extends ObjectMapper {

    public LongToStringAdapter() {
        super();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        registerModule(simpleModule);
    }
}