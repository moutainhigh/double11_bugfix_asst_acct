package com.yuwang.pinju.web.module.coupon.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.coupon.ao.TradeCouponBatchAO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchVO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * <p>卖家管理优惠券</p>
 * 
 * @author 李鑫
 * @since 2011-11-25
 */
public class SellerCouponAction extends BaseAction {

	private TradeCouponBatchAO tradeCouponBatchAO;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result;
	private List<TradeCouponBatchVO> sellCouponList = new ArrayList<TradeCouponBatchVO>();
	private TradeCouponBatchDO query = new TradeCouponBatchDO();
	
	//优惠面额
	private String couponMoney;
	//发放类型
	private Integer releaseType;
	//优惠券有效期
	  //1.开始时间
	private String couponCreateDate;
	  //2.结束时间
	private String couponInvalidDate;
	// 当前页数
	private Integer currentPage = 1;
	//单选按钮控制
	private Long radioValue = 0L;
	
	//默认时间条件 0:无默认时间  其他：有默认时间
	private String timeMark;

	@SuppressWarnings("unchecked")
	public String sellerCoupon(){
		try {
			if(StringUtil.isBlank(timeMark)){
				Date date = new Date();
				couponInvalidDate = DateFormatUtils.format(date, "yyyy-MM-dd");
				couponCreateDate = DateFormatUtils.format(DateUtils.addMonths(date, -6), "yyyy-MM-dd");
			}
			
			query.setSellerId(this.getUserId());
			if(StringUtil.isNotBlank(couponMoney)){
				query.setCouponMoney(MoneyUtil.getDollarToCent(couponMoney));
			}
			
			if(StringUtil.isNotBlank(couponInvalidDate)){
				query.setCouponInvalidDate(DateUtil.parse("yyyy-MM-dd", couponInvalidDate));
			}
			if(StringUtil.isNotBlank(couponCreateDate)){
				query.setCouponCreateDate(DateUtil.parse("yyyy-MM-dd",couponCreateDate));
			}
			query.setReleaseType(releaseType);
			
			if(query.getCouponCreateDate() != null){
				Calendar cd = Calendar.getInstance();
				cd.setTime(query.getCouponCreateDate());
				cd.set(Calendar.HOUR, 00);
				cd.set(Calendar.MINUTE, 00);
				cd.set(Calendar.SECOND, 00);
				query.setCouponCreateDate(cd.getTime());
			}
			
			if(query.getCouponInvalidDate() != null){
				Calendar ce = Calendar.getInstance();
				ce.setTime(query.getCouponInvalidDate());
				ce.set(Calendar.HOUR, 23);
				ce.set(Calendar.MINUTE, 59);
				ce.set(Calendar.SECOND, 59);
				query.setCouponInvalidDate(ce.getTime());
			}
			
			
			int count = tradeCouponBatchAO.sellerQueryTradeCouponBatchNum(query);
			if(count == 0){
				count = 1;
			}
			
			query.setItems(count);
			query.setItemsPerPage(20);
			query.setPage(currentPage);
			query.setAction("/sellerCoupon/sellerCoupon.htm");
			
			result = tradeCouponBatchAO.sellerQueryTradeCouponBatch(query);
			if(result.isSuccess()){
				sellCouponList = (List<TradeCouponBatchVO>) result.getModel("couponBatchList");
			}
			
		} catch (Exception e) {
			sellCouponList = new ArrayList<TradeCouponBatchVO>();
			query.setItems(1);
		}
		
		return SUCCESS;
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
	
	public void setTradeCouponBatchAO(TradeCouponBatchAO tradeCouponBatchAO) {
		this.tradeCouponBatchAO = tradeCouponBatchAO;
	}

	public String getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(String couponMoney) {
		this.couponMoney = couponMoney;
	}

	public Integer getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(Integer releaseType) {
		this.releaseType = releaseType;
	}

	public String getCouponCreateDate() {
		return couponCreateDate;
	}

	public void setCouponCreateDate(String couponCreateDate) {
		this.couponCreateDate = couponCreateDate;
	}

	public String getCouponInvalidDate() {
		return couponInvalidDate;
	}

	public void setCouponInvalidDate(String couponInvalidDate) {
		this.couponInvalidDate = couponInvalidDate;
	}

	public List<TradeCouponBatchVO> getSellCouponList() {
		return sellCouponList;
	}

	public void setSellCouponList(List<TradeCouponBatchVO> sellCouponList) {
		this.sellCouponList = sellCouponList;
	}

	public TradeCouponBatchDO getQuery() {
		return query;
	}
	
	public void setQuery(TradeCouponBatchDO query) {
		this.query = query;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}


	public Long getRadioValue() {
		return radioValue;
	}


	public void setRadioValue(Long radioValue) {
		this.radioValue = radioValue;
	}


	public String getTimeMark() {
		return timeMark;
	}


	public void setTimeMark(String timeMark) {
		this.timeMark = timeMark;
	}
	
	
}
