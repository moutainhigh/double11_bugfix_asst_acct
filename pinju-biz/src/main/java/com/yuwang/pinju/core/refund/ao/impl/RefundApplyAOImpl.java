package com.yuwang.pinju.core.refund.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderLogManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.manager.RefundLogisticsManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.refund.manager.TradeRefundManualManager;
import com.yuwang.pinju.core.rights.manager.RightsWorkOrderManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;

public class RefundApplyAOImpl extends BaseAO implements RefundApplyAO{
	
	private RefundManager refundManager;
	private RefundLogisticsManager refundLogisticsManager;
	private ItemManager itemManager;
	private OrderQueryManager orderQueryManager;
	private OrderUpDateManager orderUpDateManager;
	private SkuManager skuManager;
	private OrderBusinessManager orderBusinessManager;
	private TradeRefundManualManager tradeRefundManualManager;
	private OrderLogManager orderLogManager;
	
	private RightsWorkOrderManager rightsWorkOrderManager;
	

	
	/**
	 * 得到对应子订单编号的集合
	 *
	 * @param orderItemId
	 * @return List<OrderItemDO>
	 */
	public Result queryOrderItemList(List<Long> orderItemId){
		 Result result = new ResultSupport();
		try {
			List<OrderItemDO> orderItemDOs = orderQueryManager.queryOrderItemList(orderItemId);
			if(orderItemDOs == null) {
				result.setSuccess(false);
			}
			result.setModel("orderItemDOs",orderItemDOs);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundApplyAOImpl#queryOrderItemList] 得到对应子订单编号的集合", e);
		}
		return result;
	}
	
	/**
	 * 保存退款失败补单信息
	 *
	 * @param refundManualDO
	 * @return 
	 */
	public Result saveRefundManual(TradeRefundManualDO refundManualDO){
		Result result = new ResultSupport();
		try {
			tradeRefundManualManager.saveRefundManual(refundManualDO);
			result.setSuccess(true);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundApplyAOImpl#queryOrderItemList] 保存退款失败补单信息", e);
		}
		return result;
	}
	
	/**
	 * 根据订单id查询RightsWorkOrderDO
	 *
	 * @param orderId
	 * @return 
	 */
	public Result getRightsWorkOrderDOByOrderId(Long orderId){
		Result result = new ResultSupport();
		try {
			FinanceWorkOrderQuery financeWorkOrderQuery = new FinanceWorkOrderQuery();
			financeWorkOrderQuery.setOrderId(orderId);
			financeWorkOrderQuery.setBizType(RightsConstant.BIZ_TYPE_RREFUND);

			FinanceWorkOrderDO financeWorkOrderDO = rightsWorkOrderManager.getFinanceWorkOrderDOByOrderId(financeWorkOrderQuery);
			
			result.setModel("financeWorkOrderDO", financeWorkOrderDO);
			
			boolean exist = financeWorkOrderDO != null;
			
			result.setSuccess(exist);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundApplyAOImpl#getRightsWorkOrderDOByOrderId] 根据订单id查询RightsWorkOrderDO失败", e);
		}
		return result;
	}
	
	/**
	 * 保存订单操作日志
	 *
	 * @param refundDO
	 * @param ip
	 * @return 
	 */
	public void writeOrderLog(RefundDO refundDO, String ip){
		OrderLogDO orderLogDO = new OrderLogDO();
		Date now = new Date();
		orderLogDO.setBuyerId(refundDO.getBuyerId());
		orderLogDO.setGmtCreate(now);
		orderLogDO.setStateModifyTime(now);
		orderLogDO.setOrderId(refundDO.getOrderId());
		orderLogDO.setOrderItemId(refundDO.getOrderItemId());
		orderLogDO.setOrderItemState(refundDO.getRefundState());
		orderLogDO.setSellerId(refundDO.getSellerId());
		orderLogDO.setOperateIp(ip);
		orderLogDO.setOperateState(0);
		orderLogDO.setOperateType(OrderConstant.OPERATE_LOG_UPDATE_REFUND_STATE);
		
		try{
			orderLogManager.createOrderLog(orderLogDO);
		}catch(ManagerException e){
			log.error("Event=[RefundApplyAOImpl#writeOrderLog] 保存订单日志", e);
		}
	}
	
	
	
	/**
	 * 查询退款失败补单信息
	 *
	 * @param orderId
	 * @return 
	 */
	public Result loadRefundManualByOrderId(Long orderId){
		Result result = new ResultSupport();
		try {
			TradeRefundManualDO refundManualDO = tradeRefundManualManager.loadRefundManualByOrderId(orderId);
			if(refundManualDO == null){
				result.setSuccess(false);
				return result;
			}
			
			result.setModel("refundManualDO", refundManualDO);
			result.setSuccess(true);
			
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundApplyAOImpl#queryOrderItemList] 查询退款失败补单信息", e);
		}
		
		return result;
	}
	
	/**
	 * 根据订单ID查询物流信息
	 * @param orderId
	 * @retrun OrderLogisticsDO
	 */
	public OrderLogisticsDO queryOrderLogisticsByOrderId(Long orderId){
		try{
			return orderBusinessManager.queryOrderLogisticsByOrderId(orderId);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#queryOrderLogisticsByOrderId] 根据订单ID查询物流信息", e);
		}
		
		return null;
	}
	
	/**
	 * 通过商品编号 获得SKU
	 * 
	 * @param itemId
	 * @return
	 */
	public SkuDO getItemSkuById(Long itemId){
		try {
			SkuDO sku = skuManager.getItemSkuById(itemId);
			
			return sku;
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#getItemSkuByItemId] 通过商品编号 获得SKU", e);
		}
		return null;

	}
	
	/**
	 * 根据多个商品编号获取商品列表，最多20个商品编号
	 * 
	 * @param ids
	 * @return List<ItemDO>
	 */
	public Result getItemListByIds(List<Long> ids){
		Result result = new ResultSupport();
		try {
			List<ItemDO> itemDOs= itemManager.getItemListByIds(ids);
			if(itemDOs == null){
				result.setSuccess(false);
			}
			result.setModel("itemDOs", itemDOs);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundApplyAOImpl#getItemListByIds] 取得商品信息失败", e);
		}
		return result;
	}
	
	
	/**
	 * 取得订单信息
	 * 
	 * @param orderId 订单编号
	 * @return 订单
	 */
	public OrderDO getOrderInfo(Long orderId){
		try {
			return orderQueryManager.getOrderDOById(orderId);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#getOrderInfo] 取得订单信息失败", e);
		}
		return null;
		
	}
	
	/**
	 * 取得商品信息
	 * 
	 * @param itemId  商品id
	 */
	public ItemDO loadItemByID(Long itemId){
		try {
			return itemManager.getItemDOById(itemId);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#loadItemByID] 取得商品信息失败", e);
		}
		return null;
	}
	
	/**
	 * 买家申请客服介入
	 * 
	 * @param refundId 退款id
	 */
	public void custProcess(Long refundId){
		try {
			refundManager.custProcess(refundId);
		} catch (ManagerException e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void custProcess(final RefundDO refundDO) {
		// TODO Auto-generated method stub
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					// TODO Auto-generated method stub
					refundManager.custProcess(refundDO.getId());
					orderUpDateManager.upRefundState(refundDO.getOrderItemId(),RefundDO.STATUS_CS_PROCESS);
					
				} catch (Exception e) {
					status.setRollbackOnly();
					
					log.error("Event=[RefundApplyAOImpl#custProcess(RefundDO refundDO)]  买家申请客服介入失败", e);
					return false;
				}
				return true;
			}});
	}
	/**
	 * 取得退货物流信息
	 * 
	 * @param refundId 退款编号
	 * @return 订单
	 */
	public TradeRefundLogisticsDO getRefundLogistics(Long refundId){
		try {
			return refundLogisticsManager.loadRefundLogistics(refundId);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#getOrderInfo] 取得订单信息失败", e);
		}
		return new TradeRefundLogisticsDO();
		
	}
	
	/**
	 * 取得子订单信息
	 * 
	 * @param orderItemId 子订单编号
	 * @return 子订单
	 */
	public OrderItemDO getOrderItemInfo(Long orderItemId){
		try {
			return orderQueryManager.getOrderItemDOById(orderItemId);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#getOrderItemInfo] 取得子订单信息失败", e);
		}
		return null;
		
	}
	
	/**
	 * 买家退还商品  插入退款物流和更新退款状态
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean buyerDeliverGoods(final TradeRefundLogisticsDO tradeRefundLogisticsDO,final RefundDO refundDO){
		Boolean upfalg=(Boolean)getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				boolean _upfalg = true;
				try {
					if(tradeRefundLogisticsDO.getId() ==null){
						Long logistcsId = refundLogisticsManager.persist(tradeRefundLogisticsDO);
						if(_upfalg = logistcsId > 0) _upfalg = refundManager.buyerDeliverGoods(refundDO.getId(),logistcsId);
						if(_upfalg) _upfalg = orderUpDateManager.upRefundState(refundDO.getOrderItemId(),  RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS);
					}
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[RefundApplyAOImpl#saveRefundLogistics] 买家发货失败", e);
					return false;
				}
				return _upfalg;
			}});
		return upfalg;
	}
	
	
	/**
	 * 更新退货物流信息
	 * 
	 * @param tradeRefundLogisticsDO 退货物流信息
	 * 
	 * @return 退货物流id
	 */
	@Override
	public Long updateRefundLogistics(TradeRefundLogisticsDO tradeRefundLogisticsDO) {
		// TODO Auto-generated method stub
		try{
			return refundLogisticsManager.update(tradeRefundLogisticsDO);
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
		}
		return null;
		
	}
	
	/**
	 * 更新退款申请信息
	 * 
	 * @param refundDO 退款申请信息
	 */
	public void updateRefundApplyInfo(RefundDO refundDO){
		try {
			refundManager.updateRefundApplyInfo(refundDO);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#updateRefundApplyInfo] 更新退款申请信息失败", e);
		}
	}
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param refundId 退款申请信息id
	 */
	public RefundDO loadRefundApplyInfo(Long refundId){
		try {
			return refundManager.loadRefund(refundId);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#loadRefundApplyInfo] 加载退款申请信息失败", e);
		}
		return null;
	}
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param orderItemId 子订单id
	 */
	public RefundDO loadRefundByOrderItem(Long orderItemId){
		try {
			return refundManager.loadRefundByOrderItem(orderItemId);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#loadRefundApplyInfo] 加载退款申请信息失败", e);
		}
		return null;
	}
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param map 退款申请查询条件
	 */
	public Result loadRefundApplyInfo(Map<String, Object> map){
		Result result = new ResultSupport();
		try {
			
			if(map.get("startNum")==null) map.put("startNum", 1);
			if(map.get("endNum")==null) map.put("endNum", 100);
			List<RefundDO> list = refundManager.loadRefund(map);
			
			if(list == null){
				result.setSuccess(false);
			} else {
				result.setModel("obj", list);
				result.setSuccess(true);
			}
			
			return result;
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#loadRefundApplyInfo] 加载退款申请信息失败", e);
			result.setSuccess(false);
			return result;
		}
		
	}
	
	/**
	 * 加载退款申请信息 记录数
	 * @param map 退款申请查询条件
	 */
	public int loadRefundApplyInfoCount(Map<String, Object> map){
		try {
			return refundManager.loadRefundCount(map);
		} catch (ManagerException e) {
			log.error("Event=[RefundApplyAOImpl#loadRefundCount] 加载退款申请信息失败", e);
		}
		return 0;
	}
	

	
	/**
	 * <p>Description:  取消退款申请 </p>
	 * @param @param refundId
	 * @return
	 * @throws
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean cancelRefundApply(final RefundDO refundDO) {
		// TODO Auto-generated method stub
			Boolean falg = (Boolean)this.getZizuTransactionTemplate().execute(new TransactionCallback(){
				@Override
				public Object doInTransaction(TransactionStatus status) {
					boolean upfag = true;
					try{
						if(refundDO != null){
							 upfag = refundManager.cancelRefundApply(refundDO.getId());
							if(upfag){
								upfag = orderUpDateManager.upRefundPriceAndRefundStatus(refundDO.getOrderItemId(), RefundDO.STATUS_CLOSED, 0L);
							}
						}
					}catch (ManagerException e) {
						status.setRollbackOnly();
						log.error("execute RefundManager.queryRefundByOrderId () error:", e);
						return false;
					}
					return upfag;
				}
			});
		return falg;
	}
	
	@Override
	public List<OrderItemDO> queryOrderItemList(Long orderId) {
		try {
			return orderQueryManager.queryOrderItemList(orderId);
		} catch (ManagerException e) {
			log.error("RefundApplyAoImpl.queryOrderItemList:", e);
			return null;
		}
	}
	
	/**
	 * 保存退款申请
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long saveOrUpdateRefund(final RefundDO refundDO) {
		Long refundId = (Long)getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					Long _refundId = refundManager.persist(refundDO);
					if (_refundId > 0) {
						boolean upfalg = orderUpDateManager.upRefundPriceAndRefundStatus(refundDO.getOrderItemId(),refundDO.getRefundState(),refundDO.getApplySum());
						if (upfalg) return _refundId;
					}
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[RefundApplyAOImpl.saveOrUpdateRefundDO(RefundDO)]", e);
					return 0L;
				}
				return 0L;
			}});
		
		return refundId;
	}

	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}

	public void setRefundLogisticsManager(
			RefundLogisticsManager refundLogisticsManager) {
		this.refundLogisticsManager = refundLogisticsManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager) {
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setTradeRefundManualManager(
			TradeRefundManualManager tradeRefundManualManager) {
		this.tradeRefundManualManager = tradeRefundManualManager;
	}
	public void setOrderLogManager(OrderLogManager orderLogManager) {
		this.orderLogManager = orderLogManager;
	}

	public void setRightsWorkOrderManager(
			RightsWorkOrderManager rightsWorkOrderManager) {
		this.rightsWorkOrderManager = rightsWorkOrderManager;
	}	
}
