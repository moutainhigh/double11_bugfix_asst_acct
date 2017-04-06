package com.yuwang.api.core.manager;

import com.yuwang.api.domain.OpenApiSessionDO;
import com.yuwang.pinju.core.common.ManagerException;

public interface OpenApiSessionManager {
	/**
	 * 插入OpenApiSession
	 * @param sellerId
	 * @param appid
	 * @return
	 */
	public boolean saveOpenApiSession(Long sellerId,String nickName,Long appid,String sessionKey)throws ManagerException;
	
	/**
	 * 更新OpenApiSession
	 * @param sessionKey
	 * @param appId
	 * @return
	 */
	public boolean updateOpenApiSession(Long appId, String sessionkey)throws ManagerException;

	public boolean updateOpenApiSession(OpenApiSessionDO param)throws ManagerException;
	/**
	 * 根据sessionKey查询OpenApiSession
	 * @param sessionKey
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(Long appId,String sessionkey, int expireTime)throws ManagerException;
	
	/**
	 * 查询OpenApiSession
	 * @param OpenApiSessionDO
	 * @return OpenApiSessionDO
	 */
	public OpenApiSessionDO getOpenApiSession(OpenApiSessionDO param)throws ManagerException ;
}
