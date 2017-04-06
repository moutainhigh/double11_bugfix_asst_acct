package com.yuwang.pinju.core.shop.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopPageLayoutDAO;

public class ShopPageLayoutDAOImpl extends BaseDAO implements ShopPageLayoutDAO {

	/**
	 * 插入用户页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @param layoutXml
	 * @return
	 */
	@Override
	public Object insertUserPageLayout(Long userId, Integer shopId,
			Integer pageId, String layoutXml) throws DaoException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("shopId", shopId);
		map.put("pageId", pageId);
		map.put("saveStructure", layoutXml);
		return super.executeInsert("shopPageLayout.insertUserPageLayout", map);
	}

	/**
	 * 查询用户页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return 
	 */
	@Override
	public Object queryUserPageLayout(Long userPageId,Long userId, Integer shopId, Integer pageId) throws DaoException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userPageId", userPageId);
		map.put("userId", userId);
		map.put("shopId", shopId);
		map.put("pageId", pageId);
		return super.executeQueryForList("shopPageLayout.queryUserPageLayout", map);
	}

	/**
	 * 查询页面初始布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return 
	 */
	@Override
	public Object queryPageLayout(Integer pageId) throws DaoException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageId", pageId);
		return super.executeQueryForList("shopPageLayout.queryPageLayout", map);
	}

	/**
	 * 更新页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @param layoutXml
	 * @return 
	 */
	@Override
	public Object saveUserPageLayout(Long userId, Integer shopId,
			Integer pageId, String layoutXml) throws DaoException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("shopId", shopId);
		map.put("pageId", pageId);
		map.put("saveStructure", layoutXml);
		return super.executeUpdate("shopPageLayout.saveUserPageLayout", map);
	}

}
