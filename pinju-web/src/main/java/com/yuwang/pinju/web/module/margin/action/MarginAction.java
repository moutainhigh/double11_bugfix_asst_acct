package com.yuwang.pinju.web.module.margin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.MarginResultCode;
import com.yuwang.pinju.core.item.manager.CategoryMarginManager;
import com.yuwang.pinju.core.margin.ao.MarginAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.margin.MarginSellerDO;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**  
 * @Project: pinju-web
 * @Description: 我的保证金Action
 * @author 石兴 shixing@zba.com
 * @date 2011-8-10 下午01:14:23
 * @update 2011-8-10 下午01:14:23
 * @version V1.0  
 */
public class MarginAction extends BaseAction {

	private static final long serialVersionUID = 5038752806823212878L;
	
	private MarginAO marginAO;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	private CategoryMarginManager categoryMarginManager;
	
	private MarginSellerDO marginSellerDO;
	
	private String errorMsg;
	
	private DirectPayParamDO directPayParamDO;
	
	private MarginSellerOrderQuery query;
	/**
	 * 卖家在品聚里的保证金
	 */
	private String pinjuMargin = "0.00";
	/**
	 * 当前保证金
	 */
	private String currentMargin = "0.00";
	/**
	 * 需补缴的保证金
	 */
	private String requireAmount;
	
	/**
	 * Created on 2011-8-10 
	 * <p>Discription:[查询保证金,进入我的保证金页面]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[2011-8-10] [更改人姓名]
	 */
	public String goMyMargin() {
		CookieLoginInfo cookieLoginInfo = CookieLoginInfo.getCookieLoginInfo();
		if (!cookieLoginInfo.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
		}
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(cookieLoginInfo.getMemberId(), null);
			if (shopInfoDO==null || shopInfoDO.getApproveStatus()==0) {
				errorMsg = ResultCodeMsg.getResultMessage(MarginResultCode.SELLER_SHOP_NOT_EXIST);
				return SUCCESS;
			}
			Result result = marginAO.queryMarginBalance(cookieLoginInfo.getMemberId());
			marginSellerDO = (MarginSellerDO)result.getModel("marginSellerDO");
			if (marginSellerDO==null) {//开店成功但没有缴保证金
				pinjuMargin = String.valueOf(categoryMarginManager.getItemMargin(Long.valueOf(shopInfoDO.getShopCategory()),Integer.valueOf(shopInfoDO.getSellerType())));
				pinjuMargin = new Money(Long.valueOf(pinjuMargin)).getAmount().toString();
			}else{ 
				pinjuMargin = new Money(marginSellerDO.getPinjuMargin()).getAmount().toString();
				currentMargin = new Money(marginSellerDO.getCurrentMargin()).getAmount().toString();
			}
		} catch (Exception e) {
			errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			log.error("Event=[MarginAction#goMyMargin] 进入保证金页面,查询保证金失败");
		}
		return SUCCESS;
	}
	
	/**
	 * Created on 2011-9-20 
	 * @desc <p>Discription:[后台回调后进入的Action，来跳不同的成功页面，比如开店成功，或者缴消保成功]</p>
	 * @param 
	 * @return String
	 * @author:[石兴]
	 * @update:[2011-9-20] [更改人姓名]
	 */
	public String marginBackGoAction() {
		//return "fromSellerBack";
		return "fromOpenShop";
	}
	
	/**
	 * Created on 2011-8-19 
	 * <p>Discription:[从后台缴纳消保金]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String backPayMargin() {
		CookieLoginInfo cookieLoginInfo = CookieLoginInfo.getCookieLoginInfo();
		if (!cookieLoginInfo.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
		}
		try {
			if (!validateMoney(requireAmount)) {
				errorMsg = ResultCodeMsg.getResultMessage(MarginResultCode.MARGIN_FORMAT_ERROR);
				return ERROR;
			}
			long price = new Money(requireAmount).getCent();
			Result result = marginAO.backPayMargin(cookieLoginInfo.getMemberId(),price);
			if (!result.isSuccess()) {
				errorMsg = ResultCodeMsg.getResultMessage(result.getResultCode());
				return ERROR;
			}
			directPayParamDO = (DirectPayParamDO)result.getModel("directPayParamDO");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("directPayParamDO", directPayParamDO);
			//传参给确认支付页面
			ActionContext.getContext().setParameters(paramMap);
		} catch (Exception e) {
			log.error("Event=[MarginAction#payMargin] 获取保证金参数异常");
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Created on 2011-9-9 
	 * @desc <p>Discription:[校验保证金金额格式8位数字,无小数点]</p>
	 * @param 
	 * @return boolean
	 * @author:[石兴]
	 * @update:[2011-9-9] [更改人姓名]
	 */
	private boolean validateMoney(String requireAmount) {
		if (!NumberUtils.isDigits(requireAmount) || requireAmount.length() > 8) {
			return true;
		}
		return true;
	}

	/**
	 * Created on 2011-8-12 
	 * <p>Discription:[缴纳消保金]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String payMargin() {
		CookieLoginInfo cookieLoginInfo = CookieLoginInfo.getCookieLoginInfo();
		if (!cookieLoginInfo.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
		}
		try {
			Result result = marginAO.payMargin(cookieLoginInfo.getMemberId());
			if (!result.isSuccess()) {
				errorMsg = ResultCodeMsg.getResultMessage(result.getResultCode());
				return ERROR;
			}
			directPayParamDO = (DirectPayParamDO)result.getModel("directPayParamDO");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("directPayParamDO", directPayParamDO);
			//传参给确认支付页面
			ActionContext.getContext().setParameters(paramMap);
		} catch (Exception e) {
			log.error("Event=[MarginAction#payMargin] 获取保证金参数异常");
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Created on 2011-8-10 
	 * <p>Discription:[查询消保金交易明细]</p>
	 * @param 
	 * @return String
	 * @author:[石兴]
	 * @update:[日期2011-08-13] [凌建涛]
	 */
	@SuppressWarnings("unchecked")
	public String getMarginTradeList() {
		// 查询消保金交易明细
		CookieLoginInfo cookieLoginInfo = CookieLoginInfo.getCookieLoginInfo();
		if (!cookieLoginInfo.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
		}
		query = new MarginSellerOrderQuery();
		query.setItemsPerPage(15);// 每页条数，可以不设，默认10条
		query.setPage(getInteger("currentPage"));
		query.setMemberId(cookieLoginInfo.getMemberId());
		Result result = marginAO.queryMarginSellerOrderDOs(query);
		if(result.isSuccess()){
			List<MarginSellerOrderDO> marginSellerOrderDOs = (List<MarginSellerOrderDO>)result.getModel("marginSellerOrderDOs");
			query.setMarginSellerOrderDOs(marginSellerOrderDOs);
		}else {
			errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
		}
		query.setAction("/margin/margintradelist.htm");
		return SUCCESS;
	}

	public void setMarginAO(MarginAO marginAO) {
		this.marginAO = marginAO;
	}

	public void setSellerMarginDO(MarginSellerDO sellerMarginDO) {
		this.marginSellerDO = sellerMarginDO;
	}

	public MarginSellerDO getSellerMarginDO() {
		return marginSellerDO;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	public MarginSellerOrderQuery getQuery(){
		return query;
	}

	public void setQuery(MarginSellerOrderQuery query){
		this.query = query;
	}

	public void setPinjuMargin(String pinjuMargin){
		this.pinjuMargin = pinjuMargin;
	}

	public String getPinjuMargin(){
		return pinjuMargin;
	}

	public void setCurrentMargin(String currentMargin){
		this.currentMargin = currentMargin;
	}

	public String getCurrentMargin(){
		return currentMargin;
	}

	public void setRequireAmount(String requireAmount){
		this.requireAmount = requireAmount;
	}

	public String getRequireAmount(){
		return requireAmount;
	}
	
	public DirectPayParamDO getDirectPayParamDO() {
		return directPayParamDO;
	}

	public void setDirectPayParamDO(DirectPayParamDO directPayParamDO) {
		this.directPayParamDO = directPayParamDO;
	}
	
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public void setCategoryMarginManager(CategoryMarginManager categoryMarginManager) {
		this.categoryMarginManager = categoryMarginManager;
	}

}
