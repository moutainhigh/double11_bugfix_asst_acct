/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyouguo
 * 
 * @since 2011-9-27
 */
@SuppressWarnings("unchecked")
public class ResponseDatas {
	private List data;

	public ResponseDatas() {
		data = new ArrayList();
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data.addAll(data);
	}

	public void setData(Object returnData) {
		data.add(returnData);
	}
}
