package com.yuwang.pinju.core.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopShowInfoDao;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;




public class ShopShowInfoDaoImpl extends BaseDAO implements ShopShowInfoDao {
	
	/**
	 * 保存店铺基本信息
	 * @param shopInfoDO
	 * @return
	 * @throws com.yuwang.zizu.core.common.DaoException 
	 */
	@Override
	public Object saveShopBaseInfo(ShopInfoDO shopInfoDO) throws DaoException {
		Object back = super.executeInsert("shopBaseInfo.insertShopBaseInfo", shopInfoDO);
//		Object back = super.executeInsertOrUpdate("queryShopBaseInfo", "saveShopBaseInfo", "updateShopBaseInfo", shopInfoDO);
		return back;
	}
	
	/**
	 * 更新店铺基本信息
	 * @param shopInfoDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Object updateShopBaseInfo(ShopInfoDO shopInfoDO) throws DaoException {
		return super.executeUpdate("shopBaseInfo.updateShopBaseInfo", shopInfoDO);
	}
	
	
	/**
	 * 根据用户id list 返回多个店铺信息
	 * @param userIdList
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopInfoDO> queryShopInfoByUserIdList(List<Long> userIdList)
			throws DaoException {
		List<ShopInfoDO> list = super.executeQueryForList("shopBaseInfo.queryShopInfoByUserIdList",userIdList);
		return list;
	}
	
	/**
	 * 根据用户shopid 店铺信息
	 * @param shopId
	 * @param approveStatus
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopInfoDO> queryShopInfoByShopId(Integer shopId, Integer approveStatus)
			throws DaoException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopId", shopId);
		map.put("approveStatus", approveStatus);
		List<ShopInfoDO> list = super.executeQueryForList("shopBaseInfo.queryShopInfoByShopId",map);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopInfoDO> queryShopBaseInfoByUser(Long userId, Integer approveStatus) throws DaoException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("approveStatus", approveStatus);
		List<ShopInfoDO> list = super.executeQueryForList("shopBaseInfo.queryShopBaseInfoByUser",map);
		return list;
	}
  
	/**
	 * 根据用户id取品牌店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	public ShopBusinessInfoDO queryShopBussinessInfoByUser(Long userId) throws DaoException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return  (ShopBusinessInfoDO)super.executeQueryForObject("shopBusinessInfo.queryManagerInfoByUser",map);
	}
	
	/**
	 * 根据用户id取普通店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	public ShopCustomerInfoDO queryShopCusInfoByUser(Long userId) throws DaoException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return  (ShopCustomerInfoDO)super.executeQueryForObject("shopCustomerInfo.queryManagerInfoByUser",map);
	}
	/**
	 * 根据用户id取i小铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	public ShopIshopInfoDO queryIShopInfoByUser(Long userId) throws DaoException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return  (ShopIshopInfoDO)super.executeQueryForObject("shopiShopInfo.queryManagerInfoByUser",map);
	}
	
	/**
	 * 更新旗舰店铺联系人信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	public void updateBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO)throws DaoException{
		 super.executeUpdate("shopBusinessInfo.updateBusinessInfo", shopBusinessInfoDO);
	}
	
	/**
	 * 更新普通店铺联系人信息
	 * 
	 * @param shopCustomerInfoDO
	 * @return
	 */
	public void updateCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO)throws DaoException{
		 super.executeUpdate("shopCustomerInfo.updateCustomerInfo", shopCustomerInfoDO);
	}
	
	/**
	 * 更新i小铺联系人信息
	 * 
	 * @param shopIshopInfoDO
	 * @return
	 */
	public void updateIshopInfo(ShopIshopInfoDO shopIshopInfoDO)throws DaoException{
		 super.executeUpdate("shopiShopInfo.updateIshopInfo", shopIshopInfoDO);
	}
	
	/**
	 * 验证是否有B店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkHasBusinessInfo(Long userId) throws DaoException {
		List list = super.executeQueryForList("shopBaseInfo.checkHasBusinessInfo", userId);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否有C店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkHasCustomerInfo(Long userId) throws DaoException {
		List list = super.executeQueryForList("shopBaseInfo.checkHasCustomerInfo", userId);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否有i小铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkHasIShopInfo(Long userId) throws DaoException {
		List list = super.executeQueryForList("shopBaseInfo.checkHasIShopInfo", userId);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean insertBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws DaoException {
		Object object = super.executeInsert("shopInfoOpen.insertShopBusinessInfo", shopBusinessInfoDO);
		if(object != null && (Integer)object > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean insertCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws DaoException {
		Object object = super.executeInsert("shopInfoOpen.insertShopCustomerInfo", shopCustomerInfoDO);
		if(object != null && (Integer)object > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean insertIShopInfo(ShopIshopInfoDO shopIshopInfoDO) throws DaoException {
		Object object = super.executeInsert("shopInfoOpen.insertIshopInfo", shopIshopInfoDO);
		if(object != null && (Integer)object > 0){
			return true;
		}
		return false;
	}

}
