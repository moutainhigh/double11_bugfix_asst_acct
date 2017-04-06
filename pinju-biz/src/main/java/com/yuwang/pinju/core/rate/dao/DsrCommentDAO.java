package com.yuwang.pinju.core.rate.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.rate.dao.page.ItemCommentsPage;
import com.yuwang.pinju.core.rate.dao.page.SellerCommentsPage;
import com.yuwang.pinju.domain.rate.DsrCommentDO;

/**
 * <p>买家购买后评论数据表操作</p>
 *
 * @author gaobaowen
 * @since 2011-10-12 09:32:57
 */
public interface DsrCommentDAO {

	/**
	 * <p>新增一批 DSR 评价数据</p>
	 *
	 * @param comments
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 10:42:14
	 */
	void insertDsrComments(List<DsrCommentDO> comments) throws DaoException;

	/**
	 * <p>统计时间范围内针对于商品的评价数量</p>
	 *
	 * @param itemPage
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 上午10:42:35
	 */
	long countDsrCommentByItem(long itemId) throws DaoException;
	
	long countDsrCommentByItemRealtime(long itemId) throws DaoException;
	List<DsrCommentDO> pagingDsrCommentByItem(ItemCommentsPage itemPage) throws DaoException;	

	long countDsrCommentBySellerId(long sellerId) throws DaoException;
	long countDsrCommentBySellerIdRealtime(long sellerId) throws DaoException;
	List<DsrCommentDO> pagingDsrCommentBySellerId(SellerCommentsPage sellerPage) throws DaoException;
}