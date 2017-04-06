package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.SellerQualityDAO;
import com.yuwang.pinju.domain.member.SellerQualityDO;

/**
 * <p>卖家资质信息 DAO</p>
 *
 * @author gaobaowen
 * 2011-6-14 下午03:02:15
 */
public class SellerQualityDAOImpl extends BaseDAO implements SellerQualityDAO {

	/**
	 * MEMBER_SELLER_QUALITY 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "SellerQuality.";

	@Override
	public SellerQualityDO getSellerQualityByMemberId(long memberId) throws DaoException {
		return (SellerQualityDO) super.executeQueryForObject(NAMESPACE + "getSellerQualityByMemberId", memberId);
	}

	@Override
	public SellerQualityDO insertSellerQuality(SellerQualityDO sellerQuality) throws DaoException {
		Date current = DateUtil.current();
		sellerQuality.setGmtCreate(current);
		sellerQuality.setGmtModified(current);
		Number id = (Number) super.executeInsert(NAMESPACE + "insertSellerQuality", sellerQuality);
		sellerQuality.setId(id.longValue());
		return sellerQuality;
	}

	@Override
	public int updateSellerQuality(SellerQualityDO sellerQuality) throws DaoException {
		sellerQuality.setGmtModified(DateUtil.current());
		return super.executeUpdate(NAMESPACE + "updateSellerQuality", sellerQuality);
	}
}
