package com.yuwang.pinju.domain.order;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.domain.order.query.OrderStateEnum;
import com.yuwang.pinju.domain.refund.RefundDO;

/**
 * 子订单实体
 * 
 * @author 杜成
 * @version 1.0
 * @since 2011-06-01
 */
public class OrderItemDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1951818213729327757L;

	/**
	 * 无退款
	 */
	public static final int REFUND_ITEM_STATE_1 = 10001;

	/**
	 * 未付款
	 */
	public static final int ORDER_ITEM_STATE_1 = 1;

	/**
	 * 已付款
	 */
	public static final int ORDER_ITEM_STATE_2 = 2;

	/**
	 * 已发贷
	 */
	public static final int ORDER_ITEM_STATE_3 = 3;

	/**
	 * 交易成功
	 */
	public static final int ORDER_ITEM_STATE_5 = 5;

	/**
	 * 交易关闭
	 */
	public static final int ORDER_ITEM_STATE_6 = 6;

	/** 订单业务类型 */
	/**
	 * 直冲
	 */
	public static final int ORDER_ITEM_TYPE_1 = 100;

	/**
	 * 一口价
	 */
	public static final int ORDER_ITEM_TYPE_2 = 200;

	/**
	 * 限时折扣
	 */
	public static final int ORDER_ITEM_TYPE_3 = 300;
	/**
	 * 团购
	 */
	public static final int ORDER_ITEM_TYPE_4 = 400;

	/**
	 * 主键
	 */
	private Long orderItemId;

	/**
	 * 订单外键
	 */
	private Long orderId;

	/**
	 * 商品编号
	 */
	private Long itemId;

	/**
	 * 商品快照编号
	 */
	private Long snapId;
	
	/**
	 * 商品标题
	 */
	private String itemTitle;
	
	/**
	 * 商品实际支付价格到分
	 */
	private Long orderItemPrice;
	
	/**
	 * 商品实际支付价格到分,显示用
	 */
	private String orderItemPriceByYuan;
	
	/**
	 * 商品购买数量
	 */
	private Long buyNum;

	/**
	 * 商品图片路径
	 */
	private String itemPicUrl;

	/**
	 * 子订单状态最后一次编辑时间
	 */
	private Date stateModifyTime;

	/**
	 * 退款状态 默认为10001无退款
	 */
	private Integer refundState;

	/**
	 * 退款价格
	 */
	private Long refundPrice;
	
	/**
	 * 退款价格到分，显示用
	 */
	private String refundPriceByYuan;
	
	/**
	 * 预留字段 以后添加功能 key:value;(格式)
	 */
	private String orderItemAttributes;

	/**
	 * 更新时间
	 */
	private Date gmtModified;

	/**
	 * 生成时间
	 */
	private Date gmtCreate;

	/**
	 * 子订单状态
	 */
	private Integer orderItemState;

	/**
	 * 子订单商品SKU
	 */
	private Long itemSkuId;

	/**
	 * 子订单商品SKU描述
	 */
	private String itemSkuAttributes;

	/**
	 * 子订单交易类型 默认为拍卖
	 */
	private Integer bussinessType;

	/**
	 * 下单时商品产品实际价格
	 */
	private Long originalPrice;
	/**
	 * 下单时商品产品实际价格 格式化到元
	 */
	private String originalPriceByYuan;


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
	 * 订单描述
	 */
	private String failReason;
	/**
	 * 卖家延长收货确认时间
	 */
	private Integer confirmTime;

	/**
	 * 卖家更新价格 -为优惠 +为增加
	 */
	private Long sellerMarginPrice;
	
	/**
	 * 卖家更新价格 -为优惠 +为增加(显示用)
	 */
	private String sellerMarginPriceByYuan;
	/**
	 * 是否匿名购买 1为否,2为是
	 */
	private Long anonymousBuy;

	/**
	 * 商家调整后商品的总价（显示用）
	 */
	private String sellerMarginTotalPriceByYuan;
	
	/**
	 * 平台手续费金额单位为分
	 */
	private Long dealAmount = 0L;
	/**
	 * 平台手续费费率 单位为分
	 */
	private Long dealRates = 0L;
	/**
	 * 分销商编号 只做显示用
	 */
	private String channelId;

	/**
	 * 平台店铺费率
	 */
	private Long dealShopRates = 0L;
	
	/**
	 * 子订单实际总金额
	 */
	private Long totalAmount = 0l;
	
	/**
	 * 子订单实际总金额  显示用
	 */
	private String totalAmountByYuan;
	
	
	public OrderItemDO() {

	}


	/**
	 * 生成结算
	 */
	public static OrderItemDO creationSettlementOrderItemDO(Long itemId, String itemTitle, Long orderItemPrice,
			Long buyNum, String itemPicUrl, Long itemSkuId,
			String itemSkuAttributes, Integer bussinessType, Long originalPrice,Long anonymousBuy,String channelId,
			Long sellerId,String sellerNick,Long buyerId,String buyerNick,Long dealRates,Long dealAmount,Long dealShopRates){
		OrderItemDO orderItemDO = new OrderItemDO();
		orderItemDO.setRefundState(REFUND_ITEM_STATE_1);
		orderItemDO.setOrderItemState(ORDER_ITEM_STATE_1);
		orderItemDO.setBussinessType(bussinessType);
		orderItemDO.setItemId(itemId);
		orderItemDO.setItemTitle(itemTitle);
		orderItemDO.setOrderItemPrice(orderItemPrice);
		orderItemDO.setBuyNum(buyNum);
		orderItemDO.setItemPicUrl(itemPicUrl);
		orderItemDO.setItemSkuId(itemSkuId);
		orderItemDO.setItemSkuAttributes(itemSkuAttributes);
		orderItemDO.setOriginalPrice(originalPrice);
		orderItemDO.setAnonymousBuy(anonymousBuy);
		orderItemDO.setChannelId(channelId);
		orderItemDO.setSellerId(sellerId);
		orderItemDO.setSellerNick(sellerNick);
		orderItemDO.setBuyerId(buyerId);
		orderItemDO.setBuyerNick(buyerNick);
		orderItemDO.setDealRates(dealRates);
		orderItemDO.setDealAmount(dealAmount);
		orderItemDO.setDealShopRates(dealShopRates);
		orderItemDO.setOrderItemAttributes("");
		return orderItemDO;
	}
	


	/**
	 * 更新单个子订单状态
	 */
	public OrderItemDO(Long orderItemId, Integer orderItemState,String failReason) {
		this.orderItemId = orderItemId;
		this.orderItemState = orderItemState;
		this.stateModifyTime = new Date();
	}
	/**
	 * 更新单个子订单状态
	 */
	public OrderItemDO(Long orderItemId, Integer orderItemState,String failReason,Integer refundState) {
		this.orderItemId = orderItemId;
		this.orderItemState = orderItemState;
		this.stateModifyTime = new Date();
	}
	/**
	 * 更新单个子订单状态
	 */
	public OrderItemDO(Long orderItemId, Integer orderItemState) {
		this.orderItemId = orderItemId;
		this.orderItemState = orderItemState;
		this.stateModifyTime = new Date();
	}
	

	public static OrderItemDO confirmTime(Long orderItemId, Integer confirmTime){
		OrderItemDO orderItemDO = new OrderItemDO();
		orderItemDO.setOrderItemId(orderItemId);
		orderItemDO.setConfirmTime(confirmTime);
		return orderItemDO;
	}
	
	/**
	 * 更新同一个主订单下所有子订单状态
	 */
	
	public static OrderItemDO upAllOrderItemDO(Integer orderItemState, Long orderId,String failReason) {
		OrderItemDO orderItemDO = new OrderItemDO();
		orderItemDO.setOrderId(orderId);
		orderItemDO.setOrderItemState(orderItemState);
		return orderItemDO;
	}
	
	
	/**
	 * 更新同一个主订单下所有子订单状态
	 */
	public OrderItemDO(Integer orderItemState, Long orderId,String failReason,Integer refundState) {
		this.orderId = orderId;
		this.orderItemState = orderItemState;
		this.stateModifyTime = new Date();
		this.refundState = refundState;
	}
	
	/**
	 * 更新同一个主订单下所有子订单状态
	 */
	public OrderItemDO(Integer orderItemState, Long orderId) {
		this.orderId = orderId;
		this.orderItemState = orderItemState;
		this.stateModifyTime = new Date();
	}
	/**
	 * 卖家发货
	 */
	public static OrderItemDO upConfirmTimeOrderItemDO(Integer orderItemState, Long orderId, Integer confirmTime) {
		OrderItemDO orderItem = new OrderItemDO();
		orderItem.setOrderId(orderId);
		orderItem.setOrderItemState(orderItemState);
		orderItem.setConfirmTime(confirmTime);
		return orderItem;
	}

	

	/**
	 * 更新单个子订单退款状态
	 */
	public static OrderItemDO createRefundStateOrderItemDO(int refundState, Long orderItemId) {
		OrderItemDO orderItem = new OrderItemDO();
		orderItem.setOrderItemId(orderItemId);
		orderItem.setRefundState(refundState);
		return orderItem;
	}
	
	/**
	 * 更新单个子订单退款状态
	 */
	public static OrderItemDO createRefundPriceOrderItemDO(int refundState, Long refundPrice, Long orderItemId) {
		OrderItemDO orderItem = new OrderItemDO();
		orderItem.setOrderItemId(orderItemId);
		orderItem.setRefundState(refundState);
		orderItem.setRefundPrice(refundPrice);
		return orderItem;
	}
	
	private static Map<Integer, String> refundStateMap = new HashMap<Integer, String>();
	static {
		refundStateMap.put(RefundDO.STATUS_CLOSED, "退款关闭");
		refundStateMap.put(RefundDO.STATUS_CS_PROCESS, "客服仲裁");
		refundStateMap.put(RefundDO.STATUS_SELLER_REFUSE_BUYER, "卖家拒绝退款");
		refundStateMap.put(RefundDO.STATUS_SUCCESS, "退款成功");
		refundStateMap.put(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS, "等待买家退还商品");
//		refundStateMap.put(RefundDO.STATUS_WAIT_SELLER_AGREE, "退款申请中");
		refundStateMap.put(RefundDO.STATUS_WAIT_SELLER_AGREE, "等待卖家处理");
		refundStateMap.put(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS, "等待卖家确认收货");
		refundStateMap.put(RefundDO.STATUS_REFUND_FAIL, "退款失败");
		refundStateMap.put(RefundDO.STATUS_REFUND_PROTOCAL_AGREE, "退款协议达成");
		refundStateMap.put(REFUND_ITEM_STATE_1, "");
	}

	/**
	 * <p>
	 * 取得订单状态显示文字信息
	 * </p>
	 */
	public String getRefundStateDisplay() {
		return refundStateMap.get(this.refundState);
	}

	public String getRefundStateDisplay_1(int refundState) {
		return refundStateMap.get(refundState);
	}

	/**
	 * <p>
	 * 是否可以申请退款
	 * </p
	 */
	public boolean isRefund() {
		boolean flag = this.orderItemState.compareTo(ORDER_ITEM_STATE_2) == 0
				|| this.orderItemState.compareTo(ORDER_ITEM_STATE_3) == 0;
		if (this.refundState.compareTo(REFUND_ITEM_STATE_1) == 0 && flag)
			return true;
		return false;
	}
	
	/**
	 * 是否使用商品优惠
	 * 1.商品优惠返回2
	 * 2.商品无优惠返回0
	 * 3.实际成交价大于商品价格返回2（基本不可能）
	 * @return
	 */
	public Integer isCoupon(){
		if(orderItemPrice.compareTo(originalPrice)>0)
			return 1;
		else if(orderItemPrice.compareTo(originalPrice)< 0){
			return 2;
		}
		return 0;
	}
	
	/**
	 * <p>
	 * 取得订单状态显示文字信息
	 * </p>
	 */
	public String getOrderItemStateDisplay() {
		return OrderStateEnum.getValueByType(this.orderItemState).getDescription();
	}

	public String getOrderItemStateDisplay_1(int orderItemState) {
		return OrderStateEnum.getValueByType(orderItemState).getDescription();
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getOrderItemAttributes() {
		return orderItemAttributes;
	}

	public void setOrderItemAttributes(String orderItemAttributes) {
		this.orderItemAttributes = orderItemAttributes;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Long getOrderItemPrice() {
		return orderItemPrice;
	}
	
	public String getOrderItemPriceByYuan() {
		return orderItemPriceByYuan;
	}

	public void setOrderItemPriceByYuan(String orderItemPriceByYuan) {
		this.orderItemPriceByYuan = orderItemPriceByYuan;
	}

	public String getOrderItemTotalPriceByYuan() {
		return MoneyUtil.getCentToDollar(this.orderItemPrice*this.buyNum); 
	}

	public void setOrderItemPrice(Long orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
		this.setOrderItemPriceByYuan(MoneyUtil.getCentToDollar(this.orderItemPrice));
	}

	public Long getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Long refundPrice) {
		this.refundPrice = refundPrice;
		if(refundPrice != null)
			this.setRefundPriceByYuan(MoneyUtil.getCentToDollar(this.refundPrice));
	}

	public String getItemPicUrl() {
		return itemPicUrl;
	}

	public void setItemPicUrl(String itemPicUrl) {
		this.itemPicUrl = itemPicUrl;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSnapId() {
		return snapId;
	}

	public void setSnapId(Long snapId) {
		this.snapId = snapId;
	}

	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getRefundState() {
		return refundState;
	}

	public void setRefundState(Integer refundState) {
		this.refundState = refundState;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public String getItemSkuAttributes() {
		return itemSkuAttributes;
	}

	public void setItemSkuAttributes(String itemSkuAttributes) {
		this.itemSkuAttributes = itemSkuAttributes;
	}

	public Integer getOrderItemState() {
		return orderItemState;
	}

	public void setOrderItemState(Integer orderItemState) {
		this.orderItemState = orderItemState;
	}

	public Integer getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Long getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Long originalPrice) {
		this.originalPrice = originalPrice;
		this.setOriginalPriceByYuan(MoneyUtil.getCentToDollar(originalPrice));
	}
	
	public String getOriginalPriceByYuan() {
		return originalPriceByYuan;
	}

	public void setOriginalPriceByYuan(String originalPriceByYuan) {
		this.originalPriceByYuan = originalPriceByYuan;
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

	public Integer getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Integer confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Long getSellerMarginPrice() {
		return sellerMarginPrice;
	}

	public void setSellerMarginPrice(Long sellerMarginPrice) {
		this.sellerMarginPrice = sellerMarginPrice;
		if(this.sellerMarginPrice != null)
			this.setSellerMarginPriceByYuan(MoneyUtil.getCentToDollar(this.sellerMarginPrice));
		else 
			this.setSellerMarginPriceByYuan("0.0");
		
	}

	public String getSellerMarginPriceByYuan() {
		return sellerMarginPriceByYuan;
	}

	public void setSellerMarginPriceByYuan(String sellerMarginPriceByYuan) {
		this.sellerMarginPriceByYuan = sellerMarginPriceByYuan;
	}

	public String getSellerMarginTotalPriceByYuan() {
		if(sellerMarginPrice != null)
			return MoneyUtil.getCentToDollar(this.orderItemPrice*this.buyNum+this.sellerMarginPrice); 
		return MoneyUtil.getCentToDollar(this.orderItemPrice*this.buyNum); 
	}

	public void setSellerMarginTotalPriceByYuan(String sellerMarginTotalPriceByYuan) {
		this.sellerMarginTotalPriceByYuan = sellerMarginTotalPriceByYuan;
	}

	
	public Long getAnonymousBuy() {
		return anonymousBuy;
	}

	public void setAnonymousBuy(Long anonymousBuy) {
		this.anonymousBuy = anonymousBuy;
	}

	public String getChannelId() {
		return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public String getRefundPriceByYuan() {
		return refundPriceByYuan;
	}


	public void setRefundPriceByYuan(String refundPriceByYuan) {
		this.refundPriceByYuan = refundPriceByYuan;
	}


	public Long getDealAmount() {
		return dealAmount;
	}


	public void setDealAmount(Long dealAmount) {
		this.dealAmount = dealAmount;
	}


	public Long getDealRates() {
		return dealRates;
	}


	public void setDealRates(Long dealRates) {
		this.dealRates = dealRates;
	}


	public Long getDealShopRates() {
		return dealShopRates;
	}


	public Long getTotalAmount() {
		return totalAmount;
	}

	public String getTotalAmountByYuan(){
		return totalAmountByYuan; 
	}
	
	public String getTotalAmountByYuanModifyPrice(){
		if(this.sellerMarginPrice != null){
			return MoneyUtil.getCentToDollar(totalAmount - sellerMarginPrice);
		}
		return MoneyUtil.getCentToDollar(totalAmount); 
	}
	
	//取得优惠金额,负数表示优惠，正数表示加价
	public String getDiscountAmountByYuan(){
		return MoneyUtil.getCentToDollar(totalAmount - originalPrice*buyNum);
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
		if(this.totalAmount != null)
			totalAmountByYuan = MoneyUtil.getCentToDollar(this.totalAmount);
		else 
			totalAmountByYuan = "0.0";
	}


	public void setDealShopRates(Long dealShopRates) {
		this.dealShopRates = dealShopRates;
	}

	public String getItemTotalPriceByYuan() {
		if(this.sellerMarginPrice != null){
			return MoneyUtil.getCentToDollar(this.orderItemPrice*this.buyNum + this.sellerMarginPrice);
		}
		return MoneyUtil.getCentToDollar(this.orderItemPrice*this.buyNum); 
	}


	@Override
	public String toString() {
		return "OrderItemDO [anonymousBuy=" + anonymousBuy + ", bussinessType="
				+ bussinessType + ", buyNum=" + buyNum + ", buyerId=" + buyerId
				+ ", buyerNick=" + buyerNick + ", channelId=" + channelId
				+ ", confirmTime=" + confirmTime + ", dealAmount=" + dealAmount
				+ ", dealRates=" + dealRates + ", dealShopRates="
				+ dealShopRates + ", failReason=" + failReason + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + ", itemId="
				+ itemId + ", itemPicUrl=" + itemPicUrl
				+ ", itemSkuAttributes=" + itemSkuAttributes + ", itemSkuId="
				+ itemSkuId + ", itemTitle=" + itemTitle + ", orderId="
				+ orderId + ", orderItemAttributes=" + orderItemAttributes
				+ ", orderItemId=" + orderItemId + ", orderItemPrice="
				+ orderItemPrice + ", orderItemPriceByYuan="
				+ orderItemPriceByYuan + ", orderItemState=" + orderItemState
				+ ", originalPrice=" + originalPrice + ", originalPriceByYuan="
				+ originalPriceByYuan + ", refundPrice=" + refundPrice
				+ ", refundPriceByYuan=" + refundPriceByYuan + ", refundState="
				+ refundState + ", sellerId=" + sellerId
				+ ", sellerMarginPrice=" + sellerMarginPrice
				+ ", sellerMarginPriceByYuan=" + sellerMarginPriceByYuan
				+ ", sellerMarginTotalPriceByYuan="
				+ sellerMarginTotalPriceByYuan + ", sellerNick=" + sellerNick
				+ ", snapId=" + snapId + ", stateModifyTime=" + stateModifyTime
				+ "]";
	}
	
	
}
