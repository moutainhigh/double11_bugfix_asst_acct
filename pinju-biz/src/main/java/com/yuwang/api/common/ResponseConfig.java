/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.common;

import java.util.List;

/**
 * @author liyouguo
 * 
 * @since 2011-10-11
 */
public class ResponseConfig {

	/**
	 * 是否存在返回图片
	 */
	private boolean hasImageServer;

	/**
	 * 别名取值
	 */
	private List<ParserAlias> listAlias;

	public void setHasImageServer(boolean hasImageServer) {
		this.hasImageServer = hasImageServer;
	}

	public boolean isHasImageServer() {
		return hasImageServer;
	}

	public void setListAlias(List<ParserAlias> listAlias) {
		this.listAlias = listAlias;
	}

	public List<ParserAlias> getListAlias() {
		return listAlias;
	}
}
