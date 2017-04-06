package com.yuwang.pinju.core.item.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.BrandDO;

/**
 * 品牌DAO
 * 
 * @author liming
 * @since 2011-6-28
 */
public interface BrandDAO {

	/**
	 * 根据编号 获得品牌对象
	 * 
	 * @param categoryPropertyId
	 * @return
	 * @throws DaoException
	 */
	public BrandDO getItemBrandById(long id) throws DaoException;

}
