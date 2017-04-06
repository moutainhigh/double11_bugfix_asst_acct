package com.yuwang.pinju.core.order.manager;

import java.util.Date;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.query.SendDeliveryVO;

/**
 * Created on 2011-7-30
 * 
 * @see <p>
 *      Discription: 负责订单相关更新
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderUpDateManager {
	/**
	 * 
	 * Created on 2011-6-10
	 * <p>
	 * Discription: 关闭整个订单
	 * </p>
	 * 
	 * @param orderId
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean orderClose(final long orderId, final String failReason)
			throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-9-22
	 * <p>Discription: [关闭子订单]</p>
	 * @param orderItemId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean orderItemClose(final long orderItemId,final String failReason)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-7-14
	 * <p>
	 * Discription:订单物流发货
	 * </p>
	 * 
	 * @param sendDelivery
	 * @return 成功返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean confirmDelivery(final SendDeliveryVO sendDelivery)
			throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * 
	 * @param orderItemId
	 *            子订单id
	 * @param orderRefundState
	 *            退款状态
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean upRefundState(long orderItemId, int orderRefundState)
			throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 主订单状态修改 效验订单编号对应的订单是否可以修改为方法参数对应的订单状态
	 * </p>
	 * 
	 * @param optMemberId
	 *            操作人会员编号
	 * @param orderId
	 *            订单编号
	 * @param orderState
	 *            要修改的订单状态
	 * @return 修改成功返回true 失败返回false
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean upOrderState(Long optMemberId, final Long orderId,
			final Integer orderState, final String failReason)
			throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 子订单状态修改 效验订单编号对应的订单是否可以修改为方法参数对应的订单状态
	 * </p>
	 * 
	 * @param optMemberId
	 *            操作人会员编号
	 * @param orderId
	 *            订单编号
	 * @param orderState
	 *            要修改的订单状态
	 * @return 修改成功返回true 失败返回false
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean upOrderItemState(long optMemberId, final Long orderId,
			final Long orderItemId, final Integer orderItemState,
			final String failReason) throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 更新订单用户评价状态,订单与用户效验关系出错抛异常,订单该类型已评价抛出异常
	 * </p>
	 * 
	 * @param rateType
	 *            　<code>01 买家,2 卖家</code>
	 * @param memberId
	 *            　对应用户编号
	 * @param orderId
	 *            　对应订单编号
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void upRate(int rateType, Long memberId, Long orderId)
			throws ManagerException;
	/**
	 * 
	 * Created on 2011-8-19
	 * <p>Discription: 修改订单价格</p>
	 * @param orderItemId
	 * @param sellerMarginPrice
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean sellerMarginPrice(long orderItemId[], long sellerMarginPrice[],long sellerLogisticMarginPrice,String operateIp,Long[] modifyBefore)throws ManagerException ;
	
	/**
	 * 
	 * Created on 2011-10-19
	 * <p>Discription: 更新订单确认收货时间发起退款时</p>
	 * @param orderId  订单ID
	 * @param confirmStartTime 当前时间
	 * @param residueTimeHour 剩余收货小时
	 * @param isRefund 是否有退款
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrderStartConfirmTime(Long orderId,Date confirmStartTime,Integer residueTimeHour)throws ManagerException ;
	/**
	 * 
	 * Created on 2011-10-19
	 * <p>Discription:  更新订单确认收货时间退款关闭或成功时</p>
	 * @param orderId 订单ID
	 * @param confirmStartTime 当前时间
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrderEndConfirmTime(Long orderId,Date confirmStartTime)throws ManagerException ;
	
	/**
	 * <p>Discription: 更改子订单的退款金额和退款状态</p>
	 * @param orderItemId  子订单ID
	 * @param orderRefundState  状态
	 * @param refundPrice       退款价格
	 * @return  true
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-21
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean upRefundPriceAndRefundStatus(long orderItemId, int orderRefundState,Long refundPrice)throws ManagerException;

	/**
	 * 
	 * Created on 2011-11-4
	 * <p>Discription: 正式发送第三方收银台前在主订单打上标记</p>
	 * @param orderId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrderPostPayState(Long[] orderId,String orderAttributes) throws ManagerException;
}
