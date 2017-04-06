package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderItemAttributesEnum;
import com.yuwang.pinju.Constant.VouchPayAttributesEnum;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.dao.OrderUpDdateDAO;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.helper.OrderSplitAssistantManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.servicefee.manager.ServiceFeeManager;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.trade.manager.impl.PaySequenceGenerator;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.domain.distribute.ItemInfo;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;


/**
 * Created on 2011-9-19
 * 
 * @see <p>
 *      Discription: 订单分账实现
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderSplitAssistantManagerImpl extends BaseManager implements
		OrderSplitAssistantManager {

	/**
	 * 平台费率管理
	 */
	private ServiceFeeManager serviceFeeManager;

	/**
	 * 分账会员管理
	 */
	private DistributorManager distributorManager;
	
	/**
	 * 会员管理
	 */
	private MemberManager memberManager;

	/**
	 * 平台分账收款账户
	 */
	private final String PLATFORMACCOUNT = PinjuConstant.TENPAY_PAY_PINJU_ACCOUNT;

	/**
	 * 担保支付查询管理
	 */
	private VouchQueryManager vouchQueryManager;
	/**
	 * 担保交易更新管理
	 */
	private VouchCreateManager vouchCreateManager;

	/**
	 * 订单查询管理
	 */
	private OrderQueryManager orderQueryManager;

	/**
	 * 订单管理
	 */
	private OrderUpDdateDAO orderUpDdateDAO;

	/**
	 * 标记描述分隔;
	 */
	public final static String SPLITKEY = VouchPayConstant.SPLITKEY;

	/**
	 * 标记描述key:value分隔
	 */
	public final static String SPLITATTRIBUTES = VouchPayConstant.SPLITATTRIBUTES;

	/**
	 * 分割同一个key的多个值,
	 */
	public final static String SPLITVALUES = VouchPayConstant.SPLITVALUES;

	/**
	 * 担保交易商户号
	 */
	public final static String PINJU_VOUCH_PARTNER = PinjuConstant.TENPAY_PAY_PARTNER;

	/**
	 * 序列号生成
	 */
	public PaySequenceGenerator paySequenceGenerator;

	/**
	 * 退款管理
	 */
	public RefundManager refundManager;
	
	/**
	 * 支付序号SEQ
	 */
	public final static String SEQ_NAME = "SEQ_ORDER_PAY_ID";

	@Override
	public void orderSplitProcess(String orderPayId) throws ManagerException {
		VouchPayDO vouchPayDO = vouchQueryManager
				.selectOrderPayByOrderPayId(orderPayId);
		OrderDO orderDO = orderQueryManager.getOrderDOById(vouchPayDO
				.getOrderId());
		List<OrderItemDO> list = orderQueryManager
				.queryOrderItemList(vouchPayDO.getOrderId());
		orderSplit(orderDO, list);
	}

	/**
	 * 
	 * Created on 2011-9-29
	 * <p>
	 * Discription: [生成支付表对象,订单价格不排除被修改这里不包含分账]
	 * </p>
	 * 
	 * @param orderDO
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public VouchPayDO creationVouchPayDO(OrderDO orderDO)
			throws ManagerException {
		String sellerAccount = querySellerAccount(orderDO.getSellerId());
		return VouchPayDO.createVouchPayDO(getOrderPaySeq(orderDO
				.getStateModifyTime()), orderDO.getBuyerId(), orderDO
				.getSellerId(), sellerAccount, orderDO.getStateModifyTime(),
				orderDO.getPriceAmount());
	}

	/**
	 * 
	 * Created on 2011-9-19
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param vouchPayPayAttributes
	 * @param orderItemDO
	 *            分账处理
	 * @return Attributes 格式: key:id,amount key:分赃类型 id:分账id amount：金额
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void orderSplit(OrderDO orderDO, List<OrderItemDO> list)
			throws ManagerException {
		if (orderDO == null || orderDO.getOrderId().compareTo(0l) <= 0) {
			log
					.error("Event=[OrderSplitAssistantManagerImpl#orderSplit]分账处理OrderDO参数为null");
			throw new ManagerException();
		}
		if (list == null || list.isEmpty()) {
			log
					.error("Event=[OrderSplitAssistantManagerImpl#orderItemSplitProcess]分账处理无子订单记录!主订单OrderDO-->"
							.concat(orderDO.toString()));
			throw new ManagerException();
		}
		long dealAmount = 0L;
		long sellerAmount = 0L;
		long channelAmount = 0L;
		long orderItemplatformAmount = 0L;
		String vouchPayAttributes = new String();
		for (OrderItemDO orderItemDO : list) {
			// 平台分账金额
			orderItemplatformAmount = platformPayAttributes(orderItemDO);
			// 更新子订单平台分账金额
			try {
				orderItemDO.setDealAmount(orderItemplatformAmount);
				orderUpDdateDAO.updateOrderItemDealAmount(orderItemDO);
			} catch (DaoException e) {
				log.error("Event=[OrderSplitAssistantManagerImpl#orderSplit exception 更新平台分账金额出错");
				log.error("exception parms".concat(orderItemDO.toString()));
			}
			try{
				if (StringUtil.isNotEmpty(orderItemDO.getOrderItemAttributes())) {
					for (String key : orderItemDO.getOrderItemAttributes().split(
						SPLITKEY)) {
						// 分销分账
						if (StringUtil.contains(key,
							OrderItemAttributesEnum.SHOP_DISTRIBUTION
									.getFeatureName())) {
							Map<String, String> map = vouchChannelPayAttributes(orderItemplatformAmount, vouchPayAttributes,key, orderItemDO);
							vouchPayAttributes = map.get("vouchPayAttributes");
							channelAmount += Long.valueOf(map.get("channelAmount"));
						}
					}
				}
			}catch(Exception e){
				log.error("Event=[OrderSplitAssistantManagerImpl#vouchChannelPayAttributes exception 分销分账账户获取失败不影响正常分账信息处理",e);
				log.error("Event=[OrderSplitAssistantManagerImpl#vouchChannelPayAttributes exception parms".concat(orderItemDO.toString()));
			}
			/**
			 * 处理平台手续费
			 */
			dealAmount += orderItemplatformAmount;
		}
		// 添加平台分账 为0不分账
		if (dealAmount > 0) {
			String platFormValueString = PLATFORMACCOUNT.concat(SPLITVALUES)
					.concat(String.valueOf(dealAmount));
			vouchPayAttributes = OrderUtilMothed.markingAttributes(
					vouchPayAttributes, VouchPayAttributesEnum.PLATFORM_SPLIT
							.getFeatureName(), platFormValueString);
		}
		// 卖家分账
		String sellerAccount = querySellerAccount(orderDO.getSellerId());
		sellerAmount = orderDO.getPriceAmount() - dealAmount - channelAmount;
		String sellerValueString = sellerAccount.concat(SPLITVALUES).concat(
				String.valueOf(sellerAmount));
		vouchPayAttributes = OrderUtilMothed.markingAttributes(
				vouchPayAttributes, VouchPayAttributesEnum.SELLER_SPLIT
						.getFeatureName(), sellerValueString);
		VouchPayDO vouchPayDO = VouchPayDO.upSplitAssistantVouchPayDO(orderDO
				.getOrderId(), orderDO.getPriceAmount(), dealAmount,
				vouchPayAttributes);
		if (!vouchCreateManager.updateOrderPay(vouchPayDO)) {
			log
					.error("Event=[OrderSplitAssistantManagerImpl#orderItemSplitProcess]分账处理失败!"
							.concat(vouchPayDO.toString()));
			throw new ManagerException();
		}
	}

	private String getOrderPaySeq(Date date) {
		String time = DateUtil.formatDate("yyyyMMdd", date);
		return PINJU_VOUCH_PARTNER.concat(time)
				.concat(
						String.format("%010d", paySequenceGenerator.next(
								SEQ_NAME, 10)));
	}

	/**
	 * 
	 * Created on 2011-9-20
	 * <p>
	 * Discription: 获取卖家账户
	 * </p>
	 * 
	 * @param buyerId
	 * @param sellerId
	 * @return [0] buyerAccount;[1] sellerAccount;
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String querySellerAccount(Long sellerId) throws ManagerException {
		// 卖家账户
		String sellerAccount = "Fake sellerAccount";
		if (sellerId != null && StringUtil.isNotEmpty(sellerId.toString())) {
			MemberPaymentDO buyPaymentDO = memberManager
					.findBoundMemberPayment(new MemberPaymentDO(Long
							.valueOf(sellerId),
							MemberPaymentDO.INSTITUTION_TENPAY));
			if (buyPaymentDO != null
					&& StringUtil.isNotEmpty(buyPaymentDO.getAccountNO())) {
				sellerAccount = buyPaymentDO.getAccountNO();
			}
		}
		return sellerAccount;
	}

	/**
	 * 
	 * Created on 2011-9-16
	 * <p>
	 * Discription: [支付表分销分账标记]
	 * </p>
	 * 
	 * @param vouchPayPayAttributes
	 * @param key
	 * @param value
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @throws NumberFormatException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Map<String, String> vouchChannelPayAttributes(
			long orderItemplatformAmount, String vouchPayAttributes,
			String key, OrderItemDO orderItemDO) throws NumberFormatException,
			ManagerException {
		Map<String, String> map = new HashMap<String, String>();

		String channelId = key.split(SPLITATTRIBUTES)[1];

		// 取下单时的分销费率
		OrderChannelDO orderChannelDO = orderQueryManager
				.queryOrderChannelByOrderItemId(orderItemDO.getOrderItemId());
		long refundAmount = 0;
		if(orderChannelDO != null ){
			refundAmount = NumberUtil.getLong(orderItemDO.getRefundPrice()) ;
		}
		// (实际成交价格*购买数量 -退款金额 - 平台分账)*分销费率
		Long channelAmount = orderItemDO.getTotalAmount()
				- refundAmount
				- orderItemplatformAmount;
		channelAmount = Math.round(channelAmount.doubleValue() * orderChannelDO.getReward().doubleValue()/OrderConstant.ITEM_CHANNEL_RATE);
		orderChannelDO.setReAmount(channelAmount);
		try {
			orderUpDdateDAO.upChannelAmount(orderChannelDO);
		} catch (DaoException e) {
			log
					.error("Event=[OrderSplitAssistantManagerImpl#vouchChannelPayAttributes] exception 更新分销中间表,分销金额出错",e);
			log.error("exception parms".concat(orderChannelDO.toString()));
		}
		/**
		 * 分销分账金额为0的话不处理
		 */
		if (channelAmount.compareTo(0l) <= 0) {
			log.error("exception item channel amount is 0".concat(orderChannelDO
					.toString()));
			log.error("exception parms".concat(orderChannelDO.toString()));
			map.put("vouchPayAttributes", vouchPayAttributes);
			map.put("channelAmount", channelAmount.toString());
			return map;
		}

		// 非空
		if (StringUtil.isNotEmpty(vouchPayAttributes)) {
			vouchPayAttributes = vouchPayAttributes.concat(SPLITKEY);
		}
		// 标记
		vouchPayAttributes = vouchPayAttributes.concat(
				VouchPayAttributesEnum.CHANNEL_SPLIT.getFeatureName()).concat(
				SPLITATTRIBUTES);
		// 分销账户
		String channelAccount = "Fake channelAccounts";
		if (StringUtil.isNotEmpty(channelId)) {
			ItemInfo itemInfo = distributorManager.findItemInfoByCondition(channelId,orderItemDO.getItemId());
			MemberPaymentDO channelMember = memberManager
					.findBoundMemberPayment(new MemberPaymentDO(itemInfo.getMemberId(),
							MemberPaymentDO.INSTITUTION_TENPAY));
			if (channelMember != null
					&& StringUtil.isNotEmpty(channelMember.getAccountNO())) {
				channelAccount = channelMember.getAccountNO();
				vouchPayAttributes = vouchPayAttributes.concat(channelAccount)
						.concat(SPLITVALUES).concat(channelAmount.toString());

			} else {
				log
						.error("Event=[OrderSplitAssistantManagerImpl#vouchChannelPayAttributes] exception 分销商账户无绑定财富通!");
				log.error("exception 分销商会员编号---->".concat(String
						.valueOf(channelId)));
				log.error("exception 当前分销订单参数--->".concat(orderItemDO
						.toString()));
			}
		} else {
			channelAmount = 0l;
		}

		map.put("vouchPayAttributes", vouchPayAttributes);
		map.put("channelAmount", channelAmount.toString());
		return map;
	}

	/**
	 * 
	 * Created on 2011-9-20
	 * <p>
	 * Discription: 支付后获取子订单平台分账金额
	 * </p>
	 * 
	 * @param vouchPayAttributes
	 * @param key
	 * @param orderItemDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Long platformPayAttributes(OrderItemDO orderItemDO)
			throws ManagerException {
		// 商品成交价格 * 件数 -退款金额
		// 平台费率基数
		long platformAmount = 0l;
		long refundAmount = 0L;
		RefundDO refund = refundManager.loadRefundByOrderItem(orderItemDO.getOrderItemId());
		//退款金额
		if(refund !=null && refund.getApplySum()!=null && refund.getApplySum()>0 && refund.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE)==0){
			refundAmount = refund.getApplySum();
		}
		platformAmount = orderItemDO.getTotalAmount() -refundAmount;
		// 获取平台分账费率(按下单时的费率计算平台手续费)
		return serviceFeeManager.calcServiceFee(platformAmount, orderItemDO
				.getDealRates(), orderItemDO.getDealShopRates());

	}

	@Override
	public TenSubAccountDO getTenSubAccountDO(Long orderId)
			throws ManagerException {
		VouchPayDO vouchPayDO = vouchQueryManager
		.selectOrderPayByOrderId(orderId);
		this.orderSplitProcess(vouchPayDO.getOrderPayId());
		vouchPayDO = vouchQueryManager
				.selectOrderPayByOrderId(orderId);
		if (StringUtil.isEmpty(vouchPayDO.getPayAttributes())) {
			return null;
		}
		// 卖家
		Long sellerFee = 0L;
		// 平台
		Long platFormFee = 0L;
		// 卖家账户
		String sellerAccount = "";
		Map<String, Long> map = new HashMap<String, Long>();
		List<String> channelAccountList = new ArrayList<String>();
		for (String splitValue : vouchPayDO.getPayAttributes().split(SPLITKEY)) {
			String key = StringUtil.split(splitValue, SPLITATTRIBUTES)[0];
			String value = StringUtil.split(splitValue, SPLITATTRIBUTES)[1];
			if (StringUtil.contains(key, VouchPayAttributesEnum.SELLER_SPLIT
					.getFeatureName())) {
				sellerFee = Long
						.valueOf(StringUtil.split(value, SPLITVALUES)[1]);
				sellerAccount = StringUtil.split(value, SPLITVALUES)[0];
			}
			if (StringUtil.contains(key, VouchPayAttributesEnum.PLATFORM_SPLIT
					.getFeatureName())) {
				platFormFee = Long
						.valueOf(StringUtil.split(value, SPLITVALUES)[1]);
			}

			if (StringUtil.contains(key, VouchPayAttributesEnum.CHANNEL_SPLIT
					.getFeatureName())) {
				String channelAccount = StringUtil.split(value, SPLITVALUES)[0];
				if (!channelAccountList.contains(channelAccount)) {
					channelAccountList.add(channelAccount);
					map.put(channelAccount, Long.valueOf(StringUtil.split(
							value, SPLITVALUES)[1]));
				} else {
					Long tempAmount = map.get(channelAccount);
					map.put(channelAccount, Long.valueOf(StringUtil.split(
							value, SPLITVALUES)[1])
							+ tempAmount);
				}
			}

		}
		return TenSubAccountDO.createTenSubAccountDO(vouchPayDO.getOutPayId(),
				vouchPayDO.getOrderPayId(), vouchPayDO.getOrderAmount(),
				sellerAccount, sellerFee, platFormFee, map);
	}

	public void setServiceFeeManager(ServiceFeeManager serviceFeeManager) {
		this.serviceFeeManager = serviceFeeManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}

	public void setPaySequenceGenerator(
			PaySequenceGenerator paySequenceGenerator) {
		this.paySequenceGenerator = paySequenceGenerator;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}

	public void setOrderUpDdateDAO(OrderUpDdateDAO orderUpDdateDAO) {
		this.orderUpDdateDAO = orderUpDdateDAO;
	}

	public void setDistributorManager(DistributorManager distributorManager) {
		this.distributorManager = distributorManager;
	}

	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}
	
	

}
