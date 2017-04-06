package com.yuwang.pinju.web.module.coupon.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.coupon.ao.BuyerCouponAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.coupon.BuyerTradeCouponVO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.coupon.TradeCouponQueryDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * <p>买家管理优惠券</p>
 * 
 * @author 李鑫
 * @since 2011-11-24
 */
public class BuyerCouponAction extends BaseAction {

	
	private BuyerCouponAO buyerCouponAO;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result;
	private TradeCouponQueryDO query = new TradeCouponQueryDO();
	private List<TradeCouponDO> couponList;
	
	// 当前页数
	private Integer currentPage = 1;
	// 优惠券使用状态(10未使用，20已使用)
	private Long useStatus;
	//默认显示状态 标识
	private String defaultSearchTy;
	//优惠券编号
	private Long couponId;
	
	@SuppressWarnings("unchecked")
	public String buyerCoupon(){
		try {
			if(StringUtil.isBlank(defaultSearchTy)){
				useStatus = (long)TradeCouponDO.COUPON_NOT_USED;
			}
			
			if(!EmptyUtil.isBlank(useStatus) && useStatus.compareTo(30L) == 0){
				query.setCouponStatus(TradeCouponBatchDO.BATCHSTATUS_GETINVALID);
			}
			
			if(!EmptyUtil.isBlank(useStatus)){
				if(useStatus.compareTo(30L) == 0){
					query.setUseStatus(null);
				}else if(useStatus.compareTo((long)TradeCouponDO.COUPON_NOT_USED)== 0){
					query.setCouponStatus(TradeCouponBatchDO.BATCHSTATUS_NORMAL);
					query.setUseStatus(useStatus);
				}else {
					query.setUseStatus(useStatus);
				}
				
			}
			
			query.setBuyerId(this.getUserId());
				
			int count = buyerCouponAO.getCouponsNum(query);
			if(count == 0){
				count = 1;
			}
			
			query.setItems(count);
			query.setItemsPerPage(20);
			query.setPage(currentPage);
			query.setAction("/buyerCoupon/buyerCoupon.htm");
			
			result = buyerCouponAO.getCoupons(query);
			
			if(result.isSuccess()){
				couponList = (List<TradeCouponDO>) result.getModel("buyCoupons");
			}
		} catch (Exception e) {
			couponList = new ArrayList<TradeCouponDO>();
			query.setItems(1);
		}
		return SUCCESS;
	}
	
	public String closeCoupon(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			result = buyerCouponAO.buyerDeleteCoupon(couponId, this.getUserId());
			String errorConstant = result.getResultCode();
			
			StringBuffer xml = new StringBuffer(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<isSuccess>" + result.isSuccess() + "</isSuccess>");
			xml.append("<errorConstant>"+errorConstant+"</errorConstant>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(xml.toString());
		} catch (Exception e) {
			log.error("cancelOrder id: ", e);
		}
		return null;
	}

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
	
	public void setBuyerCouponAO(BuyerCouponAO buyerCouponAO) {
		this.buyerCouponAO = buyerCouponAO;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Long getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Long useStatus) {
		this.useStatus = useStatus;
	}

	public List<TradeCouponDO> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<TradeCouponDO> couponList) {
		this.couponList = couponList;
	}

	public TradeCouponQueryDO getQuery() {
		return query;
	}

	public void setQuery(TradeCouponQueryDO query) {
		this.query = query;
	}

	public String getDefaultSearchTy() {
		return defaultSearchTy;
	}

	public void setDefaultSearchTy(String defaultSearchTy) {
		this.defaultSearchTy = defaultSearchTy;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}	
	
	
}
