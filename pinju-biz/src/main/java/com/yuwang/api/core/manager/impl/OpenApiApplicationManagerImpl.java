package com.yuwang.api.core.manager.impl;

import java.util.List;

import com.yuwang.api.core.dao.OpenApiApplicationDAO;
import com.yuwang.api.core.manager.OpenApiApplicationManager;
import com.yuwang.api.domain.OpenApiApplicationDO;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;

public class OpenApiApplicationManagerImpl extends BaseManager implements OpenApiApplicationManager {

	private OpenApiApplicationDAO openApiApplicationDAO;
	public OpenApiApplicationDAO getOpenApiApplicationDAO() {
		return openApiApplicationDAO;
	}

	public void setOpenApiApplicationDAO(OpenApiApplicationDAO openApiApplicationDAO) {
		this.openApiApplicationDAO = openApiApplicationDAO;
	}
	
	/**
	 * 获取所有OpenApiApplicationDO对象
	 */
	public List<OpenApiApplicationDO> getAllOpenApiApplication() throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return openApiApplicationDAO.getAllOpenApiApplication();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("查询所有OpenApiApplicationDO List出错",e);
		}
	}
	/**
	 * 根据主键 Id获取对象
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public OpenApiApplicationDO getOpenApiApplicationById(Long id)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return openApiApplicationDAO.getOpenApiApplicationById(id);
		} catch (DaoException e) {
			throw new ManagerException("根据Id查询OpenApiApplicationDO对象出错",e);
		}
	}
	
	/**
	 * 根据Id重置密码
	 * @param id
	 * @param secret
	 * @return
	 * @throws ManagerException
	 */
	public boolean resetApplicationSecretById(Long id, String secret)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return openApiApplicationDAO.resetApplicationSecretById(id, secret);
		} catch (DaoException e) {
			throw new ManagerException("根据Id重置密码出错：",e);
		}
	}

}
