package com.yuwang.pinju.core.shop.manager;

import java.util.List;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;


public interface ShopShowInfoManager {

	/**
	 * 保存店铺基本信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	Object saveShopBaseInfo(ShopInfoDO shopInfoDO) throws ManagerException;
	
	/**
	 * 保存店铺基本信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	Object updateShopBaseInfo(ShopInfoDO shopInfoDO) throws ManagerException;
	
	/**
	 * 根据用户id list 返回多个店铺信息
	 * @param userIdList
	 * @param sellerType
	 * @return
	 * @throws ManagerException
	 */
	List<ShopInfoDO> queryShopInfoByUserIdList(List<Long> userIdList)throws ManagerException;
	
	/**
	 * 根据用户shopid 店铺信息
	 * @param shopId
	 * @param approveStatus
	 * @return
	 * @throws ManagerException
	 */
	ShopInfoDO queryShopInfoByShopId(Integer shopId, Integer approveStatus) throws ManagerException;
	
	/**
	 * 根据用户id取店铺基本信息
	 * @param userId
	 * @param approveStatus
	 * @return
	 * @throws ManagerException
	 */
	ShopInfoDO queryShopBaseInfoByUser(Long userId, Integer approveStatus) throws ManagerException;
	
	/**
	 * 根据用户id取品牌店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	ShopBusinessInfoDO queryShopBussinessInfoByUser(Long userId) throws ManagerException;
	
	/**
	 * 根据用户id取普通店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	ShopCustomerInfoDO queryShopCusInfoByUser(Long userId) throws ManagerException;
	/**
	 * 根据用户id取i小铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	ShopIshopInfoDO queryIShopInfoByUser(Long userId) throws ManagerException;
	
	
	/**
	 * 更新旗舰店铺联系人信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	void updateBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO)throws ManagerException;
	
	/**
	 * 更新普通店铺联系人信息
	 * 
	 * @param shopCustomerInfoDO
	 * @return
	 */
	void updateCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO)throws ManagerException;
	
	/**
	 * 更新i小铺联系人信息
	 * 
	 * @param shopIshopInfoDO
	 * @return
	 */
	void updateIshopInfo(ShopIshopInfoDO shopIshopInfoDO)throws ManagerException;
	
	/**
	 * 验证是否有B店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	boolean checkHasBusinessInfo(Long userId) throws ManagerException;
	
	/**
	 * 验证是否有C店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	boolean checkHasCustomerInfo(Long userId) throws ManagerException;
	
	/**
	 * 验证是否有I小铺店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	boolean checkHasIShopInfo(Long userId) throws ManagerException;
	
	boolean insertBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws ManagerException;
	
	boolean insertIShopInfo(ShopIshopInfoDO shopIshopInfoDO) throws ManagerException;
	
	boolean insertCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws ManagerException;
	
}
