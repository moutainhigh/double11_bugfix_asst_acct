package com.yuwang.api.core.ao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.api.core.ao.OpenApiMethodAO;
import com.yuwang.api.core.manager.OpenApiMethodManager;
import com.yuwang.api.domain.OpenApiMethodDO;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.user.ao.BaseAO;

public class OpenApiMethodAOImpl extends BaseAO implements OpenApiMethodAO {

	private OpenApiMethodManager openApiMethodManager;
	
	public OpenApiMethodManager getOpenApiMethodManager() {
		return openApiMethodManager;
	}

	public void setOpenApiMethodManager(OpenApiMethodManager openApiMethodManager) {
		this.openApiMethodManager = openApiMethodManager;
	}

	/**
	 * 获得所有OpenApiMethodDO
	 * @return
	 */
	public Map<String,OpenApiMethodDO> getAllList() {
		// TODO Auto-generated method stub
		Map <String,OpenApiMethodDO> map=new HashMap<String, OpenApiMethodDO>();
		try {
		 List<OpenApiMethodDO> list=openApiMethodManager.getAllList();
		 if(list.size()>0){
			 for(OpenApiMethodDO openApiMethod : list){
				 map.put(openApiMethod.getMethodName(), openApiMethod);
			 }
		 }
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
		   log.error("查询OpenApiMethodDO所有信息是出错",e);
		}
		return map;
	}

}
