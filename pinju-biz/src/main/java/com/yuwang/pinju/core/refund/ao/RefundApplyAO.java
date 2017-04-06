package com.yuwang.pinju.core.refund.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;

/**
 * 退款申请
 * @author shihongbo
 * @since   2011-6-24
 * @version 1.0
 */
public interface RefundApplyAO {
	/**
	 * 得到对应子订单编号的集合
	 *
	 * @param orderItemId
	 * @return List<OrderItemDO>
	 */
	public Result queryOrderItemList(List<Long> orderItemId);
	
	/**
	 * 保存退款失败补单信息
	 *
	 * @param refundManualDO
	 * @return 
	 */
	public Result saveRefundManual(TradeRefundManualDO refundManualDO);
	
	/**
	 * 查询退款失败补单信息
	 *
	 * @param orderId
	 * @return 
	 */
	public Result loadRefundManualByOrderId(Long orderId);
	
	
	/**
	 * 根据订单id查询RightsWorkOrderDO
	 *
	 * @param orderId
	 * @return 
	 */
	public Result getRightsWorkOrderDOByOrderId(Long orderId);
	
	/**
	 * 根据订单ID查询物流信息
	 * @param orderId
	 * @retrun OrderLogisticsDO
	 */
	public OrderLogisticsDO queryOrderLogisticsByOrderId(Long orderId);
	
	/**
	 * 根据多个商品编号获取商品列表，最多20个商品编号
	 * 
	 * @param ids
	 * @return List<ItemDO>
	 */
	public Result getItemListByIds(List<Long> ids);
	
	/**
	 * 通过商品编号 获得SKU
	 * 
	 * @param itemId
	 * @return
	 */
	public SkuDO getItemSkuById(Long itemId);
	
	/**
	 * 取得订单信息
	 * 
	 * @param orderId 订单编号
	 * @return 订单
	 */
	public OrderDO getOrderInfo(Long orderId);
	
	/**
	 * 取得商品信息
	 * 
	 * @param itemId  商品id
	 */
	public ItemDO loadItemByID(Long itemId);
	
	/**
	 * 取得退货物流信息
	 * 
	 * @param refundId 退款编号
	 * @return 订单
	 */
	public TradeRefundLogisticsDO getRefundLogistics(Long refundId);
	
	/**
	 * 取得子订单信息
	 * 
	 * @param orderItemId 子订单编号
	 * @return 子订单
	 */
	public OrderItemDO getOrderItemInfo(Long orderItemId);
	
	/**
	 * 保存退货物流信息
	 * 
	 * @param tradeRefundLogisticsDO 退货物流信息
	 * 
	 * @return 退货物流id
	 */
	public Boolean buyerDeliverGoods(TradeRefundLogisticsDO tradeRefundLogisticsDO,RefundDO refundDO);
	
	/**
	 * 更新退货物流信息
	 * 
	 * @param tradeRefundLogisticsDO 退货物流信息
	 * 
	 * @return 退货物流id
	 */
	public Long updateRefundLogistics(TradeRefundLogisticsDO tradeRefundLogisticsDO);
	/**
	 * 更新退款申请信息
	 * 
	 * @param refundDO 退款申请信息
	 */
	public void updateRefundApplyInfo(RefundDO refundDO);
	
	/**
	 * 买家申请客服介入
	 * 
	 * @param refundId 退款id
	 */
	public void custProcess(Long refundId);
	
	/**
	 * 买家申请客服介入
	 * 
	 * @param refundId 退款refundDO
	 */
	public void custProcess(RefundDO refundDO);
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param refundId 退款申请信息id
	 */
	public RefundDO loadRefundApplyInfo(Long refundId);
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param orderItemId 子订单id
	 */
	public RefundDO loadRefundByOrderItem(Long orderItemId);
	
	/**
	 * 加载退款申请信息 记录数
	 * @param map 退款申请查询条件
	 */
	public int loadRefundApplyInfoCount(Map<String, Object> map);
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param map 退款申请查询条件
	 */
	public Result loadRefundApplyInfo(Map<String, Object> map);
	
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>Discription: 根据主订单编号，获取子订单列表</p>
	 * @param orderId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderItemDO> queryOrderItemList(Long orderId);
	
	/**
	 * <p>Discription:  取消退款申请</p>
	 * @param @param refundId
	 * @return
	 * @throws
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean cancelRefundApply(RefundDO refundDO);
	
	
	/**
	 * 保存订单操作日志
	 *
	 * @param refundDO
	 * @param ip
	 * @return 
	 */
	public void writeOrderLog(RefundDO refundDO, String ip);
	
	/**
	 * 
	 * <p>Discription: 保存退款  修改子订单退款价格 和状态</p>
	 * @param refundDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-13
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long saveOrUpdateRefund(RefundDO refundDO);
}
