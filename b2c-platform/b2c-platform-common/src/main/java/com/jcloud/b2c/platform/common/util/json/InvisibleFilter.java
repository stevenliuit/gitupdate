/**
* Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
*/

package com.jcloud.b2c.platform.common.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wangx
 * @date 2013-7-31
 */
public class InvisibleFilter extends AbstractMethodFilter{

    public boolean applyMethod(final Method method) {
        if (method.isAnnotationPresent(Invisible.class)) {
            Invisible anno = method.getAnnotation(Invisible.class);
            if(null != anno){
            	return true;
            }
        }
        return false;
    }
    
    public boolean applyField(final Field field) {
        if (field.isAnnotationPresent(Invisible.class)) {
            Invisible anno = field.getAnnotation(Invisible.class);
            if(null != anno){
            	return true;
            }
        }
        return false;
    }
}

