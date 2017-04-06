package com.yuwang.pinju.core.order.ao;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;

/**
 * Created on 2011-8-19
 * @see
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderUpAO {
	/**
	 * Created on 2011-6-10
	 * @see  订单退款状态修改
	 * @param orderItemId 子订单编号
	 * @param orderRefundState 订单退款状态,根据OrderDO 中的退款状态常量
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名] 
	 */
	public void upOrderRefundState(long orderItemId,int orderRefundState);
	
	/**
	 * Created on 2011-6-10
	 * @see 订单取消
	 * @param orderId 订单编号
	 * @param userId 当前用户编号
	 * @param failReason 关闭原因
	 * @return Result
	 * 状态1 失败
	 * ResultCode  : OrderConstant.BUYER_QUERY_1
	 * Success :false
	 * 状态2 成功
	 * ResultCode  : OrderConstant.PARAMETERRROR
	 * Success :true 
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result cancelOrder(Long orderId,Long userId,String failReason);
	
	/**
	 * Created on 2011-12-13
	 * <p>Description: 取消订单  验证</p>
	 * @param orderId
	 * @param userId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result cancelOrderCheck(Long orderId,Long userId);

	/**
	 * 
	 * Created on 2011-8-19
	 * <p>Discription: </p>
	 * @param orderItemId 子订单编号
	 * @param sellerMarginPrice 卖家差幅价格（-,+）
	 * @param logisticsMarginPrice 物流差幅价格(-,+)
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[2011-8-19] [李鑫实现]
	 */
	public Result sellerUpOrderPrice(long[] orderItemId, long[] sellerMarginPrice,long logisticsMarginPrice , Long userId ,String operateIp,Long[] modifyBefore);
	
	/**
	 * @Description:正式发送第三方收银台前在主订单打上标记 
	 * @author [贺泳]
	 * @date 2011-11-4 上午10:40:32
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param orderId
	 * @return
	 */
	public Result updateOrderPostPayState(Long[] orderId);
}

