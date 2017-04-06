package com.yuwang.pinju.core.shop.dao;

import java.util.List;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;


public interface ShopShowInfoDao {
	
	/**
	 * 保存店铺基本信息
	 * @param shopInfoDO
	 * @return
	 * @throws com.yuwang.zizu.core.common.DaoException 
	 */
	Object saveShopBaseInfo(ShopInfoDO shopInfoDO) throws DaoException;
	
	/**
	 * 更新店铺基本信息
	 * @param shopInfoDO
	 * @return
	 * @throws DaoException
	 */
	Object updateShopBaseInfo(ShopInfoDO shopInfoDO) throws DaoException;
	
	/**
	 * 根据用户id list 返回多个店铺信息
	 * @param userIdList
	 * @return
	 * @throws DaoException
	 */
	List<ShopInfoDO> queryShopInfoByUserIdList(List<Long> userIdList) throws DaoException;
	
	
	/**
	 * 根据用户shopid 店铺信息
	 * @param shopId
	 * @param approveStatus
	 * @return
	 * @throws DaoException
	 */
	List<ShopInfoDO> queryShopInfoByShopId(Integer shopId, Integer approveStatus) throws DaoException;
	
	/**
	 * 根据用户id取店铺基本信息
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	List<ShopInfoDO> queryShopBaseInfoByUser(Long userId, Integer approveStatus) throws DaoException;
	
	/**
	 * 根据用户id取品牌店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	ShopBusinessInfoDO queryShopBussinessInfoByUser(Long userId) throws DaoException;
	
	/**
	 * 根据用户id取普通店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	ShopCustomerInfoDO queryShopCusInfoByUser(Long userId) throws DaoException;
	/**
	 * 根据用户id取i小铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	ShopIshopInfoDO queryIShopInfoByUser(Long userId) throws DaoException;
	
	/**
	 * 更新旗舰店铺联系人信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	void updateBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO)throws DaoException;
	
	/**
	 * 更新普通店铺联系人信息
	 * 
	 * @param shopCustomerInfoDO
	 * @return
	 */
	void updateCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO)throws DaoException;
	
	/**
	 * 更新i小铺联系人信息
	 * 
	 * @param shopIshopInfoDO
	 * @return
	 * @throws DaoException 
	 */
	void updateIshopInfo(ShopIshopInfoDO shopIshopInfoDO)throws  DaoException;
	
	/**
	 * 验证是否有B店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	boolean checkHasBusinessInfo(Long userId) throws DaoException;
	
	/**
	 * 验证是否有C店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	boolean checkHasCustomerInfo(Long userId) throws DaoException;
	
	/**
	 * 验证是否有I小铺店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	boolean checkHasIShopInfo(Long userId) throws DaoException;
	
	boolean insertBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws DaoException;
	
	boolean insertIShopInfo(ShopIshopInfoDO shopIshopInfoDO) throws DaoException;
	
	boolean insertCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws DaoException;
	
}
