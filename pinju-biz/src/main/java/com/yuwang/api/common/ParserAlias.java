/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.yuwang.api.util.ApiUtil;

/**
 * @author liyouguo
 * 
 * @since 2011-9-23
 */
public class ParserAlias {

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	public ParserAlias() {

	}

	public ParserAlias(Class<?> clazz) {
		this.clazz = clazz;
	}

	public ParserAlias(String alias, Class<?> clazz) {
		this(clazz);
		this.alias = alias;
	}

	/**
	 * 类别名
	 */
	private String alias;
	/**
	 * 对应的别名类
	 */
	private Class<?> clazz;

	/**
	 * List字段列表，并不显示为list
	 */
	private String[] collectionFields;

	/**
	 * 忽略的字段列表
	 */
	private String[] omitFields;

	/**
	 * 别名字段列表
	 */
	private Map<String, String> aliasFields;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String[] getOmitFields() {
		return omitFields;
	}

	public void setOmitFields(String[] omitFields) {
		this.omitFields = omitFields;
	}

	public Map<String, String> getAliasFields() {
		return aliasFields;
	}

	public void setAliasFields(Map<String, String> aliasFields) {
		this.aliasFields = aliasFields;
	}

	public String[] getCollectionFields() {
		return collectionFields;
	}

	public void setCollectionFields(String[] collectionFields) {
		this.collectionFields = collectionFields;
	}

	/**
	 * 设置字段别名
	 * 
	 * @param alias
	 *            --别名
	 * @param fieldName
	 *            --字段名
	 */
	public void setAliasField(String alias, String fieldName) {
		if (this.aliasFields == null)
			this.aliasFields = new HashMap<String, String>();
		aliasFields.put(alias, fieldName);
	}

	/**
	 * 设置不输出的字段列表【字符串，以“,”分隔】
	 * 
	 * @param omitFields
	 */
	public void setOmitFields(String omitFields) {
		try {
			this.omitFields = ApiUtil.splitArray(omitFields, ",");
		} catch (Exception e) {
			log.warn(e);
			// ignore
		}
	}

	/**
	 * 设置List字段列表【字符串，以“,”分隔】
	 * 
	 * @param collectionFields
	 */
	public void setCollectionFields(String collectionFields) {
		try {
			this.collectionFields = ApiUtil.splitArray(collectionFields, ",");
		} catch (Exception e) {
			log.warn(e);
			// ignore
		}
	}

	/**
	 * 设置别名，字段别名，过滤不输出的字段
	 * 
	 * @param xStream
	 */
	public void setAlias(XStream xStream) {
		if (alias != null && clazz != null) {
			try {
				xStream.alias(alias, clazz);
			} catch (Exception e) {
				log.warn(e);
				// ignore
			}
		}
		setFieldAlias(xStream);
		setOmitFields(xStream);
		setImplicitCollections(xStream);
	}

	/**
	 * 设置字段别名
	 * 
	 * @param xStream
	 */
	private void setFieldAlias(XStream xStream) {
		if (clazz != null && aliasFields != null && aliasFields.size() > 0) {
			for (Iterator<String> keys = aliasFields.keySet().iterator(); keys
					.hasNext();) {
				String alias = keys.next();
				try {
					xStream.aliasField(alias, clazz, aliasFields.get(alias));
				} catch (Exception e) {
					log.warn(e);
					// ignore;
				}
			}
		}
	}

	/**
	 * 忽略不输出的字段
	 * 
	 * @param xStream
	 */
	private void setOmitFields(XStream xStream) {
		if (clazz != null && omitFields != null && omitFields.length > 0) {
			for (int i = 0; i < omitFields.length; i++) {
				try {
					xStream.omitField(clazz, omitFields[i]);
				} catch (Exception e) {
					log.warn(e);
					// ignore
				}
			}
		}
	}
	
	/**
	 * 增加List字段列表
	 * @param xStream
	 */
	private void setImplicitCollections(XStream xStream) {
		if (clazz != null && collectionFields != null && collectionFields.length > 0) {
			for (int i = 0; i < collectionFields.length; i++) {
				try {
					xStream.addImplicitCollection(clazz, collectionFields[i]);
				} catch (Exception e) {
					log.warn(e);
					// ignore
				}
			}
		}
	}
}
