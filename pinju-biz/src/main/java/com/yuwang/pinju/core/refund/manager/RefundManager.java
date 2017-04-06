package com.yuwang.pinju.core.refund.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

/**
 * 退款信息管理
 * @author shihongbo
 * @since   2011-6-24
 */
public interface RefundManager {
	/**
	 * <p>保存退款信息</p>
	 *
	 * @param refund
	 * @return 更新或者保存的信息id
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public Long persist(RefundDO refund) throws ManagerException;
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param refundId 退款申请信息id
	 */
	public RefundDO loadRefund(Long refundId)throws ManagerException;
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param orderItemId 子订单id
	 */
	public RefundDO loadRefundByOrderItem(Long orderItemId)throws ManagerException;
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param map 退款申请查询条件
	 */
	public List<RefundDO> loadRefund(Map<String, Object> map)throws ManagerException;
	
	/**
	 * 加载退款申请信息 记录数
	 * @param map 退款申请查询条件
	 */
	public int loadRefundCount(Map<String, Object> map) throws ManagerException;
	
	/**
	 * 更新退款申请信息
	 * 
	 * @param refundDO 退款申请信息
	 */
	public void updateRefundApplyInfo(RefundDO refundDO)throws ManagerException;
	
	/**
	 * <p>保存退货物流信息</p>
	 *
	 * @param tradeRefundLogisticsDO
	 * @return
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public Long persistRefundLogistics(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws ManagerException;
	
	/**
	 * 卖家家确认收货
	 * 
	 * @param refundId 退款id
	 */
	public void sellerConfirmReceiveGoods(Long refundId)throws ManagerException;
	
	/**
	 * 卖家拒绝退款申请
	 * 
	 * @param refundId 退款id
	 */
	public void sellerRejectRefundApply(Long refundId)throws ManagerException;
	
	/**
	 * 卖家申请客服介入
	 * 
	 * @param refundId 退款id
	 */
	public void sellerApplyCustProcess(Long refundId)throws ManagerException;
	
	/**
	 * 卖家同意退款申请
	 * 
	 * @param refundId 退款id
	 */
	public void sellerAgreeRefundApply(Long refundId)throws ManagerException;
	
	/**
	 * <p>Discription: 
	 * 	 取消或关闭退款申请
	 * </p>
	 * @param refundId
	 * @throws ManagerException
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean cancelRefundApply(Long refundId) throws ManagerException;
	
	/**
	 * 买家申请客服介入
	 * 
	 * @param refundId 退款id
	 */
	public void custProcess(Long refundId)throws ManagerException;
	
	/**
	 * 买家确认发货
	 * 
	 * @param refundId 退款id
	 */
	public boolean buyerDeliverGoods(Long refundId,Long logistcsId)throws ManagerException;
	
	/**
	 * <p>Discription: 根据主订单ID获取所有的退款</p>
	 * @param orderId
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-15
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> queryRefundByOrderId(Long orderId) throws ManagerException;
	
    /**
     * 根据 订单编号 查询退款总额
     */
    public Long getRefundFee(Long orderId)throws ManagerException;

}
