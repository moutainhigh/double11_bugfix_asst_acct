package com.yuwang.pinju.web.module.item.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.core.item.ao.CategoryAO;

public class CategoryAction extends ActionSupport {

	private CategoryAO categoryAO;

	private String cpId;
	private String cpvId;

	private String itemId;

	public String getSonPro() {
		JSONObject jsonObject = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			jsonObject = (JSONObject) categoryAO.getSonPro(cpId, cpvId);
			if (jsonObject != null) {
				ServletActionContext.getResponse().getWriter().print(jsonObject.toString());
			}
		} catch (IOException e) {
			LOG.error("获得子类目属性", e);
		}
		return null;
	}

	public String getSku() {
		JSONObject jsonObject = new JSONObject();
		try {
			ServletActionContext.getResponse().getWriter().print(jsonObject.toString());
		} catch (IOException e) {
			LOG.error("获得商品SKU", e);
		} catch (NumberFormatException e) {
			LOG.error("获得商品SKU", e);
		}
		return null;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getCpvId() {
		return cpvId;
	}

	public void setCpvId(String cpvId) {
		this.cpvId = cpvId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setCategoryAO(CategoryAO categoryAO) {
		this.categoryAO = categoryAO;
	}

}
