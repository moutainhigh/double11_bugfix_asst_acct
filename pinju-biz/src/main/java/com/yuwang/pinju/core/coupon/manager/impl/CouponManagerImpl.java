package com.yuwang.pinju.core.coupon.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.dao.CouponDAO;
import com.yuwang.pinju.core.coupon.dao.TradeCouponBatchDAO;
import com.yuwang.pinju.core.coupon.dao.TradeCouponTallyDAO;
import com.yuwang.pinju.core.coupon.manager.CouponManager;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.coupon.TradeCouponQueryDO;
import com.yuwang.pinju.domain.coupon.TradeCouponTallyDO;

public class CouponManagerImpl extends BaseManager implements CouponManager{
	private CouponDAO couponDAO;
	private TradeCouponBatchDAO tradeCouponBatchDAO;
	private TradeCouponTallyDAO tradeCouponTallyDAO;

	/**
	 * 保存优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新或者保存的优惠券id
	 */
	public Long saveTradeCoupon(TradeCouponDO tradeCouponDO)throws ManagerException{
		try {
			return couponDAO.saveTradeCoupon(tradeCouponDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#saveTradeCoupon]保存优惠券信息", e);
		}
	}
	
	/**
	 * 更新优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新优惠券id
	 */
	public Integer updateTradeCoupon(TradeCouponDO tradeCouponDO)throws ManagerException{
		try {
			return couponDAO.updateTradeCoupon(tradeCouponDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#updateTradeCoupon]更新优惠券信息", e);
		}
	}
	
	/**
	 * 更新优惠券关联的订单信息
	 * 
	 * @param couponId
	 * @param orderId
	 * 
	 * @return true表示更新成功
	 */
	public Boolean updateCouponOrder(Long couponId, Long orderId)throws ManagerException{
		TradeCouponDO tradeCouponDO = new TradeCouponDO();
		tradeCouponDO.setId(couponId);
		tradeCouponDO.setOrderId(orderId);
		tradeCouponDO.setUseStatus(TradeCouponDO.COUPON_USED);
		tradeCouponDO.setCouponAttributes("更新优惠券关联的订单id");
		
		Date now = new Date();
		tradeCouponDO.setCouponModifyDate(now);
		tradeCouponDO.setGmtModify(now);
		
		try {
			Integer n = couponDAO.updateCouponOrder(tradeCouponDO);
			return n == 1;
			
		}catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#updateCouponOrder]更新优惠券关联的订单信息", e);
		}
		
	}
	
	/**
	 * Created on 2011-11-25
	 * <p>Discription: 根据买家id和店铺id,获取买家能使用的优惠券</p>
	 * @param buyerId
	 * @param shopId
	 * @param couponMoney
	 * @return
	 * @throws ManagerException
	 * @author:[石洪波]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeCouponDO> getValidCoupon(Long buyerId, Long shopId) throws ManagerException{
		try {
			TradeCouponDO tradeCouponDO = new TradeCouponDO();
			tradeCouponDO.setBuyerId(buyerId);
			tradeCouponDO.setShopId(shopId);
			return couponDAO.getValidCoupon(tradeCouponDO);
			
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#getValidCoupon]获取优惠券信息报错：", e);
		}
	}
	

	/**
	 * 买家领取优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return true表示领取成功
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean buyerGetCoupon(final TradeCouponDO tradeCouponDO)throws ManagerException{
		return (Boolean) getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					//保存优惠券
					Long saveCount = couponDAO.saveTradeCoupon(tradeCouponDO);
					if(saveCount.compareTo(0L) == 0){
						log.error("Event=[CouponManagerImpl#buyerGetCoupon()]  saveTradeCoupon failed");
						status.setRollbackOnly();
						return false;
					}

					//更新批次
					TradeCouponBatchDO tcb = tradeCouponBatchDAO.getTradeCouponBatchById(tradeCouponDO.getCouponBatchId());
					//tcb.setOperatorId(tradeCouponDO.getBuyerId());
					//tcb.setOperatorName(tradeCouponDO.getBuyerNick());

					Integer buyerGetCount = tradeCouponBatchDAO.buyerGetCoupon(tcb);
					if(buyerGetCount.compareTo(0) == 0){
						log.error("Event=[CouponManagerImpl#buyerGetCoupon()]  tradeCouponBatchDAO.buyerGetCoupon failed");
						status.setRollbackOnly();
						return false;
					}

					//更新优惠券计数信息
					TradeCouponTallyDO tradeCouponTallyDO = tradeCouponTallyDAO.loadCouponTally(tradeCouponDO.getCouponBatchId(), tradeCouponDO.getBuyerId());

					//没有计数信息，插入记录
					if(tradeCouponTallyDO == null){
						TradeCouponTallyDO tallyDO = new TradeCouponTallyDO();
						tallyDO.setBuyerId(tradeCouponDO.getBuyerId());
						tallyDO.setBuyerNick(tradeCouponDO.getBuyerNick());
						tallyDO.setCouponBatchId(tradeCouponDO.getCouponBatchId());
						tallyDO.setCouponTally(1);
						Date now = new Date();
						tallyDO.setGmtCreate(now);
						tallyDO.setGmtModify(now);
						tallyDO.setTallyModifyDate(now);
						tradeCouponTallyDAO.addTradeCouponTally(tallyDO);
						
					//有计数信息，更新计数
					}else{
						Integer couponTally = tradeCouponTallyDO.getCouponTally();
						tradeCouponTallyDO.setCouponTally(couponTally + 1);
						Integer tallyUpdCount = tradeCouponTallyDAO.updateTradeCouponTally(tradeCouponTallyDO);

						if(tallyUpdCount.compareTo(0) == 0){
							log.error("Event=[CouponManagerImpl#buyerGetCoupon()]  updateTradeCouponTally failed");
							status.setRollbackOnly();
							
							return false;
						}
					}
				} catch (DaoException e) {
					log.error("Event=[CouponManagerImpl#buyerGetCoupon()]  buyer get coupon failed", e);
					status.setRollbackOnly();
					
					return false;
				}
				return true;
			}});
	}

	@Override
	public List<TradeCouponDO> getTradeCouponDOList(TradeCouponQueryDO queryDO)
			throws ManagerException {
		try {
			return couponDAO.getTradeCouponDOs(queryDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#getTradeCouponDo]获取优惠券信息报错：", e);
		}
	}


	@Override
	public int getTradeCouponDOListNum(TradeCouponQueryDO queryDO)
			throws ManagerException {
		try {
			return couponDAO.getTradeCouponDOsNum(queryDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#getTradeCouponDONum]获取优惠券信息报错：", e);
		}
	}


	@Override
	public TradeCouponDO getTradeCouponDOById(Long id) throws ManagerException {
		try {
			return couponDAO.getTradeCouponDOByID(id);
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#getTradeCouponDOById]获取优惠券信息报错：", e);
		}
	}

	public TradeCouponDO getTradeCouponDOByOrderId(Long orderId)throws ManagerException{
		try {
			return couponDAO.getTradeCouponDOByOrderId(orderId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#getTradeCouponDOByOrderId]获取优惠券信息报错：", e);
		}
	}

	@Override
	public int buyerDeleteCoupon(TradeCouponDO couponDO)
			throws ManagerException {
		try {
			return couponDAO.buyerDeleteCoupon(couponDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#buyerDeleteCoupon]买家“删除”已使用或已过期的优惠券报错：", e);
		}
	}


	@Override
	public TradeCouponDO getTradeCouponDO(TradeCouponDO couponDO)
			throws ManagerException {
		try {
			return couponDAO.getTradeCouponDO(couponDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponManagerImpl#getTradeCouponDO]获取优惠券详情报错：", e);
		}
	}
	
	public void setCouponDAO(CouponDAO couponDAO) {
		this.couponDAO = couponDAO;
	}
	
	public void setTradeCouponTallyDAO(TradeCouponTallyDAO tradeCouponTallyDAO) {
		this.tradeCouponTallyDAO = tradeCouponTallyDAO;
	}

	public void setTradeCouponBatchDAO(TradeCouponBatchDAO tradeCouponBatchDAO) {
		this.tradeCouponBatchDAO = tradeCouponBatchDAO;
	}

}
