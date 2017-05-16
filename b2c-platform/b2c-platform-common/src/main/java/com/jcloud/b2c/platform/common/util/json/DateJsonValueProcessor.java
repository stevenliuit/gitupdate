package com.jcloud.b2c.platform.common.util.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.util.Date;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-02-20 16:23
 * @lastdate:
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
    @Override
    public Object processArrayValue(Object value, JsonConfig arg1) {
        if(null == value){
            return "";
        }
        if (value instanceof Date) {
            return ((Date)value).getTime();
        }
        return value;
    }

    @Override
    public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
        if(null == value){
            return "";
        }
        if (value instanceof Date) {
            return ((Date)value).getTime();
        }
        return value;
    }
}
