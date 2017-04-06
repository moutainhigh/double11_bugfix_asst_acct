package com.yuwang.pinju.core.rate.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.domain.rate.read.CommentsItemDO;
import com.yuwang.pinju.domain.rate.read.CommentsSellerDO;

/**
 * <p>读取买家评论</p>
 *
 * @author gaobaowen
 * @since 2011-10-13 09:05:03
 */
public interface RateReadCommentsManager {

	/**
	 * <p>按商品分页读取买家交易后的评价。分页默认页大小配置文件的 key 为<code>comments.item.default.page.size</code>。</p>
	 *
	 * @param itemId  商品编号
	 * @param page    页码。页码小于 1 时将重置为 1
	 * @param pageSize  每页大小，如果值小于 1 或者大于 100 时采用默认值 {@link PinjuConstant#COMMENTS_ITEM_PAGE_SIZE COMMENTS_ITEM_PAGE_SIZE}
	 * @return  指定页数交易后评价数据，无异常发生时不会返回 null 值
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-10-13 09:08:12
	 */
	public CommentsItemDO getItemComments(long itemId, int page, int pageSize) throws ManagerException;

	/**
	 * <p>按卖家分页读取买家交易后的评价。分页默认页大小配置文件的 key 为<code>comments.seller.default.page.size</code>。</p>
	 *
	 * @param itemId  商品编号
	 * @param page    页码。页码小于 1 时将重置为 1
	 * @param pageSize  每页大小，如果值小于 1 或者大于 100 时采用默认值 {@link PinjuConstant#COMMENTS_SELLER_PAGE_SIZE COMMENTS_SELLER_PAGE_SIZE}
	 * @return  指定页数交易后评价数据，无异常发生时不会返回 null 值
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-10-13 09:08:12
	 */
	public CommentsSellerDO getSellerComments(long memberId, int page, int pageSize) throws ManagerException;

	/**
	 * <p>按商品统计评论数量</p>
	 *
	 * @param itemId  商品编号
	 * @return 商品评论数量
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 11:25:36
	 */
	public long countItemComments(long itemId) throws ManagerException;

	/**
	 * <p>按卖家统计评论数量</p>
	 *
	 * @param sellerId  卖家会员编号
	 * @return 针对于卖家的商品评论数量
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 11:26:18
	 */
	public long countSellerComments(long sellerId) throws ManagerException;
}
