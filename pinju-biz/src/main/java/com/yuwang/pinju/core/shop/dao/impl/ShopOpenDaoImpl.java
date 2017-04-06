package com.yuwang.pinju.core.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopOpenDAO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.domain.shop.ShopQuery;

public class ShopOpenDaoImpl extends BaseDAO implements ShopOpenDAO {

	/**
	 * 同意协议
	 * @param userId
	 * @return
	 */
	@Override
	public Object agreement(ShopOpenFlowDO shopOpenFlowDO) throws DaoException {
		return super.executeInsert("shopOpenFlow.saveShopOpenFlow", shopOpenFlowDO);
	}

	/**
	 * 保存店铺信息
	 * @param sellerType
	 * @param shopInfo
	 * @return
	 */
	@Override
	public Object saveShopInfo(Integer sellerType, Object shopInfo)
			throws DaoException {
		Object object = -1;
		if (sellerType.equals(ShopConstant.SELLER_TYPE_C)) {
			object = super.executeInsert("shopInfoOpen.insertShopBaseInfo", (ShopCustomerInfoDO)shopInfo);
			object = super.executeInsert("shopInfoOpen.insertShopCustomerInfo", (ShopCustomerInfoDO)shopInfo);
			return object;
		}else if(sellerType.equals(ShopConstant.SELLER_TYPE_IShop)){
			object = super.executeInsert("shopInfoOpen.insertShopIShopInfo", (ShopIshopInfoDO)shopInfo);
			object = super.executeInsert("shopInfoOpen.insertIshopInfo", (ShopIshopInfoDO)shopInfo);
			return object;
		}else{
			object = super.executeInsert("shopInfoOpen.insertShopBusinessInfo", (ShopBusinessInfoDO)shopInfo);
			object = super.executeInsert("shopInfoOpen.insertShopBaseInfoB", (ShopBusinessInfoDO)shopInfo);
		}
		return object;
	}


	
	/**
	 * 插入开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 */
	@Override
	public Object saveShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO)
			throws DaoException {
		return super.executeInsert("shopOpenFlow.saveShopOpenFlow", shopOpenFlowDO);
	}

	/**
	 * 更新开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 */
	@Override
	public Object updateShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO)
			throws DaoException {
		return super.executeUpdate("shopOpenFlow.updateShopOpenFlow", shopOpenFlowDO);
	}

	
	/**
	 * 获取B店的店铺信息
	 * @param userId
	 * @param sellerType
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopBusinessInfoDO> queryShopBusinessInfo(Long userId,Integer sellerType) throws DaoException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("sellerType", sellerType);
		return super.executeQueryForList("shopInfoOpen.queryShopBaseInfoByUserIdB", map);
	}

	/**
	 * 获取C店的店铺信息
	 * @param userId
	 * @param sellerType
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopCustomerInfoDO> queryShopCustomerInfo(Long userId,Integer sellerType) throws DaoException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("sellerType", sellerType);
		return super.executeQueryForList("shopInfoOpen.queryShopBaseInfoByUserIdC", map);
	}
	/**
	 * 更新B店的店铺信息
	 * @param userId
	 * @param shopBusinessInfoDO
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object updateShopBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws DaoException {
		Object object =  super.executeUpdate("shopInfoOpen.updateShopBaseInfoB", shopBusinessInfoDO);
		object =  super.executeUpdate("shopInfoOpen.updateShopBusinessInfo", shopBusinessInfoDO);
		return object;
	}

	/**
	 * 更新C店的店铺信息
	 * @param userId
	 * @param shopCustomerInfoDO
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object updateShopCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws DaoException {
		Object object =  super.executeUpdate("shopInfoOpen.updateShopBaseInfo", shopCustomerInfoDO);
		object =  super.executeUpdate("shopInfoOpen.updateShopCustomerInfo", shopCustomerInfoDO);
		return object;
	}

	
	/**
	 * 获取开店流程信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopOpenFlowDO> queryShopOpenFlow(Long userId) throws DaoException {
		Map map = new HashMap();
		map.put("userId", userId);
		return super.executeQueryForList("shopOpenFlow.queryShopOpenFlowByUserId2", map);
	}

	/**
	 * 根据shopid获取店铺信息
	 * @param shopId
	 * @return
	 * @throws DaoException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ShopBusinessInfoDO> queryShopInfoByShopId(Integer shopId) throws DaoException{
		return super.executeQueryForList("shopInfoOpen.queryShopInfoByShopIdB", shopId);
	}
	
	/**
	 * 查询所有有shopid和开店状态为1的店铺信息
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopInfoDO> queryShopInfoAllList() throws DaoException {
		List<ShopInfoDO> list = super.executeQueryForList("shopBaseInfo.selectShopBaseInfo",null);
		return list;
	}
	
	
	/**
	 * 获取店铺名称是否存在
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopInfoDO> queryShopInfosByName(String name,Long userId) throws DaoException {
		Map map = new HashMap();
		map.put("name", name);
		map.put("userId", userId);
		List<ShopInfoDO> resultList=super.executeQueryForList("shopBaseInfo.queryShopsByName", map);
		return resultList==null?null:resultList;
	}
	
	
	/**
	 * 获取分销所需信息(ShopQuery : shopIdList, province, exchangeType)
	 * @param shopQuery
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopBusinessInfoDO> queryListShopBusinessInfo(ShopQuery shopQuery) throws DaoException {
		return super.executeQueryForList("shopInfoOpen.querySupplierInfo",shopQuery);
	}
	
	
	/**
	 * 设置店铺为供应商
	 * @param shopId
	 * @param isSupplier
	 * @return
	 */
	@Override
	public Object setSellerIsSupplier(Integer shopId, Integer isSupplier) throws DaoException{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("shopId", shopId);
		map.put("isSupplier", isSupplier);
		return super.executeUpdate("shopInfoOpen.setSellerIsSupplier", map);
	}
	
	
	/**
	 * 获取分销所需信息总数(ShopQuery : shopIdList, province, exchangeType)
	 * @param shopQuery
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Integer queryListShopBusinessInfoCount(ShopQuery shopQuery) throws DaoException {
		Object object = super.executeQueryForObject("shopInfoOpen.querySupplierInfoCount",shopQuery);
		if(object != null ){
			return (Integer)object;
		}else{
			return 0;
		}
		 
	}

	/**
	 * 根据店铺id更新店铺状态
	 * @param shopId
	 * @return 数据库状态信息
	 * @throws ManagerException
	 */
	@Override
	public Object updateShopApproveStatusByShopId(Integer shopId, Integer approveStatus) throws DaoException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("shopId", shopId);
		map.put("approveStatus", approveStatus);
		return super.executeUpdate("shopBaseInfo.updateShopBaseInfoByShopId", map);
	}
	
	/**
	 * 更新i小铺信息 -- 2.0新
	 * @param shopIshopInfoDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Object updateIShopInfo(ShopIshopInfoDO shopIshopInfoDO) throws DaoException {
		Object object =  super.executeUpdate("shopInfoOpen.updateIShopBaseInfo", shopIshopInfoDO);
		object =  super.executeUpdate("shopInfoOpen.updateIShopInfo", shopIshopInfoDO);
		return object;
	}

	/**
	 * 获取i小铺的店铺信息 --2.0新
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	public List<ShopIshopInfoDO> queryShopIShopnfo(long userId) throws DaoException{
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", userId);
		return super.executeQueryForList("shopInfoOpen.queryShopBaseInfoByUserIdIShop",map);
	}
	
	/**
	 * 删除C店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopCustomerInfo(Long userId) throws DaoException{
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", userId);
		super.executeUpdate("shopInfoOpen.deleteShopCustomerInfoByUserId",map);
	}
	
	
	/***
	 * 删除B店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopBusinessInfo(Long userId)throws DaoException{
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", userId);
		super.executeUpdate("shopInfoOpen.deleteShopBusinessInfoByUserId",map);
	}
	
	/***
	 * 删除i小铺信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopIShopInfo(Long userId)throws DaoException{
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", userId);
		super.executeUpdate("shopInfoOpen.deleteShopIshopInfoByUserId",map);
	}

	/***
	 * 删除店铺基本信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopInfo(Long userId)throws DaoException{
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", userId);
		super.executeUpdate("shopInfoOpen.deleteShopBaseInfoByUserId",map);
	}
	
	/***
	 * 删除开店流程信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopOpenFlow(Long userId)throws DaoException{
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", userId);
		super.executeUpdate("shopOpenFlow.deleteShopOpenFlowByUserId",map);
	}
	
	
}
