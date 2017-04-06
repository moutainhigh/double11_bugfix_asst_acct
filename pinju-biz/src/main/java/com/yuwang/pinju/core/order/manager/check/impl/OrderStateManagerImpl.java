package com.yuwang.pinju.core.order.manager.check.impl;


import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.member.dao.MemberAsstDAO;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.order.manager.check.OrderStateManager;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;

/**
 * 
 * @author 杜成
 * @date 2011-6-24
 * @version 1.0
 */
public class OrderStateManagerImpl extends BaseManager implements
		OrderStateManager {
	/**
	 * 从属买家状态
	 */
	private final static int BUYER_STATE = 1;
	/**
	 * 从属卖家状态
	 */
	private final static int SELL_STATE = 2;
	/**
	 * 从属(买家+卖家)
	 */
	private final static int BUYER_SELL_STATE = 3;
	/**
	 * 参数出错
	 */
	private final static int STATE_PARAMETER_ERROR = 4;

	private OrderQueryDAO orderQueryDAO;
	
	private MemberAsstDAO memberAsstDAO;
	
	@Override
	public boolean checkOrderState(long optMemberId, long orderId,
			int editOrderState) throws ManagerException {
		// 判断状态从属
		final int relation = this.stateAttributable(editOrderState);
		final boolean flag = false;
		try {
			if (STATE_PARAMETER_ERROR == relation){
				log.warn("error editOrderState 要修改的状态是非法订单状态!--->".concat(String.valueOf(editOrderState)));
				log.warn("optMemberId --->".concat(String.valueOf(optMemberId)));
				log.warn("orderId --->".concat(String.valueOf(orderId)));
				return flag;
			}
			OrderDO orderDO = orderQueryDAO.queryOrder(orderId);
			// 效验操作员是否能变更该状态
			if (!this.checkOptMember(optMemberId, relation, editOrderState,
					orderDO)){
				log.warn("check error checkOrderState 当前买家无权编辑订单到此状态!");
				log.warn("optMemberId --->".concat(String.valueOf(optMemberId)));
				log.warn("orderId --->".concat(String.valueOf(orderId)));
				log.warn("editOrderState --->".concat(String.valueOf(editOrderState)));
				return flag;
			}
			//由于限时折扣等活动与收银台的时间差问题,这里只要是活动商品订单关闭,支付平台付款成功后回调,从新打开订单
			List<OrderItemDO> list = orderQueryDAO.queryOrderItemList(orderId);
			if(list !=null && list.size()>0){
				OrderItemDO orderItemDO = list.get(0);
				// 效验要更新的订单状态
				boolean ischeck = (orderItemDO.getBussinessType().compareTo(OrderItemDO.ORDER_ITEM_TYPE_3)==0
						||orderItemDO.getBussinessType().compareTo(OrderItemDO.ORDER_ITEM_TYPE_4)==0);
				ischeck = ischeck && (editOrderState == OrderItemDO.ORDER_ITEM_STATE_2);
				if(ischeck){
					return true;
				}
			}
			if (!this.checkState(orderDO.getOrderState(), editOrderState)){
				log.warn("check error checkOrderState 无法从当前状态更新至要编辑的订单状态!");
				log.warn("optMemberId --->".concat(String.valueOf(optMemberId)));
				log.warn("orderId --->".concat(String.valueOf(orderId)));
				log.warn("editOrderState --->".concat(String.valueOf(editOrderState)));
				log.warn("orderState --->".concat(String.valueOf(orderDO.getOrderState())));
				return flag;
			}
			return true;
		} catch (DaoException e) {
			log.error("Event=[OrderStateManagerImpl#checkOrderState] 订单效验异常", e);
			log.error("optMemberId --->".concat(String.valueOf(optMemberId)));
			log.error("orderId --->".concat(String.valueOf(orderId)));
			log.error("editOrderState --->".concat(String.valueOf(editOrderState)));
			throw new ManagerException(
					"Event=[OrderStateManagerImpl#checkOrderState] 订单效验异常", e);
		}
	}

	/**
	 * @see 效验要操作的状态针对当前订单状态是否合法
	 * @return
	 */
	private boolean checkState(int orderState, int editOrderState) {

		switch (editOrderState) {
		case OrderDO.ORDER_STATE_1:
			return false;
		case OrderDO.ORDER_STATE_2:
			return orderState == OrderDO.ORDER_STATE_1;
		case OrderDO.ORDER_STATE_3:
			return orderState == OrderDO.ORDER_STATE_2 ;
		case OrderDO.ORDER_STATE_5:
			return orderState == OrderDO.ORDER_STATE_3;
		case OrderDO.ORDER_STATE_6:
			return orderState == OrderDO.ORDER_STATE_1;
		default:
			return false;
		}

	}

	/**
	 * @see 根据状态归属结果判断操作会员编号与订单关系
	 * @param optMemberId
	 *            操作员会员编号
	 * @param relation
	 *            状态重属
	 * @param editOrderState
	 *            要更新的状态
	 * @param orderDO
	 *            对应订单实体
	 * @return 判断为真返回true
	 * @throws DaoException 
	 */
	private boolean checkOptMember(long optMemberId, int relation,
			int editOrderState, OrderDO orderDO) throws DaoException {
		MemberAsstRelationDO memberAsstRelationDO;
		memberAsstRelationDO = memberAsstDAO.getMemberAsstRalationByAsst(optMemberId);
		if(memberAsstRelationDO != null){
			optMemberId = memberAsstRelationDO.getMasterMemberId();
		}
		switch (relation) {
		case BUYER_STATE:
			return orderDO.getBuyerId() == optMemberId;
		case SELL_STATE:
			return orderDO.getSellerId() == optMemberId;
		case BUYER_SELL_STATE:
			return (orderDO.getBuyerId() == optMemberId)
					|| (orderDO.getSellerId() == optMemberId);
		default:
			return false;
		}
	}

	/**
	 * @see 判断状态归属买家修改还是卖家修改 返回1代表买家修改状态 返回2 代表卖家可修改状态 返回3 代表卖家与买家都可以修改 返回4
	 *      代表参数出错
	 * @param state
	 * @return
	 */
	private int stateAttributable(int state) {
		switch (state) {
		case OrderDO.ORDER_STATE_2:
			return BUYER_STATE;
		case OrderDO.ORDER_STATE_3:
			return SELL_STATE;
		case OrderDO.ORDER_STATE_5:
			return BUYER_STATE;
		case OrderDO.ORDER_STATE_6:
			return BUYER_SELL_STATE;
		default:
			return STATE_PARAMETER_ERROR;
		}
	}


	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}

	public void setMemberAsstDAO(MemberAsstDAO memberAsstDAO) {
		this.memberAsstDAO = memberAsstDAO;
	}



}
