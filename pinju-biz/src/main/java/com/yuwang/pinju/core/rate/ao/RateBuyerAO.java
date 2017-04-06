package com.yuwang.pinju.core.rate.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.rate.RateKeyConstant;
import com.yuwang.pinju.core.constant.rate.RateResultConstant;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;
import com.yuwang.pinju.domain.rate.comment.RateOrderModel;

/**
 * <p>买家动态评价</p>
 *
 * @author gaobaowen
 * 2011-6-15 上午09:22:59
 */
public interface RateBuyerAO {

	/**
	 * 过滤词类型 -- 购买后评价（7）
	 */
	Integer WORD_FILTER_TYPE = 7;

	/**
	 * <p>查询买家订单的评价时显示的数据</p>
	 *
	 * @param orderId
	 * @return Result 中相关对象说明，使用 {@link Result#getModel(String)} 方法获取强转后使用。
	 * <ul>
	 *   <li>{@link RateKeyConstant#KEY_RATE_ORDER}：{@link OrderDO} 对象</li>
	 *   <li>{@link RateKeyConstant#KEY_RATE_ORDER_ITEMS}：List<{@link OrderItemDO}> 对象</li>
	 *   <li>{@link RateKeyConstant#KEY_RATE_SELLER_MEMBER}：卖家 {@link MemberDO} 对象</li>
	 *   <li>{@link RateKeyConstant#KEY_RATE_BUYER_MEMBER}：买家 {@link MemberDO} 对象</li>
	 *   <li>{@link RateKeyConstant#KEY_RATE_ITEM_DSRS}：商品 DSR {@link DsrDimensionDO} 对象</li>
	 *   <li>{@link RateKeyConstant#KEY_RATE_SELLER_MEMBER}：店铺 DSR {@link DsrDimensionDO} 对象</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link RateResultConstant#RESULT_RATE_ORDER_INVALID}：订单不存在，或者订单结构无效</li>
	 *   <li>{@link RateResultConstant#RESULT_RATE_INNER_ERROR}：服务端处理内部错误</li>
	 *   <li>{@link RateResultConstant#RESULT_RATE_ORDER_ACCESS_DENIED}：当前会员不存在，或者订单的买家不是当前会员</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 */
	Result queryOrderForRate(long buyerMemberId, long orderId);

	/**
	 * <p>订单评论。将订单数据更新为已评价，同时新增用户的 DSR 评论，在执行这些操作之前会再次执行 {@link #queryOrderForRate}
	 * 方法，以再次检查评论状态</p>
	 *
	 * @param buyerMemberId   买家会员编号
	 * @param orderId         评论订单编号
	 * @param dsrResults      买家的 DSR 评论（评论条数为购买商品量乘以 DSR 维度数量）
	 * @return  <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link RateResultConstant#RESULT_RATE_SUBMIT_DATA_ERROR}：DSR 评论所提交的数据不正确</li>
	 *   <li>除此之外，其他余错误代码见 {@link #queryOrderForRate} 相关说明</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 */
	Result rateOrder(long buyerMemberId, final RateOrderModel model);
}
