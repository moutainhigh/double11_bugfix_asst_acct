package com.yuwang.api.core.ao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.api.core.ao.OpenApiApplicationAO;
import com.yuwang.api.core.manager.OpenApiApplicationManager;
import com.yuwang.api.domain.OpenApiApplicationDO;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.user.ao.BaseAO;

public class OpenApiApplicationAOImpl extends BaseAO implements OpenApiApplicationAO {

	private OpenApiApplicationManager openApiApplicationManager;
	
	public OpenApiApplicationManager getOpenApiApplicationManager() {
		return openApiApplicationManager;
	}

	public void setOpenApiApplicationManager(
			OpenApiApplicationManager openApiApplicationManager) {
		this.openApiApplicationManager = openApiApplicationManager;
	}

	/**
	 * 获取所有信息
	 * @return
	 */
	public Map<Long, OpenApiApplicationDO> getAllOpenApiApplication() {
		Map <Long,OpenApiApplicationDO> map=new HashMap<Long, OpenApiApplicationDO>();
		try {
			 List<OpenApiApplicationDO> list=openApiApplicationManager.getAllOpenApiApplication();
			 if(list.size()>0){
				 for(OpenApiApplicationDO openApiApplication : list){
					 map.put(openApiApplication.getId(), openApiApplication);
				 }
			 }
			} catch (ManagerException e) {
				// TODO Auto-generated catch block
			   log.error("查询OpenApiMethodDO所有信息是出错",e);
			}
			return map;
	}

}
