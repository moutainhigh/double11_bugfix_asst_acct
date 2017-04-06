package com.yuwang.api.core.dao.impl;

import java.util.HashMap;

import com.yuwang.api.core.dao.OpenApiSessionDAO;
import com.yuwang.api.domain.OpenApiSessionDO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;

public class OpenApiSessionDAOImpl extends BaseDAO implements OpenApiSessionDAO{

	/**
	 * 根据sessionKey查询OpenApiSession
	 * @param sessionKey
	 * @return
	 */
	public OpenApiSessionDO getOpenApiSession(Long appid,String sessionKey, int expireTime) throws DaoException {
		 HashMap<Object,Object> map=new HashMap<Object, Object>();
	      map.put("appid", appid);
	      map.put("sessionKey", sessionKey);
	      if(-1 != expireTime){
	    	  map.put("expireTime", expireTime);
	      }
		return (OpenApiSessionDO) super.executeQueryForObject("open_api_session.selectOpenApiSessionBysessionKey", map);
	}

	/**
	 * 插入OpenApiSession
	 * @param sellerId
	 * @param appid
	 * @return
	 */
	public Long saveOpenApiSession(OpenApiSessionDO apiSession) throws DaoException {
		return (Long) super.executeInsert("open_api_session.insertOpenApiSession",apiSession);
	}

	/**
	 * 更新OpenApiSession
	 * @param sessionKey
	 * @param appid
	 * @return
	 */
	public Integer updateOpenApiSession(Long appid,String sessionKey) throws DaoException {
        HashMap<Object,Object> map=new HashMap<Object, Object>();
        map.put("appid", appid);
        map.put("sessionKey", sessionKey);
		return super.executeUpdate("open_api_session.updateOpenApiSession",map);
     } 
	
	public Integer updateOpenApiSession(OpenApiSessionDO param) throws DaoException {
		return super.executeUpdate("open_api_session.updateOpenApiSession",param);
	}
	
	/**
	 * 查询OpenApiSession
	 * @param OpenApiSessionDO
	 * @return
	 */
	@Override
	public OpenApiSessionDO getOpenApiSession(OpenApiSessionDO param) throws DaoException {
		return (OpenApiSessionDO)super.executeQueryForObject("open_api_session.selectOpenApiSession", param);
	}

}
