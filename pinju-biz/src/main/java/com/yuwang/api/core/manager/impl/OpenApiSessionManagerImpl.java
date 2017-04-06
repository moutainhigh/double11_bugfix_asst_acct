package com.yuwang.api.core.manager.impl;

import com.yuwang.api.core.dao.OpenApiSessionDAO;
import com.yuwang.api.core.manager.OpenApiSessionManager;
import com.yuwang.api.domain.OpenApiSessionDO;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;

public class OpenApiSessionManagerImpl extends BaseManager implements OpenApiSessionManager {

	private OpenApiSessionDAO openApiSessionDAO;

	public void setOpenApiSessionDAO(OpenApiSessionDAO openApiSessionDAO) {
		this.openApiSessionDAO = openApiSessionDAO;
	}

	/**
	 * 根据sessionKey查询OpenApiSession
	 * 
	 * @param sessionKey
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(Long appId, String sessionkey, int expireTime) throws ManagerException {
		try {
			return openApiSessionDAO.getOpenApiSession(appId,sessionkey, expireTime);
		} catch (DaoException e) {
			throw new ManagerException("根据sessionKey查询ApiSession错误", e);
		}
	}

	/**
	 * 插入OpenApiSession
	 * 
	 * @param sellerId
	 * @param appid
	 * @return
	 */
	public boolean saveOpenApiSession(Long sellerId, String nickName, Long appid, String sessionKey) throws ManagerException {
		try {
			OpenApiSessionDO apiSession = new OpenApiSessionDO();
			apiSession.setAppid(appid);
			apiSession.setNickName(nickName);
			apiSession.setSellerId(sellerId);
			apiSession.setSessionKey(sessionKey);
			return openApiSessionDAO.saveOpenApiSession(apiSession) == null ? false : true;
		} catch (DaoException e) {
			throw new ManagerException("保存ApiSession错误", e);
		}
	}

	/**
	 * 更新OpenApiSession
	 * 
	 * @param sessionKey
	 * @return
	 */
	public boolean updateOpenApiSession(Long appId,String sessionKey) throws ManagerException {
		try {
			return openApiSessionDAO.updateOpenApiSession(appId,sessionKey) == null ? false : true;
		} catch (DaoException e) {
			throw new ManagerException("更新ApiSession错误", e);
		}
	}


	/**
	 * 更新OpenApiSession
	 * 
	 * @param sessionKey
	 * @return
	 */
	public boolean updateOpenApiSession(OpenApiSessionDO param) throws ManagerException {
		try {
			return openApiSessionDAO.updateOpenApiSession(param) == null ? false : true;
		} catch (DaoException e) {
			throw new ManagerException("更新ApiSession错误", e);
		}
	}

	/**
	 * 查询OpenApiSession
	 * @param OpenApiSessionDO
	 * @return OpenApiSessionDO
	 */
	@Override
	public OpenApiSessionDO getOpenApiSession(OpenApiSessionDO param) throws ManagerException {
		try {
			return openApiSessionDAO.getOpenApiSession(param);
		} catch (DaoException e) {
			throw new ManagerException("查询ApiSession错误", e);
		}
	}

	public OpenApiSessionDAO getOpenApiSessionDAO() {
		return openApiSessionDAO;
	}

}
