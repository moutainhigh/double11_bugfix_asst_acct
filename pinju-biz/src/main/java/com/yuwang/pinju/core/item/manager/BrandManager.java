package com.yuwang.pinju.core.item.manager;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.BrandDO;

/**
 * 品牌manager
 * 
 * @author liming
 * @since 2011-6-28
 * 
 */
public interface BrandManager {

	/**
	 * 根据编号 获得品牌对象
	 * 
	 * @param categoryPropertyId
	 * @return
	 * @throws DaoException
	 */
	public BrandDO getItemBrandById(long id) throws ManagerException;

}
