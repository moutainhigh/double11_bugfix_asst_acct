package com.yuwang.pinju.web.module.shop.screen;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.shop.ao.ShopCategoryAO;
import com.yuwang.pinju.core.shop.ao.ShopDomainAO;
import com.yuwang.pinju.core.shop.ao.ShopInfoMemcacheAO;
import com.yuwang.pinju.core.shop.ao.ShopPageLayoutAO;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;
import com.yuwang.pinju.web.freemarker.share.PinjuEncoder;
import com.yuwang.pinju.web.interceptor.MemberAuthInterceptor;
import com.yuwang.pinju.web.module.PinjuControl;
import com.yuwang.pinju.web.module.member.control.MemberSellerQualityControl;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author Kevin
 * 
 * @since 2011-7-4
 */
public class ShopDecorationAction extends BaseWithUserAction {
	private Long sellerId;
	private Integer pageId;
	private Long userPageId;
	private ShopUserPageAO shopUserPageAO;
	private ShopPageLayoutAO shopPageLayoutAO;
	private String pageHtml = "";
	private String preview;
	private Integer shopId;
	private String isRelease;
	private List<ShopUserPageDO> userPageList;
	private String templateColor;
	private ShopUserPageManager shopUserPageManager;
	private ShopShowInfoManager shopShowInfoManager;
	private String shopName;
	private ShopDomainAO shopDomainAO;
	private String backgroundeffect;
	private String backgroundtype;
	private String url;
	private String colour;
	//店铺标签
	private String shopLabel;
	//店铺类型
	private String shopType;
	//店铺分类列表字符串
	private String shopCategoryStr;

	private ShopCategoryAO shopCategoryAO;
	private ShopInfoMemcacheAO shopInfoMemcacheAO;
	private CategoryManager categoryManager;
	public CategoryManager getCategoryManager() {
		return categoryManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public ShopInfoMemcacheAO getShopInfoMemcacheAO() {
		return shopInfoMemcacheAO;
	}

	public void setShopInfoMemcacheAO(ShopInfoMemcacheAO shopInfoMemcacheAO) {
		this.shopInfoMemcacheAO = shopInfoMemcacheAO;
	}

	public void setShopCategoryStr(String shopCategoryStr) {
		this.shopCategoryStr = shopCategoryStr;
	}

	public ShopCategoryAO getShopCategoryAO() {
		return shopCategoryAO;
	}

	public void setShopCategoryAO(ShopCategoryAO shopCategoryAO) {
		this.shopCategoryAO = shopCategoryAO;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getShopLabel() {
		return shopLabel;
	}

	public void setShopLabel(String shopLabel) {
		this.shopLabel = shopLabel;
	}

	@Override
	public String execute() throws Exception {
		if(isRelease==null||!isRelease.equals("true")){
			if(!hasAsstPerm("shop","deco")){
				return MemberAuthInterceptor.accessDenied();
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest ();
		String url = request.getRequestURL().toString();
		String domain = url.substring(url.indexOf("http://")+"http://".length(), url.indexOf(".pinju.com"));
		log.info("===========================Host:"+request.getHeader("Host"));
		log.info("=================================="+domain);
		if(!domain.equals("www")){
			if(domain.matches("shop[0-9]+")){
				shopId = Integer.valueOf(domain.substring(domain.indexOf("shop")+"shop".length()));
				log.info("shopId=================================="+shopId);
			}else{
				ShopInfoDO shopInfoDO = shopDomainAO.getShopDomainObject(null,  domain+".pinju.com");
				if(shopInfoDO != null){
					if(shopInfoDO.getApproveStatus()==null){
						return "NOT_OPEN";
					}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
							|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
						return "IS_CLOSE";
					}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
						return "NOT_OPEN";
					}
				}else{
					return "NOT_EXIST";
				}
				shopId = shopInfoDO.getShopId();
				sellerId = shopInfoDO.getUserId();
				shopName = shopInfoDO.getName();
				log.info("shopId+++++++++++++++++++++++"+shopId);
			}
		}
		if (pageId == null) {
			pageId = ShopConstants.SHOP_FIRST_PAGE;
		}
		if (shopId == null && getUserId() == 0
				&& (userPageId == null || userPageId.equals(0L))
				&& (sellerId == null || sellerId.equals(0L))) {
			errorMessage = "查看店铺出错。";
			return "error";
		}
		boolean b = (isRelease != null && isRelease.equals("true"));

		/**
		 * 如果不是查看发布页面，就是查看登录用户
		 */
		if (shopId == null && (userPageId == null || userPageId.equals(0L))
				&& (sellerId == null || sellerId.equals(0L)))
			sellerId = getUserId();
		
		/**
		 * 根据用户页面的编号查看具体页面
		 */
		if (userPageId != null) {
			ShopUserPageDO userPageDO = shopUserPageManager
					.selectShopUserPageById(userPageId);
			shopId = userPageDO.getShopId();
			sellerId = userPageDO.getUserId();
		}
		
		/**
		 * 根据卖家会员编号查看店铺
		 */
		ShopInfoDO shopInfoDO = null;
		if (shopInfoDO == null && sellerId != null) {
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(sellerId, null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					return "NOT_OPEN";
				}
			}else{
				return "NOT_EXIST";
			}
			
		}
		
		/**
		 * 根据店铺编号查看店铺
		 */
		if(shopInfoDO==null && shopId != null){
			shopInfoDO = shopShowInfoManager.queryShopInfoByShopId(shopId, null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					return "NOT_OPEN";
				}
			}else{
				return "NOT_EXIST";
			}
		}
		
		if(shopInfoDO!=null){
			shopName = shopInfoDO.getName();
			shopId = shopInfoDO.getShopId();
			sellerId = shopInfoDO.getUserId();
		try{
			shopLabel = shopInfoDO.getShopLabel();
			if(shopInfoDO.getSellerType().equals("0")){
				shopType = "普通店";
			}
			if(shopInfoDO.getSellerType().equals("1")){
				shopType = "品牌店";
			}
			if(shopInfoDO.getSellerType().equals("2")){
				shopType = "旗舰店";
			}
			if(shopInfoDO.getSellerType().equals("3")){
				shopType = "i小铺";
			}
			//店铺类目
			CategoryDO shopCategoryDO = categoryManager.getItemCategory(Long.parseLong(shopInfoDO.getShopCategory()));
			if(shopCategoryDO!=null){
				shopCategoryStr = shopCategoryDO.getName();
			}
		}catch(Exception e){
			log.error("店铺首页左侧显示店铺标签和<meta>标签获取店铺类目、标签异常", e);
		}
		}else{
			errorMessage = "查看店铺出错。";
			return "error";
		}

		if(isRelease==null||!isRelease.equals("true")){
			if(!sellerId.equals(getUserId())){
				errorMessage = "你没有权限查看此页面。";
				return "error";
			}
		}

		List<ShopPageLayoutDO> list = shopPageLayoutAO.queryPageLayout(
				userPageId, sellerId, shopId, pageId, b);
		
		String seller = PinjuEncoder.getInstance().fixedEncodeMemberId(sellerId);
		pageHtml = shopUserPageAO.getPageHtml(list, pageId, sellerId, shopId,
				preview, seller, b,getClientIp());
		templateColor = shopUserPageAO.getSkinColor(sellerId, shopId, b);
		String configuration = shopUserPageAO.getFristPageConfiguration(sellerId, shopId, b);
		
		ShopUserPageDO userPageDO = new ShopUserPageDO();
		backgroundeffect = userPageDO.getConfig(configuration, "backgroundeffect");
		backgroundtype = userPageDO.getConfig(configuration, "backgroundtype");
		this.url = userPageDO.getConfig(configuration, "url");
		colour = userPageDO.getConfig(configuration, "colour");
			
		userPageDO.setShopId(shopId);
		userPageDO.setUserId(sellerId);
		userPageList = shopUserPageAO.selectShopUserCustomerPage(userPageDO, b);
		setSellerApInfo(sellerId);
		return SUCCESS;
	}

	/**
	 * 设置卖家资质信息
	 * @param memberId
	 */
	private void setSellerApInfo(long memberId){
		PinjuControl pc = new MemberSellerQualityControl(memberId);
		pc.doControl();
	}
	
	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public ShopUserPageAO getShopUserPageAO() {
		return shopUserPageAO;
	}

	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}

	public ShopPageLayoutAO getShopPageLayoutAO() {
		return shopPageLayoutAO;
	}

	public void setShopPageLayoutAO(ShopPageLayoutAO shopPageLayoutAO) {
		this.shopPageLayoutAO = shopPageLayoutAO;
	}

	public String getPageHtml() {
		return pageHtml;
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}

	public Long getUserPageId() {
		return userPageId;
	}

	public void setUserPageId(Long userPageId) {
		this.userPageId = userPageId;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public List<ShopUserPageDO> getUserPageList() {
		return userPageList;
	}

	public void setUserPageList(List<ShopUserPageDO> userPageList) {
		this.userPageList = userPageList;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getTemplateColor() {
		return templateColor;
	}

	public void setTemplateColor(String templateColor) {
		this.templateColor = templateColor;
	}

	public ShopUserPageManager getShopUserPageManager() {
		return shopUserPageManager;
	}

	public void setShopUserPageManager(ShopUserPageManager shopUserPageManager) {
		this.shopUserPageManager = shopUserPageManager;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public ShopDomainAO getShopDomainAO() {
		return shopDomainAO;
	}

	public void setShopDomainAO(ShopDomainAO shopDomainAO) {
		this.shopDomainAO = shopDomainAO;
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


	public String getShopCategoryStr() {
		return shopCategoryStr;
	}
}
