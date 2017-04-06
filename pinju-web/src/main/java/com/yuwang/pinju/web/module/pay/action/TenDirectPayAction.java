package com.yuwang.pinju.web.module.pay.action;




import java.util.SortedMap;
import java.util.Map.Entry;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.trade.ao.TenDirectPayAO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * 
 * Created on 2011-8-11
 * <p>Discription: 财付通支付Action</p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class TenDirectPayAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1849090456044152477L;

	private DirectPayParamDO directPayParamDO;
	
	private TenDirectPayAO tenDirectPayAO;
	
	private String postUrl;
	//支付金额
	private String amount;
	//支付订单号
	private String orderNo;
	
	/**
	 * 
	 * Created on 2011-8-11
	 * <p>Discription: 财富通支付</p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	public String tenSendOrder() {
		try {
			try {
				directPayParamDO.setBuyerIp(ServletUtil.getRemoteNumberIp()); //Add By ShiXing@2011.08.26 Cause:下单数字IP
			} catch (Exception e) {
				//未获得IP继续后续业务
			}
			Result result = null;
			
			if(directPayParamDO==null)
				return ERROR;
				
			switch (directPayParamDO.getBizType()) {
			//TODO 保证金
			case DirectPayConstant.BIZ_TYPE_MARGIN:
				result = tenDirectPayAO.tenDirectPay(directPayParamDO);
				break;
			default:
				result = tenDirectPayAO.groupTenDirectPay(directPayParamDO);
				break;
			}
	
			// 生成内部支付订单失败
			if (!result.isSuccess()) {
				/**
				 * TODO 失败处理
				 */
				return ERROR;
			}
			postUrl =(String)result.getModel("parametersUrl");
			amount =(String)result.getModel("orderAmount");
			amount = MoneyUtil.getCentToDollar(Long.valueOf(amount));
			orderNo =(String)result.getModel("payOrderID");
			setParmsResult((SortedMap<String, String>)result.getModel("parameters"));
		} catch (Exception e) {
			log.error("Event=[TenDirectPayAction#tenSendOrder]:", e);
			return ERROR;
		}
		
		return SUCCESS;
	}


	/**
	 * 
	 * Created on 2011-10-10
	 * <p>Discription: 封装页面传递参数</p>
	 * @param result
	 * @param parameters
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void setParmsResult(SortedMap<String, String> parameters){
		for(Entry<String, String> entry: parameters.entrySet()){
			request.setAttribute(entry.getKey(), String.valueOf(entry.getValue()));
		}
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	public DirectPayParamDO getDirectPayParamDO() {
		return directPayParamDO;
	}


	public void setDirectPayParamDO(DirectPayParamDO directPayParamDO) {
		this.directPayParamDO = directPayParamDO;
	}

	public void setTenDirectPayAO(TenDirectPayAO tenDirectPayAO) {
		this.tenDirectPayAO = tenDirectPayAO;
	}

	public String getPostUrl() {
		return postUrl;
	}


	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


}
