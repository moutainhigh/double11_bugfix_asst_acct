package com.yuwang.pinju.web.module.shop.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

public class ShopTemplateManagerAction extends BaseWithUserAction implements ServletRequestAware{
	private ShopUserPageAO shopUserPageAO;
	HttpServletRequest request;
	

	@Override
	public String execute() throws Exception {
		Integer shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		
		StringBuffer sbf = new StringBuffer();
		StringBuffer configuration = new StringBuffer();
		for (Object keyObj : request.getParameterMap().keySet()) {
			String key = (String) keyObj;
			if (key != null && key.trim().length() > 0
					&& request.getParameter(key) != null) {
				if(key.equals("templateColor")){
					sbf.append(key).append("=").append(
							StringUtil
									.array2String(request.getParameterValues(key)))
							.append("\n");
				}else{
					configuration.append(key).append("=").append(
							StringUtil
									.array2String(request.getParameterValues(key)))
							.append("\n");
				}
			}
		}
		ShopUserPageDO shopUserPageDO = new ShopUserPageDO();
		shopUserPageDO.setUserId(getUserId());
		shopUserPageDO.setShopId(shopId);
		shopUserPageDO.setSkinId(sbf.toString());
		shopUserPageDO.setConfiguration(configuration.toString());
		shopUserPageDO.setPageId(2);
		shopUserPageAO.updateShopUserPage(shopUserPageDO);
		shopUserPageDO.setConfiguration(null);
		shopUserPageDO.setPageId(3);
		shopUserPageAO.updateShopUserPage(shopUserPageDO);
		return "success";
	}
	
	public ShopUserPageAO getShopUserPageAO() {
		return shopUserPageAO;
	}
	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
}
