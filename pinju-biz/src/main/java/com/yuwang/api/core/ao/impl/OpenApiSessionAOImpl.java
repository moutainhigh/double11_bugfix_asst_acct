package com.yuwang.api.core.ao.impl;

import com.yuwang.api.core.ao.OpenApiSessionAO;
import com.yuwang.api.core.manager.OpenApiSessionManager;
import com.yuwang.api.domain.OpenApiSessionDO;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.user.ao.BaseAO;

public class OpenApiSessionAOImpl extends BaseAO implements OpenApiSessionAO {

	private OpenApiSessionManager openApiSessionManager;

	public OpenApiSessionManager getOpenApiSessionManager() {
		return openApiSessionManager;
	}

	public void setOpenApiSessionManager(OpenApiSessionManager openApiSessionManager) {
		this.openApiSessionManager = openApiSessionManager;
	}

	/**
	 * 根据sessionKey查询OpenApiSession
	 * 
	 * @param sessionKey
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(Long appId, String sessionKey, int expireTime) {
		try {
			return openApiSessionManager.getOpenApiSession(appId, sessionKey, expireTime);
		} catch (ManagerException e) {
			log.error("查询OpenApiSession出错", e);
		}
		return null;
	}

	/**
	 * 插入OpenApiSession
	 * 
	 * @param sellerId
	 * @param appid
	 * @return
	 */
	public boolean saveOpenApiSession(Long sellerId, String nickName, Long appid, String sessionKey) {
		try {
			return openApiSessionManager.saveOpenApiSession(sellerId, nickName, appid, sessionKey);
		} catch (ManagerException e) {
			log.error("查询OpenApiSession出错", e);
		}
		return false;
	}

	/**
	 * 更新OpenApiSession
	 * 
	 * @param sessionKey
	 * @param appId
	 * @return
	 */
	public boolean updateOpenApiSession(Long appId, String sessionKey) {
		try {
			return openApiSessionManager.updateOpenApiSession(appId, sessionKey);
		} catch (ManagerException e) {
			log.error("查询OpenApiSession出错", e);
		}
		return false;
	}

	/**
	 * 更新OpenApiSession
	 * 
	 * @param param
	 * @return
	 */
	public boolean updateOpenApiSession(OpenApiSessionDO param) {
		try {
			return openApiSessionManager.updateOpenApiSession(param);
		} catch (ManagerException e) {
			log.error("查询OpenApiSession出错", e);
		}
		return false;
	}

	/**
	 * 查询OpenApiSession
	 * 
	 * @param OpenApiSessionDO
	 * @return
	 */
	@Override
	public OpenApiSessionDO getOpenApiSession(OpenApiSessionDO param) {
		try {
			return openApiSessionManager.getOpenApiSession(param);
		} catch (ManagerException e) {
			log.error("查询OpenApiSession出错", e);
		}
		return null;
	}

	/**
	 * 检查Session是否可用
	 * 
	 * @param appId
	 * @param sessionKey
	 * @return 用户ID
	 */
	@Override
	public OpenApiSessionDO checkSessionAuthorized(String appId, String sessionKey, int expireTime) {
		OpenApiSessionDO param = new OpenApiSessionDO();
		param.setAppid(Long.parseLong(appId));
		param.setSessionKey(sessionKey);
		OpenApiSessionDO oa = this.getOpenApiSession(Long.parseLong(appId), sessionKey, expireTime);
		if (null != oa && updateOpenApiSession(param)) {
			return oa;
		}
		return null;
	}
}
