/**
 * 
 */
package com.yuwang.pinju.web.module.shop.screen;

import java.util.List;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author liyouguo
 * 
 */
public class ShopDesignPageManagerAction extends BaseWithUserAction {
	private Integer shopId;
	private ShopUserPageAO shopUserPageAO;
	private List<ShopUserPageDO> userPageList;
	private ShopShowInfoManager shopShowInfoManager;
	private String shopName;
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public List<ShopUserPageDO> getUserPageList() {
		return userPageList;
	}

	public void setUserPageList(List<ShopUserPageDO> userPageList) {
		this.userPageList = userPageList;
	}

	public ShopUserPageAO getShopUserPageAO() {
		return shopUserPageAO;
	}

	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		ShopInfoDO shopInfoDO = null;
		if(shopId != null){
			shopInfoDO = shopShowInfoManager.queryShopInfoByShopId(shopId, ShopConstant.APPROVE_STATUS_YES);
			shopName = shopInfoDO.getName();
		}
		ShopUserPageDO userPageDO = new ShopUserPageDO();
		userPageDO.setShopId(shopId);
		userPageDO.setUserId(getUserId());
		userPageList = shopUserPageAO.selectShopUserCustomerPage(userPageDO,
				false);
		return SUCCESS;
	}
}
