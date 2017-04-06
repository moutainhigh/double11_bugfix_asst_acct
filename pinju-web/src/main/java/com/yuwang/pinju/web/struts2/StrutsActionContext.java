package com.yuwang.pinju.web.struts2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;

public class StrutsActionContext {

	private final static Log log = LogFactory.getLog(StrutsActionContext.class);
	
	private StrutsActionContext() {}

	public static <T> T getValue(Class<T> clazz) {
		return getValue(clazz, clazz.getName() + ".actionContext");
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(Class<T> clazz, String name) {
		return (T)ActionContext.getContext().get(name);
	}

	public static void putValue(Object obj) {
		putValue(obj, obj.getClass().getName() + ".actionContext");
	}

	public static void putValue(Object obj, String name) {
		ActionContext.getContext().put(name, obj);
		if(log.isDebugEnabled()) {
			log.debug("add key: " + name + ", value: " + obj + " to ActionContext");
		}
	}
}
