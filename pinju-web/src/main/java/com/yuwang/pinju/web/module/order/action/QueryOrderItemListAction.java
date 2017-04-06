package com.yuwang.pinju.web.module.order.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.web.message.OrderMessage;
import com.yuwang.pinju.web.message.OrderMessageName;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * 
 * Created on 2011-7-27
 * <p>
 * Discription: 子订单分页查询
 * </p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class QueryOrderItemListAction extends BaseAction implements
		OrderMessageName {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6657404809616724610L;

	private final Log log = LogFactory.getLog(this.getClass().getName());

	private QueryOrderItem queryOrderItemDO;

	private OrderQueryAO orderQueryAO;

	private List<OrderItemDO> orderItemlist;
	// 总记录数
	private long countNum;
	// 出错信息
	private String errorMessage;
	// 1代表成功
	private int resultCode = 0;
	/**
	 * 
	 * Created on 2011-7-27
	 * <p>
	 * Discription: 限时折扣分页查看 传入1. queryOrderItemDO.page 当前页码
	 * 2.queryOrderItemDO.itemId 商品编号 3.queryOrderItemDO.day 查询的天数 传出 orderItemlist
	 * countNum queryOrderItemDO.page
	 * </p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String queryAjaxLastTimeBuyList() {
		
		queryOrderItemDO.setBussinessType(new int[]{OrderItemDO.ORDER_ITEM_TYPE_3,OrderItemDO.ORDER_ITEM_TYPE_4});
		
		queryOrderItemDO.setOrderItemState(new int[]{OrderItemDO.ORDER_ITEM_STATE_5});
		
		Result result = null;
		try {
			result = orderQueryAO.queryLastTimeBuyList(queryOrderItemDO);
			if (!result.isSuccess()) {
				errorMessage = OrderMessage.getMessage(
						OrderMessageName.OPERATE_FAILED, null);
			}
			orderItemlist = (List<OrderItemDO>) result
					.getModel("orderItemDOList");
			countNum = (Long) result.getModel("num");
		} catch (Exception e) {
			log.error("Event=[QueryOrderItemListAction#queryAjaxLastTimeBuyList] 商品购买记录分页查询失败:", e);
			errorMessage = OrderMessage.getMessage(
					OrderMessageName.OPERATE_INVALID, null);
		}
		resultCode = 1;
		return SUCCESS;
	}

	public QueryOrderItem getQueryOrderItemDO() {
		return queryOrderItemDO;
	}

	public void setQueryOrderItemDO(QueryOrderItem queryOrderItemDO) {
		this.queryOrderItemDO = queryOrderItemDO;
	}

	
	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}

	public List<OrderItemDO> getOrderItemlist() {
		return orderItemlist;
	}

	public void setOrderItemlist(List<OrderItemDO> orderItemlist) {
		this.orderItemlist = orderItemlist;
	}

	public long getCountNum() {
		return countNum;
	}

	public void setCountNum(long countNum) {
		this.countNum = countNum;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
