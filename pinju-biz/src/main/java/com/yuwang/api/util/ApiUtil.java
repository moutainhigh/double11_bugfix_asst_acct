/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

import com.yuwang.api.ApiException;

/**
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
public final class ApiUtil {

	/**
	 * 校验:数字数组，用逗号分隔
	 * 
	 * @param target
	 * @throws ApiException
	 */
	public static void checkIntArray(String target) throws ApiException {
		// 正则表达式:只允许数字后加一个逗号 (([0-9]+)(,?))*    ^(0|-?[1-9][0-9]*)
		Pattern p = Pattern.compile("((0|-?[1-9][0-9]*)(,?))*");
		// 操作的字符串
		Matcher m = p.matcher(target);
		if (!m.matches()) {
			throw new ApiException("传入参数出错:" + target + "，必须传入数字型参数，多个时用逗号分隔。");
		}
	}

	@SuppressWarnings("unchecked")
	public static String[] splitArray(String strs, String token) {
		List list = new java.util.ArrayList();
		if (strs == null || "".equals(strs.trim()))
			return new String[] {};
		StringTokenizer st = new StringTokenizer(strs, token);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken().trim());
		}
		String[] strArray = new String[list.size()];
		for (int i = 0, size = list.size(); i < size; i++)
			strArray[i] = list.get(i).toString();
		return strArray;
	}

	@SuppressWarnings("unchecked")
	public static Class getPropertyType(Class domainClazz, String property) {
		try {
			Field field = domainClazz.getDeclaredField(property);
			return field.getType();
		} catch (Exception e) {
			// ignore
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void copyProperty(Class objClazz, Object targetObject, String propertyValue) {
		String[] temp = propertyValue.split("=");
		String property = temp[0];
		String value = temp[1];
		try {
			Class type = ApiUtil.getPropertyType(objClazz, property);
			if (type != null){
				BeanUtils.copyProperty(targetObject, property, ApiUtil.createObject(type, value));
			}
		} catch (Exception e) {
			// ignore
		}
	}

	@SuppressWarnings("unchecked")
	public static Object createObject(Class typeCls, Object o) throws ApiException {
		if (o == null)
			return null;
		if ("java.lang.String".equals(typeCls.getName()))
			return o.toString();
		if (typeCls.getName().equals("long") || typeCls.getName().equals("java.lang.Long"))
			return (o instanceof Long) ? o : new Long(o.toString());
		if (typeCls.getName().equals("int") || typeCls.getName().equals("java.lang.Integer"))
			return (o instanceof Integer) ? o : new Integer(o.toString());
		if (typeCls.getName().equals("short") || typeCls.getName().equals("java.lang.Short"))
			return (o instanceof Short) ? o : new Short(o.toString());
		if (typeCls.getName().equals("double") || typeCls.getName().equals("java.lang.Double"))
			return (o instanceof Double) ? o : new Double(o.toString());
		if (typeCls.getName().equals("float") || typeCls.getName().equals("java.lang.Float"))
			return (o instanceof Float) ? o : new Float(o.toString());
		if (typeCls.getName().equals("boolean") || typeCls.getName().equals("java.lang.Boolean"))
			return (o instanceof Boolean) ? o : new Boolean(o.toString().toLowerCase().equals("true"));
		if (typeCls.getName().equals("String[]") || typeCls.getName().equals("[Ljava.lang.String;")) {
			String[] strs = null;
			if (o instanceof String[])
				strs = (String[]) o;
			else
				strs = splitArray(o.toString(), ",");
			return strs;
		}
		if (typeCls.getName().equals("long[]") || typeCls.getName().equals("[Ljava.lang.Long;")) {
			if (o instanceof long[])
				return o;
			String[] strs = null;
			if (o instanceof String[])
				strs = (String[]) o;
			else
				strs = splitArray(o.toString(), ",");
			long[] rets = new long[strs.length];
			for (int i = 0; i < strs.length; i++) {
				try {
					rets[i] = Long.parseLong(strs[i]);
				} catch (Exception e) {
					rets[i] = 0;
				}
			}
			return rets;
		}
		if (typeCls.getName().equals("int[]") || typeCls.getName().equals("[Ljava.lang.Integer;")) {
			if (o instanceof int[])
				return o;
			String[] strs = null;
			if (o instanceof String[])
				strs = (String[]) o;
			else
				strs = splitArray(o.toString(), ",");
			int[] rets = new int[strs.length];
			for (int i = 0; i < strs.length; i++) {
				try {
					rets[i] = Integer.parseInt(strs[i]);
				} catch (Exception e) {
					rets[i] = 0;
				}
			}
			return rets;
		}
		if (typeCls.getName().equals("short[]") || typeCls.getName().equals("[Ljava.lang.Short;")) {
			if (o instanceof short[])
				return o;
			String[] strs = null;
			if (o instanceof String[])
				strs = (String[]) o;
			else
				strs = splitArray(o.toString(), ",");
			short[] rets = new short[strs.length];
			for (int i = 0; i < strs.length; i++) {
				try {
					rets[i] = Short.parseShort(strs[i]);
				} catch (Exception e) {
					rets[i] = 0;
				}
			}
			return rets;
		}
		if (typeCls.getName().equals("double[]") || typeCls.getName().equals("[Ljava.lang.Double;")) {
			if (o instanceof double[])
				return o;
			String[] strs = null;
			if (o instanceof String[])
				strs = (String[]) o;
			else
				strs = splitArray(o.toString(), ",");
			double[] rets = new double[strs.length];
			for (int i = 0; i < strs.length; i++) {
				try {
					rets[i] = Double.parseDouble(strs[i]);
				} catch (Exception e) {
					rets[i] = 0;
				}
			}
			return rets;
		}
		if (typeCls.getName().equals("float[]") || typeCls.getName().equals("[Ljava.lang.Float;")) {
			if (o instanceof float[])
				return o;
			String[] strs = null;
			if (o instanceof String[])
				strs = (String[]) o;
			else
				strs = splitArray(o.toString(), ",");
			float[] rets = new float[strs.length];
			for (int i = 0; i < strs.length; i++) {
				try {
					rets[i] = Float.parseFloat(strs[i]);
				} catch (Exception e) {
					rets[i] = 0;
				}
			}
			return rets;
		}
		if (typeCls.getName().equals("Date") || typeCls.getName().equals("java.util.Date")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 设置宽松性转换:false
			sdf.setLenient(false);
			try {
				return sdf.parse(o.toString());
			} catch (ParseException e) {
				throw new ApiException("日期类型格式错误:" + o.toString() + "，正确的格式是yyyy-MM-dd HH:mm:ss");
			}
		}
		// 实体类中包含其他YUWANG实体类的情况
		if (typeCls.getName().contains("com.yuwang")) {
			Object rets = null;
			String valueStr = (String) o;
			// 数组的情况
			if (typeCls.isArray()) {
				Class elementType = typeCls.getComponentType();
				// 多个实体类用@!@分隔
				String values[] = splitArray(valueStr, "@!@");
				Object array = Array.newInstance(elementType, values.length);
				for (int i = 0; i < values.length; i++) {
					try {
						String value[] = splitArray(values[i], ",");
						Object domain = elementType.newInstance();
						for (String pv : value) {
							copyProperty(elementType, domain, pv);
						}
						Array.set(array, i, domain);
					} catch (Exception e) {
						// ignore
					}
				}
				rets = array;
			} else {
				// 单个实例的情况
				try {
					String value[] = splitArray(valueStr, ",");
					Object domain = typeCls.newInstance();
					for (String pv : value) {
						copyProperty(typeCls, domain, pv);
					}
					rets = domain;
				} catch (Exception e) {
					rets = null;
				}
			}
			return rets;
		}
		return o;
	}
}
