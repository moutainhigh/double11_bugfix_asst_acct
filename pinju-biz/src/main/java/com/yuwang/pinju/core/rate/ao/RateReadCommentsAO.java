package com.yuwang.pinju.core.rate.ao;

import com.yuwang.pinju.core.common.Result;

public interface RateReadCommentsAO {

	/**
	 * <p>通过商品编号分页获取针对于商品的评论数量</p>
	 *
	 * @param itemId  商品编号
	 * @param page 分页页码
	 * @param pageSize 每页数量
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 11:31:20
	 */
	Result getItemComments(long itemId, int page, int pageSize);

	/**
	 * <p>通过卖家会员编号分页获取针对于卖家的评论数量</p>
	 *
	 * @param memberId  商品编号
	 * @param page 分页页码
	 * @param pageSize 每页数量
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 11:31:20
	 */
	Result getSellerComments(long memberId, int page, int pageSize);

	/**
	 * <p>通过商品编号统计针对于该商品的评论数量</p>
	 *
	 * @param itemId 商品编号
	 * @return 评论数量。若发生错误时将返回 0 值
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 11:32:32
	 */
	long countItemComments(long itemId);

	/**
	 * <p>通过卖家会员编号统计针对于该卖家的评论数量</p>
	 *
	 * @param sellerId 卖家会员编号
	 * @return 评论数量。若发生错误时将返回 0 值
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 11:32:32
	 */
	long countSellerComments(long sellerId);
}
