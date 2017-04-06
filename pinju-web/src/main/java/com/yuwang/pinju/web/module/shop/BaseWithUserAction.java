/**
 * 
 */
package com.yuwang.pinju.web.module.shop;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * @author liyouguo
 * 
 */
public class BaseWithUserAction implements Action {

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private ShopShowInfoAO shopShowInfoAO;
	
	protected String errorMessage="错误";
	
	protected boolean hasAsstPerm(String target,String action) {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(login.isAssistantAccount()){
			return login.hasAsstPerm(target, action);
		}else{
			return true;
		}
	}
	
	/**
	 * 获取当前登录用户的ID
	 * 
	 * @author liyouguo
	 * @return
	 */
	protected long getUserId() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(login.isLogin())
			return login.getMasterMemberId();
		return 0;
	}
	
	protected String getNickName() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(login.isLogin())
			return login.getMasterMemberName();
		return "";
	}
	
	protected Integer getUserShopId(){
		if(getUserId()!=0){
			Integer shopId = shopShowInfoAO.queryShopIdByUserId(getUserId());
			return shopId;
		}
		return null;
	}
	
	protected Integer getUserShopId(Long memberId){
		if(memberId != null&& memberId != 0){
			Integer shopId = shopShowInfoAO.queryShopIdByUserId(memberId);
			return shopId;
		}
		return null;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}

	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	public String getClientIp() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
