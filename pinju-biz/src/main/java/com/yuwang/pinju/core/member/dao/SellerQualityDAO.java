package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.SellerQualityDO;

public interface SellerQualityDAO {

	/**
	 * <p>根据用户编号查找卖家资质信息</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	SellerQualityDO getSellerQualityByMemberId(long memberId) throws DaoException;

	/**
	 * <p>新增一个商家资质信息</p>
	 *
	 * @param sellerQuality
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	SellerQualityDO insertSellerQuality(SellerQualityDO sellerQuality) throws DaoException;

	/**
	 * <p>更新卖家资质信息</p>
	 *
	 * @param sellerQuality
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 15:39:17
	 */
	int updateSellerQuality(SellerQualityDO sellerQuality) throws DaoException;
}
