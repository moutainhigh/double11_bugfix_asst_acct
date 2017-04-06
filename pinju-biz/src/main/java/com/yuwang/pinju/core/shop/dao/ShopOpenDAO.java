package com.yuwang.pinju.core.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.domain.shop.ShopQuery;

public interface ShopOpenDAO {
	/**
	 * 保存店铺信息
	 * @param sellerType
	 * @param shopInfo
	 * @return
	 */
	Object saveShopInfo(Integer sellerType, Object shopInfo) throws DaoException;
	
	/**
	 * 同意协议
	 * @param userId
	 * @return
	 */
	Object agreement(ShopOpenFlowDO shopOpenFlowDO) throws DaoException;
	
	
	/**
	 * 获取开店流程信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopOpenFlowDO> queryShopOpenFlow(Long userId) throws DaoException;
	
	/**
	 * 插入开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 */
	Object saveShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO) throws DaoException;
	
	/**
	 * 更新开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 */
	Object updateShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO) throws DaoException;
	
	/**
	 * 获取C的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopCustomerInfoDO> queryShopCustomerInfo(Long userId,Integer sellerType) throws DaoException;
	
	/**
	 * 获取B的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopBusinessInfoDO> queryShopBusinessInfo(Long userId,Integer sellerType) throws DaoException;
	
	/**
	 * 更新C的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	Object updateShopCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws DaoException;
	
	/**
	 * 更新B的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	Object updateShopBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws DaoException;
	
	/**
	 * 查询店铺名称是否存在
	 * @param name
	 * @return
	 * @throws DaoException
	 */
	List<ShopInfoDO> queryShopInfosByName(String name,Long userId) throws DaoException;
	
	/**
	 * 根据shopid获取店铺信息
	 * @param shopId
	 * @return
	 * @throws DaoException
	 */
	public List<ShopBusinessInfoDO> queryShopInfoByShopId(Integer shopId) throws DaoException;
	/**
	 * 查询所有有shopid和开店状态为1的店铺信息
	 * @return
	 * @throws DaoException
	 */
	public List<ShopInfoDO> queryShopInfoAllList() throws DaoException;
	
	
	/**
	 * 获取分销所需信息(ShopQuery : shopIdList, province, exchangeType)
	 * @param shopQuery
	 * @return
	 * @throws DaoException
	 */
	public List<ShopBusinessInfoDO> queryListShopBusinessInfo(ShopQuery shopQuery) throws DaoException;
	
	/**
	 * 获取分销所需信息总数(ShopQuery : shopIdList, province, exchangeType)
	 * @param shopQuery
	 * @return
	 * @throws DaoException
	 */
	public Integer queryListShopBusinessInfoCount(ShopQuery shopQuery) throws DaoException;
	
	
	
	/**
	 * 设置店铺为供应商
	 * @param shopId
	 * @param isSupplier
	 * @return
	 */
	public Object setSellerIsSupplier(Integer shopId, Integer isSupplier) throws DaoException;
	
	
	/**
	 * 根据店铺id更新店铺状态
	 * @param shopId
	 * @return 数据库状态信息
	 * @throws ManagerException
	 */
	Object updateShopApproveStatusByShopId(Integer shopId, Integer approveStatus) throws DaoException;
	
	/**
	 * 更新i小铺信息 -- 2.0新
	 * @param shopIshopInfoDO
	 * @return
	 * @throws DaoException
	 */
	Object updateIShopInfo(ShopIshopInfoDO shopIshopInfoDO) throws DaoException;
	
	/**
	 * 获取i小铺的店铺信息--2.0新
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopIshopInfoDO> queryShopIShopnfo(long userId) throws DaoException;
	
	/**
	 * 删除C店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopCustomerInfo(Long userId) throws DaoException;
	
	
	/***
	 * 删除B店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopBusinessInfo(Long userId)throws DaoException;
	
	/***
	 * 删除i小铺信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopIShopInfo(Long userId)throws DaoException;
	
	
	/***
	 * 删除店铺基本信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopInfo(Long userId)throws DaoException;
	
	/***
	 * 删除开店流程信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopOpenFlow(Long userId)throws DaoException;
}
