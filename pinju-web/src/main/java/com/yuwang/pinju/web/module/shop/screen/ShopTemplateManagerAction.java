package com.yuwang.pinju.web.module.shop.screen;

import java.util.List;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author Kevin
 *
 * @since 2011-7-4
 */
public class ShopTemplateManagerAction extends BaseWithUserAction {
	private ShopUserPageAO shopUserPageAO;
	private Integer pageId;
	private String template;
	private ShopShowInfoManager shopShowInfoManager;
	private String shopName;
	private Integer shopId;
	private String backgroundeffect;
	private String backgroundtype;
	private String url;
	private String colour;
	@Override
	public String execute() throws Exception {
		if (pageId == null) {
			pageId = ShopConstants.SHOP_FIRST_PAGE;
		}
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
		ShopUserPageDO shopUserPageDO = new ShopUserPageDO();
		shopUserPageDO.setUserId(getUserId());
		shopUserPageDO.setShopId(shopId);
		shopUserPageDO.setPageId(pageId);
		List<ShopUserPageDO> shopUserPageDOList = shopUserPageAO
				.selectShopUserPage(shopUserPageDO, false);
		if (shopUserPageDOList.size() > 0) {
			template = shopUserPageDOList.get(0).getConfig(
					shopUserPageDOList.get(0).getSkinId(), "templateColor");
			backgroundeffect = shopUserPageDOList.get(0).getConfig("backgroundeffect");
			backgroundtype = shopUserPageDOList.get(0).getConfig("backgroundtype");
			url = shopUserPageDOList.get(0).getConfig("url");
			colour = shopUserPageDOList.get(0).getConfig("colour");
		}
		if (template == null)
			template =  "red";
		return "success";
	}

	public ShopUserPageAO getShopUserPageAO() {
		return shopUserPageAO;
	}

	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
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

	public String getBackgroundeffect() {
		return backgroundeffect;
	}

	public void setBackgroundeffect(String backgroundeffect) {
		this.backgroundeffect = backgroundeffect;
	}

	public String getBackgroundtype() {
		return backgroundtype;
	}

	public void setBackgroundtype(String backgroundtype) {
		this.backgroundtype = backgroundtype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

}
