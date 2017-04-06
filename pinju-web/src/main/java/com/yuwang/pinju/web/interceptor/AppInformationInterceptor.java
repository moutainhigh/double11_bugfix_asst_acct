package com.yuwang.pinju.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.util.SystemUtil;

public class AppInformationInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ServletActionContext.getRequest().setAttribute("hostName", SystemUtil.getHostInfo().getName());
		ServletActionContext.getRequest().setAttribute("image_server", PinjuConstant.VIEW_IMAGE_SERVER);
		return invocation.invoke();
	}

}
