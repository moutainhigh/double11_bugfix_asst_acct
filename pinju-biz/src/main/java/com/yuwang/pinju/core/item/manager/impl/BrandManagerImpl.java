package com.yuwang.pinju.core.item.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.BrandDAO;
import com.yuwang.pinju.core.item.manager.BrandManager;
import com.yuwang.pinju.domain.item.BrandDO;

/**
 * 品牌manager实现
 * 
 * @author liming
 * @since 2011-6-28
 * 
 */
public class BrandManagerImpl extends BaseManager implements BrandManager {

	private BrandDAO brandDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.BrandManager#getItemBrandById(long)
	 */
	@Override
	public BrandDO getItemBrandById(long id) throws ManagerException {
		try {
			return brandDAO.getItemBrandById(id);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	public void setBrandDAO(BrandDAO brandDAO) {
		this.brandDAO = brandDAO;
	}

}
