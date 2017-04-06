package com.yuwang.pinju.web.message;

/**
 * Class description TODO
 *
 * @(#) RefundMessageName.java
 * @version    $Revision: 17812 $
 * @author     MaYuanChao
 * @Date: $Date$
 */
public interface RefundMessageName {
	/**
	 * 最大文件的大小  500K
	 */
	public static long FILE_MAX_SIZE=500L;
	
	/**
	 * 文件过大
	 */
	public  static String FILE_SIZE_TO_LARGE="refund.file.size.to.large";
	 /**
	  * 文件类型不正确
	  */
	public static String FILE_TYPE_INVALID ="refund.file.type.invalid";
	
	/**
	 * 操作失败
	 */
	public static String OPERATE_FAILED = "operate.failed";
	

	
	/**
	 * 申请的最大金额
	 */
	public static String APPLY_MAX_SUM = "apply.max.sum";
	
	/**
	 * 页面过期提示
	 */
	public static String REFUND_OPERATE_SUBMIT_TIMEOUT ="refund.operate.submit.timeOut";
	
	/**
	 * 立即支付 申请退款提示
	 */
	public static String REFUND_DIRECT_ORDER_INVALID ="refund.direct.order.invalid";
	
	/**
	 * 买卖家超过显示的信息
	 */
	public static String REFUND_TIMING_STATUS  ="refund.timing.status";
	
	/**
	 * 非法的卖家访问
	 */
	public final static String INVALID_SELLER_REFUND = "invalidSellerRefund";
	
	/**
	 * 非法的买家访问
	 */
	public final static String INVALID_BUYER_REFUND ="invalidBuyerRefund";
	
	/**
	 * 买家超时跳转
	 */
	public final static String BUYER_REPLY_TIMEOUT ="buyerReplyTimeout";
	
	/**
	 * 状态改变默认的提示信息
	 */
	public static String REFUND_DEFAULT_MESSAGE= "refund.default.message";
	
	/**
	 * 关闭此退款申请
	 */
	public final static String REFUND_OPERATE_CLOSED ="refund.operate.closed";

	/**
	 * 您不能再同意此退款申请！目前退款状态： {0}。
	 */
	public final static String REFUND_OPERATE_AGREE ="refund.operate.agree";
	
	/**
	 * 您不能再修改此退款申请！目前退款状态为： {0}。
	 */
	public final static String REFUND_OPERATE_UPDATE ="refund.operate.update"; 

	/**
	 * 您不能再申请客服介入！目前退款状态为： {0}。
	 */
	public final static String REFUND_OPERATE_CUSTPROCESS ="refund.operate.custProcess";

	/**
	 * 您不能再拒绝此退款申请！目前退款状态为： {0}。
	 */
	public final static String REFUND_OPERATE_REJECT ="refund.operate.reject";

	/**
	 * 您不能进行此操作！目前退款状态为： {0}。
	 */
	public final static String REFUND_OPERATE_DELIVERGOODS ="refund.operate.deliverGoods";

	/**
	 * 您不能进行此操作！目前退款状态为： {0}。
	 */
	public final static String REFUND_OPERATE_CONFIRMRECEIVEGOOD ="refund.operate.confirmReceiveGood";

	/**
	 * 该退款状态已更改，请查看最新的退款详情！目前退款状态为： {0}。
	 */
	public final static String REFUND_VIEW_TIP ="refund.view.tip"; 
}

