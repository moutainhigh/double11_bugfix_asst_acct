package com.yuwang.pinju.core.rate.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.rate.dao.DsrCommentDAO;
import com.yuwang.pinju.core.rate.dao.page.ItemCommentsPage;
import com.yuwang.pinju.core.rate.dao.page.SellerCommentsPage;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.rate.DsrCommentDO;

public class DsrCommentDAOImpl extends BaseDAO implements DsrCommentDAO {

	/**
	 * RATE_DSR_COMMENT 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "rate_dsr_comment.";

	private ReadBaseDAO readBaseDAOForMySql;
	private ReadBaseDAO readBaseDAOForOracle;

	public void setReadBaseDAOForMySql(ReadBaseDAO readBaseDAOForMySql) {
		this.readBaseDAOForMySql = readBaseDAOForMySql;
	}

	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}

	@Override
	public void insertDsrComments(List<DsrCommentDO> comments) throws DaoException {
		if (EmptyUtil.isBlank(comments)) {
			return;
		}
		Date current = DateUtil.current();
		for (DsrCommentDO comment : comments) {
			comment.setGmtCreate(current);
			comment.setGmtModified(current);
		}
		super.executeBatchInsert(NAMESPACE + "insertDsrComment", comments, 100);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DsrCommentDO> pagingDsrCommentByItem(ItemCommentsPage itemPage) throws DaoException {
		if (itemPage == null) {
			return new ArrayList<DsrCommentDO>(0);
		}
		return (List<DsrCommentDO>)readBaseDAOForMySql.executeQueryForList(NAMESPACE + "pagingDsrCommentByItem", itemPage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DsrCommentDO> pagingDsrCommentBySellerId(SellerCommentsPage sellerPage) throws DaoException {
		if (sellerPage == null) {
			return new ArrayList<DsrCommentDO>(0);
		}
		return (List<DsrCommentDO>)readBaseDAOForMySql.executeQueryForList(NAMESPACE + "pagingDsrCommentBySellerId", sellerPage);
	}

	@Override
	public long countDsrCommentByItem(long itemId) throws DaoException {
		if (itemId < 1) {
			return 0L;
		}
		Long count = (Long)readBaseDAOForOracle.executeQueryForObject(NAMESPACE + "countDsrCommentByItem", itemId);
		return (count == null ? 0 : count);
	}

	@Override
	public long countDsrCommentBySellerId(long sellerId) throws DaoException {
		if (sellerId < 1) {
			return 0L;
		}
		Long count = (Long)readBaseDAOForOracle.executeQueryForObject(NAMESPACE + "countDsrCommentBySellerId", sellerId);
		return (count == null ? 0 : count);
	}

	@Override
	public long countDsrCommentByItemRealtime(long itemId) throws DaoException {
		if (itemId < 1) {
			return 0L;
		}
		Long count = (Long)readBaseDAOForMySql.executeQueryForObject(NAMESPACE + "countDsrCommentByItemRealtime", itemId);
		return (count == null ? 0 : count);
	}

	@Override
	public long countDsrCommentBySellerIdRealtime(long sellerId) throws DaoException {
		if (sellerId < 1) {
			return 0L;
		}
		Long count = (Long)readBaseDAOForMySql.executeQueryForObject(NAMESPACE + "countDsrCommentBySellerIdRealtime", sellerId);
		return (count == null ? 0 : count);
	}
}
