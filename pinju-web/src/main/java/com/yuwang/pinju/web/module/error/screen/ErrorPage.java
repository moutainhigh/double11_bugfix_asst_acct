package com.yuwang.pinju.web.module.error.screen;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;

/**
 * @Project: pinju-web
 * @Title: Error.java
 * @Package com.yuwang.pinju.web.module.error.screen
 * @Description: 错误页面信息展示
 * @author liuboen liuboen@zba.com
 * @date 2011-6-10 上午09:54:44
 * @version V1.0
 */
public class ErrorPage implements Action, MessageName {
	private final static String ERROR_MESSAGE="errorMessage";
	public String errorMessage;
	
	public String execute(){
		String msg =null;
		msg =(String)ActionContext.getContext().get(ERROR_MESSAGE);
		if (EmptyUtil.isBlank(msg)) {
			msg=errorMessage;
		}
		if(EmptyUtil.isBlank(msg)) {
			ActionContext.getContext().put(ERROR_MESSAGE, Message.getMessage(ERROR_DEFAULT_MESSAGE));
		}
		return SUCCESS;
	}

	/**
	 * <p>往错误消息栈中添加错误消息</p>
	 *
	 * @param message
	 *
	 * @author gaobaowen
	 */
	public static void addErrorMessage(String message) {
		if(EmptyUtil.isBlank(message)) {
			return;
		}
		ActionContext.getContext().put(ERROR_MESSAGE, message);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	
}
