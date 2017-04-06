package com.yuwang.api.core.manager;

import java.util.List;

import com.yuwang.api.domain.OpenApiMethodDO;
import com.yuwang.pinju.core.common.ManagerException;

public interface OpenApiMethodManager {
	/**
	 * 获得所有OpenApiMethodDO
	 * @return
	 */
   public List<OpenApiMethodDO> getAllList() throws ManagerException ;
}
