/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.yuwang.api.common.ParserAlias;

/**
 * 将response对象转成相应格式的字符串输出
 * 
 * @author liyouguo
 * 
 * @since 2011-9-5
 */
public abstract class Parser {

	private List<ParserAlias> aliasList;

	/**
	 * 解析接口
	 * 
	 * @param response
	 * @return
	 */
	public abstract String parser(ApiResponse response);

	/**
	 * 需要解析的Class
	 * 
	 * @param c
	 */
	public void setAliasList(List<ParserAlias> aliasList) {
		this.aliasList = aliasList;
	}

	/**
	 * 设置别名及忽略字段
	 * 
	 * @param xStream
	 */
	protected void setAlias(XStream xStream) {
		if (aliasList != null && aliasList.size() > 0) {
			for (ParserAlias alias : aliasList) {
				alias.setAlias(xStream);
			}
		}
	}
}
