package com.pyg.user.mapper;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;

import java.math.BigInteger;

/**
 * Description: 消息转换器
 *
 * @author AK
 * @date 2018/9/4 0:08
 * @since 1.0.0
 */
public class FastJsonConfigExt extends FastJsonConfig {
    public FastJsonConfigExt() {
        super();
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class,ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE,ToStringSerializer.instance);
        this.setSerializeConfig(serializeConfig);
    }
}