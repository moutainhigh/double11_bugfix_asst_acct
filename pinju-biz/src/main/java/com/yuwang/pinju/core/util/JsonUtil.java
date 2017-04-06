package com.yuwang.pinju.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.xml.XMLSerializer;

/**
 * Json 工具类
 */
public class JsonUtil {

	/**
	 * 功能: 根据json字符串得到对象
	 */
	@SuppressWarnings("unchecked")
	public static Object getObject4JsonString(String jsonString, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Object pojo = JSONObject.toBean(jsonObject, clazz, getMap4Profundity(clazz));
		return pojo;
	}

	/**
	 * 功能: 根据json字符串得到map对象（map的key限定String类型）
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap4Json(String jsonString, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, getObject4JsonString(value.toString(), clazz));
		}
		return valueMap;
	}

	/**
	 * 功能: 根据json字符串得到对象数组
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getObjectArray4Json(String jsonString, Class clazz) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return (Object[]) JSONArray.toArray(jsonArray, clazz, getMap4Profundity(clazz));
	}

	/**
	 * 功能: 根据json字符串得到Collection对象
	 */
	@SuppressWarnings("unchecked")
	public static Collection getList4Json(String jsonString, Class clazz) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Collection collection = JSONArray.toCollection(jsonArray, clazz);
		return collection;
	}

	/**
	 * 功能: 根据java对象生成json字符串
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerDefaultValueProcessor(Long.class,     
		        new DefaultValueProcessor() {     
		            @SuppressWarnings("unchecked")
					public Object getDefaultValue(Class type) {     
		                return "";
		              }     
		        }); 
		jsonConfig.registerDefaultValueProcessor(Integer.class,     
		        new DefaultValueProcessor() {     
		            @SuppressWarnings("unchecked")
					public Object getDefaultValue(Class type) {     
		                return "";
		              }     
		        });
		jsonConfig.registerDefaultValueProcessor(Double.class,     
		        new DefaultValueProcessor() {     
		            @SuppressWarnings("unchecked")
					public Object getDefaultValue(Class type) {     
		                return "";
		              }     
		        });
		JSONObject json = JSONObject.fromObject(javaObj,jsonConfig);
		return json.toString();
	}
	
	public static String getJsonString4JavaPOJO(Object javaObj,String[] strs){
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerDefaultValueProcessor(Long.class,     
		        new DefaultValueProcessor() {     
		            @SuppressWarnings("unchecked")
					public Object getDefaultValue(Class type) {     
		                return "";
		              }     
		        }); 
		jsonConfig.registerDefaultValueProcessor(Integer.class,     
		        new DefaultValueProcessor() {     
		            @SuppressWarnings("unchecked")
					public Object getDefaultValue(Class type) {     
		                return "";
		              }     
		        });
		jsonConfig.registerDefaultValueProcessor(Double.class,     
		        new DefaultValueProcessor() {     
		            @SuppressWarnings("unchecked")
					public Object getDefaultValue(Class type) {     
		                return "";
		              }     
		        });
		jsonConfig.setExcludes(strs);
		JSONObject json = JSONObject.fromObject(javaObj,jsonConfig);
		return json.toString();
	}
	/**
	 * 功能: 根据map生成json字符串
	 */
	public static String getJsonString4Map(Map<String, Object> map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}

	/**
	 * 功能: 根据java对象生成json字符串
	 */
	public static String getJsonString4ObjectArray(Object[] objArray) {
		JSONArray jsonArray = JSONArray.fromObject(objArray);
		return jsonArray.toString();
	}

	/**
	 * 功能: 根据Collection或者其子类得到json字符串
	 */
	@SuppressWarnings("unchecked")
	public static String getJsonString4Collection(Collection collection) {
		JSONArray array = JSONArray.fromObject(collection);
		return array.toString();
	}

	/**
	 * 功能: 根据json字符串生成XML字符串
	 */
	public static String getXML4Json(String jsonString) {
		JSON json = JSONObject.fromObject(jsonString);
		if (json.isArray()) {
			json = JSONArray.fromObject(jsonString);
		}
		XMLSerializer xmlSerializer = new XMLSerializer();
		String xml = xmlSerializer.write(json);
		return xml;
	}

	/**
	 * 功能: 根据XML字符串生成json字符串
	 */
	public static String getJson4XML(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(xml);
		return json.toString();
	}

	/**
	 * 功能: 将java对象转换成json字符串,并设定日期格式
	 */
	/*public static String getJsonString4JavaPOJO(Object javaObj, String[] params, String dataFormat) {
		Field[] fields = javaObj.getClass().getDeclaredFields();
		List<String> fieldList = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			fieldList.add(fields[i].getName());
		}
		if (params != null && params.length > 0) {
			List<String> paramsList = Arrays.asList(params);
			fieldList.removeAll(paramsList);
		}
		String[] excludes = (String[]) fieldList.toArray();
		JsonConfig jsonConfig = configJson(excludes, dataFormat);
		JSONObject json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();
	}*/

	/**
	 * 功能: json时间解析器具
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));
		return jsonConfig;
	}

	/**
	 * 功能:更深一层的泛型解析
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Type> getMap4Profundity(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Type> map = null;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType() == List.class || fields[i].getType() == Set.class
					|| fields[i].getType() == Collection.class) {
				ParameterizedType pt = (ParameterizedType) (fields[i].getGenericType());
				map = new HashMap<String, Type>();
				map.put(fields[i].getName(), pt.getActualTypeArguments()[0]);
			}
		}
		return map;
	}
	/***
	* 将JSON对象数组转换为传入类型的List
	* @param <T>
	* @param jsonArray
	* @param objectClass
	* @return
	*/
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass)
	{
	return JSONArray.toList(jsonArray, objectClass);
	}

	/***
	* 将对象转换为传入类型的List
	* @param <T>
	* @param jsonArray
	* @param objectClass
	* @return
	*/
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> List<T> toList(Object object, Class<T> objectClass)
	{
	JSONArray jsonArray = JSONArray.fromObject(object);

	return JSONArray.toList(jsonArray, objectClass);
	}

	/***
	* 将JSON对象转换为传入类型的对象
	* @param <T>
	* @param jsonObject
	* @param beanClass
	* @return
	*/
	@SuppressWarnings("unchecked")
	public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/***
	* 将将对象转换为传入类型的对象
	* @param <T>
	* @param object
	* @param beanClass
	* @return
	*/
	@SuppressWarnings("unchecked")
	public static <T> T toBean(Object object, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(object);

		return (T) JSONObject.toBean(jsonObject, beanClass);
	}
	/***
	* 将JSON文本反序列化为主从关系的实体
	* @param <T> 泛型T 代表主实体类型
	* @param <D> 泛型D 代表从实体类型
	* @param jsonString JSON文本
	* @param mainClass 主实体类型
	* @param detailName 从实体类在主实体类中的属性名称
	* @param detailClass 从实体类型
	* @return
	*/
	public static <T, D> T toBean(String jsonString, Class<T> mainClass,
			String detailName, Class<D> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);
		
		T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
		List<D> detailList = JsonUtil.toList(jsonArray, detailClass);

		try {
			BeanUtils.setProperty(mainEntity, detailName, detailList);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}
	/**
	 * 将Json字符串转换成SendMessage
	 * @param  json 要转换的字符串
	 * @param  mainClass<T> 主，类型
	 * @param  detailClass<D> 从类型，主类型中包含的对象类型
	 */
	public static <T,D> T toBeanContBean(String jsonString,Class<T> mainClass,String detailName,Class<D> detailClass){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONObject jsonObject1 = (JSONObject)jsonObject.get(detailName);

		T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
		D mainEntity1 =JsonUtil.toBean(jsonObject1,detailClass);

		try {
			BeanUtils.setProperty(mainEntity,detailName, mainEntity1);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}
	
	/***
	* 将JSON文本反序列化为主从关系的实体
	* @param <T>泛型T 代表主实体类型
	* @param <D1>泛型D1 代表从实体类型
	* @param <D2>泛型D2 代表从实体类型
	* @param jsonString JSON文本
	* @param mainClass  主实体类型
	* @param detailName1 从实体类在主实体类中的属性
	* @param detailClass1 从实体类型
	* @param detailName2 从实体类在主实体类中的属性
	* @param detailClass2 从实体类型
	* @return
	*/
	public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass,
			String detailName1, Class<D1> detailClass1, String detailName2,
			Class<D2> detailClass2) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONObject jsonObject1 = (JSONObject) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject1.get(detailName2);

		T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
		D1 mainEntity1 = JsonUtil.toBean(jsonObject1, detailClass1);
		List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);

		try {
			BeanUtils.setProperty(mainEntity, detailName1, mainEntity1);
			BeanUtils.setProperty(mainEntity1, detailName2, detailList2);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}
	
	/***
	* 将JSON文本反序列化为主从关系的实体
	* @param <T>泛型T 代表主实体类型
	* @param <D1>泛型D1 代表从实体类型
	* @param <D2>泛型D2 代表D1的从实体类型
	* @param <D2>泛型D3 代表D1的从实体类型
	* @param jsonString JSON文本
	* @param mainClass  主实体类型
	* @param detailName1 从实体类在主实体类中的属性
	* @param detailClass1 从实体类型
	* @param detailName2 从实体类在主实体类中从实体类的属性
	* @param detailClass2 从实体类型
	* @param detailName3 从实体类在主实体类中从实体类的属性
	* @param detailClass3 从实体类型
	* @return
	*/
	public static <T, D1, D2, D3> T toBean(String jsonString,
	Class<T> mainClass, String detailName1, Class<D1> detailClass1,
	String detailName2, Class<D2> detailClass2, String detailName3,
	Class<D3> detailClass3)
	{
	JSONObject jsonObject = JSONObject.fromObject(jsonString);
	JSONObject jsonObject1 = (JSONObject) jsonObject.get(detailName1);
	JSONArray jsonArray2 = (JSONArray) jsonObject1.get(detailName2);
	JSONArray jsonArray3 = (JSONArray) jsonObject1.get(detailName3);

	T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
	D1 mainEntity1 = JsonUtil.toBean(jsonObject1, detailClass1);
	List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);
	List<D3> detailList3 = JsonUtil.toList(jsonArray3, detailClass3);

	try
	{
	BeanUtils.setProperty(mainEntity, detailName1, mainEntity1);
	BeanUtils.setProperty(mainEntity1, detailName2, detailList2);
	BeanUtils.setProperty(mainEntity1, detailName3, detailList3);
	}
	catch (Exception ex)
	{
	throw new RuntimeException("主从关系JSON反序列化实体失败！");
	}

	return mainEntity;
	}
}
