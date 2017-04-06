package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopFlowInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;

public interface ShopOpenAO {

	/**
	 * 验证财付通
	 * 
	 * @param userId
	 * @return
	 */
	Result checkTenpay(Long userId);

	/**
	 * 验证是否已经同意服务协议
	 * 
	 * @param userId
	 * @return
	 */
	boolean checkIsAgreement(ShopOpenFlowDO shopOpenFlowDO);

	/**
	 * 验证是否审核状态
	 * 
	 * @param userId
	 * @return
	 */
	boolean checkAudit(ShopOpenFlowDO shopOpenFlowDO);

	/**
	 * 验证是否已经填写店铺信息
	 * 
	 * @param userId
	 * @return
	 */
	boolean checkIsFillInfo(ShopOpenFlowDO shopOpenFlowDO);

	/**
	 * 保存店铺信息
	 * 
	 * @param sellerType
	 * @param shopInfo
	 * @return
	 */
	Object saveShopInfo(Integer sellerType, Object shopInfo);


	/**
	 * 开店
	 * @param shopOpenFlowDO
	 * @param tenPayError
	 * @return
	 */
	Result shopOpen(ShopOpenFlowDO shopOpenFlowDO, Long userId, Integer sellerType);

	/**
	 * 获取开店流程信息
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopOpenFlowDO> queryShopOpenFlow(Long userId);

	/**
	 * 更新开店流程信息
	 * 
	 * @param shopOpenFlowDO
	 * @return
	 * @throws ManagerException
	 */
	Object updateShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO);

	/**
	 * 插入开店流程信息
	 * 
	 * @param shopOpenFlowDO
	 * @return
	 * @throws ManagerException
	 */
	Object saveShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO);

	/**
	 * 获取C的店铺信息
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopCustomerInfoDO> queryShopCustomerInfo(Long userId);

	/**
	 * 获取B的店铺信息
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	List<ShopBusinessInfoDO> queryShopBusinessInfo(Long userId);

	/**
	 * 更新C的店铺信息
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	Object updateShopCustomerInfo(Long userId,
			ShopCustomerInfoDO shopCustomerInfoDO);

	/**
	 * 更新B的店铺信息
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	Object updateShopBusinessInfo(Long userId,
			ShopBusinessInfoDO shopBusinessInfoDO);
	
	/**
	 * 获取所有店铺信息
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	boolean queryShopInfosByName(String name,Long userId);
	
	/**
	 * 获取i小铺的店铺信息 -- 2.0新
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
//	ShopOpenFlowDO setShopIsOpen(ShopOpenFlowDO shopOpenFlowDO, Integer exchangeMargin, Integer exchangePrice)  throws ManagerException;
	
	
	/**
	 * 签订同意服务协议【2.0版本新增】
	 * 
	 * @param userId
	 * @return
	 */
	boolean agreement(Integer sellerType,ShopOpenFlowDO shopOpenFlowDO,ShopInfoDO shopInfoDO);

	List<ShopIshopInfoDO> queryShopIShopnfo(Long userId);
	
	/**
	 * 获取流程DO -- 2.0新
	 * @param userId
	 * @return 流程DO
	 */
	ShopFlowInfoDO queryFlowInfo(Long userId);
	
	/**
	 * 删除C店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopCustomerInfo(Long userId);
	
	
	/***
	 * 删除B店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopBusinessInfo(Long userId);
	
	/***
	 * 删除i小铺信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopIShopInfo(Long userId);
	
	/***
	 * 删除店铺基本信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopInfo(Long userId);
	
	/***
	 * 删除开店流程信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopOpenFlow(Long userId);
	
	
}
