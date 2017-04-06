package com.yuwang.pinju.core.shop.manager.impl;

import java.util.ArrayList;
import java.util.List;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopShowInfoDao;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;

public class ShopShowInfoManagerImpl extends BaseManager implements ShopShowInfoManager {
	private ShopShowInfoDao shopShowInfoDao;

	public ShopShowInfoDao getShopShowInfoDao() {
		return shopShowInfoDao;
	}

	public void setShopShowInfoDao(ShopShowInfoDao shopShowInfoDao) {
		this.shopShowInfoDao = shopShowInfoDao;
	}

	/**
	 * 保存店铺基本信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	@Override
	public Object saveShopBaseInfo(ShopInfoDO shopInfoDO) throws ManagerException {
		Object back = null;
		try{
			back = shopShowInfoDao.saveShopBaseInfo(shopInfoDO);
			return back;
		}catch(DaoException e){
			throw new ManagerException("保存店铺基本信息出错",e);
		}
		
	}
	/**
	 * 更新店铺基本信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	@Override
	public Object updateShopBaseInfo(ShopInfoDO shopInfoDO) throws ManagerException {
		Object back = null;
		try{
			back = shopShowInfoDao.updateShopBaseInfo(shopInfoDO);
			return back;
		}catch(DaoException e){
			throw new ManagerException("更新店铺基本信息出错",e);
		}
	}

	/**
	 * 根据用户id list 返回多个店铺信息
	 * @param userIdList
	 * @param sellerType
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public List<ShopInfoDO> queryShopInfoByUserIdList(List<Long> userIdList) throws ManagerException {
		try{
			if(userIdList==null || userIdList.size() == 0){
				return new ArrayList<ShopInfoDO>();
			}
			return shopShowInfoDao.queryShopInfoByUserIdList(userIdList);
		}catch(DaoException e){
			throw new ManagerException("查询店铺基本信息出错",e);
		}
		
	}
	
	/**
	 * 根据用户shopid 店铺信息
	 * @param shopId
	 * @param approveStatus
	 * @return
	 * @throws DaoException
	 */
	@Override
	public ShopInfoDO queryShopInfoByShopId(Integer shopId, Integer approveStatus) throws ManagerException{
		try{
			if(shopId == null){
				return null;
			}
			List<ShopInfoDO> list = shopShowInfoDao.queryShopInfoByShopId(shopId, approveStatus);
			if(list!=null && list.size()>0){
				return list.get(0);
			}
			return null;
		}catch(DaoException e){
			throw new ManagerException("根据用户shopid 店铺信息出错",e);
		}
		
	}
	
	/**
	 * 根据用户id取店铺基本信息
	 * @param userId
	 * @param approveStatus
	 * @return
	 * @throws DaoException
	 */
	@Override
	public ShopInfoDO queryShopBaseInfoByUser(Long userId, Integer approveStatus) throws ManagerException {
		try{
			if(userId!=null){
				List<ShopInfoDO> list = shopShowInfoDao.queryShopBaseInfoByUser(userId, approveStatus);
				if(list!=null && list.size()>0){
					return list.get(0);
				}
			}
			return null;
		}catch(DaoException e){
			throw new ManagerException("查询店铺基本信息出错",e);
		}
	}
	
	/**
	 * 根据用户id取品牌店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public ShopBusinessInfoDO queryShopBussinessInfoByUser(Long userId) throws ManagerException{
		ShopBusinessInfoDO back = null;
		try{
			back = shopShowInfoDao.queryShopBussinessInfoByUser(userId);
			return back;
		}catch(DaoException e){
			throw new ManagerException("根据用户id取品牌店铺联系人基本信息出错",e);
		}
	}
	
	/**
	 * 根据用户id取普通店铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public ShopCustomerInfoDO queryShopCusInfoByUser(Long userId) throws ManagerException{
		ShopCustomerInfoDO back = null;
		try{
			back = shopShowInfoDao.queryShopCusInfoByUser(userId);
			return back;
		}catch(DaoException e){
			throw new ManagerException("根据用户id取普通店铺联系人基本信息出错",e);
		}
	}
	/**
	 * 根据用户id取i小铺联系人基本信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public ShopIshopInfoDO queryIShopInfoByUser(Long userId) throws ManagerException{
		ShopIshopInfoDO back = null;
		try{
			back = shopShowInfoDao.queryIShopInfoByUser(userId);
			return back;
		}catch(DaoException e){
			throw new ManagerException("根据用户id取i小铺联系人基本信息出错",e);
		}
	}
	/**
	 * 更新旗舰店铺联系人信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	public void updateBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO)throws ManagerException{
		try{
			 shopShowInfoDao.updateBusinessInfo(shopBusinessInfoDO);
		}catch(DaoException e){
			throw new ManagerException("更新旗舰店铺联系人信息出错",e);
		}
	}
	
	/**
	 * 更新普通店铺联系人信息
	 * 
	 * @param shopCustomerInfoDO
	 * @return
	 */
	public void updateCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO)throws ManagerException{
		try{
			 shopShowInfoDao.updateCustomerInfo(shopCustomerInfoDO);
		}catch(DaoException e){
			throw new ManagerException("根据用户id取i小铺联系人基本信息出错",e);
		}
	}
	
	/**
	 * 更新i小铺联系人信息
	 * 
	 * @param shopIshopInfoDO
	 * @return
	 */
	public void updateIshopInfo(ShopIshopInfoDO shopIshopInfoDO)throws ManagerException{
		try{
			 shopShowInfoDao.updateIshopInfo(shopIshopInfoDO);
		}catch(DaoException e){
			throw new ManagerException("根据用户id取i小铺联系人基本信息出错",e);
		}
	}
	
	/**
	 * 验证是否有B店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public boolean checkHasBusinessInfo(Long userId) throws ManagerException {
		try{
			 return shopShowInfoDao.checkHasBusinessInfo(userId);
		}catch(DaoException e){
			throw new ManagerException("验证是否有B店信息出错",e);
		}
	}
	
	/**
	 * 验证是否有C店信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public boolean checkHasCustomerInfo(Long userId) throws ManagerException {
		try{
			return shopShowInfoDao.checkHasCustomerInfo(userId);
		}catch(DaoException e){
			throw new ManagerException("验证是否有C店信息出错",e);
		}
	}
	
	/**
	 * 验证是否有I小铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public boolean checkHasIShopInfo(Long userId) throws ManagerException {
		try{
			return shopShowInfoDao.checkHasIShopInfo(userId);
		}catch(DaoException e){
			throw new ManagerException("验证是否有I小铺信息出错",e);
		}
	}

	@Override
	public boolean insertBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws ManagerException {
		try{
			return shopShowInfoDao.insertBusinessInfo(shopBusinessInfoDO);
		}catch(DaoException e){
			throw new ManagerException("验证是否有I小铺信息出错",e);
		}
	}

	@Override
	public boolean insertCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws ManagerException {
		try{
			return shopShowInfoDao.insertCustomerInfo(shopCustomerInfoDO);
		}catch(DaoException e){
			throw new ManagerException("验证是否有I小铺信息出错",e);
		}
	}

	@Override
	public boolean insertIShopInfo(ShopIshopInfoDO shopIshopInfoDO) throws ManagerException {
		try{
			return shopShowInfoDao.insertIShopInfo(shopIshopInfoDO);
		}catch(DaoException e){
			throw new ManagerException("验证是否有I小铺信息出错",e);
		}
	}

}
