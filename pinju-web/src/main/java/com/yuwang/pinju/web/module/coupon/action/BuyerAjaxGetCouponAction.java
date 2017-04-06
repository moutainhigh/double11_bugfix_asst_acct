package com.yuwang.pinju.web.module.coupon.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.coupon.manager.CouponManager;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @Discription: ajax异步取得买家的优惠券信息(立即购买详情页面和购物车结算详情页面使用)
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.trade.ao.impl
 * @Title: TenSubAccountAOImpl.java
 * @author: [贺泳]
 * @date 2011-11-28 上午10:00:22
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class BuyerAjaxGetCouponAction extends BaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券Manager
	 */
	private CouponManager couponManager;
	/**
	 * 买家的优惠券信息List
	 */
	List<TradeCouponDO> tradeCouponList;

	/**
	 * 店铺优惠券Map
	 */
	private Map<Long, List<TradeCouponDO>> tradeCouponMap;
    
    private Long shopId;
    
    private Long[] muchShopsId;
    
	/**
	 * @Description: 根据买家ID和店铺ID,Ajax异步取得买家的优惠券信息
	 * @author [贺泳]
	 * @date 2011-11-28 下午10:01:23
	 * @version 1.0
	 * @param tenSubAccountDO
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String shopCoupon() {
		try {
			tradeCouponMap = new HashMap<Long, List<TradeCouponDO>>();
			for (Long id : muchShopsId) {
				if(tradeCouponMap.get(id) == null){
					tradeCouponList = couponManager.getValidCoupon(this.getUserId(), id);
					tradeCouponMap.put(id, tradeCouponList);
				}
			}
		} catch (Exception e) {
			log.error("[Event BuyerAjaxGetCouponAction#ajaxGetShopCoupon]" + e);
		}
		return SUCCESS;
	}
	
	/**
	 * @Description: 根据买家ID和店铺ID,Ajax异步取得买家的优惠券信息
	 * @author [贺泳]
	 * @date 2011-11-28 下午10:01:23
	 * @version 1.0
	 * @param tenSubAccountDO
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String ajaxGetShopCoupon() {
		try {
			tradeCouponList = couponManager.getValidCoupon(this.getUserId(), shopId);
		} catch (Exception e) {
			log.error("[Event BuyerAjaxGetCouponAction#ajaxGetShopCoupon]" + e);
		}
		return SUCCESS;
	}

	/**
	 * 查询字符串转换成Map<br/>
	 * name1=key1&name2=key2&...
	 * 
	 * @param queryString
	 * @return
	 */
	private Map<String, Long> queryString2Map(String queryString) {
		if (null == queryString || "".equals(queryString)) {
			return null;
		}

		Map<String, Long> m = new HashMap<String, Long>();
		String[] strArray = queryString.split(";");
		for (int index = 0; index < strArray.length; index++) {
			String pair = strArray[index];
			this.putMapByPair(pair, m);
		}
		return m;

	}

	/**
	 * 把键值添加至Map<br/>
	 * pair:name=value
	 * 
	 * @pa mayuanchao(马远超) 14:38:04 ram pair name=value
	 * @param m
	 */
	private void putMapByPair(String pair, Map<String, Long> m) {
		if (null == pair || "".equals(pair)) {
			return;
		}

		int indexOf = pair.indexOf(":");
		if (-1 != indexOf) {
			String k = pair.substring(0, indexOf);
			String v = pair.substring(indexOf + 1, pair.length());
			if (null != k && !"".equals(k)) {
				m.put(k, Long.parseLong(v));
			}
		} 
	}

	/**
	 * @Description: 取得当前登录的会员ID
	 * @author [贺泳]
	 * @date 2011-11-28 下午10:01:23
	 * @version 1.0
	 * @param tenSubAccountDO
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMemberId();
		} else {
			return 0;
		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	public List<TradeCouponDO> getTradeCouponList() {
		return tradeCouponList;
	}

	public void setTradeCouponList(List<TradeCouponDO> tradeCouponList) {
		this.tradeCouponList = tradeCouponList;
	}

	public Map<Long, List<TradeCouponDO>> getTradeCouponMap() {
		return tradeCouponMap;
	}

	public void setTradeCouponMap(Map<Long, List<TradeCouponDO>> tradeCouponMap) {
		this.tradeCouponMap = tradeCouponMap;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long[] getMuchShopsId() {
		return muchShopsId;
	}

	public void setMuchShopsId(Long[] muchShopsId) {
		this.muchShopsId = muchShopsId;
	}

}
