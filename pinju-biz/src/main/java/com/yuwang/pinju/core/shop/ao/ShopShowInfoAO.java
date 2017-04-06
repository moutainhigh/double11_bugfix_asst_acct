package com.yuwang.pinju.core.shop.ao;

import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;


public interface ShopShowInfoAO {

	/**
	 * 保存店铺基本信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	Object saveShopBaseInfo(ShopInfoDO shopInfoDO);
	
	/**
	 * 根据用户id取店铺ID
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	Integer queryShopIdByUserId(Long userId);
	
	/**
	 * 获取类目信息
	 */
	@SuppressWarnings("unchecked")
	Map<Long, String> initShopCategoryList() ; 
	
	
	/**
	 * 更新旗舰店铺联系人信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	boolean updateBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO);
	
	/**
	 * 更新普通店铺联系人信息
	 * 
	 * @param shopCustomerInfoDO
	 * @return
	 */
	boolean updateCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO);
	
	/**
	 * 更新i小铺联系人信息
	 * 
	 * @param shopIshopInfoDO
	 * @return
	 */
	boolean updateIshopInfo(ShopIshopInfoDO shopIshopInfoDO);
}
