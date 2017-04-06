package com.yuwang.api.core.manager;

import java.util.List;

import com.yuwang.api.domain.OpenApiApplicationDO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;

public interface OpenApiApplicationManager {
	/**
	 * 获取所有OpenApiApplicationDO对象
	 */
	public List<OpenApiApplicationDO> getAllOpenApiApplication()throws ManagerException  ;
	/**
	 * 根据Id获取OpenApiApplicationDO对象
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public OpenApiApplicationDO getOpenApiApplicationById(Long id)throws ManagerException;
	
	/**
	 * 根据Id重置密码
	 * @param id
	 * @param secret
	 * @return
	 * @throws ManagerException
	 */
	public boolean resetApplicationSecretById(Long id, String secret) throws ManagerException;
}
