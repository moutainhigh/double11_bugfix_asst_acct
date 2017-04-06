package com.yuwang.pinju.web.module.coupon.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.coupon.ao.BuyerCouponAO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;


/**
 * <p>买家领取优惠券</p>
 * 
 * @author shihongbo
 * @since 2011-11-23
 */          
@SuppressWarnings("serial")
public class BuyerGetCouponAction extends BaseAction implements Action{

	private static Log log = LogFactory.getLog(BuyerGetCouponAction.class);
	
	//优惠券批次id
	private Long bid;

	//领取是否成功，为true表示成功
	private Boolean isSuccess;
	
	//是否登录，为true表示已经登录
	private Boolean isLogin;
	
	//失败原因
	private String errorMessage;
	
	private BuyerCouponAO buyerCouponAO;

	private String callBack;
	
	private InputStream inputStream;
	
	/**
	 * 买家领取优惠券,返回结果页面
	 * 
	 * */
	public String buyerObtainCoupon() {
		CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
		
		//没有登录
		if(!loginInfo.isLogin()){
			
			try{	
				String returnUrl = "http://www.pinju.com/async/coupon/buyerObtainCoupon.htm?bid=" + bid;
				returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
				String loginUrl = "http://www.pinju.com/member/login.htm?returnUrl=" + returnUrl;
				response.sendRedirect(loginUrl);
			}catch (Exception e) {
				log.error("Event=[BuyerGetCouponAction.buyerGetCoupon] sendRedirect error");
			}
			
			return null;
		}

		//已经登录
		isLogin = true;
		Long buyerId = loginInfo.getMemberId();
		String buyerNick = loginInfo.getNickname();
		
		//领取优惠券
		Result result = buyerCouponAO.buyerObtainCoupon(bid, buyerId, buyerNick);
		
		errorMessage = (String)result.getModel("errorMessage");
		isSuccess = (Boolean)result.getModel("isSuccess");
		
		return SUCCESS;
	}
	
	/**
	 * 买家领取优惠券,返回jsonp格式
	 * */
	public String buyerGet() {
		CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
		
		//没有登录
		if(!loginInfo.isLogin()){
			isSuccess = false;
			isLogin = false;
			return getCouponDataByJsonp();
		}

		//已经登录
		isLogin = true;
		Long buyerId = loginInfo.getMemberId();
		String buyerNick = loginInfo.getNickname();
		
		//领取优惠券
		Result result = buyerCouponAO.buyerObtainCoupon(bid, buyerId, buyerNick);
		
		errorMessage = (String)result.getModel("errorMessage");
		isSuccess = (Boolean)result.getModel("isSuccess");
		
		return getCouponDataByJsonp();
	}

	private String getCouponDataByJsonp(){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("isSuccess", isSuccess);
		data.put("isLogin", isLogin);
		data.put("errorMessage", errorMessage);
		
		
		JSONArray array = JSONArray.fromObject(data);
		String result = callBack + "(" + array.toString() + ")";
		return response(result);
	}
	
	private String response(String responseData) {
		try {
			inputStream = new ByteArrayInputStream(responseData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setBuyerCouponAO(BuyerCouponAO buyerCouponAO) {
		this.buyerCouponAO = buyerCouponAO;
	}

	public Boolean getIsLogin() {
		return isLogin;
	}
}