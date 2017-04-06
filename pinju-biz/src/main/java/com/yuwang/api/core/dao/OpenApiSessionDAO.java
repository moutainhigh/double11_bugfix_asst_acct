package com.yuwang.api.core.dao;

import com.yuwang.api.domain.OpenApiSessionDO;
import com.yuwang.pinju.core.common.DaoException;

public interface OpenApiSessionDAO {
	/**
	 * 更新OpenApiSession
	 * @param sessionKey
	 * @return
	 */
	public Integer updateOpenApiSession(Long appid,String sessionKey)throws DaoException ;

	public Integer updateOpenApiSession(OpenApiSessionDO param)throws DaoException ;
	/**
	 * 插入OpenApiSession
	 * @param sellerId
	 * @param appid
	 * @return
	 */
	public Long saveOpenApiSession(OpenApiSessionDO apiSession)throws DaoException ;
	/**
	 * 根据sessionKey查询OpenApiSession
	 * @param sessionKey
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(Long appid,String sessionkey, int expireTime)throws DaoException ;
	/**
	 * 查询OpenApiSession
	 * @param OpenApiSessionDO
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(OpenApiSessionDO param)throws DaoException ;
}
