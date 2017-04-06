package com.yuwang.pinju.core.trade.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.trade.ao.TenSearchOrderAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.trade.TenSearchOrderDO;


public class TenSearchOrderAOImpl extends TenPayAbstractBaseAO implements TenSearchOrderAO {
	
	//版本号
	private final String VERSION = PinjuConstant.TENPAY_SEARCHORDER_VERSION;
	//业务代码，查询96
	private final String CMDNO = PinjuConstant.TENPAY_SEARCHORDER_CMDNO;
	//回调地址
	private final String RETURNURL = PinjuConstant.TENPAY_SEARCHORDER_RETURNURL;
	//查询订单请求地址
	private final String REQUESTURL = PinjuConstant.TENPAY_SEARCHORDER_SEARCHURL;
	

	private OrderQueryManager orderQueryManager;
	

	@Override
	public Result getOrderDetail(TenSearchOrderDO tenSearchOrderDO) {
		Result result = new ResultSupport();
		tenSearchOrderDO.setVersion(VERSION);
		tenSearchOrderDO.setReturn_url(RETURNURL);
		tenSearchOrderDO.setCmdno(CMDNO);
		try {
			//封装支付参数
			SortedMap<String, String> parameters = createParameters(tenSearchOrderDO.getVersion(), tenSearchOrderDO.getCmdno()
					, tenSearchOrderDO.getReturn_url(), tenSearchOrderDO.getBargainor_id(), tenSearchOrderDO.getTransaction_id()
					, tenSearchOrderDO.getSp_billno());
			//生成签名
			super.createSign(parameters);
			//参数报文带签名
			final String sendParameter = super.parametersToString(parameters);
			//发送地址带参数
			final String sendDetail = REQUESTURL + "?" + sendParameter;
			result.setModel("sendDetail",sendDetail);
			
		} catch (Exception e) {
			log.error("TenSearchOrderAOImpl#getSendDetail  获得发送报文出错：",e);
		}
		return result; 
	}
	
	
	/**
	 * Created on 2011-9-9
	 * <p>Discription: 封装财付通参数</p>
	 * @param version
	 * @param cmdno
	 * @param returnUrl
	 * @param bargainnorId
	 * @param transactionId
	 * @param spBillno
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private SortedMap<String, String> createParameters(String version, String cmdno, String returnUrl,
			String bargainnorId, String transactionId, String spBillno) throws ManagerException {
		String date = null;
		date  = DateUtil.formatDate(PinjuConstant.TENPAY_SEARCHORDER_DATE_FORMAT, new Date());
		SortedMap<String, String> createParameters = new TreeMap<String, String>();
		createParameters.put("cmdno", cmdno);
		createParameters.put("date", date);
		createParameters.put("bargainor_id", bargainnorId);
		createParameters.put("transaction_id", transactionId);
		createParameters.put("sp_billno", spBillno);
		createParameters.put("return_url", returnUrl);
		createParameters.put("version", version);
		return createParameters;
	}

	@Override
	protected String getMD5Key() {
		return PinjuConstant.TENPAY_PAY_MD5KEY;
	}


	@Override
	public Result getOrderInfo(SortedMap<String, String> createParameters,String sign,Integer cmdno,Integer version,Long sp_billno) {
		Result result = new ResultSupport();
		
		try {
			if (!this.verifySign(createParameters, sign)){
				result.setSuccess(false);
				result.setResultCode("11");
			    return result;
			}
			
			if (cmdno != 96 && version != 4) {
				result.setSuccess(false);
				result.setResultCode("12");
			    return result;
			}
			
			OrderDO orderDO = orderQueryManager.getOrderDOById(sp_billno);
			List<OrderItemDO> orderItemList = orderQueryManager.queryOrderItemList(sp_billno);
			result.setSuccess(true);
			result.setModel("orderDO",orderDO);
			result.setModel("orderItemList",orderItemList);
		} catch (ManagerException e) {
			log.error("TenSearchOrderAOImpl#getSendDetail  获得发送报文出错：",e);
		}
		
		return result;
	}


	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}
	

}
