package com.yuwang.pinju.web.module.shop.screen;

import com.opensymphony.xwork2.Action;

public class ShopNotOpenAction implements Action{
	private String red_shop;
	@Override
	public String execute() throws Exception {
		return "NOT_OPEN";
	}

	public String getRed_shop() {
		return red_shop;
	}

	public void setRed_shop(String redShop) {
		red_shop = redShop;
	}

}
