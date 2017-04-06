package com.yuwang.pinju.web.module.shop.screen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.CategoryMarginManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.ao.ShopOpenAO;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.interceptor.MemberAuthInterceptor;
import com.yuwang.pinju.web.interceptor.LoginInterceptor.MemberCheckerAware;

/**
 * 开店action
 * @author xueqi
 *
 * @since 2011-7-4
 */
public class ShopOpenAction extends ShopOpenBaseAction implements MemberCheckerAware{

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private ShopOpenFlowDO shopOpenFlowDO;

	private ShopOpenAO shopOpenAO;

	private ShopCustomerInfoDO shopCustomerInfoDO;

	private ShopBusinessInfoDO shopBusinessInfoDO;

	private CategoryCacheManager categoryCacheManager;
	
	private CategoryMarginManager categoryMarginManager;
	
	private String selectedCategory;
	
	private ShopOpenManager shopOpenManager;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	private String categoryId;
	
	private String tenPayError;
	
	private MemberManager memberManager;
	
	/**
	 * 绑定状态
	 */
	private int bondStatus;
	
	private int signAgreement;
	
	/**
	 * 财付通商户号Add By ShiXing@2011.09.15
	 */
	private String merchantno = PinjuConstant.TENPAY_PAY_PARTNER;
	
	/**
	 * 店铺分类list
	 */
	@SuppressWarnings("unchecked")
	private Map shopCategoryList;
	
	/**
	 * C店店铺性质
	 */
	private List<String> shopNatureListC;
	
	/**
	 * 商品来源
	 */
	private List<String> goodsSourceList;
	
	/**
	 * B店店铺性质
	 */
	private List<String> shopNatureListB;
	
	/**
	 * 卖家性质
	 */
	private List<String> sellerNatureListB;

	private String sellerType;
	
	private String exchangePrice;
	
	/**
	 * 上一步的步骤号
	 */
	private Integer prevStep;
	
	private String errorMessage;
	
	/**
	 * 展示开店类型选择页面
	 * @return
	 */
	public String showShopOpenBegin() {
		long userId = queryUserId();
		shopOpenFlowDO = queryShopOpenFlow(userId);
		return "success";
	}
	
	/**
	 * 获取开店流程
	 * @param userId
	 * @return
	 */
	private ShopOpenFlowDO queryShopOpenFlow(long userId) {
		List<ShopOpenFlowDO> resultList = shopOpenAO.queryShopOpenFlow(userId);
		if (resultList != null && resultList.size() > 0) {
			shopOpenFlowDO = resultList.get(0);
		} else {
			shopOpenFlowDO = null;
		}
		return shopOpenFlowDO;

	}
	
	/**
	 * 进行开店流程,根据开店步骤返回相应需完成的步骤页面
	 * @return
	 */
	public String shopOpenBegin() {
		String returnString = null;
		long userId = queryUserId();
		try {
			shopOpenFlowDO = queryShopOpenFlow(userId);
			Result result = new ResultSupport();
			if(sellerType == null || sellerType.equals("")){
				if(ActionContext.getContext().getParameters() != null && ActionContext.getContext().getParameters().size() > 0){
					sellerType = ActionContext.getContext().getParameters().get("sellerType").toString();
				}
				
			}
			result = shopOpenAO.shopOpen(shopOpenFlowDO, userId,
					sellerType == null ? null : Integer.parseInt(sellerType));
			returnString = result.getSubResultCode();
			if(returnString.equals(ShopConstant.CHECK_TENPAY)){
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("returnModule", "shopOpen");
				parameters.put("merchantno", merchantno);
				parameters.put("sellerType", sellerType);
				ActionContext.getContext().setParameters(parameters);
			}
			if(sellerType == null){
				ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
				sellerType = shopInfoDO.getSellerType();
				returnString = ShopConstant.SHOP_OPEN_BEGIN;
				if(null!=shopOpenFlowDO && null!=shopOpenFlowDO.getNoPassReason() && !"".equals(shopOpenFlowDO.getNoPassReason())){
					String [] str=shopOpenFlowDO.getNoPassReason().split("@!@");
					shopOpenFlowDO.setNoPassReason(str[str.length-1]);
				}
				return returnString;
			}
			initParam(returnString, userId);
		} catch (ManagerException e) {
			log.info(e.getMessage());
		}
		return returnString;
	}
	
	/**
	 * 上一步
	 * @return
	 * @throws ManagerException
	 */
	public String prevStep() throws ManagerException{
		long userId = queryUserId();
		String returnString = "";
		List<ShopBusinessInfoDO> list = shopOpenAO.queryShopBusinessInfo(userId);
		if(list != null && list.size() > 0){
			shopBusinessInfoDO = list.get(0);
			initParam(returnString, userId);
			sellerType = shopBusinessInfoDO.getSellerType();
			return ShopConstant.PREV_STEP[prevStep];
		}else{
			returnString = "error";
			errorMessage = "页面跳转出错";
		}
		return returnString;
	}
	
	/**
	 * 初始化展现流程页面中需要的参数
	 * @param returnString
	 * @param userId
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	public void initParam(String returnString, Long userId) throws ManagerException{
		int price = 0;
		ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
		if (returnString.equals("EXCHANGE_MARGIN")) {
			categoryId = shopInfoDO.getShopCategory();
			if (categoryId == null || categoryId.length() == 0) {
				categoryId = "-1";
			}
			price = categoryMarginManager.getItemMargin(Long.parseLong(categoryId), Integer.parseInt(shopInfoDO.getSellerType()));
		}
		
		//获取已选择的类目名称
		List<CategoryDO> categoryList = categoryCacheManager.getRootCategoryList();
		if (categoryList != null && categoryList.size() > 0) {
			shopCategoryList = new HashMap<String,String>();
			for (int i = 0; i < categoryList.size(); i++) {
				shopCategoryList.put((categoryList.get(i))
						.getId(),(categoryList.get(i).getName()));
				
				if(categoryId!=null && categoryId.equals(String.valueOf((categoryList.get(i)).getId()))){
					selectedCategory = categoryList.get(i).getName();
				}
			}
		}
		
		//需要缴纳的保证金
		exchangePrice = new Money(Long.valueOf(price)).getAmount().toString();
		//卖家性质C
		shopNatureListC = ShopConstant.SHOP_NATURE_LIST_C;
		//店铺性质B
		shopNatureListB = ShopConstant.SHOP_NATURE_LIST_B;
		//货物来源
		goodsSourceList = ShopConstant.GOODS_SOURCE_LIST;
		//店铺性质
		sellerNatureListB = ShopConstant.SELLER_NATURE_LIST_B;
		
		initOutShopInfoParam();
	}
	
	/**
	 * 点击我要开店时使用该方法,用来检验开店审核状态,以展现初始页面.
	 * @return
	 */
	public String showShopOpen() {
		long userId = queryUserId();
		shopOpenFlowDO = queryShopOpenFlow(userId);
		//已经提交过开店信息
		if (shopOpenFlowDO != null) {
			//开店已成功 (补绑定)
			if(shopOpenFlowDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END)) {
				try {
					 MemberPaymentDO memberPaymentDO = memberManager.findBoundMemberPayment(new MemberPaymentDO(userId,
							MemberPaymentDO.INSTITUTION_TENPAY));
					if (memberPaymentDO != null
							&& MemberPaymentDO.BOUND_STATUS_BOUND.equals(memberPaymentDO.getBondStatus())) { // 已绑定
						bondStatus = 1;
					}
					if (memberPaymentDO != null
							&& MemberPaymentDO.SIGN_AGREEMENT_YES.equals(memberPaymentDO.getSignAgreement())) { // 已签约
						signAgreement = 1;
					}
				} catch (ManagerException e) {
					log.debug("", e);
				}

				return "SHOP_OPEN_BEGIN";
			}
			
			//未审核
			if(shopOpenFlowDO.getAuditStatus().intValue() == ShopConstant.AUDIT_STATUS_WAIT
					.intValue()){
				return "redirectShopOpen";
			}
			//审核未通过
			if (shopOpenFlowDO.getAuditStatus().intValue() == ShopConstant.AUDIT_STATUS_NO
					.intValue()) {
				if(null!=shopOpenFlowDO && null!=shopOpenFlowDO.getNoPassReason() && !"".equals(shopOpenFlowDO.getNoPassReason())){
					String [] str=shopOpenFlowDO.getNoPassReason().split("@!@");
					shopOpenFlowDO.setNoPassReason(str[str.length-1]);
				}
				return "SHOP_OPEN_BEGIN";
			}
			//审核通过
			if (shopOpenFlowDO.getAuditStatus().intValue() == ShopConstant.AUDIT_STATUS_PASS
					.intValue()) {
				return "SHOP_OPEN_BEGIN";
			}
		}
		//未提交过开店信息
		else{
			try {
				ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
				if(shopInfoDO!=null){
					sellerType = shopInfoDO.getSellerType();
				}else{
					sellerType = "-1";
				}
			} catch (ManagerException e) {
				log.info(e.getMessage());
			}
			
			
		}
		return "success";
	}
	
	
	/**
	 * 点击开始按钮选择店铺类型【2.0新增】
	 * @return
	 */
	public String shopChoiceShop(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		return "success";
	}
	
	/**
	 * 跳转到开店协议页面【2.0新增】
	 * @return
	 */
	public String shopShowAgreement() {
		if (CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()) {
			return MemberAuthInterceptor.accessDenied();
		}
		return "showAgreement";
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

	public ShopCustomerInfoDO getShopCustomerInfoDO() {
		return shopCustomerInfoDO;
	}

	public void setShopCustomerInfoDO(ShopCustomerInfoDO shopCustomerInfoDO) {
		this.shopCustomerInfoDO = shopCustomerInfoDO;
	}

	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}

	public String getTenPayError() {
		return tenPayError;
	}

	public void setTenPayError(String tenPayError) {
		this.tenPayError = tenPayError;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
		this.shopBusinessInfoDO = shopBusinessInfoDO;
	}

	public ShopOpenFlowDO getShopOpenFlowDO() {
		return shopOpenFlowDO;
	}

	public void setShopOpenFlowDO(ShopOpenFlowDO shopOpenFlowDO) {
		this.shopOpenFlowDO = shopOpenFlowDO;
	}

	public ShopOpenAO getShopOpenAO() {
		return shopOpenAO;
	}

	public void setShopOpenAO(ShopOpenAO shopOpenAO) {
		this.shopOpenAO = shopOpenAO;
	}

	public String getExchangePrice() {
		return exchangePrice;
	}

	public void setExchangePrice(String exchangePrice) {
		this.exchangePrice = exchangePrice;
	}

	public CategoryMarginManager getCategoryMarginManager() {
		return categoryMarginManager;
	}

	public void setCategoryMarginManager(CategoryMarginManager categoryMarginManager) {
		this.categoryMarginManager = categoryMarginManager;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
	

	public Integer getPrevStep() {
		return prevStep;
	}

	public void setPrevStep(Integer prevStep) {
		this.prevStep = prevStep;
	}

	
	
	@SuppressWarnings("unchecked")
	public Map getShopCategoryList() {
		return shopCategoryList;
	}

	@SuppressWarnings("unchecked")
	public void setShopCategoryList(Map shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}
	
	

	public ShopOpenManager getShopOpenManager() {
		return shopOpenManager;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}


	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getMerchantno() {
		return merchantno;
	}

	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public int getBondStatus() {
		return bondStatus;
	}


	public void setBondStatus(int bondStatus) {
		this.bondStatus = bondStatus;
	}

	public int getSignAgreement() {
		return signAgreement;
	}

	public void setSignAgreement(int signAgreement) {
		this.signAgreement = signAgreement;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	
}
