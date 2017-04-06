package com.yuwang.pinju.web.module.shop.screen;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.MemberCheckAction;

public class ShopOpenBaseAction  extends MemberCheckAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * C店店铺性质List
	 */
	private List<String> shopNatureListC;
	
	/**
	 * 商品来源list
	 */
	private List<String> goodsSourceList;
	
	/**
	 * B店店铺性质list
	 */
	private List<String> shopNatureListB;
	
	/**
	 * B店卖家类型
	 */
	private List<String> sellerNatureListB;
	
	/**
	 * 店铺等级list
	 */
	private List<String> shopLevelList;

	/**
	 * 销售规模
	 */
	private List<String> saleScopeList;
	/**
	 * 是否入住过B2C
	 */
	private List<String> isEnterB2cList;
	
	/**
	 * 是否开过外部网店
	 */
	private List<String> isOpenOuterShopList;
	

	protected long queryUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		} else {

		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}
	

	protected String queryNickName() {
		String nickName = "";
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			nickName = login.getMasterMemberName();
		} else {

		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + nickName);
		}
		return nickName;
	}
	
	/**
	 * 初始化返回页面的信息(基础信息)
	 * @return
	 */
	protected void initBaseParam() {
		// 返回页面所需信息
		shopNatureListC = ShopConstant.SHOP_NATURE_LIST_C;
		goodsSourceList = ShopConstant.GOODS_SOURCE_LIST;
		shopNatureListB = ShopConstant.SHOP_NATURE_LIST_B;
		sellerNatureListB = ShopConstant.SELLER_NATURE_LIST_B;
	}
	
	/**
	 * 初始化返回页面的信息(外部店铺信息)
	 * @return
	 */
	protected void initOutShopInfoParam(){
		isOpenOuterShopList= ShopConstant.ISOPEN_OUTER_SHOP_LIST;
		shopLevelList = ShopConstant.SHOP_LEVEL_LIST;
		saleScopeList = ShopConstant.SALE_SCOPE_LIST;
		isEnterB2cList = ShopConstant.ISENTER_B2C_LIST;
	}

	public List<String> getShopNatureListC() {
		return shopNatureListC;
	}


	public void setShopNatureListC(List<String> shopNatureListC) {
		this.shopNatureListC = shopNatureListC;
	}


	public List<String> getGoodsSourceList() {
		return goodsSourceList;
	}


	public void setGoodsSourceList(List<String> goodsSourceList) {
		this.goodsSourceList = goodsSourceList;
	}


	public List<String> getShopNatureListB() {
		return shopNatureListB;
	}


	public void setShopNatureListB(List<String> shopNatureListB) {
		this.shopNatureListB = shopNatureListB;
	}


	public List<String> getSellerNatureListB() {
		return sellerNatureListB;
	}


	public void setSellerNatureListB(List<String> sellerNatureListB) {
		this.sellerNatureListB = sellerNatureListB;
	}

	public List<String> getShopLevelList() {
		return shopLevelList;
	}


	public void setShopLevelList(List<String> shopLevelList) {
		this.shopLevelList = shopLevelList;
	}


	public List<String> getSaleScopeList() {
		return saleScopeList;
	}


	public void setSaleScopeList(List<String> saleScopeList) {
		this.saleScopeList = saleScopeList;
	}


	public List<String> getIsEnterB2cList() {
		return isEnterB2cList;
	}


	public void setIsEnterB2cList(List<String> isEnterB2cList) {
		this.isEnterB2cList = isEnterB2cList;
	}

	public List<String> getIsOpenOuterShopList() {
		return isOpenOuterShopList;
	}


	public void setIsOpenOuterShopList(List<String> isOpenOuterShopList) {
		this.isOpenOuterShopList = isOpenOuterShopList;
	}
	
}
