package com.yuwang.pinju.core.refund.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.refund.RefundDO;

public interface BuyerRefundTimeoutAO {
	
	/**
	 * <p>检查是否卖家拒绝退款，买家5天超时</p>
	 * @param refundDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-4
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Boolean isBuyerNoReplyTimeout(RefundDO refundDO);
	
	 /**
	 * <p>Description: 卖家同意退款，而买家在5天内不退还商品。
	 * 1.获取所有超时 状态为等待买家退还商品的退款单
	 * 2.关闭该退款 和子订单的退款状态</p>
	 * @param refundDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-4
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Boolean isBuyerNotReturnGoods(RefundDO refundDO);
	
	/**
	 * 卖家拒绝退款，买家响应超时的处理
	 * 
	 * @param refundId 退款id
	 * @param isBuyer 为true表示买家，否则表示卖家
	 * @return result
	 */
	public Result buyerReplyTimeout(Long refundId, Boolean isBuyer);
	
	/**
	 * 卖家同意退款，而买家在5天内不退还商品的超时处理
	 * 
	 * @param refundId 退款id
	 * @param isBuyer 为true表示买家，否则表示卖家
	 * @return result
	 */
	public Result buyerNotReturnGoods(Long refundId, Boolean isBuyer);
}
