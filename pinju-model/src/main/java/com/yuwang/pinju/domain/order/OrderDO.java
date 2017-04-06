package com.yuwang.pinju.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderTimeEnum;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.domain.order.query.OrderStateEnum;

/**
 * 订单实体
 * 
 * @author 杜成
 * @version 1.1
 * @since 2011-06-01
 */
public class OrderDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5931150369369371717L;

	/**
	 * 用户动态评价时效（天）
	 */
	public static final int ORDER_RATE_TIME_LIMIT = 15;

	/**
	 * 评价状态 -- 未评价（0）
	 */
	public final static int IS_RATE_NO = 0;

	/**
	 * 评价状态 -- 已评价（1）
	 */
	public final static int IS_RATE_YES = 1;
	
	/**
	 * 评价状态 -- 超时关闭(2)
	 */
	public final static int RATE_CLOSE = 2;

	/**
	 * 未付款
	 */
	public static final int ORDER_STATE_1 = 1;

	/**
	 * 已付款
	 */
	public static final int ORDER_STATE_2 = 2;

	/**
	 * 已发贷
	 */
	public static final int ORDER_STATE_3 = 3;

	/**
	 * 交易中
	 */
	public static final int ORDER_STATE_4 = 4;
	
	/**
	 * 交易成功
	 */
	public static final int ORDER_STATE_5 = 5;

	/**
	 * 交易关闭
	 */
	public static final int ORDER_STATE_6 = 6;

	/**
	 * 订单主键
	 */
	private Long orderId;

	/**
	 * 卖家编号
	 */
	private Long sellerId;

	/**
	 * 买家编号
	 */
	private Long buyerId;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 订单状态
	 */
	private Integer orderState = ORDER_STATE_1;

	/**
	 * 订单状态最后一次修改状态时间
	 */
	private Date stateModifyTime;

	/**
	 * 订单结束描述
	 */
	private String failReason;

	/**
	 * 买家是否已评价0未评价,1已评价
	 */
	private Integer isBuyerRate;

	/**
	 * 卖家是否已评价 0未评价,1已评价
	 */
	private Integer isSellerRate;

	/**
	 * 订单发起ip
	 */
	private String buyIp;

	/**
	 * 买家留言
	 */
	private String buyerMeMo;

	/**
	 * 卖家留言
	 */
	private String sellerMeMo;

	/**
	 * 订单总额
	 */
	private Long priceAmount;
	
	/**
	 * 订单总额,显示用
	 */
	private String priceAmountByYuan;

	/**
	 * 预留字段 以后添加功能 key:value;(格式)
	 */
	private String orderAttributes;

	/**
	 * 更新时间
	 */
	private Date gmtModified;

	/**
	 * 生成时间
	 */
	private Date gmtCreate;

	/**
	 * 店铺编号
	 */
	private Long shopId;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 确认收货开始时间
	 */
	private Date confirmStartTime;
	
	/**
	 * 确认收货剩余小时
	 */
	private Integer residueTimeHour;
	
	/**
	 * 是否有进行中退款
	 */
	private Integer isRefund = OrderConstant.IS_REFUND_NO;
	
	private List<OrderItemDO> orderItemlist = new ArrayList<OrderItemDO>();
	
	private OrderLogisticsDO orderLogisticsDO;
	
	public OrderDO() {

	}

	
	/**
	 * 生成订单使用
	 */
	public OrderDO(Long sellerId, Long buyerId, String buyerNick,
			String sellerNick, String buyIp, String buyerMeMo, Long priceAmount,Long shopId,String shopName) {
		this.orderState = ORDER_STATE_1;
		this.isBuyerRate = IS_RATE_NO;
		this.isSellerRate = IS_RATE_NO;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.buyerMeMo = buyerMeMo;
		this.buyerNick = buyerNick;
		this.sellerNick = sellerNick;
		this.buyIp = buyIp;
		this.priceAmount = priceAmount;
		this.shopId = shopId;
		this.shopName = shopName;
	}
	
	 /** 
	 * Created on 2011-8-17
	 * <p>Discription: 封装结算order,后续可根据需要修改封装参数</p>
	 * @param buyerId
	 * @param sellerId
	 * @param sellerNick
	 * @param shopId
	 * @param shopName
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static OrderDO createSettlementOrderDO(Long buyerId, String buyerNick ,Long sellerId, String sellerNick,
			Long shopId, String shopName){
		OrderDO orderDO = new OrderDO();
		orderDO.setBuyerId(buyerId);
		orderDO.setBuyerNick(buyerNick);
		orderDO.setSellerId(sellerId);
		orderDO.setSellerNick(sellerNick);
		orderDO.setShopId(shopId);
		orderDO.setShopName(shopName);
		orderDO.setIsBuyerRate(0);
		orderDO.setIsSellerRate(0);
		return orderDO;
	}
	

	
	/**
	 * 更新订单状态
	 */
	public OrderDO(Long orderId, Integer orderState,String failReason) {
		this.orderState = orderState;
		this.stateModifyTime = new Date();
		this.failReason = failReason;
		this.orderId = orderId;
	}


	/**
	 * 
	 * Created on 2011-10-13
	 * <p>Discription: 更新订单评价</p>
	 * @param orderId
	 * @param isSellerRate
	 * @param isBuyerRate
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static OrderDO initRateOrderDO(Long orderId, Integer isSellerRate, Integer isBuyerRate) {
		OrderDO orderDO = new OrderDO();
		orderDO.setOrderId(orderId) ;
		orderDO.setIsBuyerRate(isBuyerRate) ;
		orderDO.setIsSellerRate(isSellerRate) ;
		return orderDO;
	}
	
	
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getOrderAttributes() {
		return orderAttributes;
	}

	public void setOrderAttributes(String orderAttributes) {
		this.orderAttributes = orderAttributes;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getBuyIp() {
		return buyIp;
	}

	public void setBuyIp(String buyIp) {
		this.buyIp = buyIp;
	}

	public String getBuyerMeMo() {
		return buyerMeMo;
	}

	public void setBuyerMeMo(String buyerMeMo) {
		this.buyerMeMo = buyerMeMo;
	}

	public String getSellerMeMo() {
		return sellerMeMo;
	}

	public void setSellerMeMo(String sellerMeMo) {
		this.sellerMeMo = sellerMeMo;
	}

	public Integer getIsBuyerRate() {
		return isBuyerRate;
	}

	public void setIsBuyerRate(Integer isBuyerRate) {
		this.isBuyerRate = isBuyerRate;
	}

	public Integer getIsSellerRate() {
		return isSellerRate;
	}

	public void setIsSellerRate(Integer isSellerRate) {
		this.isSellerRate = isSellerRate;
	}

	public Long getPriceAmount() {
		return priceAmount;
	}

	public void setPriceAmount(Long priceAmount) {
		this.priceAmount = priceAmount;
		
		this.priceAmountByYuan = MoneyUtil.getCentToDollar(this.priceAmount);
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}



	public Date getConfirmStartTime() {
		return confirmStartTime;
	}


	public void setConfirmStartTime(Date confirmStartTime) {
		this.confirmStartTime = confirmStartTime;
	}


	public Integer getResidueTimeHour() {
		return residueTimeHour;
	}


	public void setResidueTimeHour(Integer residueTimeHour) {
		this.residueTimeHour = residueTimeHour;
	}


	public Integer getIsRefund() {
		return isRefund;
	}


	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}


	public Long getShopId() {
		return shopId;
	}


	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public boolean isBuyerRate() {
		if (this.orderState.compareTo(ORDER_STATE_5) == 0 && this.isBuyerRate.compareTo(IS_RATE_NO)==0) {
			if (!DateUtil.isIndate(this.stateModifyTime, ORDER_RATE_TIME_LIMIT))
				return true;
		}
		return false;
	}

	public String getPriceAmountByYuan(){
		return this.priceAmountByYuan;
	}
	
	/**
	 * <p>
	 * 取得订单状态显示文字信息
	 * </p>
	 * @return 订单状态显示文字信息
	 */
	public String getOrderStateDisplay() {
		return OrderStateEnum.getValueByType(this.orderState).getDescription();
	}

	public String getOrderStateDisplay_1(int orderState) {
		return OrderStateEnum.getValueByType(orderState).getDescription();
	}

	/**
	 * Created on 2011-8-30
	 * <p>Discription: 列表页倒计时</p>
	 * @return
	 * @param bussinessType:交易类型
	 * @param consignmentMode : 物流类型
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("deprecation")
	public String countDown(Integer bussinessType,String consignmentMode){
		Date date = new Date(this.gmtCreate.getTime());
		//等待买家付款
		if (this.orderState.compareTo(ORDER_STATE_1) == 0 && this.isBuyerRate.compareTo(IS_RATE_NO) == 0) {
			if(bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_1) ==0 || bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_2) == 0 ){
				date = DateUtils.addHours(date, Integer.valueOf(OrderTimeEnum.PAY_CLOSE_TIME.getFeatureName()));
			}
			if(bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_3) == 0){
				//date = DateUtils.addMinutes(date, 30);
				date = DateUtils.addMinutes(date, Integer.valueOf(OrderTimeEnum.PAY_CLOSE_TIME_Z.getFeatureName()));
			}
			if(bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_4) == 0){
				//date = DateUtils.addHours(date, 3);
				date = DateUtils.addHours(date, Integer.valueOf(OrderTimeEnum.PAY_CLOSE_TIME_T.getFeatureName()));
			}
			//获得下订单时间
			if (!DateUtil.isIndate(date, 0))  //待付款
				return "等待买家付款";
			else 
				return "交易关闭";
		}
		
		//等待买家收货
		if (this.orderState.compareTo(ORDER_STATE_3) == 0 && this.isBuyerRate.compareTo(IS_RATE_NO) == 0) {
			if(consignmentMode != null && !consignmentMode.equals("")){
				if(this.confirmStartTime != null){
					date = new Date(this.confirmStartTime.getTime());
				}
				
				if(this.isRefund.compareTo(OrderConstant.IS_REFUND_YES) == 0){
					date = new Date();
				}
				
				if(this.residueTimeHour != null){
				//	date.setHours(date.getHours()+this.residueTimeHour);
					date = DateUtils.addHours(date, this.residueTimeHour);
				}
			}
			if (!DateUtil.isIndate(date, 0))  //待付款
				return "卖家已发货";
			else 
				return "交易成功";
		}
		
		return "其他";
	}


	public List<OrderItemDO> getOrderItemlist() {
		return orderItemlist;
	}


	public void setOrderItemlist(List<OrderItemDO> orderItemlist) {
		this.orderItemlist = orderItemlist;
	}


	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}


	public void setOrderLogisticsDO(OrderLogisticsDO orderLogisticsDO) {
		this.orderLogisticsDO = orderLogisticsDO;
	}



	@Override
	public String toString() {
		return "OrderDO [buyIp=" + buyIp + ", buyerId=" + buyerId
				+ ", buyerMeMo=" + buyerMeMo + ", buyerNick=" + buyerNick
				+ ", failReason=" + failReason + ", gmtCreate=" + gmtCreate
				+ ", gmtModified=" + gmtModified + ", isBuyerRate="
				+ isBuyerRate + ", isSellerRate=" + isSellerRate
				+ ", orderAttributes=" + orderAttributes + ", orderId="
				+ orderId + ", orderItemlist=" + orderItemlist
				+ ", orderLogisticsDO=" + orderLogisticsDO + ", orderState="
				+ orderState + ", priceAmount=" + priceAmount
				+ ", priceAmountByYuan=" + priceAmountByYuan + ", sellerId="
				+ sellerId + ", sellerMeMo=" + sellerMeMo + ", sellerNick="
				+ sellerNick + ", shopId=" + shopId + ", shopName=" + shopName
				+ ", stateModifyTime=" + stateModifyTime + "]";
	}

	
}
