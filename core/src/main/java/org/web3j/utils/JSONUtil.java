package org.web3j.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ziv
 * date On 2019/4/6
 */
public class JSONUtil {

    private static SerializeConfig serializeConfig;

    private static final SerializerFeature[] serializerFeature = {
            //打开循环引用检测，JSONField(serialize = false)不循环
            SerializerFeature.DisableCircularReferenceDetect,
            //默认使用系统默认 格式日期格式化
            SerializerFeature.WriteDateUseDateFormat,
            //输出空置字段
            SerializerFeature.WriteMapNullValue,
            //list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            //Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            //字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty
    };

    static {
        serializeConfig = new SerializeConfig();
    }

    private JSONUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将json字符串反序列化成javabean
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将map字符串反序列化成javabean
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(Map<String, String> map, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(toJSONString(map), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 把JSON文本parse成JavaBean集合
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            list = JSON.parseArray(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将对象序列化程json字符串
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        String json = "";
        try {
            json = JSON.toJSONString(object, serializeConfig, serializerFeature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * map转json字符串
     *
     * @param params
     * @return
     */
    public static String toJSONString(Map<String, String> params) {

        JSONObject jsonObject = new JSONObject();

        if (params == null || params.isEmpty()) {
            return jsonObject.toString();
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        return jsonObject.toString();
    }

    /**
     * json string 转换为 map 对象
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> result = new HashMap<>();
        try {
            result = (Map<String, Object>) JSON.parse(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 对象转map形式
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T t) {
        return jsonToMap(toJSONString(t));
    }
}