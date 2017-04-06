package com.yuwang.pinju.web.module.refund.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
import com.yuwang.pinju.core.refund.ao.ItemPropertyAO;
import com.yuwang.pinju.core.refund.ao.MaxRefundMoneyAO;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundBaseAO;
import com.yuwang.pinju.core.refund.ao.RefundCheckAO;
import com.yuwang.pinju.core.refund.ao.RefundManageAO;
import com.yuwang.pinju.core.refund.ao.TradeRefundAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.RefundMessage;
import com.yuwang.pinju.web.message.RightsMessageName;

/**
 * 申请退款信息
 * @author shihongbo
 * @date 2011-6-28
 * @version 1.0
 */
public class RefundApplyAction  extends RefundBaseAction implements BuyerRefund{
	private static final long serialVersionUID = -6362793196408487327L;
	
	private RefundApplyAO refundApplyAO;
	private TradeRefundAO tradeRefundAO;
	private ItemPropertyAO itemPropertyAO;
	private RefundCheckAO refundCheckAO;
	private RefundManageAO refundManageAO;
	private OrderCouponAO orderCouponAO;
	private MaxRefundMoneyAO maxRefundMoneyAO;

	List<ItemPropertyVO> itemPropertyList;

	private OrderDO order;
	private OrderItemDO orderItem;
	private ItemDO itemDO;
	private OrderLogisticsDO orderLogisticsDO;

	private Long orderItemId;

	private File[] voucherPic;
    private String[] voucherPicFileName;
    private String[] voucherPicContentType;

	private  RefundDO refundDO;
	private String applySum;
	private Long refundId;

	private String couponMoneyByYuan;
	private String maxValue;
	private String  needPayMax;
	private RefundBaseAO refundBaseAO;
	private Map<String,String> priceMap = new HashMap<String,String>();
	
	public Map<String, String> getPriceMap() {
		return priceMap;
	}
	
	public String getNeedPayMax() {
		return needPayMax;
	}

	/**
	 * 申请退款
	 */
	public String execute() {
		if (log.isInfoEnabled()) {
			log.info("Event=[RefundApplyAction.execute() load refund]");
		}
		String isValid = validRefund();
		if(SUCCESS.compareTo(isValid) !=0){
			return isValid;
		}
		if (refundDO != null && refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) == 0){
			return "waitSellerAgree";
		}
		return loadRefundData();
	}
	
	/**
	 * <p>Description: 修改退款申请</p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-16
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String updateRefundApply(){
		if (log.isInfoEnabled()) {
			log.info("Event=[RefundApplyAction.updateRefundApply() load refundApply]");
		}
		String isValid = validRefund();
		if(SUCCESS.compareTo(isValid) !=0){
			return isValid;
		}
		return loadRefundData();
	}
	/**
	 * <p>Description:  保存和更新退款申请</p>
	 * @Date：2011-8-16
	 * @return  SUCCESS
	 * @author:[ShiHongBo]
	 * @version 1.3
	 * @update:[日期2011-10-20] [MaYuanChao]
	 */
	public String saveOrUpdateApply() {
		try {
			this.clearActionErrors();
			//验证是否登录
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if(!loginInfo.isLogin()) {
				this.validRefund();
				this.loadRefundData();
				addActionError(Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
			
			// 验证申请金额是否超过最大值
			OrderItemDO _orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
			Long maxApplySum = maxRefundMoneyAO.getMaxRefundMoney(_orderItem.getOrderId(), orderItemId);
			
			if(maxApplySum.compareTo(MoneyUtil.getDollarToCent(applySum)) < 0){
				this.validRefund();
				this.loadRefundData();
				addActionError(RefundMessage.getMessage(APPLY_MAX_SUM,refundDO.getApplySum(),maxApplySum));
				return ERROR;
			}
			
			// 验证退款状态是否是申请中
			RefundDO _refundDO = refundApplyAO.loadRefundByOrderItem(orderItemId);
			if(_refundDO != null && _refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) != 0){
				buyerTipMessage(_refundDO,REFUND_OPERATE_UPDATE);
				return INPUT;
			}
			//如果在不同页面申请的时候
			if(_refundDO != null && refundDO.getId() == null){
				this.errorMessage = RefundMessage.getMessage(REFUND_OPERATE_SUBMIT_TIMEOUT);
				return INVALID_BUYER_REFUND;
			}
			
			// 验证上传的图片是否合法
			if(!verifyPics(loginInfo)){
				return ERROR;
			}
			
			boolean isNew = true;
			orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
			order = refundApplyAO.getOrderInfo(orderItem.getOrderId());
			refundDO.setSellerId(order.getSellerId());
			refundDO.setSellerNick(order.getSellerNick());
			refundDO.setBuyerId(order.getBuyerId());
			refundDO.setBuyerNick(order.getBuyerNick());
				
			//如果已经申请了退款协议,申请时候和创建该申请的时间就不能修改了
			if(refundDO.getId() != null){
				isNew = false;
				refundDO.setGmtModified(new Date());
				refundDO.setStateModified(new Date());
			}else{
				isNew = _refundDO != null ? false : true;
				refundDO.setApplyDate(new Date());
				refundDO.setGmtModified(new Date());
				refundDO.setStateModified(new Date());
				refundDO.setGmtCreate(new Date());
			}
			refundDO.setOrderId(order.getOrderId());
			refundDO.setOrderItemId(orderItem.getOrderItemId());
			refundDO.setTradeSum(order.getPriceAmount());
			refundDO.setApplySum(MoneyUtil.getDollarToCent(applySum));
			refundDO.setIsCustProcessed(RefundDO.CUST_PROCESS_NO);
			
			// 保存或者更新退款申请
			refundId = refundApplyAO.saveOrUpdateRefund(refundDO);  
			if(isNew){
				RefundDO _refund = refundApplyAO.loadRefundApplyInfo(refundId);
				refundManageAO.startRefund(_refund);
			}
		} catch (Exception e) {
			// TODO: handle exception 
			log.error("RefundApplyAction.saveApply() occurs exception",e);
			this.errorMessage = RefundMessage.getMessage(OPERATE_FAILED);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}

	/**
	 * <p>Description: 图片验证和处理</p>
	 * @param loginInfo
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean verifyPics(CookieLoginInfo loginInfo){
		if(voucherPic != null && voucherPic.length > 0) {
			for (File file : voucherPic) {
				if (file.length()/PinjuConstant.FILE_SIZE_K > FILE_MAX_SIZE) {
					this.loadRefundData();
					addActionError(RefundMessage.getMessage(FILE_SIZE_TO_LARGE,FILE_MAX_SIZE));
					return false;
				}
				if (!FileSecurityUtils.isImageValid(file)) {
					this.loadRefundData();
					addActionError(RefundMessage.getMessage(FILE_TYPE_INVALID));
					return false;
				}
			}
			Result result = tradeRefundAO.saveFile(voucherPic, voucherPicFileName, loginInfo.getMemberId(), loginInfo.getMember().getNickname());
			   if (result.isSuccess()) {
				   String[] retFileName = (String[])result.getModel("fileNames");
				   if (retFileName.length==1) {
					   refundDO.setPic1(retFileName[0]);
				   }else if(retFileName.length==2){
					   refundDO.setPic1(retFileName[0]);
					   refundDO.setPic2(retFileName[1]);
				   }else if(retFileName.length==3){
					   refundDO.setPic1(retFileName[0]);
					   refundDO.setPic2(retFileName[1]);
					   refundDO.setPic3(retFileName[2]);
				   }
			   }
		}   
		return true;
	}
	
	/**
	 * <p>Description: 加载退款信息和订单详情</p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-20
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String loadRefundData() {
		try {
			itemDO = refundApplyAO.loadItemByID(orderItem.getItemId());
			orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());

			// 获取商品总价给
			Long orderPriceAmount = order.getPriceAmount() != null ? order.getPriceAmount() : 0L;
			Long postPrice = orderLogisticsDO.getPostPrice() != null ? orderLogisticsDO.getPostPrice() : 0L;
			Long totalPrice = orderPriceAmount - postPrice;
			priceMap.put("totalPrice", MoneyUtil.getCentToDollar(totalPrice));
			
			String realPayPrice = refundBaseAO.getRealPayMentamount(orderItem.getOrderId());
			//实际支付金额为 0
			if(realPayPrice.compareTo("0.00") == 0){
				this.errorMessage = RefundMessage.getMessage(REFUND_DIRECT_ORDER_INVALID);
				return INVALID_BUYER_REFUND;
			}
			
			// 获取实付价格
			priceMap.put("realPayPrice",realPayPrice);

			// 获取优惠价格
			Long sellerMarginPrice = orderItem.getSellerMarginPrice() != null ? orderItem.getSellerMarginPrice() : 0L;
			sellerMarginPrice = (orderItem.getOrderItemPrice() - orderItem.getOriginalPrice())* orderItem.getBuyNum()+ sellerMarginPrice;
			priceMap.put("sellerMarginPrice",MoneyUtil.getCentToDollar(sellerMarginPrice));

			Long skuid = orderItem.getItemSkuId()== null ? 0L:orderItem.getItemSkuId();
			// 如果有skuid, 设置商品sku属性
			if (skuid.compareTo(0L) != 0) {
				SkuDO sku = refundApplyAO.getItemSkuById(skuid);
				itemPropertyList = itemPropertyAO.getItemPropertyBySku(sku);
			}

			maxValue = MoneyUtil.getCentToDollar(
					maxRefundMoneyAO.getMaxRefundMoney(orderItem.getOrderId(), orderItemId));
			
			
			//获取支付给卖家的金额
			long  needPaySellerSum= refundCheckAO.getNeedPaySellerSum(orderItem.getOrderId());
			needPaySellerSum = MoneyUtil.getDollarToCent(priceMap.get("realPayPrice"))-needPaySellerSum;
			needPaySellerSum = needPaySellerSum < 0 ? 0:needPaySellerSum;
			
			needPayMax = MoneyUtil.getCentToDollar(needPaySellerSum);
			
			//获取优惠券优惠金额
			couponMoneyByYuan = orderCouponAO.getCouponMoneyByYuan(orderItem.getOrderId());
			
		} catch (Exception e) {
			log.error("Event=[RefundApplyAction.loadRefundData() occurs exception]##出现错误", e);
			this.errorMessage = RefundMessage.getMessage(OPERATE_FAILED);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}
	
	/**
	 * <p>Description: 验证申请退款的合法性</p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-16
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String validRefund(){
		try {
			if (orderItemId == null){
				orderItemId = getLong("oid");
			}
			if (orderItemId == 0) {
				this.errorMessage = RefundMessage.getMessage(OPERATE_FAILED);
				return INVALID_BUYER_REFUND;
			}
			orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
			if (orderItem == null) {
				this.errorMessage = RefundMessage.getMessage(OPERATE_FAILED);
				return INVALID_BUYER_REFUND;
			}
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			if (orderItem.getBuyerId().compareTo(login.getMemberId()) != 0) {
				this.errorMessage = RefundMessage.getMessage(OPERATE_FAILED);
				return INVALID_BUYER_REFUND;
			}
			refundDO = refundApplyAO.loadRefundByOrderItem(orderItemId);
			if (refundDO != null) {
				if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) != 0) {
					buyerTipMessage(refundDO,REFUND_DEFAULT_MESSAGE);
					return INPUT;
				}
			}
			order = refundApplyAO.getOrderInfo(orderItem.getOrderId());
			//订单不存在
			if (order == null) {
				this.errorMessage = RefundMessage.getMessage(OPERATE_FAILED);
				return INVALID_BUYER_REFUND;
			}
			//订单状态为“等待卖家发货”、“卖家已发货”才可以申请退款
			if (order.getOrderState().compareTo(OrderDO.ORDER_STATE_2) != 0
					&& order.getOrderState().compareTo(OrderDO.ORDER_STATE_3) != 0) {
				return "orderError";
			}
		} catch (Exception e) {
			log.error("Event=[RefundApplyAction.validRefund() occurs exception]##出现错误", e);
			this.errorMessage = RefundMessage.getMessage(OPERATE_FAILED);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}
	
	/**
	 * <p>Description:验证退款申请的最大金额</p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Long loadMaxApplySum(){
		Long _maxValue = new Long(0);
		orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
		orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());
		boolean isLast = refundCheckAO.isLastRefundOrder(orderItem.getOrderId(), refundDO);
		// 如果是最后一个退款申请 最大退款金额 = 商品金额 + 运费
		Long marginPrice = 0L;
		if (orderItem.getSellerMarginPrice() != null) {
			marginPrice = orderItem.getSellerMarginPrice();
		}
		if (isLast) {
			_maxValue = orderItem.getOrderItemPrice()* orderItem.getBuyNum()+ marginPrice + orderLogisticsDO.getPostPrice();
			// 否则等于商品金额
		} else {
			_maxValue = orderItem.getOrderItemPrice()* orderItem.getBuyNum()+ marginPrice;
		}
		return _maxValue;
	}
	

	public String getCouponMoneyByYuan() {
		return couponMoneyByYuan;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public OrderItemDO getOrderItem() {
		return orderItem;
	}

	public List<ItemPropertyVO> getItemPropertyList() {
		return itemPropertyList;
	}

	public OrderDO getOrder() {
		return order;
	}

	public Long getOrderItemId() {
		return getLong("oid");
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	
	public ItemDO getItemDO() {
		return itemDO;
	}

	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
	}

	public File[] getVoucherPic() {
		return voucherPic;
	}

	public void setVoucherPic(File[] voucherPic) {
		this.voucherPic = voucherPic;
	}

	public String[] getVoucherPicFileName() {
		return voucherPicFileName;
	}

	public void setVoucherPicFileName(String[] voucherPicFileName) {
		this.voucherPicFileName = voucherPicFileName;
	}

	public String[] getVoucherPicContentType() {
		return voucherPicContentType;
	}

	public void setVoucherPicContentType(String[] voucherPicContentType) {
		this.voucherPicContentType = voucherPicContentType;
	}

	public void setTradeRefundAO(TradeRefundAO tradeRefundAO) {
		this.tradeRefundAO = tradeRefundAO;
	}

	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}
	
	public void setItemPropertyAO(ItemPropertyAO itemPropertyAO) {
		this.itemPropertyAO = itemPropertyAO;
	}

	public void setItemPropertyList(List<ItemPropertyVO> itemPropertyList) {
		this.itemPropertyList = itemPropertyList;
	}

	public void setOrder(OrderDO order) {
		this.order = order;
	}

	public void setOrderItem(OrderItemDO orderItem) {
		this.orderItem = orderItem;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public void setRefundBaseAO(RefundBaseAO refundBaseAO) {
		this.refundBaseAO = refundBaseAO;
	}

	public RefundDO getRefundDO() {
		return refundDO;
	}

	public void setRefundDO(RefundDO refundDO) {
		this.refundDO = refundDO;
	}

	@Override
	public Long getRefundId() {
		return refundId;
	}

	public String getApplySum() {
		return applySum;
	}

	public void setApplySum(String applySum) {
		this.applySum = applySum;
	}

	public void setOrderLogisticsDO(OrderLogisticsDO orderLogisticsDO) {
		this.orderLogisticsDO = orderLogisticsDO;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	public void setPriceMap(Map<String, String> priceMap) {
		this.priceMap = priceMap;
	}

	public void setRefundCheckAO(RefundCheckAO refundCheckAO) {
		this.refundCheckAO = refundCheckAO;
	}

	public void setRefundManageAO(RefundManageAO refundManageAO) {
		this.refundManageAO = refundManageAO;
	}
	
	public void setOrderCouponAO(OrderCouponAO orderCouponAO) {
		this.orderCouponAO = orderCouponAO;
	}

	public void setMaxRefundMoneyAO(MaxRefundMoneyAO maxRefundMoneyAO) {
		this.maxRefundMoneyAO = maxRefundMoneyAO;
	}

}
