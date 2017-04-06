package com.yuwang.pinju.core.constant.rate;

import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.domain.order.OrderDO;

/**
 * <p>动态评价 {@link ResultSupport#resultCode} 常量</p>
 * @author gaobaowen
 * 2011-6-15 上午09:21:14
 */
public interface RateResultConstant {

	/**
	 * 内部执行错误
	 */
	String RESULT_RATE_INNER_ERROR = "result.rate.inner.error";

	/**
	 * 订单无效（非成功状态，即 {@link OrderDO#orderState} 值不是 {@link OrderDO#ORDER_STATE_5}）
	 */
	String RESULT_RATE_ORDER_INVALID = "result.rate.order.invalid";

	/**
	 * 订单的买家不是当前用户
	 */
	String RESULT_RATE_ORDER_ACCESS_DENIED = "result.rate.order.access.denied";

	/**
	 * 用户评价已经超过评价时效（15 天，常量定义于 {@link OrderDO#ORDER_RATE_TIME_LIMIT}）
	 */
	String RESULT_RATE_ORDER_RATE_EXPIRED = "result.rate.order.rate.expired";

	/**
	 * 已经评价过，不再允许评价
	 */
	String RESULT_RATE_ORDER_RATE_DUPLICATE = "result.rate.order.rate.duplicate";

	/**
	 * 动态评论的数据错误
	 */
	String RESULT_RATE_SUBMIT_DATA_ERROR = "result.rate.submit.data.error";
}
