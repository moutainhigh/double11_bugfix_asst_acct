package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.domain.shop.ShopQuery;

public interface ShopOpenManager {
	/**
	 * 保存店铺信息
	 * 
	 * @param sellerType
	 * @param shopInfo
	 * @return
	 */
	Object saveShopInfo(Integer sellerType, Object shopInfo)
			throws ManagerException;

	/**
	 * 同意协议
	 * 
	 * @param userId
	 * @return
	 */
	Object agreement(ShopOpenFlowDO shopOpenFlowDO) throws ManagerException;

	
	
	/**
	 * 获取开店流程信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopOpenFlowDO> queryShopOpenFlow(Long userId) throws ManagerException;
	
	/**
	 * 插入开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 */
	Object saveShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO) throws ManagerException;
	
	/**
	 * 更新开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 * @throws ManagerException 
	 */
	Object updateShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO) throws ManagerException;
	
	/**
	 * 获取C的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopCustomerInfoDO> queryShopCustomerInfo(Long userId) throws ManagerException;
	
	/**
	 * 获取B的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopBusinessInfoDO> queryShopBusinessInfo(Long userId) throws ManagerException;
	
	/**
	 * 更新C的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	Object updateShopCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws ManagerException;
	
	/**
	 * 更新B的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	Object updateShopBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws ManagerException;
	
	/**
	 * 查询店铺名称是否存在
	 * @param name
	 * @return
	 * @throws ManagerException
	 */
	boolean queryShopInfosByName(String name,Long userId) throws ManagerException;
	
	/**
	 * 根据shopid获得店铺信息
	 * @param shopId
	 * @return
	 * @throws ManagerException
	 */
	ShopBusinessInfoDO queryShopInfoByShopId(Integer shopId) throws ManagerException;
	
	/**
	 * 查询所有有shopid和开店状态为1的店铺信息
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopInfoDO> queryShopInfoAllList() throws ManagerException;
	
	/**
	 * 获取分销所需信息(ShopQuery : shopIdList, province, exchangeType)
	 * @param shopQuery
	 * @return
	 * @throws DaoException
	 */
	public ShopQuery queryListShopBusinessInfo(ShopQuery shopQuery) throws ManagerException;
	
	/**
	 * 设置店铺为供应商
	 * @param shopId
	 * @param isSupplier
	 * @return
	 */
	public Object setSellerIsSupplier(Integer shopId, Integer isSupplier) throws ManagerException;
	
	/**
	 * 保存保证金缴纳信息(开店并没有完成,只记录保证金类型和金额)
	 * @param shopOpenFlowDO
	 * @param exchangeMargin
	 * @param exchangePrice
	 * @return
	 * @throws ManagerException
	 */
	Object saveExchangeInfo(Long userId, Integer exchangeMargin, String exchangePrice) throws ManagerException;
	
	
	/**
	 * 根据店铺id更新店铺状态
	 * @param shopId
	 * @return 数据库状态信息
	 * @throws ManagerException
	 */
	Object updateShopApproveStatusByShopId(Integer shopId, Integer approveStatus) throws ManagerException;
	
	
	/**
	 * 开店成功(给消保金调用)
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	Result setShopIsOpenForMargin(Long userId)  throws ManagerException;
	
	/**
	 * 更新i小铺信息 -- 2.0新
	 * @param shopIshopInfoDO
	 * @return
	 * @throws ManagerException
	 */
	Object updateIShopInfo(ShopIshopInfoDO shopIshopInfoDO) throws ManagerException;
	
	/**
	 * 获取i小铺的店铺信息--2.0新
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopIshopInfoDO> queryShopIShopnfo(long userId)throws ManagerException;
	
	
	/**
	 * 删除C店信息
	 * @param userId
	 * @return
	 */
	public void deleteShopCustomerInfo(Long userId)throws ManagerException;
	
	
	/***
	 * 删除B店信息
	 * @param userId
	 * @return
	 */
	public void deleteShopBusinessInfo(Long userId)throws ManagerException;
	
	/***
	 * 删除i小铺信息
	 * @param userId
	 * @return
	 */
	public void deleteShopIShopInfo(Long userId)throws ManagerException;
	
	/***
	 * 删除店铺基本信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopInfo(Long userId)throws ManagerException;
	
	/***
	 * 删除开店流程信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopOpenFlow(Long userId)throws ManagerException;
	
	
	/**
     * 签订协议初始化开店流程店铺基本信息数据【2.0新增版本】
     * @param sellerType
     * @param shopInfo
     * @param shopOpenFlowDO
     * @return
     */
	public Object signAgreementLoadData(final Integer sellerType,final Object shopInfo,final ShopOpenFlowDO shopOpenFlowDO);
	
	/****
	 * 删除店铺相关的店铺数据【2.0新增版本】
	 * @param sellerType
	 * @param userId
	 * @return
	 */
	public Object deleteShopDateByUpdateSellerType(final Integer sellerType,final Long userId);
	

}
