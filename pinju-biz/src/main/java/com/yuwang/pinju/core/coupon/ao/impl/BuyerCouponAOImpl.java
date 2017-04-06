package com.yuwang.pinju.core.coupon.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.CouponResultCode;
import com.yuwang.pinju.core.coupon.ao.BuyerCouponAO;
import com.yuwang.pinju.core.coupon.manager.CouponManager;
import com.yuwang.pinju.core.coupon.manager.TradeCouponBatchManager;
import com.yuwang.pinju.core.coupon.manager.TradeCouponTallyManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.coupon.TradeCouponQueryDO;
import com.yuwang.pinju.domain.coupon.TradeCouponTallyDO;

public class BuyerCouponAOImpl implements BuyerCouponAO{
	private  final   Log log = LogFactory.getLog(this.getClass().getName());
	
	private CouponManager couponManager;
	private TradeCouponBatchManager tradeCouponBatchManager;
	private TradeCouponTallyManager tradeCouponTallyManager;
	
	//设置result
	private Result getResult(Result result, String errorMessage, Boolean isSuccess){
		if(result == null)
			result = new ResultSupport();
		
		result.setSuccess(true);
		result.setModel("errorMessage", errorMessage);
		result.setModel("isSuccess", isSuccess);
		
		return result;
	}
	
	//领取优惠券
	private Boolean buyerGetCoupon(Long batchId, Long buyerId, String buyerNick, TradeCouponBatchDO couponBatch) throws ManagerException{

		TradeCouponDO coupon = new TradeCouponDO();
		coupon.setCouponBatchId(batchId);
		coupon.setBuyerId(buyerId);
		coupon.setUseStatus(TradeCouponDO.COUPON_NOT_USED);
		coupon.setCouponStatus(TradeCouponBatchDO.BATCHSTATUS_NORMAL);
		coupon.setBuyerNick(buyerNick);
		coupon.setInvalidDate(couponBatch.getCouponInvalidDate());

		Date now = new Date();
		coupon.setCouponCreateDate(now);
		coupon.setGmtCreate(now);
		coupon.setGmtModify(now);
		coupon.setCouponModifyDate(now);
		coupon.setCouponMoney(couponBatch.getCouponMoney());
		coupon.setSellerId(couponBatch.getSellerId());
		coupon.setSellerNick(couponBatch.getSellerNick());
		coupon.setShopId(couponBatch.getShopId());
		coupon.setShopName(couponBatch.getShopName());
		coupon.setUseCondition(couponBatch.getUseCondition());
		

		return couponManager.buyerGetCoupon(coupon);
	}
	
	/**
	 * 买家领取优惠券
	 * 
	 * @param batchId 批次id
	 * @param buyerId 买家id
	 * @param buyerNick 买家昵称
	 * @return
	 */
	public Result buyerObtainCoupon(Long batchId, Long buyerId, String buyerNick){
		Result result = new ResultSupport();
		//领取是否成功，为true表示成功
		Boolean isSuccess = false;
		
		//失败原因
		String errorMessage = "";
		
		try {
			//校验批次
			if(batchId == null){
				errorMessage = "无效的领取链接";
				isSuccess = false;
				return getResult(result, errorMessage, isSuccess);
			}
			
			TradeCouponBatchDO couponBatch = tradeCouponBatchManager.getTradeCouponBatchById(batchId);
			
			if(couponBatch == null){
				errorMessage = "无效的领取链接";
				isSuccess = false;
				return getResult(result, errorMessage, isSuccess);
			}
			
			
			//校验卖家
			if(couponBatch.getSellerId().compareTo(buyerId) == 0){
				errorMessage = "领取失败！您不可以领取自己的优惠券！";
				isSuccess = false;
				return getResult(result, errorMessage, isSuccess);
			}
			
			//优惠券有效期到了
			if(couponBatch.getCouponInvalidDate().compareTo(new Date()) < 0){
				errorMessage = "领取失败！此优惠券已失效！";
				isSuccess = false;
				return getResult(result, errorMessage, isSuccess);
			}
			
			//检查优惠券批次状态
			if(couponBatch.getBatchStatus().compareTo(TradeCouponBatchDO.BATCHSTATUS_NORMAL) != 0){
				
				//领取失效
				if(couponBatch.getBatchStatus().compareTo(TradeCouponBatchDO.BATCHSTATUS_GETINVALID) == 0){
					errorMessage = "领取失败！此优惠券已经停止发放！";
				}
				
				//失效
				if(couponBatch.getBatchStatus().compareTo(TradeCouponBatchDO.BATCHSTATUS_INVALID) == 0){
					errorMessage = "领取失败！此优惠券已失效！";
				}
				
				//关闭
				if(couponBatch.getBatchStatus().compareTo(TradeCouponBatchDO.BATCHSTATUS_CLOSE) == 0){
					errorMessage = "领取失败！此优惠券已停止发放！";	
				}
				
				isSuccess = false;
				return getResult(result, errorMessage, isSuccess);
			}
			
			//校验剩余数量
			Long surplusAmount = couponBatch.getSurplusAmount();
			if(surplusAmount.compareTo(0L) == 0){
				errorMessage = "领取失败！这张优惠券已经被领完了！";	
				
				isSuccess = false;
				return getResult(result, errorMessage, isSuccess);
			}
			
			//校验领取数量
			Integer restrictCount = couponBatch.getRestrictAmount();
			
			//0表示不限制
			if(restrictCount.compareTo(0) != 0){
				TradeCouponTallyDO tradeCouponTallyDO = tradeCouponTallyManager.loadCouponTally(batchId, buyerId);
				if(tradeCouponTallyDO != null){
					Integer couponTally = tradeCouponTallyDO.getCouponTally();
					
					if(couponTally.compareTo(restrictCount) >= 0){
						errorMessage = "领取失败！您可以领取的优惠券超过限领数量！";
						isSuccess = false;
						return getResult(result, errorMessage, isSuccess);
					}
				}
			}

			//领取优惠券
			Boolean isbuyerGetCoupon = buyerGetCoupon(batchId, buyerId, buyerNick, couponBatch);
			if(!isbuyerGetCoupon){
				log.error("BuyerCouponAOImpl.buyerGetCoupon error");
				
				errorMessage = "领取失败！";
				isSuccess = false;
				return getResult(result, errorMessage, isSuccess);
			}
			
		} catch (ManagerException e) {
			log.error("BuyerCouponAOImpl.buyerObtainCoupon error", e);
			
			errorMessage = "领取失败！";
			isSuccess = false;
			return getResult(result, errorMessage, isSuccess);
		}
		
		isSuccess = true;
		
		return getResult(result, "", isSuccess);
	}
	
	
	@Override
	public Result getCoupons(TradeCouponQueryDO queryDO) {
		Result result = new ResultSupport();
		try {
			List<TradeCouponDO> couponList = couponManager.getTradeCouponDOList(queryDO);
			
			if(couponList == null){
				result.setSuccess(false);
				return result;
			}
			result.setModel("buyCoupons",couponList);
			result.setSuccess(true);
			return result;
			
		} catch (ManagerException e) {
			log.error("Event=[BuyerCouponAOImpl#getCoupons] 获取买家优惠券记录报错:" , e);
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public int getCouponsNum(TradeCouponQueryDO queryDO) {
		try {
			return couponManager.getTradeCouponDOListNum(queryDO);
		} catch (ManagerException e) {
			log.error("Event=[BuyerCouponAOImpl#getCouponsNum] 获取买家优惠券记录数报错:" , e);
		}
		return 0;
	}
	
	@Override
	public Result buyerDeleteCoupon(Long couponID, Long userId) {
		Result result = new ResultSupport();
		
		//1.判断该优惠券是否属于买家
		  //2.判断优惠券状态
		  
		try {
			if(EmptyUtil.isBlank(couponID) || EmptyUtil.isBlank(userId)){
				result.setSuccess(false);
				result.setResultCode(ResultCodeMsg.getResultMessage(CouponResultCode.COUPON_NOTEXITS));
				return result;
			}
			
			TradeCouponDO couponDO = couponManager.getTradeCouponDOById(couponID);
			if(couponDO == null){
				result.setSuccess(false);
				result.setResultCode(ResultCodeMsg.getResultMessage(CouponResultCode.COUPON_NOTEXITS));
				return result;
			}else {
				if(couponDO.getBuyerId().compareTo(userId) != 0){
					result.setSuccess(false);
					result.setResultCode(ResultCodeMsg.getResultMessage(CouponResultCode.COUPON_BUYERMEMBERERROR));
					return result;
				}
				
				if(couponDO.getUseStatus().compareTo(TradeCouponDO.COUPON_USED)!=0 && couponDO.getCouponStatus().compareTo(TradeCouponBatchDO.BATCHSTATUS_GETINVALID) != 0){
					result.setSuccess(false);
					result.setResultCode(ResultCodeMsg.getResultMessage(CouponResultCode.COUPON_DELETE_STATEERROR,couponID));
					return result;
				}
				if(couponDO.getCouponStatus().compareTo(TradeCouponBatchDO.BATCHSTATUS_CLOSE) == 0){
					result.setSuccess(false);
					result.setResultCode(ResultCodeMsg.getResultMessage(CouponResultCode.COUPON_DELETE_STATEERROR,couponID));
					return result;
				}
				
				couponDO.setCouponStatus(TradeCouponBatchDO.BATCHSTATUS_CLOSE);
				//执行删除操作
				boolean flag = couponManager.buyerDeleteCoupon(couponDO) > 0;
				if(flag){
					result.setSuccess(true);
					return result;
				}else {
					result.setSuccess(false);
					result.setResultCode(CouponResultCode.COUPON_POST_EXCEPTION);
					return result;
				}
				
			}
		} catch (ManagerException e) {
			log.error("Event=[BuyerCouponAOImpl#closeCoupon] 买家删除优惠券报错:" , e);
			result.setResultCode(CouponResultCode.COUPON_DELETE_STATEERROR);
			result.setSuccess(false);
			return result;
		}
	}
	
	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	public void setTradeCouponBatchManager(TradeCouponBatchManager tradeCouponBatchManager) {
		this.tradeCouponBatchManager = tradeCouponBatchManager;
	}

	public void setTradeCouponTallyManager(TradeCouponTallyManager tradeCouponTallyManager) {
		this.tradeCouponTallyManager = tradeCouponTallyManager;
	}

}
