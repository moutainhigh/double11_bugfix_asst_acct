package com.yuwang.pinju.core.active.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.active.ActiveDescDO;
import com.yuwang.pinju.domain.active.ActiveInfoDO;
import com.yuwang.pinju.domain.active.ActiveInfoQuery;
import com.yuwang.pinju.domain.active.ActiveRegtDO;

public interface ActiveManager {

	/**
	 * 根据限制条件查询活动列表
	 * 
	 * @param query
	 * @return
	 */
	List<ActiveInfoDO> queryActiveList(ActiveInfoQuery query)
			throws ManagerException;

	/**
	 * 查询活动信息总数
	 * @param activeInfoQeury
	 * @return
	 * @throws ManagerException
	 */
	int queryActiveCount(ActiveInfoQuery query) throws ManagerException;
	
	/**
	 * 根据ID查询活动信息
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	ActiveInfoDO queryActiveInfoById(Long id) throws ManagerException;

	/**
	 * 根据活动信息查询活动报名描述信息
	 * 
	 * @param activeInfo
	 * @return
	 * @throws ManagerException
	 */
	ActiveDescDO queryActiveDescByActiveInfo(ActiveInfoDO activeInfo)
			throws ManagerException;

	/**
	 * 添加活动报名信息
	 * 
	 * @param acticeRegt
	 * @return
	 */
	Result addActiveRegistInfo(ActiveRegtDO activeRegt) throws ManagerException;

	/**
	 * 查询卖家已报名和已通过的活动信息
	 * @param activeInfoQeury
	 * @return
	 * @throws ManagerException
	 */
	List<ActiveInfoDO> queryActivePageList(ActiveInfoQuery activeInfoQeury)
			throws ManagerException;

	/**
	 * 查询卖家已报名和已通过的活动信息总数
	 * @param activeInfoQeury
	 * @return
	 * @throws ManagerException
	 */
	int queryActivePageCount(ActiveInfoQuery query) throws ManagerException;

	/**
	 * 查询卖家报名信息
	 * @param query
	 * @return
	 */
	List<ActiveRegtDO> queryActiveRegtList(ActiveInfoQuery query) throws ManagerException;

	/**
	 * 撤销活动报名
	 * @param activeRegt
	 * @return
	 * @throws ManagerException
	 */
	Result cancelActiveRegist(ActiveRegtDO activeRegt) throws ManagerException;

	/**
	 * 验证卖家还可报名商品数
	 * @param query
	 * @return
	 */
	int queryCanRegistActiveNum(ActiveInfoQuery query) throws ManagerException;

	/**
	 * 添加报名信息成功后，更新活动报名总数
	 * @param map
	 */
	int updateActiveInfoRegistNum(Map<String, Object> map) throws ManagerException;

}
