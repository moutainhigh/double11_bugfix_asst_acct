package com.yuwang.api.core.ao;

import java.util.Map;

import com.yuwang.api.domain.OpenApiMethodDO;

public interface OpenApiMethodAO {
	/**
	 * 获得所有的OpenApiMethodDO
	 * @return
	 */
  public Map<String,OpenApiMethodDO> getAllList();
}
