package com.yuwang.pinju.core.refund.dao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.refund.RefundDO;

public interface RefundDAO {
	/**
	 * <p>保存退款信息</p>
	 *
	 * @param refund
	 * @return 更新或者保存的信息id
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	Long persist(RefundDO refund) throws DaoException;
	
	/**
	 * <p>加载退款信息</p>
	 *
	 * @param refundId
	 * @return
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public RefundDO loadRefund(Long refundId) throws DaoException;
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param orderItemId 子订单id
	 * @return
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public RefundDO loadRefundByOrderItem(Long orderItemId) throws DaoException;

	/**
	 * 加载退款申请信息
	 * 
	 * @param map 退款申请查询条件
	 */
	public List<RefundDO> loadRefund(Map<String, Object> map) throws DaoException;
	
	/**
	 * 加载退款申请信息 记录数
	 * @param map 退款申请查询条件
	 */
	public int loadRefundCount(Map<String, Object> map) throws DaoException;
	
	/**
	 * 更新退款申请信息
	 * 
	 * @param refundDO 退款申请信息
	 */
	public int updateRefundApplyInfo(RefundDO refundDO) throws DaoException;
	
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
	public List<RefundDO> queryRefundByOrderId(Long orderId) throws DaoException;
	
	/**
	 * <p>Discription: 更新退款信息</p>
	 * @param refundDO
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-23
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateRefundDO(RefundDO refundDO) throws DaoException;
}
