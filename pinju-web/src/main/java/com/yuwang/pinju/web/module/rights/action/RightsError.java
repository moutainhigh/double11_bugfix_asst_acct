package com.yuwang.pinju.web.module.rights.action;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.BaseAction;

public class RightsError extends BaseAction implements MessageName{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String ERROR_MESSAGE="errorMessage";
	public String errorMessage;
	private Integer status;
	private String isSeller; 
	private Integer isGoods;
	
	public Integer getIsGoods() {
		return isGoods;
	}
	public void setIsGoods(Integer isGoods) {
		this.isGoods = isGoods;
	}
	public String getIsSeller() {
		return isSeller;
	}

	public void setIsSeller(String isSeller) {
		this.isSeller = isSeller;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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
