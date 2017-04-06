package com.yuwang.pinju.web.module.index.screen;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.item.ao.ItemAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;

public class IndexAction implements Action {
	
	private String message;

	public String execute() {
		item();
		message = "javax.servlet.context.tempdir: " + ServletActionContext.getServletContext().getAttribute("javax.servlet.context.tempdir");;
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	private void item() {
		/*ItemAO itemAO = SpringBeanFactory.getBean(ItemAO.class);
		List<ItemDO> itemList = itemAO.getAllItem(new ItemQuery());
		ActionContext.getContext().put("items", itemList);		*/
	}
}
