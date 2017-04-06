package com.yuwang.pinju.core.active.manager.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.active.dao.ActiveDao;
import com.yuwang.pinju.core.active.manager.ActiveManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.domain.active.ActiveDescDO;
import com.yuwang.pinju.domain.active.ActiveInfoDO;
import com.yuwang.pinju.domain.active.ActiveInfoQuery;
import com.yuwang.pinju.domain.active.ActiveRegtDO;

public class ActiveManagerImpl extends BaseManager implements ActiveManager {
	private ActiveDao activeDao;

	@Override
	public List<ActiveInfoDO> queryActiveList(ActiveInfoQuery query)
			throws ManagerException {
		List<ActiveInfoDO> activeInfoList = null;
		try {
			activeInfoList = activeDao.selectActiveList(query);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryActiveList Exception : ", e);
		}
		return activeInfoList;
	}
	
	@Override
	public int queryActiveCount(ActiveInfoQuery query)
			throws ManagerException {
		int pageCount = 0;
		try {
			pageCount = activeDao.selectActiveCount(query);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryActiveCount() Exception : ", e);
		}
		return pageCount;
	}

	@Override
	public List<ActiveRegtDO> queryActiveRegtList(ActiveInfoQuery query)
			throws ManagerException {
		List<ActiveRegtDO> activeRegtList = null;
		try {
			activeRegtList = activeDao.selectActiveRegtList(query);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryActiveRegtList Exception : ", e);
		}
		return activeRegtList;
	}

	@Override
	public ActiveInfoDO queryActiveInfoById(Long id) throws ManagerException {
		ActiveInfoDO activeInfo = null;
		try {
			activeInfo = activeDao.selectActiveInfoById(id);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryActiveInfoById Exception : ", e);
		}
		return activeInfo;
	}

	@Override
	public ActiveDescDO queryActiveDescByActiveInfo(ActiveInfoDO activeInfo)
			throws ManagerException {
		ActiveDescDO activeDesc = null;
		try {
			activeDesc = activeDao.selectActiveDescByActiveInfo(activeInfo);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryActiveDescByActiveInfo Exception : ", e);
		}
		return activeDesc;
	}

	@Override
	public Result addActiveRegistInfo(ActiveRegtDO activeRegt)
			throws ManagerException {
		Result result = new ResultSupport();
		result.setSuccess(true);
		try {
			activeDao.insertActiveRegistInfo(activeRegt);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.addActiveRegistInfo Exception : ", e);
		}
		return result;
	}

	@Override
	public List<ActiveInfoDO> queryActivePageList(
			ActiveInfoQuery activeInfoQeury) throws ManagerException {
		List<ActiveInfoDO> activeInfoList = null;
		try {
			activeInfoList = activeDao.selectActivePageList(activeInfoQeury);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryActivePageList Exception : ", e);
		}
		return activeInfoList;
	}

	@Override
	public int queryActivePageCount(ActiveInfoQuery query)
			throws ManagerException {
		int pageCount = 0;
		try {
			pageCount = activeDao.selectActivePageCount(query);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryActivePageCount() Exception : ", e);
		}
		return pageCount;
	}

	@Override
	public Result cancelActiveRegist(ActiveRegtDO activeRegt)
			throws ManagerException {
		Result result = new ResultSupport();
		result.setSuccess(true);
		try {
			// 撤销活动报名，则删除原活动报名信息
			// int res = activeDao.updateActiveRegistCheckStatus(activeRegt);
			int res = activeDao.deleteActiveRegistInfoById(activeRegt.getId());
			if(res != 1){
				result.setSuccess(false);
				result.setModel("errorMessage", "撤销活动报名异常：影响记录数错误！");
			}
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.cancelActiveRegist Exception : ", e);
		}
		return result;
	}

	@Override
	public int queryCanRegistActiveNum(ActiveInfoQuery query) throws ManagerException {
		int num = 1;
		try {
			num = activeDao.selectCanRegistActiveNum(query);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.queryCanRegistActiveNum() Exception : ", e);
		}
		return num;
	}

	public ActiveDao getActiveDao() {
		return activeDao;
	}

	public void setActiveDao(ActiveDao activeDao) {
		this.activeDao = activeDao;
	}

	@Override
	public int updateActiveInfoRegistNum(Map<String, Object> map)
			throws ManagerException {
		int result = 0;
		try {
			result = activeDao.updateActiveInfoRegistNum(map);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActiveManager.updateActiveInfoRegistNum Exception : ", e);
		}
		return result;
	}

}
