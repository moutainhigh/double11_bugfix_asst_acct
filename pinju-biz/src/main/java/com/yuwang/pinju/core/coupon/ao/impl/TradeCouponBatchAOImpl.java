package com.yuwang.pinju.core.coupon.ao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.coupon.ao.TradeCouponBatchAO;
import com.yuwang.pinju.core.coupon.manager.TradeCouponBatchManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchVO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class TradeCouponBatchAOImpl extends BaseAO implements TradeCouponBatchAO {
	
	/**
	 * 优惠券批次
	 */
	private TradeCouponBatchManager tradeCouponBatchManager;
	
	/**
	 * 会员管理
	 */
	private MemberManager memberManager;

	/**
	 * 店铺管理
	 */
	private ShopShowInfoManager shopShowInfoManager;
	
	/**
	 * 根据店铺接口封装
	 * TODO 若同一会员可以开多家店则此接口存在问题
	 * @throws ManagerException
	 */
	private ShopInfoDO getShopBusinessInfoDO(Long sellerId) {
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(sellerId);
		ShopInfoDO shopInfoDO = null;
		List<ShopInfoDO> list = null;
		try {
			list = shopShowInfoManager.queryShopInfoByUserIdList(userIdList);
		} catch (ManagerException e) {
			log.error("shopShowInfoManager#queryShopInfoByUserIdList:" + e);
		}
		if (list != null && list.size() > 0) {
			shopInfoDO = list.get(0);
		}
		return shopInfoDO;
	}
	
	@Override
	public long addTradeCouponBatch(TradeCouponBatchDO tcb) {
		long batchId=0;
		try {
			MemberDO memberDO = memberManager.findMember(tcb.getSellerId());
			ShopInfoDO shop = getShopBusinessInfoDO(tcb.getSellerId());
			
			Calendar c = Calendar.getInstance();
			c.setTime(tcb.getCouponInvalidDate());
			c.set(Calendar.HOUR, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			tcb.setCouponInvalidDate(c.getTime());
			
			tcb.setSellerNick(memberDO.getNickname());
			tcb.setCouponMoney(MoneyUtil.getDollarToCent(tcb.getCouponMoney().toString()));
			tcb.setShopId((long) shop.getShopId());
			tcb.setShopName(shop.getName());
			tcb.setOperatorId(memberDO.getMemberId());
			tcb.setOperatorName(memberDO.getNickname());
			tcb.setUseCondition(MoneyUtil.getDollarToCent(tcb.getUseCondition().toString()));
			tcb.setSurplusAmount(tcb.getReleaseCount());
			tcb.setBatchStatus(TradeCouponBatchDO.BATCHSTATUS_NORMAL);
			tcb.setReleaseType(TradeCouponBatchDO.RELEASETYPE_BUYERGET);
			tcb.setCouponCreateDate(new Date());
			tcb.setCouponModifyDate(new Date());
			tcb.setCouponCode(tcb.getCouponCode().replaceAll("&quot;", "'"));
			tcb.setVersion(1L);
			
			batchId = tradeCouponBatchManager.addTradeCouponBatch(tcb);
		} catch (ManagerException e) {
			log.error("TradeCouponBatchManagerImpl#addTradeCouponBatch:" + e);
		}
		return batchId;
	}

	@Override
	public TradeCouponBatchDO getTradeCouponBatchById(long batchId) {
		try {
			return tradeCouponBatchManager.getTradeCouponBatchById(batchId);
		} catch (ManagerException e) {
			log.error("TradeCouponBatchManagerImpl#getTradeCouponBatchById:" + e);
		}
		return null;
	}

	@Override
	public long updateTradeCouponBatchById(TradeCouponBatchDO tcb) {
		try {
			MemberDO memberDO = memberManager.findMember(tcb.getSellerId());
			
			tcb.setCouponModifyDate(new Date());
			tcb.setOperatorId(memberDO.getMemberId());
			tcb.setOperatorName(memberDO.getNickname());
			if (tcb.getCouponCode() != null) {
				tcb.setCouponCode(tcb.getCouponCode().replaceAll("&quot;", "'"));
			}
			return tradeCouponBatchManager.updateTradeCouponBatchById(tcb);
		} catch (ManagerException e) {
			log.error("TradeCouponBatchManagerImpl#getTradeCouponBatchById:" + e);
		}
		return 0;
	}

	@Override
	public Result queryTradeCouponBatchAll(TradeCouponBatchDO tcb) {
		Result result = new ResultSupport();
		try {
			tcb.setBatchStatus(TradeCouponBatchDO.BATCHSTATUS_CLOSE);
			int count = (int) tradeCouponBatchManager.queryTradeCouponBatchAllCount(tcb);
			
			result.setModel("CouponBatchCount", count);
//			tcb.setItemsPerPage(2);
//			tcb.setSellerId(1111L);
			tcb.setItems(count);
			
			List<TradeCouponBatchDO> couponList = new ArrayList<TradeCouponBatchDO>();
			List<TradeCouponBatchDO> list = tradeCouponBatchManager.queryTradeCouponBatchAll(tcb);
			if (list != null) {
				for (TradeCouponBatchDO tb : list) {
					if (new Date().after(tb.getCouponInvalidDate())) {
						tb.setBatchStatus(TradeCouponBatchDO.BATCHSTATUS_INVALID);
					}
					couponList.add(tb);
				}
			}
			
			result.setModel("CouponBatchListAll", couponList);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("TradeCouponBatchManagerImpl#queryTradeCouponBatchAll:" + e);
		}
		return result;
	}

	@Override
	public long updateTradeCouponBatchByInvalid(TradeCouponBatchDO tcb) {
		try {
			MemberDO memberDO = memberManager.findMember(tcb.getSellerId());
			
			tcb.setCouponModifyDate(new Date());
			tcb.setOperatorId(memberDO.getMemberId());
			tcb.setOperatorName(memberDO.getNickname());
			tcb.setBatchStatus(TradeCouponBatchDO.BATCHSTATUS_CLOSE);
			return tradeCouponBatchManager.updateTradeCouponBatchByInvalid(tcb);
		} catch (ManagerException e) {
			log.error("TradeCouponBatchManagerImpl#updateTradeCouponBatchByInvalid:" + e);
		}
		return 0;
	}
	
	public void setTradeCouponBatchManager(
			TradeCouponBatchManager tradeCouponBatchManager) {
		this.tradeCouponBatchManager = tradeCouponBatchManager;
	}


	@Override
	public Result sellerQueryTradeCouponBatch(TradeCouponBatchDO couponBatchDO) {
		Result result = new ResultSupport();
		
		try {
			List<TradeCouponBatchVO> couponBatchVOList = tradeCouponBatchManager.getCouponBatchVO(couponBatchDO);
			
			if(couponBatchVOList == null){
				result.setSuccess(false);
				return result;
			}
			
			result.setModel("couponBatchList",couponBatchVOList);
			result.setSuccess(true);
			return result;
		} catch (ManagerException e) {
			e.printStackTrace();
			log.error("TradeCouponBatchManagerImpl#sellerQueryTradeCouponBatch:" + e);
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public int sellerQueryTradeCouponBatchNum(TradeCouponBatchDO couponBatchDO) {
		try {
			return tradeCouponBatchManager.getCouponBatchVONum(couponBatchDO);
		} catch (ManagerException e) {
			e.printStackTrace();
			log.error("TradeCouponBatchManagerImpl#sellerQueryTradeCouponBatchNum:" + e);
		}
		return 0;
	}


	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

}
