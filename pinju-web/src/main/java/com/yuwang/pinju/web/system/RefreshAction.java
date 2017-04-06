package com.yuwang.pinju.web.system;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.core.Refreshable;
import com.yuwang.pinju.web.module.BaseAction;


public class RefreshAction extends BaseAction {

	private static final long serialVersionUID = 7891529256881775132L;

	private Refreshable refreshable;
	
	private String className;
	
	private String refreshMsg = "刷新失败";
	
	public String refresh() {
		if (StringUtils.isEmpty(className)) {
			return SUCCESS;
		}
		try {
			refreshable = (Refreshable)Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		boolean result = refreshable.refresh();
		if (result) {
			setRefreshMsg("刷新成功！类名：" + className);
		}
		return SUCCESS;
	}

	public String getRefreshMsg() {
		return refreshMsg;
	}

	public void setRefreshMsg(String refreshMsg) {
		this.refreshMsg = refreshMsg;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
