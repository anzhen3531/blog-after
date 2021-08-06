package com.anzhen.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Classname : FastJson2JsonRedisSerializer
 * @Date : 21/08/02 11:43
 * @Created : anzhen
 * @Description :
 * @目的:
 */

public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET;

    private Class<T> clazz;

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        /**
         * 开启fastjson autotype功能（不开启，造成EntityWrapper<T>中的T无法正常解析）
         */
        ParserConfig.getGlobalInstance().addAccept("com.anzhen.service");
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 反序列化
     */
    @Override
    public T deserialize(@Nullable byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }

    /**
     * 序列化
     */
    @Override
    public byte[] serialize(@Nullable Object t) {
        if (t == null) {
            return new byte[0];
        }
        /**
         * SerializerFeature.WriteClassName 这个很关键，
         * 这样序列化后的json中就会包含这个类的全称 ==> "@type": "com.lvcoding.entity.SysUser",
         * 在反序列化的时候，就可以直接转换成SysUser对象了
         */
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
}
