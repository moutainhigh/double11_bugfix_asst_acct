package com.yuwang.api.core.ao;

import com.yuwang.api.domain.OpenApiSessionDO;

public interface OpenApiSessionAO {

	/**
	 * 插入OpenApiSession
	 * @param sellerId
	 * @param appid
	 * @return
	 */
	public boolean saveOpenApiSession(Long sellerId,String nickName,Long appid,String sessionKey);
	
	/**
	 * 更新OpenApiSession
	 * @param sessionKey
	 * @param appId
	 * @return
	 */
	public boolean updateOpenApiSession(Long appId, String sessionKey);
	
	public boolean updateOpenApiSession(OpenApiSessionDO param);
	
	/**
	 * 根据sessionKey查询OpenApiSession
	 * @param sessionKey
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(Long appId,String sessionKey, int expireTime);
	
	/**
	 * 查询OpenApiSession
	 * @param OpenApiSessionDO
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(OpenApiSessionDO param);
	
	/**
	 * 检查Session是否可用
	 * @param appId
	 * @param sessionKey
	 * @return 用户ID
	 */
	public OpenApiSessionDO checkSessionAuthorized(String appId, String sessionKey, int expireTime);
}
