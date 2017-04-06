package com.yuwang.pinju.web.module.shop.screen;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.interceptor.MemberAuthInterceptor;
import com.yuwang.pinju.web.module.TokenAction;

/**
 * 显示店铺基本信息页
 * @author xueqi
 *
 * @since 2011-7-4
 */
public class ShopShowShopInfoAction extends TokenAction {
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ShopShowInfoManager shopShowInfoManager;
	
	private ShopShowInfoAO shopShowInfoAO;
	
	/**********旗舰店联系人信息【2.0新增】****************/
	private ShopBusinessInfoDO shopBusinessInfoDO;

	/**********i小铺联系人信息【2.0新增】****************/
	private ShopIshopInfoDO shopIshopInfoDO;
	
	/**********普通店联系人信息【2.0新增】****************/
	private ShopCustomerInfoDO shopCustomerInfoDO;
	
	/**********是否入驻过B2C【2.0新增】****************/
	private List<String> isEnterB2cList;
	
	/**********年销售规模信息【2.0新增】****************/
	private List<String> saleScopeList;
	
	/**********店铺等级信息【2.0新增】****************/
	private List<String> shopLevelList;
	
	/**
	 * 是否有在其他网站开过店
	 */
	private int isHaveOuterShop=1;
	
	private List<String>  isOpenOuterShopList;
	
	
	/**
	 * 店铺类型
	 */
	private List<String> sellTypeList;
	
	/**
	 * 店铺分类list
	 */
	@SuppressWarnings("unchecked")
	private Map shopCategoryList;
	
	/**
	 * 店铺基本信息DO
	 */
	private ShopInfoDO shopInfoDO;
	
	
	private String message;
	
	/**
	 * 商品来源
	 */
	private List<String> goodsSourceList;
	
	private String picServer;

	private long getUserId() {
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
	
	/**
	 * 显示店铺基本信息页
	 * @return
	 */
	@AssistantPermission(target = "shop", action = "set")
	public String showShopInfo() {
		long userId = getUserId();
		try {
			picServer = PinjuConstant.VIEW_IMAGE_SERVER;
			
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
			//是否已开店
			if (shopInfoDO != null && shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO) {
				return "NOT_OPEN";
			}
			//是否被合规
			if(shopInfoDO != null && shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI){
				return "IS_CLOSE";
			}
			if (shopInfoDO != null) {
				if (shopInfoDO.getName() == null) {
					setShopInfoDO(null);
				} else {
					setShopInfoDO(shopInfoDO);
				}
				goodsSourceList = ShopConstant.GOODS_SOURCE_LIST;
				isEnterB2cList=ShopConstant.ISENTER_B2C_LIST;
				saleScopeList=ShopConstant.SALE_SCOPE_LIST;
				shopLevelList=ShopConstant.SHOP_LEVEL_LIST;
				isOpenOuterShopList=ShopConstant.ISOPEN_OUTER_SHOP_LIST;

				if (shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_C))) {
					sellTypeList = ShopConstant.SHOP_NATURE_LIST_C;
					shopCustomerInfoDO=shopShowInfoManager.queryShopCusInfoByUser(userId);
				}else if(shopInfoDO.getSellerType().equals(ShopConstant.SELLER_TYPE_IShop)){
					sellTypeList = ShopConstant.SHOP_NATURE_LIST_IShop;
					shopIshopInfoDO=shopShowInfoManager.queryIShopInfoByUser(userId);
				}else {
					sellTypeList = ShopConstant.SHOP_NATURE_LIST_B;
					shopBusinessInfoDO=shopShowInfoManager.queryShopBussinessInfoByUser(userId);
				}
				shopCategoryList = shopShowInfoAO.initShopCategoryList();
				if(null!=shopInfoDO.getOuterShopAddressUrl() && !"".equals(shopInfoDO.getOuterShopAddressUrl())){
					isHaveOuterShop=0;
				}
			} else {
				setShopInfoDO(null);
				return "NOT_OPEN";
			}
			return SUCCESS;
		} catch (ManagerException e) {
			log.info("显示店铺基本信息页出错",e);
		}
		return SUCCESS;

	}
	
	public String getPicServer() {
		return picServer;
	}

	public void setPicServer(String picServer) {
		this.picServer = picServer;
	}

	@SuppressWarnings("unchecked")
	public Map getShopCategoryList() {
		return shopCategoryList;
	}

	@SuppressWarnings("unchecked")
	public void setShopCategoryList(Map shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

	public List<String> getSellTypeList() {
		return sellTypeList;
	}

	public void setSellTypeList(List<String> sellTypeList) {
		this.sellTypeList = sellTypeList;
	}

	public List<String> getGoodsSourceList() {
		return goodsSourceList;
	}

	public void setGoodsSourceList(List<String> goodsSourceList) {
		this.goodsSourceList = goodsSourceList;
	}

	public ShopInfoDO getShopInfoDO() {
		return shopInfoDO;
	}

	public void setShopInfoDO(ShopInfoDO shopInfoDO) {
		this.shopInfoDO = shopInfoDO;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public int getIsHaveOuterShop() {
		return isHaveOuterShop;
	}

	public void setIsHaveOuterShop(int isHaveOuterShop) {
		this.isHaveOuterShop = isHaveOuterShop;
	}

	public List<String> getIsOpenOuterShopList() {
		return isOpenOuterShopList;
	}

	public void setIsOpenOuterShopList(List<String> isOpenOuterShopList) {
		this.isOpenOuterShopList = isOpenOuterShopList;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}
	
	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
		this.shopBusinessInfoDO = shopBusinessInfoDO;
	}

	public void setShopIshopInfoDO(ShopIshopInfoDO shopIshopInfoDO) {
		this.shopIshopInfoDO = shopIshopInfoDO;
	}

	public void setShopCustomerInfoDO(ShopCustomerInfoDO shopCustomerInfoDO) {
		this.shopCustomerInfoDO = shopCustomerInfoDO;
	}
	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}

	public ShopIshopInfoDO getShopIshopInfoDO() {
		return shopIshopInfoDO;
	}

	public List<String> getIsEnterB2cList() {
		return isEnterB2cList;
	}

	public void setIsEnterB2cList(List<String> isEnterB2cList) {
		this.isEnterB2cList = isEnterB2cList;
	}

	public List<String> getSaleScopeList() {
		return saleScopeList;
	}

	public void setSaleScopeList(List<String> saleScopeList) {
		this.saleScopeList = saleScopeList;
	}

	public List<String> getShopLevelList() {
		return shopLevelList;
	}

	public void setShopLevelList(List<String> shopLevelList) {
		this.shopLevelList = shopLevelList;
	}

	public ShopCustomerInfoDO getShopCustomerInfoDO() {
		return shopCustomerInfoDO;
	}
}
