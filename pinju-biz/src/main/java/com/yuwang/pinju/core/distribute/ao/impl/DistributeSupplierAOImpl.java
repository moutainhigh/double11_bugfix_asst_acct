package com.yuwang.pinju.core.distribute.ao.impl;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.util.ObjectUtils;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.ao.DistributeSupplierAO;
import com.yuwang.pinju.core.distribute.manager.DistributeSupplierManager;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:45
 */
public class DistributeSupplierAOImpl extends BaseAO implements DistributeSupplierAO {

	private DistributeSupplierManager distributeSupplierManager;

	private ShopOpenManager shopOpenManager;

	public DistributeSupplierManager getDistributeSupplierManager() {
		return distributeSupplierManager;
	}

	public void setDistributeSupplierManager(DistributeSupplierManager distributeSupplierManager) {
		this.distributeSupplierManager = distributeSupplierManager;
	}

	/**
	 * 申请供应商
	 * 
	 * @param distribureChannelDO
	 */
	@Override
	public DistributeSupplierDO applySupplier(final DistributeSupplierParamDO distributeSupplierDO) {
	    return executeMysqlTransaction(DistributeSupplierDO.class, new TransactionCallback<DistributeSupplierDO>() {
		@Override
		public DistributeSupplierDO doInTransaction(TransactionStatus status) {
			String nickName = distributeSupplierDO.getNickName();
			distributeSupplierDO.setNickName(null);
			DistributeSupplierDO exist = null;
			try {
			    exist = distributeSupplierManager.getDistributeSupplier(distributeSupplierDO);
				if (null == exist) {
					if (!ObjectUtils.nullSafeEquals(shopOpenManager.setSellerIsSupplier(distributeSupplierDO.getShopId(), 1), new Integer(1))) {
						log.error("申请供应商流程:[设置店铺为供应商]出错");
						// 更新失败
						return exist;
					}
					exist = new DistributeSupplierDO();
					exist.setShopId(distributeSupplierDO.getShopId());
					exist.setMemberId(distributeSupplierDO.getMemberId());
					exist.setNickName(nickName);
					if(null == distributeSupplierManager.saveDistributeSupplier(exist)){
					    throw new RuntimeException();
					}
				}
			} catch (Exception e) {
			    throw new RuntimeException("申请供应商流程出错");
			}
			return exist;
		}
	    });
	}

	/**
	 * 发布招募书
	 * 
	 * @param distribureChannelDO
	 */
	@Override
	public DistributeSupplierDO releaseSupplier(DistributeSupplierDO distributeSupplierDO) {
		try {
			distributeSupplierManager.updateDistributeSupplier(distributeSupplierDO);
			return distributeSupplierManager.getDistributeSupplierById(distributeSupplierDO.getId());
		} catch (ManagerException e) {
			log.error("申请供应商流程出错", e);
		}
		return null;
	}
	
	/**
	 * 根据userID取得供应商
	 */
	@Override
	public DistributeSupplierDO getDistributeSupplier(long userId) {
		DistributeSupplierParamDO dspo = new DistributeSupplierParamDO();
		dspo.setMemberId(userId);
		try {
			return distributeSupplierManager.getDistributeSupplier(dspo);
		} catch (ManagerException e) {
			log.error("根据userID取得供应商出错", e);
		}
		return null;
	}

	public ShopOpenManager getShopOpenManager() {
		return shopOpenManager;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}

}