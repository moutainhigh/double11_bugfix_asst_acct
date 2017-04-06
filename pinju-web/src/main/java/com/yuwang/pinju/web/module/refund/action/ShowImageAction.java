package com.yuwang.pinju.web.module.refund.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;

public class ShowImageAction implements Action{
	
	private final static Log log = LogFactory.getLog(ShowImageAction.class);
	
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String execute() {
		return SUCCESS;
	}
}
