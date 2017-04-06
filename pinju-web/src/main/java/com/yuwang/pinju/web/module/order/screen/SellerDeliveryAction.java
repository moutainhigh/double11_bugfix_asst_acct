package com.yuwang.pinju.web.module.order.screen;



import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.logistics.ao.LogisticsSellerDeliveryAO;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.order.ao.OrderBusinessAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.logistics.LogisticsSellerDefaultDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.SendDeliveryVO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.annotatioin.AssistantPermissions;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.OrderMessage;
import com.yuwang.pinju.web.message.OrderMessageName;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * 卖家发货
 * @author 杜成
 * @date 2011-7-13
 * @version 1.0
 */
public class SellerDeliveryAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -304222090784772040L;

	private OrderBusinessAO orderBusinessAO;
	
	private SendDeliveryVO sendDelivery;
	
	private LogisticsSellerDeliveryAO logisticsSellerDeliveryAO;
	
	private LogisticsSellerDefaultDO logistics;
	
	private List<LogisticsSellerDefaultDO> sellerDeliveryList;
	
	private String memberId;
	
	private String status;


	private String message;
	
	private boolean flag;
	
	private LogisticsCorpAO logisticsCorpAO;
	
	private List<LogisticsCorpDO> corpList;
	
	private OrderLogisticsDO orderLogisticsDO;
	
	private MemberAsstLog memberAsstLog;
	
	/**
	 * 输出订单明细
	 */
	private Map<OrderDO,List<OrderItemDO>> orderMap;
	
	/**
	 * 输出卖家默认发货地址
	 */
	private List<MemberDeliveryDO> memberDeliveriesList;
	
	private String errorMessage;
	
	private String tp; // 买家 1 卖家 2
	
	
	public LogisticsSellerDeliveryAO getLogisticsSellerDeliveryAO() {
		return logisticsSellerDeliveryAO;
	}


	public void setLogisticsSellerDeliveryAO(
			LogisticsSellerDeliveryAO logisticsSellerDeliveryAO) {
		this.logisticsSellerDeliveryAO = logisticsSellerDeliveryAO;
	}


	public LogisticsSellerDefaultDO getLogistics() {
		return logistics;
	}


	public void setLogistics(LogisticsSellerDefaultDO logistics) {
		this.logistics = logistics;
	}

	public void setOrderBusinessAO(OrderBusinessAO orderBusinessAO) {
		this.orderBusinessAO = orderBusinessAO;
	}

	public Map<OrderDO, List<OrderItemDO>> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<OrderDO, List<OrderItemDO>> orderMap) {
		this.orderMap = orderMap;
	}


	public List<MemberDeliveryDO> getMemberDeliveriesList() {
		return memberDeliveriesList;
	}


	public void setMemberDeliveriesList(List<MemberDeliveryDO> memberDeliveriesList) {
		this.memberDeliveriesList = memberDeliveriesList;
	}

	public SendDeliveryVO getSendDelivery() {
		return sendDelivery;
	}


	public void setSendDelivery(SendDeliveryVO sendDelivery) {
		this.sendDelivery = sendDelivery;
	}

	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		} 
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}
	
	/**
	 * 
	 * @see 
	 * <p>
	 * 	进入卖家发货确认页面
	 *  in
	 *  	1)发货的主订单编号
	 *  out
	 *  	1)订单详细
	 *  	2)卖家发货地址列表(这里的发货地址,默认即为买家的收货地址)
	 * </p>
	 * @return
	 * @since  2011-7-13
	 * @author 杜成
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	@AssistantPermissions({
		  @AssistantPermission(target = "trade",action = "set"),
		  @AssistantPermission(target = "trade",action = "shipment")
		})
	public String inSellerDelivery(){
		Result result = orderBusinessAO.inSellerDelivery(this.sendDelivery.getOrderId(),this.getUserId());
		
		if(!result.isSuccess()){
			if(result.getResultCode().equals(OrderConstant.PARAMETERRROR+""))
				errorMessage = "您无权限对该订单进行操作！";
			
			if(result.getResultCode().equals(OrderConstant.ORDERSTATEEERROR+""))
				errorMessage = "订单状态已更改，您不能对该订单进行发货操作！";
			
			tp="2";
			return ERROR;
		}
		
		this.setSellerDeliveryList(logisticsSellerDeliveryAO.queryByMemberId(this.getUserId()));
		this.setMemberId(""+this.getUserId());
		this.setOrderMap((Map<OrderDO, List<OrderItemDO>>)result.getModel("orderMap"));
		this.setMemberDeliveriesList((List<MemberDeliveryDO>)result.getModel("memberDeliveryDOList"));
		this.setOrderLogisticsDO((OrderLogisticsDO) result.getModel("logistics"));
		
		return SUCCESS;
	}
	
	/**
	 * 判断主订单下是否有退款记录
	 * @return
	 */
	public String isOrderRefund() {
		int i=0;
		Result result = orderBusinessAO.isOrderRefund(sendDelivery.getOrderId());
		List<RefundDO> refundList = (List<RefundDO>) result.getModel("refundList");
		if (refundList != null) {
			for (RefundDO rd : refundList) {
				if (rd.getRefundState().compareTo(RefundDO.STATUS_CLOSED) != 0) {
					i++;
				}
			}
		}
		
		if (i > 0) {
			this.setFlag(true);
		} else {
			this.setFlag(false);
		}
		return SUCCESS;
	}
	
	/**
	 * 更新发货状态
	 * @return
	 */
	@AssistantPermission(target = "trade",action = "shipment")
	public String confirmdelivery(){
		sendDelivery.setSenderMemberId(this.getUserId());
		sendDelivery.setSendIp(String.valueOf(ServletUtil.getRemoteNumberIp()));
		Result result = orderBusinessAO.confirmDelivery(sendDelivery);
		this.flag = result.isSuccess();
		if(this.flag) {
			this.message = OrderMessage.getMessage(OrderMessageName.OPERATE_SUCCESS, new Object());
			memberAsstLog.log("确认发货订单"+sendDelivery.getOrderId());
		} else { 
			this.message = OrderMessage.getMessage(OrderMessageName.OPERATE_FAILED, new Object());
		}
		
		if(result.isSuccess())
			return SUCCESS;
		return ERROR;
	}

	/**
	 * 查询已设置为默认的物流公司
	 * @return
	 */
	@AssistantPermission(target = "trade",action = "set")
	public String queryByMemberId() {
		this.setSellerDeliveryList(logisticsSellerDeliveryAO.queryByMemberId(this.getUserId()));
		return Action.SUCCESS;
	}
	
	/**
	 * 新增默认发货物流公司
	 * @return
	 */
	@AssistantPermission(target = "trade",action = "set")
	public String addLogisticsSellerDelivery() {
//		String[] strs = logistics.getCompanyId().split("\\|");
//		logistics.setCompanyId(strs[0]);
//		logistics.setLogisticsType(Integer.parseInt(strs[1]));
		logistics.setState(1);
		logistics.setOrderNum(0);
		logistics.setGmtCreate(new Date());
		logistics.setGmtModified(new Date());
		logisticsSellerDeliveryAO.add(logistics);
		
		return Action.SUCCESS;
		
	}
	
	/**
	 * 删除已默认的的物流公司
	 * @return
	 */
	@AssistantPermission(target = "trade",action = "set")
	public String delLogisticsSellerDeliveryById() {
		logisticsSellerDeliveryAO.deleteById(logistics.getId());
		return Action.SUCCESS;
	}
	
	/**
	 * 设置默认发货 物流公司
	 * @return 0正常 1物流信息已有10个 2物流公司已存在
	 */
	@AssistantPermission(target = "trade",action = "set")
	public String isLogisticsSellerDevliery() {
		List<LogisticsSellerDefaultDO> list = logisticsSellerDeliveryAO.queryByMemberId(this.getUserId());
		
		if (list != null && list.size() < 10) {
			this.setStatus("0");
			
			for (LogisticsSellerDefaultDO ldo : list) {
				String companyId = this.logistics.getCompanyId().split("\\|")[0];
				if (ldo.getCompanyId().equals(companyId)) {
					this.setStatus("2");
					break;
				}
			}
		} else {
			this.setStatus("1");
		}
		
		return Action.SUCCESS;
	}
	
	/**
	 * 查询已设为默认的物流公司
	 * @return
	 */
	@AssistantPermissions({
		  @AssistantPermission(target = "trade",action = "set"),
		  @AssistantPermission(target = "trade",action = "shipment")
		})
	public String getLogisticsCorp() {
		LogisticsCorpDO corpDO = new LogisticsCorpDO();
		corpDO.setLogisticsStatus(Long.parseLong("1"));
		
		this.setCorpList(logisticsCorpAO.getLogisticsCorpByStatus(corpDO));
		
		return Action.SUCCESS;
	}
	


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public boolean isFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public List<LogisticsCorpDO> getCorpList() {
		return corpList;
	}


	public void setCorpList(List<LogisticsCorpDO> corpList) {
		this.corpList = corpList;
	}


	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public List<LogisticsSellerDefaultDO> getSellerDeliveryList() {
		return sellerDeliveryList;
	}


	public void setSellerDeliveryList(
			List<LogisticsSellerDefaultDO> sellerDeliveryList) {
		this.sellerDeliveryList = sellerDeliveryList;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getTp() {
		return tp;
	}


	public void setTp(String tp) {
		this.tp = tp;
	}


	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}


	public void setOrderLogisticsDO(OrderLogisticsDO orderLogisticsDO) {
		this.orderLogisticsDO = orderLogisticsDO;
	}


	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
	
}
