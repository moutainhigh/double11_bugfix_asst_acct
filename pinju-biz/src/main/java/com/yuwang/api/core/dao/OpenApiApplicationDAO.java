package com.yuwang.api.core.dao;

import java.util.List;
import com.yuwang.api.domain.OpenApiApplicationDO;
import com.yuwang.pinju.core.common.DaoException;

public interface OpenApiApplicationDAO {
	/**
	 * 获取所有OpenApiApplicationDO对象
	 */
	public List<OpenApiApplicationDO> getAllOpenApiApplication()
			throws DaoException;

	/**
	 * 根据Id获取OpenApiApplicationDO对象
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public OpenApiApplicationDO getOpenApiApplicationById(Long id)
			throws DaoException;

	/**
	 * 根据Id重置密码
	 * 
	 * @param id
	 * @param secret
	 * @return
	 * @throws DaoException
	 */
	public boolean resetApplicationSecretById(Long id, String secret)
			throws DaoException;
}
