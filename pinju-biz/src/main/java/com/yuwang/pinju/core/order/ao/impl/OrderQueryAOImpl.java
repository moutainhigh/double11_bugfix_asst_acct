package com.yuwang.pinju.core.order.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderTimeEnum;
import com.yuwang.pinju.Constant.OrderttributesEnum;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.coupon.manager.CouponManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsCorpManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.service.OpenQueryOrderService;
import com.yuwang.pinju.core.rights.manager.RightsManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.OrderAllVO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.order.query.QueryVO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.rights.RightsQuery;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;


/**
 * Created on 2011-7-27
 * 
 * @see <p>
 *      Discription: 订单查询AO
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderQueryAOImpl implements OrderQueryAO {
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private OrderQueryManager orderQueryManager;	
	private OrderBusinessManager orderBusinessManager;	
	private OrderCheckManager orderCheckManager;	
	private LogisticsCorpManager logisticsCorpManager;
	private MemberManager memberManager;
	private VouchQueryManager vouchQueryManager;
	private OpenQueryOrderService openQueryOrderService;
	private RightsManager rightsManager;
	private CouponManager couponManager;

	public void setOpenQueryOrderService(OpenQueryOrderService openQueryOrderService) {
		this.openQueryOrderService = openQueryOrderService;
	}

	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager) {
		this.orderBusinessManager = orderBusinessManager;
	}

	@Override
	public Result queryLastTimeBuyList(QueryOrderItem queryOrderItemDO) {
		Result result = new ResultSupport();
		try {

			long num = orderQueryManager.queryOrderItemDONum(queryOrderItemDO);

			List<OrderItemDO> list = orderQueryManager
					.queryOrderItemDOList(queryOrderItemDO);

			result.setModel("orderItemDOList", list);

			result.setModel("num", num);
			
			result.setResultCode(OrderConstant.OPERATESUCCED);
			
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(OrderConstant.EXCEPTION);
			log.error("Event=[OrderBusinessManagerImpl#confirmDelivery] 子订单分页查询错误:" , e);
		}
		return result;
	}

	
	/**
	 * 获取关闭订单原因,订单关闭类型
	 */
	private String[] closeReason(String closeCode){
	    String failReason = "";
	    String closeType = "";
		if(!StringUtil.isNumeric(closeCode)){
			return new String[]{"",""};
		}
		Integer code = Integer.valueOf(closeCode);
		switch (code) {
			case 1001:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_BUYER;
				failReason = OrderConstant.ORDER_BUYER_CLOSE_REASON_NOBUY;
				break;
			case 1002:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_BUYER;
				failReason = OrderConstant.ORDER_BUYER_CLOSE_REASON_ERRORINFO;
				break;
			case 1003:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_BUYER;
				failReason = OrderConstant.ORDER_BUYER_CLOSE_REASON_NOGOODS;
				break;
			case 1004:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_BUYER;
				failReason = OrderConstant.ORDER_BUYER_CLOSE_REASON_FACETOFACE;
				break;
			case 1005:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_BUYER;
				failReason = OrderConstant.ORDER_BUYER_CLOSE_REASON_OTHER;
				break;
			case 2001:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_SELLER;
				failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_NOBUY;
				break;
			case 2002:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_SELLER;
				failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_ERRORINFO;
				break;
			case 2003:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_SELLER;
				failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_NOGOODS;
				break;
			case 2004:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_SELLER;
				failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_FACETOFACE;
				break;
			case 2005:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_SELLER;
				failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_OTHER;
				break;
			case 2006:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_SELLER;
				failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_PAY_BANK;
				break;
			case 2007:
				closeType = OrderConstant.ORDER_CLOSE_TYPE_SELLER;
				failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_PAY_INLINE;
				break;
		}	 
		
		return new String[]{failReason,closeType};
	}
	
	//买卖家查看订单详情   身份校验
	public Result searchOrderInfoCheck(Long orderId,Long userID, String tp) throws ManagerException{
		Result result = new ResultSupport();
		if (!EmptyUtil.isBlank(tp) && !EmptyUtil.isBlank(userID)) {
			if (tp.equals("1")) {
				if (!orderCheckManager.isBuyerOrder(orderId, userID)) {
					result.setModel("message", "买家非法查看订单详情");
					result.setSuccess(false);
					return result;
				}
			} else if (tp.equals("2")) {
				if (!orderCheckManager.isSellerOrder(orderId, userID)) {
					result.setModel("message", "卖家非法查看订单详情");
					result.setSuccess(false);
					return result;
				}
			} else {
				result.setModel("message", "非法查看订单详情");
				result.setSuccess(false);
				return result;
			}

		} else {
			result.setModel("message", "非法查看订单详情");
			result.setSuccess(false);
			return result;
		}
		
		return result;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public Result getOrderDOById(long orderId, Long userID, String tp) {
		Result result = new ResultSupport();
		try {
			//买卖家身份校验
			result = this.searchOrderInfoCheck(orderId, userID, tp);
			if(!result.isSuccess()){
				return result;
			}

			OrderDO orderDO = orderQueryManager.getOrderDOById(orderId);

			if (orderDO == null) {
				result.setSuccess(false);
				return result;
			}

			// 获取关闭订单原因
			String failReason = "";
			// 订单关闭类型
			String closeType = "";
			if (orderDO.getOrderState().compareTo(OrderDO.ORDER_STATE_6) == 0
					&& StringUtil.isNotBlank(orderDO.getFailReason())) {
				String[] fCode = this.closeReason(orderDO.getFailReason());
				failReason = fCode[0];
				closeType = fCode[1];
			}

			result = this.queryOrderItemList(orderId);
			List<OrderItemDO> orderItemList = new ArrayList<OrderItemDO>();
			orderItemList = (List<OrderItemDO>) result.getModel("list");

			/** 交易类型 100直冲,200 一口价,300 限时折扣,400 团购 **/
			Integer bussinessType = null;
			// 不含运费的钱
			String orderAountPriceNoPost;
			// 订单总价
			String orderAllPrice;
			// 退款总金额
			long refundAllPrice = 0;
			// 倒计时截止日期
			Date delineDate = orderDO.getGmtCreate();
			// 卖家是否可修改订单价格
			Integer postPay = 1; // 1：卖家可修改价格 0：卖家不可修改价格
			if (StringUtil.contains(orderDO.getOrderAttributes(),
					OrderttributesEnum.IS_POST_PAY.getFeatureName())) {
				postPay = 0;
			}

			for (OrderItemDO orderItemDO : orderItemList) {
				if (orderItemDO.getRefundState().compareTo(
						RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0
						|| orderItemDO.getRefundState().compareTo(
								RefundDO.STATUS_SUCCESS) == 0) {
					if (!EmptyUtil.isBlank(orderItemDO.getRefundPrice())) {
						// 退款总金额
						refundAllPrice = refundAllPrice
								+ orderItemDO.getRefundPrice();
					}
				}
				if (EmptyUtil.isBlank(bussinessType)) {
					// 交易类型
					bussinessType = orderItemDO.getBussinessType();
				}

			}

			// 含运费的订单总额
			orderAllPrice = MoneyUtil.getCentToDollar(orderDO.getPriceAmount()
					- refundAllPrice);

			// 订单物流
			OrderLogisticsDO orderLogisticsDO = orderBusinessManager
					.queryOrderLogisticsByOrderId(orderDO.getOrderId());

			// 不含运费的订单总额
			if (!EmptyUtil.isBlank(orderLogisticsDO.getPostPrice())) {
				orderAountPriceNoPost = MoneyUtil.getCentToDollar(orderDO
						.getPriceAmount() - orderLogisticsDO.getPostPrice());
			} else {
				orderAountPriceNoPost = MoneyUtil.getCentToDollar(orderDO
						.getPriceAmount());
			}

			// 获取物流公司信息
			LogisticsCorpDO logisticsCorpDO = this
					.getLogisticsCorpDOInfo(orderLogisticsDO);

			// 获取订单倒计时时限
			delineDate = this.getDelineDate(orderDO, bussinessType);

			// 订单支付信息(担保交易)
			VouchPayDO vouchPayDO = this.getPayInfo(orderId);

			// 获得订单优惠信息(限时折扣优惠)
			Map<Long, String[]> dMap = this.getDiscountDesc(orderId);

			// 获取订单优惠券信息
			String couponInfo = this.getCouponInfo(orderId);

			MemberInfoDO memberInfo;

			if ("1".equals(tp)) { // 买家
				memberInfo = (MemberInfoDO) memberManager.findMemberInfo(Long
						.valueOf(orderDO.getSellerId().toString())); // 卖家信息
				if (memberInfo != null)
					memberInfo.setIdCard("卖家信息");

			} else { // 卖家
				memberInfo = (MemberInfoDO) memberManager.findMemberInfo(Long
						.valueOf(orderDO.getBuyerId().toString())); // 买家信息BigDecimal
				if (memberInfo != null)
					memberInfo.setIdCard("买家信息");
			}

			result.setSuccess(true);
			result.setModel("orderDO", orderDO);
			result.setModel("orderItemList", orderItemList);
			result.setModel("orderAountPriceNoCost", orderAountPriceNoPost);
			result.setModel("delineDate", delineDate);
			result.setModel("orderLogisticsDO", orderLogisticsDO);
			result.setModel("logisticsCorpDO", logisticsCorpDO);
			result.setModel("memberInfo", memberInfo);
			result.setModel("vouchPayDO", vouchPayDO);
			result.setModel("dMap", dMap);
			result.setModel("orderAllPrice", orderAllPrice);
			result.setModel("refundAllPrice",
					MoneyUtil.getCentToDollar(refundAllPrice));
			result.setModel("postPay", postPay);
			result.setModel("couponInfo", couponInfo);
			result.setModel("failReason", failReason);
			result.setModel("closeType", closeType);

			return result;

		} catch (Exception e) {
			log.error("OrderCreationAOImpl.getOrderDOById error, " + e);
			result.setSuccess(false);
			result.setModel("message","网络繁忙，请稍后重试！");
		}
		return result;
	}

	 /**
	 * 获取倒计时时限
	 */
	public Date getDelineDate(OrderDO orderDO,Integer bussinessType){
		Date delineDate = orderDO.getGmtCreate();
		
		if(orderDO.getOrderState().compareTo(OrderDO.ORDER_STATE_1) == 0) {
			if(bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_1) == 0 || bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_2) == 0){	
				delineDate = DateUtils.addHours(delineDate, Integer.valueOf(OrderTimeEnum.PAY_CLOSE_TIME.getFeatureName()));
			}else if(bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_3) == 0){				
				//delineDate = DateUtils.addMinutes(delineDate, 30);
				delineDate = DateUtils.addMinutes(delineDate, Integer.valueOf(OrderTimeEnum.PAY_CLOSE_TIME_Z.getFeatureName()));
			}else {
				//delineDate = DateUtils.addHours(delineDate, 3);
				delineDate = DateUtils.addHours(delineDate, Integer.valueOf(OrderTimeEnum.PAY_CLOSE_TIME_T.getFeatureName()));
			}
			
		}
		if (orderDO.getOrderState().compareTo(OrderDO.ORDER_STATE_3) == 0) {
			delineDate = orderDO.getConfirmStartTime();
			if(delineDate == null){
				delineDate = new Date();
			}
			
			if(orderDO.getIsRefund().compareTo(OrderConstant.IS_REFUND_YES) == 0){
				delineDate = new Date();
			}
			
			if(!EmptyUtil.isBlank(orderDO.getResidueTimeHour())){
				delineDate = DateUtils.addHours(delineDate, orderDO.getResidueTimeHour());
			}
			
		}
		
		return delineDate;
	}
	
	/**
	 * 获取订单的支付信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VouchPayDO getPayInfo(Long orderId){
		//订单支付信息(担保交易)
		VouchPayDO vouchPayDO = new VouchPayDO();
		Map map = new HashMap();
		map.put("orderId", orderId);
		long [] payState = new long[]{VouchPayConstant.VOUCH_PAY_STATE_PAID,VouchPayConstant.VOUCH_PAY_STATE_SPLIT};
		map.put("payState", payState); 
		try {
			vouchPayDO = vouchQueryManager.selectOrderPay(map);
		} catch (ManagerException e) {
			log.error("OrderCreationAOImpl.getPayInfo 获取订单支付信息报错： " + e);
		}
		
		return vouchPayDO;
	}
	
	/**
	 * 获得订单优惠信息
	 */
	@SuppressWarnings("rawtypes")
	public Map<Long, String[] > getDiscountDesc(Long orderId){
		Map<Long,String> descMap = openQueryOrderService.queryOrderItemDiscountDesc(orderId);
		
		Map<Long, String[] > dMap = new HashMap<Long, String[]>();
        for (Iterator it = descMap.keySet().iterator(); it.hasNext();) {
            Long key =  (Long) it.next();
            if(!EmptyUtil.isBlank(descMap.get(key))){
	            dMap.put(key, StringUtil.split(descMap.get(key).toString(),VouchPayConstant.SPLITKEY));
            }
        }
        
        return dMap;
	}
	
	/**
	 * 获取订单优惠券信息
	 */
	public String getCouponInfo(Long orderId){
		TradeCouponDO couponDO = new TradeCouponDO();
        couponDO.setOrderId(orderId);
        try {
			couponDO = couponManager.getTradeCouponDO(couponDO);
		} catch (ManagerException e) {
			log.error("OrderCreationAOImpl.getCouponInfo 获取订单使用优惠券记录：" + e);
		}
        String couponInfo = "";
        if(couponDO != null){
        	couponInfo = "省 "+couponDO.getCouponMoneyByYuan()+"元  ";
        }
        
        return couponInfo;
	}
	
	/**
	 *	获取订单物流公司信息
	 */
	public LogisticsCorpDO getLogisticsCorpDOInfo(OrderLogisticsDO orderLogisticsDO){
		LogisticsCorpDO logisticsCorpDO = new LogisticsCorpDO();
		 LogisticsCorpDO logisticsCorpDO1=new LogisticsCorpDO();
		 if(StringUtil.isNotBlank(orderLogisticsDO.getLogisticsType())){
			 logisticsCorpDO1.setCorpCode(orderLogisticsDO.getLogisticsType());
			 List<LogisticsCorpDO> logisticsCorpList = null;
			try {
				logisticsCorpList = logisticsCorpManager.getLogisticsCorpByStatus(logisticsCorpDO1);
			} catch (ManagerException e) {
				log.error("OrderCreationAOImpl.getLogisticsCorpDOInfo 获取订单物流公司信息报错： " + e);
			}
		    if(logisticsCorpList != null && logisticsCorpList.size() > 0){
		    	logisticsCorpDO = logisticsCorpList.get(0);
		    }
		 }
		 if(logisticsCorpDO == null){
			logisticsCorpDO = new LogisticsCorpDO();
		 }
		 
		 return logisticsCorpDO;
	}
	
	@Override
	public Result queryOrderItemList(long orderId){
		Result result = new ResultSupport();
		try {
			List<OrderItemDO> list = orderQueryManager
					.queryOrderItemList(orderId);

			if (list == null) {
				result.setSuccess(false);
			} else {
				result.setModel("list", list);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			log.error("OrderCreationAOImpl.getOrderDOById error, " + e);
		}
		return result;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	@Override
	public int queryOrderListCount(Map<String, Object> map) {
		int i=0;
		try {
			i = orderQueryManager.getOrderListInTimeCount(map);
		} catch (ManagerException e) {
			log.error("OrderQueryAoImpl.queryOrderListCount", e);
		}
		return i;
	}
	
	@Override
	public Result queryOrderList(Map<String, Object> map) {
		Result result = new ResultSupport();
		try {
			List<OrderDO> orderList = orderQueryManager.getOrderListInTime(map);
			List<OrderAllVO> orderAllList = new ArrayList<OrderAllVO>();
			for (OrderDO od : orderList) {
				OrderAllVO orderAll = new OrderAllVO();
				orderAll.setOrderDO(od);
				if(StringUtil.contains(od.getOrderAttributes(), OrderttributesEnum.IS_POST_PAY.getFeatureName())){
					orderAll.setPostPay(0);
				}
				orderAll.setOrderItemList(orderQueryManager.queryOrderItemList(od.getOrderId()));
				orderAll.setLogisticsDO(orderBusinessManager.queryOrderLogisticsByOrderId(od.getOrderId()));
				if(orderAll.getLogisticsDO() == null){
					orderAll.setLogisticsDO(new OrderLogisticsDO());
				}
				orderAllList.add(orderAll);
			}
			result.setSuccess(true);
			result.setModel("orderAllList", orderAllList);
			return result;
		} catch (Exception e) {
			log.error("OrderQueryAoImpl.queryOrderList", e);
			result.setSuccess(false);
			return result;
		}
		
	}

	@Override
	public Result queryOrderListByItemTitle(long buyerId, String itemTitle,String gmtCreateStart,String gmtCreateEnd,int page,String orderItemState,Integer [] refundState,String isBuyerRate) {
		Result result = new ResultSupport();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyerId);
		map.put("itemTitle", itemTitle);
		map.put("gmtCreateStart", gmtCreateStart);
		map.put("gmtCreateEnd", gmtCreateEnd);
		map.put("startRow", (page-1)*20);
		map.put("endRow", page*20);
		map.put("orderItemState", orderItemState);
		map.put("refundState", refundState);
		map.put("isBuyerRate", isBuyerRate);
		try {
			List<OrderDO> orderList = orderQueryManager.getOrderItemListByItemTitle(map);
			List<OrderAllVO> orderAllList = new ArrayList<OrderAllVO>();
			for (OrderDO od : orderList) {
				OrderAllVO orderAll = new OrderAllVO();
				orderAll.setOrderDO(od);
				orderAll.setOrderItemList(orderQueryManager.queryOrderItemList(od.getOrderId()));
				orderAll.setLogisticsDO(orderBusinessManager.queryOrderLogisticsByOrderId(od.getOrderId()));
				
				if(orderAll.getLogisticsDO() == null){
					orderAll.setLogisticsDO(new OrderLogisticsDO());
				}
				
				orderAllList.add(orderAll);
			}
			
			
			result.setSuccess(true);
			result.setModel("orderAllList", orderAllList);
		} catch (ManagerException e) {
			log.error("OrderQueryAoImpl.queryOrderListByItemTitle", e);
			result.setSuccess(false);
			return result;
		}
		return result;
	}

	@Override
	public long queryOrderItemListNum(long buyerId, String itemTitle,
			String gmtCreateStart, String gmtCreateEnd, String orderItemState,Integer [] refundState) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyerId);
		map.put("itemTitle", itemTitle);
		map.put("gmtCreateStart", gmtCreateStart);
		map.put("gmtCreateEnd", gmtCreateEnd);
		map.put("orderItemState", orderItemState);
		map.put("refundState", refundState);
		long num = 0;
		try {
			num = orderQueryManager.getOrderItemListByItemTitleNum(map);
			if(num>0){
				if(num%20!=0){
					num = num/20 + 1;
				}else {
					num = num/20;
				}
			}
			return num;
		} catch (ManagerException e) {
			log.error("OrderQueryAoImpl.queryOrderItemListNum"+e);
		}
		return num;
	}

	@Override
	public Result createOrderPay(Long[] orderId) {
		Result result = new ResultSupport();
		boolean flag = true;
		try {
			if (orderId == null) {
				result.setSuccess(false);
				return result;
			}
			List<DirectPayParamDO> list = new ArrayList<DirectPayParamDO> ();
			for (Long id : orderId) {
				
				//判断订单状态是否为“等待买家付款状态”
				if(orderQueryManager.getOrderDOById(id).getOrderState() != 1){
					result.setSuccess(false);
					result.setResultCode(OrderConstant.ORDERSTATEEERROR);
					return result;
				}
				
				
				OrderDO orderDO = orderQueryManager.getOrderDOById(id);
				List<OrderItemDO>  orderItemDOlist = orderQueryManager.queryOrderItemList(id);
				if(orderItemDOlist == null || orderItemDOlist.isEmpty()){
					log.error("主订单与子订单参数不一致");
					throw new  ManagerException();
				}
				DirectPayParamDO directPayParamDO = null;
				for(OrderItemDO orderItemDO : orderItemDOlist){
					directPayParamDO = new DirectPayParamDO();
					directPayParamDO.setBizType(orderItemDO.getBussinessType());
					directPayParamDO.setBuyAmount(orderItemDO.getBuyNum().intValue());
					directPayParamDO.setBuyerId(orderItemDO.getBuyerId());
					directPayParamDO.setBuyerNick(orderItemDO.getBuyerNick());
					directPayParamDO.setCreateTime(orderItemDO.getGmtCreate());
					directPayParamDO.setItemId(orderItemDO.getItemId());
					directPayParamDO.setItemPrice(orderItemDO.getOrderItemPrice());
					directPayParamDO.setItemTitle(orderItemDO.getItemTitle());
					directPayParamDO.setOrderId(orderItemDO.getOrderId());
					directPayParamDO.setOrderPrice(orderDO.getPriceAmount());
					directPayParamDO.setSellerId(orderItemDO.getSellerId());
					directPayParamDO.setSellerNick(orderItemDO.getSellerNick());
				}
				list.add(directPayParamDO);
			}
			result.setModel("directPayParamDOList",list);
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAOImpl#createOrderPay] 订单列表封装支付参数错误:" , e);
			flag = false;
		}catch (Exception e) {
			log.error("Event=[OrderQueryAOImpl#createOrderPay] 订单列表封装支付参数错误:" , e);
			flag = false;
		}
		if(!flag){
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public Result selectOrderListCount(Map<String, Object> map) {
		Result result = new ResultSupport();
		try {
			int count = orderQueryManager.getOrderListInTimeCount(map);
			result.setModel("count",count);
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAOImpl#selectOrderListCount] 查询不同状态订单的数量错误:" , e);
			result.setSuccess(false);
		} catch (Exception e) {
			log.error("Event=[OrderQueryAOImpl#selectOrderListCount] 查询不同状态订单的异常:" , e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public Result queryOrderListByState(long buyerId,int page, int orderItemState[]) {
		Result result = new ResultSupport();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyerId);
		map.put("startRow", (page-1)*10);
		map.put("endRow", page*10);
		map.put("orderItemState", orderItemState);
		try {
			List<OrderDO> orderList = orderQueryManager.queryOrderItemListByState(map);
			List<OrderAllVO> orderAllList = new ArrayList<OrderAllVO>();
			for (OrderDO od : orderList) {
				OrderAllVO orderAll = new OrderAllVO();
				orderAll.setOrderDO(od);
				orderAll.setOrderItemList(orderQueryManager.queryOrderItemList(od.getOrderId()));
				orderAll.setLogisticsDO(orderBusinessManager.queryOrderLogisticsByOrderId(od.getOrderId()));
				if(orderAll.getLogisticsDO() == null){
					orderAll.setLogisticsDO(new OrderLogisticsDO());
				}
				orderAllList.add(orderAll);
			}
			result.setSuccess(true);
			result.setModel("orderAllList", orderAllList);
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAoImpl#queryOrderListByState]", e);
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	
	@Override
	public Result getRightsManagerNum(long buyerId) {
		Result result = new ResultSupport();
		try {
			RightsQuery rightsQuery = new RightsQuery();
			rightsQuery.setBuyerId(buyerId);
			int count = rightsManager.getRightsCount(rightsQuery);
			result.setModel("count",count);
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAoImpl#getrightsManagerNum] 查询订单维权数量错误", e);
			result.setSuccess(false);
		} catch (Exception e) {
			log.error("Event=[OrderQueryAOImpl#getrightsManagerNum] 查询订单维权数量异常:" , e);
			result.setSuccess(false);
		}
		return result;
	}
	
	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}

	public void setLogisticsCorpManager(LogisticsCorpManager logisticsCorpManager) {
		this.logisticsCorpManager = logisticsCorpManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}
	
	public void setRightsManager(RightsManager rightsManager) {
		this.rightsManager = rightsManager;
	}

	@Override
	public OrderItemDO queryOrderItem(Long orderItemId) {
		try {
			return orderQueryManager.getOrderItemDOById(orderItemId);
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAOImpl#selectOrderListCount] 获取子订单信息异常:" , e);
		}
		return null;
	}

	@Override
	public Result queryOrderListNoPage(Map<String, Object> map) {
		Result result = new ResultSupport();
		try {
			List<OrderDO> orderList = orderQueryManager.getOrderList(map);
			List<OrderAllVO> orderAllList = new ArrayList<OrderAllVO>();
			for (OrderDO od : orderList) {
				OrderAllVO orderAll = new OrderAllVO();
				orderAll.setOrderDO(od);
				orderAll.setOrderItemList(orderQueryManager.queryOrderItemList(od.getOrderId()));
				orderAll.setLogisticsDO(orderBusinessManager.queryOrderLogisticsByOrderId(od.getOrderId()));
				if(orderAll.getLogisticsDO() == null){
					orderAll.setLogisticsDO(new OrderLogisticsDO());
				}
				orderAllList.add(orderAll);
			}
			result.setSuccess(true);
			result.setModel("orderAllList", orderAllList);
			return result;
		} catch (Exception e) {
			log.error("OrderQueryAoImpl.queryOrderList", e);
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public Result queryOrderByBuyer(QueryVO queryVO) {
		Result result = new ResultSupport();
		try {
			List<OrderDO> orderList = orderQueryManager.queryOrderByBuyer(queryVO);
			List<OrderAllVO> orderAllList = new ArrayList<OrderAllVO>();
			for (OrderDO od : orderList) {
				OrderAllVO orderAll = new OrderAllVO();
				orderAll.setOrderDO(od);
				orderAll.setOrderItemList(orderQueryManager.queryOrderItemList(od.getOrderId()));
				orderAll.setLogisticsDO(orderBusinessManager.queryOrderLogisticsByOrderId(od.getOrderId()));
				
				if(orderAll.getLogisticsDO() == null){
					orderAll.setLogisticsDO(new OrderLogisticsDO());
				}
				
				orderAllList.add(orderAll);
			}
			
			
			result.setSuccess(true);
			result.setModel("orderAllList", orderAllList);
		} catch (ManagerException e) {
			log.error("OrderQueryAoImpl.queryOrderByBuyer", e);
			result.setSuccess(false);
			return result;
		}
		return result;
	}

	@Override
	public Long queryOrderNumByBuyer(QueryVO queryVO) {
		try {
			return orderQueryManager.queryOrderNumByBuyer(queryVO);
		} catch (ManagerException e) {
			log.error("OrderQueryAoImpl.queryOrderNumByBuyer", e);
		}
		return (long) 0;
	}
	
	@Override
	public Result checkModifyPrice(Long sellerId,Long orderId){
		Result result = new ResultSupport();
		OrderDO orderDO = new OrderDO();
		try {			
			orderDO = orderQueryManager.getOrderDOById(orderId);
			
			if(orderDO == null){
				result.setResultCode(OrderConstant.ORDERISNO);
				result.setSuccess(false);
				return result;
			}
			
			
			//判断订单是否属于卖家
			if(!orderCheckManager.isSellerOrder(orderId, sellerId)){
				result.setResultCode(OrderConstant.SELLERMEMBERERROR);
				result.setSuccess(false);
				return result;
			}
			
			//判断订单状态
			if(orderDO.getOrderState().compareTo(OrderDO.ORDER_STATE_1) != 0 ){
				result.setResultCode(OrderConstant.ORDERSTATEEERROR);
				result.setSuccess(false);
				return result;
			}
			
			//判断订单是否可以修改价格
			
			if(StringUtil.contains(orderDO.getOrderAttributes(), OrderttributesEnum.IS_POST_PAY.getFeatureName())){
				result.setResultCode(OrderConstant.ORDERMODIFYPRICE);
				result.setSuccess(false);
				return result;
			}
			
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("OrderQueryAoImpl.checkModifyPrice",e);
			return result;
		}
		result.setModel("orderDO",orderDO);
		return result;
	}
	
	@Override
	public OrderDO queryOrderDO(Long orderId) {
		try {
			return orderQueryManager.getOrderDOById(orderId);
		} catch (ManagerException e) {
			log.error("OrderQueryAoImpl.queryOrderDO",e);
		}
		return null;
	}

	@Override
	public Result queryOrderPriceAmount(Map<String, Object> map) {
		Result result = new ResultSupport();
		try {
			int sum = orderQueryManager.queryOrderPriceAmount(map);
			result.setModel("sum",sum);
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAOImpl#queryOrderPriceAmount] 查询订单的总金额错误:" , e);
			result.setSuccess(false);
		} catch (Exception e) {
			log.error("Event=[OrderQueryAOImpl#queryOrderPriceAmount] 查询订单的总金额:" , e);
			result.setSuccess(false);
		}
		return result;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}
	
	
}
