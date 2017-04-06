package com.yuwang.pinju.web.module.coupon.action;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.coupon.ao.TradeCouponBatchAO;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

@SuppressWarnings("serial")
public class TradeCouponBatchAction extends BaseAction {

	private TradeCouponBatchAO tradeCouponBatchAO;
	
	private TradeCouponBatchDO tcb;
	
	private List<TradeCouponBatchDO> couponBatchList;
	
	private String errorMessage;
	
	private MemberAsstLog memberAsstLog;
	
	/**
	 * 查询已存在优惠券批次数量
	 */
	private long allCount;
	
	/**
	 * 增加优惠券发放数量
	 */
	private Long increaseReleaseCount;
	
	/**
	 * 跳转新增优惠券页面
	 * @return
	 */
	public String addCouponBatch() {
		return SUCCESS;
	}
	
	/**
	 * 新增一个优惠券批次
	 * @return
	 */
	@AssistantPermission(target = "promotion",action = "coupon")
	public String saveCouponBatch() {
		this.tcb.setSellerId(this.getUserId());
		long batchId = tradeCouponBatchAO.addTradeCouponBatch(this.tcb);
		memberAsstLog.log("设置店铺优惠券"+batchId);
		return SUCCESS;
	}

	/**
	 * 更新一个优惠券批次
	 * @return
	 */
	@AssistantPermission(target = "promotion",action = "coupon")
	public String updateCouponBatchById() {
		TradeCouponBatchDO cbd = tradeCouponBatchAO.getTradeCouponBatchById(tcb.getId());
		if (cbd == null) {
			this.setErrorMessage("此优惠券不存在！");
			return ERROR;
		}
		if (TradeCouponBatchDO.BATCHSTATUS_NORMAL.equals(cbd.getBatchStatus()) && new Date().before(cbd.getCouponInvalidDate()) && cbd.getSellerId().equals(this.getUserId())) {
			tcb.setSellerId(this.getUserId());
			if (this.getIncreaseReleaseCount() != null) {
				tcb.setReleaseCount(this.getIncreaseReleaseCount()+tcb.getReleaseCount());
				tcb.setSurplusAmount(this.getIncreaseReleaseCount()+tcb.getSurplusAmount());
			}
			long l = tradeCouponBatchAO.updateTradeCouponBatchById(tcb);
			
			memberAsstLog.log("设置店铺优惠券"+tcb.getId());
			
			return SUCCESS;
		} else {
			if (!cbd.getSellerId().equals(this.getUserId())) {
				this.setErrorMessage("此优惠券不属于自己！");
			} else if (TradeCouponBatchDO.BATCHSTATUS_GETINVALID.equals(cbd.getBatchStatus()) 
					|| TradeCouponBatchDO.BATCHSTATUS_INVALID.equals(cbd.getBatchStatus())
					|| new Date().after(cbd.getCouponInvalidDate())) {
				this.setErrorMessage("此活动已失效，无法编辑！");
			} else if (TradeCouponBatchDO.BATCHSTATUS_CLOSE.equals(cbd.getBatchStatus())) {
				this.setErrorMessage("此活动已被删除，无法编辑！");
			}
			return ERROR;
		}
	}
	
	/**
	 * 关闭一个优惠券批次
	 * @return
	 */
	@AssistantPermission(target = "promotion",action = "coupon")
	public String closeCouponBatchById() {
		TradeCouponBatchDO cbd = tradeCouponBatchAO.getTradeCouponBatchById(tcb.getId());
		if (TradeCouponBatchDO.BATCHSTATUS_INVALID.equals(cbd.getBatchStatus()) || TradeCouponBatchDO.BATCHSTATUS_GETINVALID.equals(cbd.getBatchStatus())
				|| (TradeCouponBatchDO.BATCHSTATUS_NORMAL.equals(cbd.getBatchStatus()) && new Date().after(cbd.getCouponInvalidDate()))) {
			tcb.setSellerId(this.getUserId());
			tcb.setBatchStatus(TradeCouponBatchDO.BATCHSTATUS_CLOSE);
			long l = tradeCouponBatchAO.updateTradeCouponBatchById(tcb);
			
			memberAsstLog.log("设置店铺优惠券"+tcb.getId());
			
			return SUCCESS;
		} else {
			if (TradeCouponBatchDO.BATCHSTATUS_CLOSE.equals(cbd.getBatchStatus())) {
				this.setErrorMessage("此活动已被删除！");
			}
			return ERROR;
		}
	}
	
	/**
	 * 失效优惠券领取
	 * @return
	 */
	@AssistantPermission(target = "promotion",action = "coupon")
	public String invalidCouponBatchById() {
		TradeCouponBatchDO cbd = tradeCouponBatchAO.getTradeCouponBatchById(tcb.getId());
		if (TradeCouponBatchDO.BATCHSTATUS_NORMAL.equals(cbd.getBatchStatus()) && new Date().before(cbd.getCouponInvalidDate())) {
			tcb.setSellerId(this.getUserId());
			tcb.setBatchStatus(TradeCouponBatchDO.BATCHSTATUS_GETINVALID);
			long l = tradeCouponBatchAO.updateTradeCouponBatchById(tcb);
			
			memberAsstLog.log("设置店铺优惠券"+tcb.getId());
			
			return SUCCESS;
		} else {
			if (TradeCouponBatchDO.BATCHSTATUS_CLOSE.equals(cbd.getBatchStatus())) {
				this.setErrorMessage("此活动已被删除，无法失效！");
			} else if (TradeCouponBatchDO.BATCHSTATUS_GETINVALID.equals(cbd.getBatchStatus()) 
					|| TradeCouponBatchDO.BATCHSTATUS_INVALID.equals(cbd.getBatchStatus())
					|| new Date().after(cbd.getCouponInvalidDate())) {
				this.setErrorMessage("此活动已失效！");
			}
			return ERROR;
		}
		
	}
	
	/**
	 * 批量更新失效优惠券
	 * @return
	 */
	@AssistantPermission(target = "promotion",action = "coupon")
	public String updateCouponBatchInvalid() {
		tcb = new TradeCouponBatchDO();
		tcb.setSellerId(this.getUserId());
		long l = tradeCouponBatchAO.updateTradeCouponBatchByInvalid(tcb);
		
		memberAsstLog.log("设置店铺优惠券");
		
		return SUCCESS;
	}
	
	/**
	 * 根据批次id查询要编辑的优惠券 ,若状态是失效或关闭则跳回列表页面
	 * @return
	 */
	public String queryCouponBatchById() {
		tcb = tradeCouponBatchAO.getTradeCouponBatchById(tcb.getId());
		
		if (TradeCouponBatchDO.BATCHSTATUS_NORMAL.equals(tcb.getBatchStatus()) && new Date().before(tcb.getCouponInvalidDate())) {
			return SUCCESS;
		} else {
			if (TradeCouponBatchDO.BATCHSTATUS_CLOSE.equals(tcb.getBatchStatus())) {
				this.setErrorMessage("此活动已被删除，无法编辑！");
			} else if (TradeCouponBatchDO.BATCHSTATUS_GETINVALID.equals(tcb.getBatchStatus()) 
					|| TradeCouponBatchDO.BATCHSTATUS_INVALID.equals(tcb.getBatchStatus())
					|| new Date().after(tcb.getCouponInvalidDate())) {
				this.setErrorMessage("此活动已失效，无法编辑！");
			}
			return ERROR;
		}
	}
	
	/**
	 * 优惠券批次分页查询
	 * @return
	 */
	public String queryCouponBatchListAll() {
		Result result = new ResultSupport();
		
		if (this.tcb == null) tcb = new TradeCouponBatchDO();
		
		tcb.setSellerId(this.getUserId());
		result = tradeCouponBatchAO.queryTradeCouponBatchAll(tcb);
		
		allCount = (Integer) result.getModel("CouponBatchCount");
		
		this.couponBatchList = (List<TradeCouponBatchDO>) result.getModel("CouponBatchListAll");
		
		return SUCCESS;
	}
	
	public void setTradeCouponBatchAO(TradeCouponBatchAO tradeCouponBatchAO) {
		this.tradeCouponBatchAO = tradeCouponBatchAO;
	}

	public List<TradeCouponBatchDO> getCouponBatchList() {
		return couponBatchList;
	}

	public void setCouponBatchList(List<TradeCouponBatchDO> couponBatchList) {
		this.couponBatchList = couponBatchList;
	}

	public TradeCouponBatchDO getTcb() {
		return tcb;
	}

	public void setTcb(TradeCouponBatchDO tcb) {
		this.tcb = tcb;
	}
	
	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}

	public long getAllCount() {
		return allCount;
	}

	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}

	public Long getIncreaseReleaseCount() {
		return increaseReleaseCount;
	}

	public void setIncreaseReleaseCount(Long increaseReleaseCount) {
		this.increaseReleaseCount = increaseReleaseCount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
