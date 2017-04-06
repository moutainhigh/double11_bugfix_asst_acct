package com.yuwang.api.core.manager.impl;

import java.util.List;

import com.yuwang.api.core.dao.OpenApiMethodDAO;
import com.yuwang.api.core.manager.OpenApiMethodManager;
import com.yuwang.api.domain.OpenApiMethodDO;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;

public class OpenApiMethodManagerImpl extends BaseManager  implements OpenApiMethodManager {

	private OpenApiMethodDAO openApiMethodDAO;
	public OpenApiMethodDAO getOpenApiMethodDAO() {
		return openApiMethodDAO;
	}
	public void setOpenApiMethodDAO(OpenApiMethodDAO openApiMethodDAO) {
		this.openApiMethodDAO = openApiMethodDAO;
	}
	/**
	 * 获得所有OpenApiMethodDO
	 * @return
	 * @throws MangerException 
	 */
	public List<OpenApiMethodDO> getAllList() throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return openApiMethodDAO.getAllList();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("查询所有OpenApiMethodDO List出错",e);
		}
	}

}
