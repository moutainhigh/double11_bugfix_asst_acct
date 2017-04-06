package com.yuwang.pinju.common;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Project: pinju-model
 * @Description: 反射工具类
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午07:05:38
 * @update 2011-8-12 下午07:05:38
 * @version V1.0
 */
public abstract class ReflectionUtils {
    private static final Log logger = LogFactory.getLog(ReflectionUtils.class);

    public static void setFieldValue(Object target, String fname, Class<?> ftype, Object fvalue) {
        setFieldValue(target, target.getClass(), fname, ftype, fvalue);
    }

    public static void setFieldValue(Object target, Class<?> clazz, String fname, Class<?> ftype, Object fvalue) {
        if (target == null || fname == null || "".equals(fname)
                || (fvalue != null && !ftype.isAssignableFrom(fvalue.getClass()))) {
            return;
        }

        try {
            Method method = clazz.getDeclaredMethod(
                    "set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), ftype);
            //if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
            //}
            method.invoke(target, fvalue);

        }
        catch (Exception me) {
            if (logger.isDebugEnabled()) {
                logger.debug(me);
            }

            try {
                Field field = clazz.getDeclaredField(fname);
                //if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
                //}
                field.set(target, fvalue);
            }
            catch (Exception fe) {
                if (logger.isDebugEnabled()) {
                    logger.debug(fe);
                }
            }
        }
    }

    public static Object getFieldValue(Object target, String fname) {
        return getFieldValue(target, target.getClass(), fname);
    }

    public static Object getFieldValue(Object target, Class<?> clazz, String fname) {
        if (target == null || fname == null || "".equals(fname)) {
            return null;
        }

        boolean exCatched = false;
        try {
            String methodname = "get" + StringUtils.capitalize(fname);
            Method method = clazz.getDeclaredMethod(methodname);
            //if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
            //}
            return method.invoke(target);
        }
        catch (NoSuchMethodException e) {
            exCatched = true;
        }
        catch (InvocationTargetException e) {
            exCatched = true;
        }
        catch (IllegalAccessException e) {
            exCatched = true;
        }

        if (exCatched) {
            try {
                Field field = clazz.getDeclaredField(fname);
                //if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
                //}
                return field.get(target);
            }
            catch (Exception fe) {
                if (logger.isDebugEnabled()) {
                    logger.debug(fe);
                }
            }
        }
        return null;
    }

    /**
     * <p>获取一个 Bean 属性中含有指定标记的 PropertyDescriptor 数组集合</p>
     *
     * @param obj 需要扫描的 Bean 对象
     * @param annotationClass 需要扫描的注解
     * @return
     *
     * @author gaobaowen
     * @throws IntrospectionException 分析 Bean 错误时产生的异常
     *
     * @since 2011-11-29 14:58:04
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Object obj, Class<? extends Annotation> annotationClass)
    		throws IntrospectionException {
    	if (obj == null || annotationClass == null) {
    		return new PropertyDescriptor[0];
    	}
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
    	List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>(pds.length);
    	for (int i = 0; i < pds.length; i++) {
    		Method read = pds[i].getReadMethod();
    		if (read != null && read.isAnnotationPresent(annotationClass)) {
    			list.add(pds[i]);
    			continue;
    		}
    		Method write = pds[i].getWriteMethod();
    		if (write != null && write.isAnnotationPresent(annotationClass)) {
    			list.add(pds[i]);
    			continue;
    		}
    	}
    	return list.toArray(new PropertyDescriptor[list.size()]);
    }
}