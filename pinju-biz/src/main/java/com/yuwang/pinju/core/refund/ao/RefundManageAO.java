package com.yuwang.pinju.core.refund.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.refund.RefundDO;

public interface RefundManageAO {
    /**
     * 根据 订单编号 查询支付记录
     */
    public Result selectOrderPayByOrderId(Long orderId);
    
    /**
     * 根据 订单编号 查询退款总额
     */
    public Result getRefundFee(Long orderId);
    
    /**
     * 根据 订单编号 查询平台退款信息
     */
    public Result getPlatformRefundParam(Long orderId);
    
    /**
     * 发起退款，更新订单确认收货时间和订单的退款状态
     * ByLiXin 2011-10-21
     */
    public Result startRefund(RefundDO refundDO);
    
    /**
     * 结束退款，更新订单确认收货时间和订单的退款状态
     * ByLiXin 2011-10-21
     */
    public Result endRefund(RefundDO refundDO);

}
