package com.yuwang.api.core.dao.impl;

import java.util.List;

import com.yuwang.api.core.dao.OpenApiMethodDAO;
import com.yuwang.api.domain.OpenApiMethodDO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;

public class OpenApiMethodDAOImpl extends BaseDAO implements OpenApiMethodDAO {

	/**
	 * 获得所有OpenApiMethodDO
	 * @return
	 * @throws DaoException 
	 */
	@SuppressWarnings("unchecked")
	public List<OpenApiMethodDO> getAllList() throws DaoException {
		// TODO Auto-generated method stub
		OpenApiMethodDO method = new OpenApiMethodDO();
		method.setStatus(0);
		return super.executeQueryForList("open_api_method.selectOpenApiMethod", method);
	}

}
