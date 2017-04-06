package com.yuwang.pinju.web.module.shop.screen;

import com.opensymphony.xwork2.Action;

public class ShopNotExistAction implements Action{
	private String red_shop;
	@Override
	public String execute() throws Exception {
		return "NOT_EXIST";
	}

	public String getRed_shop() {
		return red_shop;
	}

	public void setRed_shop(String redShop) {
		red_shop = redShop;
	}

}
