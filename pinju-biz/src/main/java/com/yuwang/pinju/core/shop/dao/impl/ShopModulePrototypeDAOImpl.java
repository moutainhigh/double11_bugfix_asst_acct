package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopModulePrototypeDAO;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * 装修模块原型DAO层实现
 * 
 * @author liyouguo
 * 
 * @since 2011-7-4
 */
public class ShopModulePrototypeDAOImpl extends BaseDAO implements
		ShopModulePrototypeDAO {

	/**
	 * 获取待编辑的页面的指定部分可添加的模块
	 * 
	 * @param pageId
	 *            --页面编号与表SHOP_PAGE_PROTOTYPE关联 type --页面的哪个部分（上，左，右，下）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopModulePrototypeDO> getDesignerModuleByPage(
			ShopModulePrototypeDO shopModulePrototypeDO) throws DaoException {
		// TODO Auto-generated method stub

		List<ShopModulePrototypeDO> resultList = (List<ShopModulePrototypeDO>) super
				.executeQueryForList("shopModulePrototype.getDesignerModuleByPage",
						shopModulePrototypeDO);
		return resultList;
	}
}
