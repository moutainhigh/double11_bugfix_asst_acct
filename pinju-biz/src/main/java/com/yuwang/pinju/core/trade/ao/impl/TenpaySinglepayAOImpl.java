package com.yuwang.pinju.core.trade.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.yuwang.pinju.core.util.DateUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.constant.system.PinjuConstant;

import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.trade.ao.TenpaySinglepayAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.trade.TenpaySinglepayParamDO;

public class TenpaySinglepayAOImpl extends TenPayAbstractBaseAO implements TenpaySinglepayAO{

	private OrderQueryManager orderQueryManager;
	
	public TenpaySinglepayParamDO createTenpaySinglepayParamDO(String date, String desc,
			String transactionId, String spBillno,
			Long totalFee, String attach,
			String spbillCreateIp, 
			Long buyerId, Long sellerId, String sellerNick){
		
		TenpaySinglepayParamDO tenpaySinglepayParamDO = new TenpaySinglepayParamDO(date, desc, 
				transactionId, spBillno, totalFee,  attach, spbillCreateIp, 
				buyerId, sellerId);
		
		tenpaySinglepayParamDO.setVer("1");
		tenpaySinglepayParamDO.setCmdno(1);
		tenpaySinglepayParamDO.setBankType(0);
		tenpaySinglepayParamDO.setFeeType(1);
		tenpaySinglepayParamDO.setReturnUrl(PinjuConstant.TENPAY_MERGE_PAY_NOTIFYURL);
		tenpaySinglepayParamDO.setCs(PinjuConstant.TENPAY_DIRECTPAY_INPUT_CHARSET);
		tenpaySinglepayParamDO.setBargainorId(PinjuConstant.TENPAY_PAY_PARTNER);
		tenpaySinglepayParamDO.setSellerNick(sellerNick);

		return tenpaySinglepayParamDO;
	}
	
	/**
	 * <p>
	 * 创建单笔支付参数
	 * </p>
	 * 
	 * @param orderId
	 * @return 
	 */
	public Result createSinglepayParam(Long orderId){
		Result result = new ResultSupport();
		try{
			String parametersUrl = parametersToString(createParameterMap(orderId));
			
			String postUrl = PinjuConstant.TENPAY_PAY_URL + "?" + parametersUrl;
			
			result.setModel("postUrl", postUrl);
		}catch (Exception e) {
			log.error("Event=[TenpaySinglepayAOImpl#createSinglepayParam]:", e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private String getDesc(Long orderId){
		try{
			List<OrderItemDO> queryOrderItemList = orderQueryManager.queryOrderItemList(orderId);
			if(queryOrderItemList == null || queryOrderItemList.size() == 0)
				return "";
			
			OrderItemDO OrderItemDO = queryOrderItemList.get(0);
			return OrderItemDO.getItemTitle();
			
		}catch (Exception e) {
			log.error("Event=[TenpaySinglepayAOImpl#getDesc]:", e);
		}
		return null;
	}
	
	private SortedMap<String, String> createParameterMap(Long orderId) throws ManagerException {
		OrderDO orderDO = orderQueryManager.getOrderDOById(orderId);
		
		
		SortedMap<String, String> paramMap = new TreeMap<String, String>();
		
		//版本号
		paramMap.put("ver", PinjuConstant.TENPAY_SINGLE_PAY_VER);
		
		//业务代码
		paramMap.put("cmdno", PinjuConstant.TENPAY_SINGLE_PAY_CMDNO);
		
		//商户日期
		paramMap.put("date", DateUtil.formatDateTime("yyyyMMdd", new Date()));
		
		//银行类型
		paramMap.put("bank_type", PinjuConstant.TENPAY_SINGLE_PAY_BANKTYPE);
		
		//交易的商品名称
		paramMap.put("desc", getDesc(orderDO.getOrderId()));
		
		//用户(买方)的财付通帐户(QQ或EMAIL)
		//paramMap.put("purchaser_id", RETURNURL);
		
		//商家的商户号,有腾讯公司唯一分配
		paramMap.put("bargainor_id", PinjuConstant.TENPAY_DIRECTPAY_PINJU_PAYACCOUNT);
		
		//交易号(订单号)，由商户网站产生(建议顺序累加)，一对请求和应答的交易号必须相同）。
		//transaction_id 为28位长的数值，其中前10位为商户网站编号(SPID)，由财付通统一分配；.
		//之后8位为订单产生的日期，如20050415；最后10位商户需要保证一天内不同的事务（用户订购一次商品或购买一次服务），其ID不相同
		//paramMap.put("transaction_id", MERCHANTNO);
		
		//商户系统内部的定单号，此参数仅在对账时提供,28个字符内、可包含字母。 
		paramMap.put("sp_billno", "" + orderDO.getOrderId());

		//总金额，以分为单位,不允许包含任何字符
		paramMap.put("total_fee", orderDO.getPriceAmountByYuan());
		
		//现金支付币种，目前只支持人民币1
		paramMap.put("fee_type", PinjuConstant.TENPAY_DIRECTPAY_FEE_TYPE);
		
		//接收财付通返回结果的URL(推荐使用ip)
		paramMap.put("return_url", PinjuConstant.TENPAY_MERGE_PAY_NOTIFYURL);
		
		return paramMap;
	}
	
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	@Override
	protected String getMD5Key() {
		// TODO Auto-generated method stub
		return null;
	}
}
