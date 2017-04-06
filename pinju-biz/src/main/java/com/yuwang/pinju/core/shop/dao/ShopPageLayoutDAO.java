package com.yuwang.pinju.core.shop.dao;

import com.yuwang.pinju.core.common.DaoException;


public interface ShopPageLayoutDAO {
	/**
	 * 更新页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @param layoutXml
	 * @return 
	 */
	public Object saveUserPageLayout(Long userId,Integer shopId,Integer pageId,String layoutXml) throws DaoException;
	
	/**
	 * 插入用户页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @param layoutXml
	 * @return
	 */
	public Object insertUserPageLayout(Long userId,Integer shopId,Integer pageId,String layoutXml) throws DaoException;
	/**
	 * 查询用户页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return 
	 */
	public Object queryUserPageLayout(Long userPageId,Long userId,Integer shopId,Integer pageId) throws DaoException;
	
	/**
	 * 查询页面初始布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return 
	 */
	public Object queryPageLayout(Integer pageId) throws DaoException;
}
