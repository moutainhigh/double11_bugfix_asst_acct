package com.yuwang.api.core.dao.impl;

import java.util.List;
import com.yuwang.api.core.dao.OpenApiApplicationDAO;
import com.yuwang.api.domain.OpenApiApplicationDO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;

public class OpenApiApplicationDAOImpl extends BaseDAO implements
		OpenApiApplicationDAO {

	@SuppressWarnings("unchecked")
	/**
	 * 获取所有OpenApiApplicationDO对象
	 */
	public List<OpenApiApplicationDO> getAllOpenApiApplication()
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeQueryForList(
				"open_api_application.selectAllOpenApiApplication",
				new OpenApiApplicationDO());
	}

	/**
	 * 根据主键Id获取OpenApiApplicationDO对象
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public OpenApiApplicationDO getOpenApiApplicationById(Long id)
			throws DaoException {
		// TODO Auto-generated method stub
		return (OpenApiApplicationDO) super.executeQueryForObject(
				"open_api_application.selectOpenApiApplicationByid", id);
	}

	/**
	 * 根据Id重置密码
	 * 
	 * @param id
	 * @param secret
	 * @return
	 * @throws DaoException
	 */
	public boolean resetApplicationSecretById(Long id, String secret)
			throws DaoException {
		// TODO Auto-generated method stub
		OpenApiApplicationDO appDo = new OpenApiApplicationDO();
		appDo.setId(id);
		appDo.setSecret(secret);
		return super.executeUpdate(
				"open_api_application.resetApplicationSecretById", appDo) > 0;
	}
}
