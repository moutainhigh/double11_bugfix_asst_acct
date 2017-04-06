package com.yuwang.pinju.core.active.dao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.active.ActiveDescDO;
import com.yuwang.pinju.domain.active.ActiveInfoDO;
import com.yuwang.pinju.domain.active.ActiveInfoQuery;
import com.yuwang.pinju.domain.active.ActiveRegtDO;

public interface ActiveDao {

	/**
	 * 根据限制条件查询活动列表
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	List<ActiveInfoDO> selectActiveList(ActiveInfoQuery query) throws DaoException;
	
	/**
	 * 根据限制条件查询活动总数
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	int selectActiveCount(ActiveInfoQuery activeInfoQeury) throws DaoException;

	/**
	 * 根据ID查询活动详情
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	ActiveInfoDO selectActiveInfoById(Long id) throws DaoException;

	/**
	 * 根据活动信息查询活动报名描述信息
	 * @param activeInfo
	 * @return
	 * @throws DaoException
	 */
	ActiveDescDO selectActiveDescByActiveInfo(ActiveInfoDO activeInfo) throws DaoException;

	/**
	 * 添加活动报名信息
	 * @param acticeRegt
	 * @throws DaoException
	 */
	void insertActiveRegistInfo(ActiveRegtDO activeRegt) throws DaoException;

	/**
	 * 查询卖家已报名和已通过的活动信息
	 * @param activeInfoQeury
	 * @return
	 */
	List<ActiveInfoDO> selectActivePageList(ActiveInfoQuery activeInfoQeury) throws DaoException;

	/**
	 * 查询卖家已报名和已通过的活动信息总数
	 * @param activeInfoQeury
	 * @return
	 * @throws DaoException
	 */
	int selectActivePageCount(ActiveInfoQuery activeInfoQeury) throws DaoException;

	/**
	 * 查询卖家报名信息
	 * @param query
	 * @return
	 */
	List<ActiveRegtDO> selectActiveRegtList(ActiveInfoQuery query) throws DaoException;

	/**
	 * 更新活动报名信息状态
	 * @param activeRegt
	 * @return
	 */
	int updateActiveRegistCheckStatus(ActiveRegtDO activeRegt) throws DaoException;

	/**
	 * 撤销活动报名信息（删除）
	 * @param activeRegt
	 * @return
	 * @throws DaoException
	 */
	int deleteActiveRegistInfoById(Long id) throws DaoException;

	/**
	 * 验证卖家还可报名商品数
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	int selectCanRegistActiveNum(ActiveInfoQuery query) throws DaoException;

	/**
	 * 更新活动报名数量
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	int updateActiveInfoRegistNum(Map<String, Object> map) throws DaoException;
}
