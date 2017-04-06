package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopPagePrototypeDAO;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * 装修页面原型DAO层实现
 * 
 * @author liyouguo
 * 
 * @since 2011-7-4
 */
public class ShopPagePrototypeDAOImpl extends BaseDAO implements
		ShopPagePrototypeDAO {

	/**
	 * 获取所有待编辑的页面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopPagePrototypeDO> getAllDesignerPage() throws DaoException {
		// TODO Auto-generated method stub
		List<ShopPagePrototypeDO> resultMap = (List<ShopPagePrototypeDO>) super
				.executeQueryForList("shopPagePrototype.getAllDesignerPage", null);
		return resultMap;
	}

	/**
	 * 获取指定类型【PAGEID=2:首页，PGAEID=3:自定义页面】页面原型
	 * 
	 * @param pageId
	 * @return
	 * @throws DaoException
	 * 
	 */
	public ShopPagePrototypeDO queryPageProtoByPageid(Integer pageId)
			throws DaoException {
		// TODO Auto-generated method stub
		return (ShopPagePrototypeDO) super.executeQueryForObject(
				"shopPagePrototype.queryPageProtoByPageid", pageId);
	}
}
