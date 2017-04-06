package com.yuwang.api.core.dao;

import java.util.List;

import com.yuwang.api.domain.OpenApiMethodDO;
import com.yuwang.pinju.core.common.DaoException;

public interface OpenApiMethodDAO {
	
	/**
	 * 获得所有OpenApiMethodDO
	 * @return
	 */
   public List<OpenApiMethodDO> getAllList() throws DaoException;
}
