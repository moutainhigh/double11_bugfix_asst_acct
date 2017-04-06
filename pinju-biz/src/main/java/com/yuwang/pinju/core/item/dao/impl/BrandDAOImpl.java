package com.yuwang.pinju.core.item.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.BrandDAO;
import com.yuwang.pinju.domain.item.BrandDO;

/**
 * 品牌DAO实现
 * 
 * @author liming
 * @since 2011-6-28
 */
public class BrandDAOImpl extends BaseDAO implements BrandDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.BrandDAO#getItemBrandByCategoryPropertyId(long)
	 */
	@Override
	public BrandDO getItemBrandById(long id) throws DaoException {
		return (BrandDO) executeQueryForObject("item_brand.getItemBrandById", id);
	}

}
