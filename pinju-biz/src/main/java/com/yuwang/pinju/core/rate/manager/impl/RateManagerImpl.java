package com.yuwang.pinju.core.rate.manager.impl;

import java.util.LinkedList;
import java.util.List;

import com.yuwang.pinju.core.cache.CacheUtil;
import com.yuwang.pinju.core.cache.MemcachedManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.rate.dao.DsrCommentDAO;
import com.yuwang.pinju.core.rate.dao.DsrDimensionDAO;
import com.yuwang.pinju.core.rate.dao.DsrResultDAO;
import com.yuwang.pinju.core.rate.manager.RateManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.rate.DsrCommentDO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;
import com.yuwang.pinju.domain.rate.DsrResultDO;

public class RateManagerImpl extends BaseManager implements RateManager {

	private DsrDimensionDAO dsrDimensionDAO;
	private DsrResultDAO dsrResultDAO;
	private DsrCommentDAO dsrCommentDAO;
	private MemcachedManager qualitityMemcachedManager;

	public List<DsrDimensionDO> getValidDsrDimension() throws ManagerException {
		return getValidDsrDimension(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DsrDimensionDO> getValidDsrDimension(boolean isCacheDebug) throws ManagerException {
		if (log.isInfoEnabled()) {
			log.debug("getValidDsrDimension, isCacheDebug: [" + isCacheDebug + "], isReadCache? [" + CacheUtil.isReadCache(isCacheDebug) + "]");
		}
		try {
			List<DsrDimensionDO> dimensions = (List<DsrDimensionDO>)qualitityMemcachedManager.getCacheObject(DsrDimensionDO.MEMCACHED_KEY, List.class);
			if (log.isDebugEnabled()) {
				log.debug("getValidDsrDimension from memcached, key: " + DsrDimensionDO.MEMCACHED_KEY + ", result: " + dimensions);
			}

			if (CacheUtil.isReadCache(isCacheDebug) && dimensions != null) {
				if (log.isDebugEnabled()) {
					log.debug("getValidDsrDimension, was found in memcached, dimensions size: [" + dimensions.size() + "]");
				}
				return dimensions;
			}

			dimensions = dsrDimensionDAO.getValidDsrDimensions();
			boolean result = qualitityMemcachedManager.setCacheObject(DsrDimensionDO.MEMCACHED_KEY, dimensions);
			log.info("getValidDsrDimension, can not find data from memcached, rebuild it from database and set it to " +
					"memcached, set memcached result: " + result + ", dimensions size: " + (dimensions == null ? "null" : dimensions.size()));
			return dimensions;
		} catch (DaoException e) {
			log.error("getValidDsrDimension error", e);
			throw new ManagerException("getValidDsrDimension error", e);
		}
	}

	@Override
	public List<DsrDimensionDO> filterItemDsrDimension(List<DsrDimensionDO> list) throws ManagerException {
		return filterDsr(list, DsrDimensionDO.DSR_TYPE_ITEM);
	}

	@Override
	public List<DsrDimensionDO> filterRateDsrDimension(List<DsrDimensionDO> list) throws ManagerException {
		return filterDsr(list, DsrDimensionDO.DSR_TYPE_RATE);
	}

	@Override
	public void saveDsrResults(List<DsrResultDO> dsrResults) throws ManagerException {
		try {
			dsrResultDAO.saveDsrResults(dsrResults);
		} catch (DaoException e) {
			log.error("saveDsrResults error", e);
			throw new ManagerException("saveDsrResults error", e);
		}
	}

	@Override
	public void saveDsrComments(List<DsrCommentDO> comments) throws ManagerException {
		if (EmptyUtil.isBlank(comments)) {
			return;
		}
		try {
			dsrCommentDAO.insertDsrComments(comments);
		} catch (DaoException e) {
			if (EmptyUtil.isBlank(comments)) {
				log.error("saveDsrComments error, comments is null or empty", e);
			} else {
				StringBuilder builder = new StringBuilder();
				builder.append("saveDsrComments error, comments size: " + comments.size());
				int index = 1;
				for (DsrCommentDO comment : comments) {
					builder.append("\n").append(index++).append(". ").append(comment);
				}
				log.error(builder.toString(), e);
			}
			throw new ManagerException("saveDsrComments error", e);
		}
	}

	/**
	 * <p>在所有有效的 DSR 数据中过滤出指定类型的 DSR</p>
	 *
	 * @param list     所有有效的 DSR 数据
	 * @param dsrType  DSR 类型
	 * @return
	 *
	 * @author gaobaowen
	 */
	private List<DsrDimensionDO> filterDsr(List<DsrDimensionDO> list, Integer dsrType) {
		List<DsrDimensionDO> filters = new LinkedList<DsrDimensionDO>();
		for(DsrDimensionDO dsr : list) {
			if(dsrType.equals(dsr.getDsrType())) {
				filters.add(dsr);
			}
		}
		return filters;
	}

	public void setDsrDimensionDAO(DsrDimensionDAO dsrDimensionDAO) {
		this.dsrDimensionDAO = dsrDimensionDAO;
	}

	public void setDsrResultDAO(DsrResultDAO dsrResultDAO) {
		this.dsrResultDAO = dsrResultDAO;
	}

	public void setDsrCommentDAO(DsrCommentDAO dsrCommentDAO) {
		this.dsrCommentDAO = dsrCommentDAO;
	}

	public void setQualitityMemcachedManager(MemcachedManager qualitityMemcachedManager) {
		this.qualitityMemcachedManager = qualitityMemcachedManager;
	}
}
