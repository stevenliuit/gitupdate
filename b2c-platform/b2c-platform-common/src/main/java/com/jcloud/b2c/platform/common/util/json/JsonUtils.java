package com.jcloud.b2c.platform.common.util.json;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.util.Map;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-20 16:22
 * @lastdate:
 */
public class JsonUtils {
    public static final JsonConfig serializerConfig = new JsonConfig();

    static {
        serializerConfig.setIgnoreDefaultExcludes(false);
        JsonValueProcessor jsonValueProcessor = new DateJsonValueProcessor();
        serializerConfig.registerJsonValueProcessor(java.sql.Timestamp.class, jsonValueProcessor);
        serializerConfig.registerJsonValueProcessor(java.sql.Date.class, jsonValueProcessor);
        serializerConfig.registerJsonValueProcessor(java.util.Date.class, jsonValueProcessor);
//		serializerConfig.registerDefaultValueProcessor(String.class, new NullValueProcessor());
        serializerConfig.setJsonPropertyFilter(new InvisibleFilter());
    }


    public static String toJSON(Object object){
        return JSONSerializer.toJSON(object, serializerConfig).toString();
    }

    public static <T> T fromJSON(String json, Class<T> rootClass, Map<String, Class<?>> classMap) throws Exception{

        JsonConfig jsonConfig = new JsonConfig();
        /**一对多或者一对一种的对象类型*/
        if(null != classMap){
            jsonConfig.setClassMap(classMap);
        }
        jsonConfig.setRootClass(rootClass);

        JSONObject jo = JSONObject.fromObject(json, jsonConfig);
        T t = (T)JSONObject.toBean(jo, jsonConfig);
        return t;
    }

    public static String getString(String json, String key){
        JSONObject jo = JSONObject.fromObject(json);
        return jo.getString(key);
    }
}
