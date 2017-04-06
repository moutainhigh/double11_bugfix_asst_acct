package com.yuwang.pinju.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * @Discription: 物流模板拦截器
 * @Project: pinju-web
 * @Package: com.yuwang.pinju.web.interceptor
 * @Title: LogisticsInterceptor.java
 * @author: [贺泳]
 * @date 2011-11-8 下午05:31:00
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class LogisticsInterceptor extends AbstractInterceptor {
	/**
	 * @Description: serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Log log = LogFactory.getLog(LogisticsInterceptor.class);
	
	/**
	 * 没有开店
	 */
	private final static String NOT_OPEN = "NOT_OPEN";
	
	/**
	 * 店铺已经关闭
	 */
	private final static String IS_CLOSE ="IS_CLOSE";
	
	/**
	 * 店铺不存在
	 */
	private final static String NOT_EXIST ="NOT_EXIST";
	
	/**
	 * 当前没有登录
	 */
	private final static String LOGIN = "login";
	
	/**
	 * 店铺管理
	 */
	private ShopShowInfoManager shopShowInfoManager;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		/**
		 * 判断用户是否开店 
		 */
		ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(login.getMasterMemberId(), null);
		if(shopInfoDO == null || shopInfoDO.getShopId() == null){
			return NOT_OPEN;
		}else{
			if(shopInfoDO.getShopId()!= null && shopInfoDO.getApproveStatus() == ShopConstant.APPROVE_STATUS_HEGUI ){
				return IS_CLOSE;	
			}else if(shopInfoDO.getShopId()!= null && shopInfoDO.getApproveStatus() != ShopConstant.APPROVE_STATUS_YES ){
				return NOT_EXIST;
			}
		}
		return invocation.invoke();
	}
	
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
}
