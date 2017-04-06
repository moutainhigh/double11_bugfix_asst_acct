package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopCustomPageDAO;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;


/**
 * 店铺自定义页面
 * 
 * @author yisong
 *
 * @since 2011-6-20
 */
public class ShopCustomPageDAOImpl extends BaseDAO  implements ShopCustomPageDAO {

	/**
	 * 获得自定义模块
	 * @param shopModulePrototypeDO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopModulePrototypeDO> getCustomModuleByPage(ShopModulePrototypeDO shopModulePrototypeDO) throws DaoException {
		// TODO Auto-generated method stub
		
		List<ShopModulePrototypeDO> resultList = (List<ShopModulePrototypeDO>) super
				.executeQueryForList("shopModulePrototype.getCustomModuleByPage", shopModulePrototypeDO);
		return resultList;
	}
	
	/**
	 * 保存自定义模块
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @param layoutXml
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Object saveCustomModuleByPage(ShopUserPageDO shopUserPageDO) throws DaoException{
		return super.executeInsert("shopUserPage.insertShopUserPage", shopUserPageDO);
	}

	
	/**
	 * 获取自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShopUserPageDO queryCustomPageModule(ShopUserPageDO shopUserPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		List<ShopUserPageDO> resultList = super.executeQueryForList("shopUserPage.selectShopUserPage", shopUserPageDO);
		return resultList.size() == 0 ? null : resultList.get(0);
	}

	/**
	 * 更新自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Object updateCustomModuleByPage(ShopUserPageDO shopUserPageDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserPage.updateShopUserPage", shopUserPageDO);
	}
}
