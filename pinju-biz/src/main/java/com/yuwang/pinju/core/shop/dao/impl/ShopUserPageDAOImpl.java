package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;

import com.yuwang.pinju.core.shop.dao.ShopUserPageDAO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * 店铺装修页面
 * 
 * @author yisong
 * 
 * @since 2011-6-20
 */
@SuppressWarnings("unchecked")
public class ShopUserPageDAOImpl extends BaseDAO implements ShopUserPageDAO {

	/**
	 * 删除指定页面（主要是根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserPage.deleteShopUserPage", userPageDO);
	}

	/**
	 * 保存已增加的一条装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserPage.insertShopUserPage", userPageDO);
	}

	/**
	 * 获取指定店铺自定义页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserPageDO> selectShopUserCustomerPage(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeQueryForList("shopUserPage.selectShopUserCustomerPage", userPageDO);
	}

	/**
	 * 获取相关参数获取所有可装修的页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserPageDO> selectShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeQueryForList("shopUserPage.selectShopUserPage", userPageDO);
	}

	/**
	 * 修改页面结构（根据USERID,PAGEID,SHOPID）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserPage.updateShopUserPage", userPageDO);
	}

	/**
	 * 修改页面内容（根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserPageByKey(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserPage.updateShopUserPageByKey", userPageDO);
	}

	/**
	 * 发布用户装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object releaseShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserPage.releaseShopUserPage", userPageDO);
	}

	/**
	 * 还原用户所有装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object restoreDesignUserPage(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserPage.restoreDesignUserPage", userPageDO);
	}

	@Override
	public ShopUserPageDO selectShopUserPageById(ShopUserPageDO userPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return (ShopUserPageDO) super.executeQueryForObject(
				"shopUserPage.selectShopUserPageById", userPageDO);
	}
}
