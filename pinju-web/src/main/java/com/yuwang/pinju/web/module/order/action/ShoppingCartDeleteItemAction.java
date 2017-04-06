package com.yuwang.pinju.web.module.order.action;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.web.cookie.manager.ShoppingCarCookieManager;

/**
 * 购物车删除商品
 * @author shihongbo
 * @date   2011-6-10
 * @version 1.0
 */
public class ShoppingCartDeleteItemAction implements Action {


	/**
	 * 删除购物车的商品id + ' ' + skuid, 如果skuid没有，则为0；如果有多组，用,分隔
	 * 例如
	 */
	private String delItemId;

	public String getDelItemId() {
		return delItemId;
	}

	public void setDelItemId(String delItemId) {
		this.delItemId = delItemId;
	}


	public String execute() {
		ShoppingCarCookieManager.deleteItemFromShoppingCart(delItemId);
		
		return SUCCESS;
	}
}
