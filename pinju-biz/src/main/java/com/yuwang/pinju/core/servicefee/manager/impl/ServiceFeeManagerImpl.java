package com.yuwang.pinju.core.servicefee.manager.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.mail.imap.protocol.Item;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.servicefee.dao.ServiceFeeDAO;
import com.yuwang.pinju.core.servicefee.manager.ServiceFeeManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.servicefee.ServiceFeeDO;
import com.yuwang.pinju.domain.servicefee.ServiceFeeResultDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class ServiceFeeManagerImpl extends BaseManager implements
		ServiceFeeManager {

	private ServiceFeeDAO serviceFeeDAO;

	public void setServiceFeeDAO(ServiceFeeDAO serviceFeeDAO) {
		this.serviceFeeDAO = serviceFeeDAO;
	}

	private ShopShowInfoManager shopShowInfoManager;

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	private ItemManager itemManager;

	@Override
	public ServiceFeeResultDO queryServiceFeeByItem(ItemDO itemDO, Long shopId)
			throws ManagerException {
		ServiceFeeResultDO result = new ServiceFeeResultDO();
		List<ItemDO> itemDOList = new ArrayList<ItemDO>();
		try {
			List<ServiceFeeDO> list = serviceFeeDAO
					.selectServFeeByItemDO(itemDO);
			if (null == list || list.size() == 0) {
				result.setServiceFee(0L);
				result.setServiceFeeRate(0L);
				result.setShopRate(0L);
				return result;
			} else {
				Double coeff = 1d;
				Double platRate = 0d;
				ServiceFeeDO servFeeDO = null;
				if (list.size() == 2) {
					for (int i = 0; i < 2; i++) {
						if (list.get(i).getCategoryLevel() == 2) {
							platRate = list.get(i).getPlatFeeRate();
						}
					}
				} else {
					servFeeDO = list.get(0);
					platRate = servFeeDO.getPlatFeeRate();
				}
				coeff = serviceFeeDAO.selectShopServiceFeeRateById(shopId);
				if (coeff == null) {
					coeff = 1d;
				}
				Double total = Math
						.ceil((itemDO.getPrice() * platRate * coeff) / 100);
				Double rePrice = Math.ceil(itemDO.getPrice() * 0.9);
				if (total > rePrice) {
					result.setServiceFee(rePrice.longValue());
				} else {
					result.setServiceFee(total.longValue());// 设置单位为分
				}
				Double plat = platRate * 100;
				Double fee = coeff * 100;
				result.setServiceFeeRate(plat.longValue());
				result.setShopRate(fee.longValue());
			}
		} catch (DaoException e) {
			log.error("Find shop serviceFee by itemDO", e);
			throw new ManagerException("Find shop serviceFee by itemDO", e);
		}
		return result;
	}

	@Override
	public ServiceFeeResultDO queryServiceFeeByItem(ItemDO itemDO)
			throws ManagerException {
		ServiceFeeResultDO result = new ServiceFeeResultDO();

		try {
			List<ServiceFeeDO> list = serviceFeeDAO
					.selectServFeeByItemDO(itemDO);
			if (null == list || list.size() == 0) {
				result.setServiceFee(0L);
				result.setServiceFeeRate(0L);
				result.setShopRate(0L);
				return result;
			} else {
				Double coeff = 1d;
				Double platRate = 0d;
				ServiceFeeDO servFeeDO = null;
				if (list.size() == 2) {
					for (int i = 0; i < 2; i++) {
						if (list.get(i).getCategoryLevel() == 2) {
							platRate = list.get(i).getPlatFeeRate();
						}
					}
				} else {
					servFeeDO = list.get(0);
					platRate = servFeeDO.getPlatFeeRate();
				}
				// 查询商品获取sellerId
				ItemDO item = itemManager.getItemDOById(itemDO.getId());
				// 获取店铺信息
				ShopInfoDO shopInfoDO = shopShowInfoManager
						.queryShopBaseInfoByUser(item.getSellerId(),
								new Integer(1));
				if (shopInfoDO != null) {
					coeff = serviceFeeDAO.selectShopServiceFeeRateById(Long
							.valueOf(shopInfoDO.getId()));
				} else {
					coeff = 1d;
				}
				if (coeff == null) {
					coeff = 1d;
				}
				Double total = Math
						.ceil((itemDO.getPrice() * platRate * coeff) / 100);

				Double rePrice = Math.ceil(itemDO.getPrice() * 0.9);
				if (total > rePrice) {
					result.setServiceFee(rePrice.longValue());
				} else {
					result.setServiceFee(total.longValue());// 设置单位为分
				}
				Double plat = platRate * 100;
				Double fee = coeff * 100;
				result.setServiceFeeRate(plat.longValue());
				result.setShopRate(fee.longValue());
			}
		} catch (DaoException e) {
			log.error("Find shop serviceFee by itemDO", e);
			throw new ManagerException("Find shop serviceFee by itemDO", e);

		}
		return result;
	}

	public Long calcServiceFee(long price, long cateRate, long shopRate)
			throws ManagerException {
		Double serviceFee = 0d;
		double cateRate1 = (double) cateRate / 100; // 将类目费率转化为double
		double shopRate1 = (double) shopRate / 100; // 将店铺费率转化为double
		double price1 = (double) price;
		serviceFee = Math.ceil((price1 * cateRate1 * shopRate1) / 100);
		Double rePrice = Math.ceil(price1 * 0.9);
		if (serviceFee > rePrice) {
			return rePrice.longValue();
		} else {
			return serviceFee.longValue();
		}
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

}
