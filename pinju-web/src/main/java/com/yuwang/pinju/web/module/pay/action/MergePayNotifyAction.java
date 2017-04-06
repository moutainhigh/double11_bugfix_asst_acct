package com.yuwang.pinju.web.module.pay.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.trade.ao.TenpayMergepayNotifyAO;
import com.yuwang.pinju.domain.trade.TenpayMergepayRecvParamDO;
import com.yuwang.pinju.domain.trade.TenpaySinglepayRecvParamDO;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * <p>财付通合并支付通知Action</p>
 * 
 * @author:Shihongbo
 */
public class MergePayNotifyAction extends BaseAction {
	
	protected final Log PAYLOG = LogFactory.getLog("tenpay-pay");
	
	//返回错误信息
	private String errorMessage;
	
	private static final long serialVersionUID = -3875222488017592621L;

	private TenpayMergepayNotifyAO tenpayMergepayNotifyAO;

	//接受参数
	private List<TenpaySinglepayRecvParamDO> recvParamList;
	
	//支付总额
	private String totalFee;
	
	//支付成功订单ID
	private List<String> orderIdList;
	
	/**
	 * <p>财付通合并支付回调前台</p>
	 * 
	 * @author:shihongbo
	 */
	@SuppressWarnings("unchecked")
	public String mergePayNotify() {
		try {
			//获取参数
			TenpayMergepayRecvParamDO param = getMergeParam();

			//合并支付回调通知业务处理
			Result notifyResult = tenpayMergepayNotifyAO.mergePayNotify(param);
			//处理结果
			if (!notifyResult.isSuccess())
			{
				return ERROR;
			}
			//支付金额
			totalFee = (String)notifyResult.getModel("totalFee");
			recvParamList = (List<TenpaySinglepayRecvParamDO>)notifyResult.getModel("recvParamList");
			if(recvParamList!=null&&!recvParamList.isEmpty()){
				orderIdList = new ArrayList<String>();
				for(TenpaySinglepayRecvParamDO tenpaySinglepayRecvParamDO :recvParamList){
					orderIdList.add(tenpaySinglepayRecvParamDO.getSpBillno());
				}
			}
			return SUCCESS;

		}catch (Exception e) {
			log.error("Event=[MergePayNotifyAction#mergePayNotify] 财付通合并支付回调处理失败:", e);
			return ERROR;
		}
	}
	

	/**
	 * <p>页面支付成功后回调Action</p>
	 * 
	 * @author:shihongbo
	 */
	public String mergePayNotifySucces() {
		totalFee = (String)getString("totalFee");
		String [] spBillno = (String[])getStrings("spBillnoParms");
		orderIdList = new ArrayList<String>();
		for(String orderIdString :spBillno){
			orderIdList.add(orderIdString);
		}
		return SUCCESS;
	}
	
	/**
	 * <p>支付失败后并回调Action</p>
	 * 
	 * @author:shihongbo
	 */
	public String mergePayNotifyError() {
		return SUCCESS;
	}
	
	
	
	/**
	 * <p>获取参数</p>
	 * 
	 * @author:shihongbo
	 */
	private TenpayMergepayRecvParamDO getMergeParam(){
		TenpayMergepayRecvParamDO param = new TenpayMergepayRecvParamDO();
		
		Integer returnNO = 1;
		try{
			returnNO = Integer.parseInt(getString("return_no"));
		}catch (Exception e) {
			returnNO = 1;
		}
		
		// 请求笔数
		param.setReturnNo(returnNO);

		List<String> request = new LinkedList<String>();
		for(int i = 0; i < returnNO; i++){
			String name = "";
			if(i == 0){
				name = "request";
			} else {
				name = "request" + i;
			}
			
			String s = getString(name);
			
			request.add(s);
		}
		
		param.setRequest(request);
		
		//商户号
		param.setSpid(getString("spid"));
		
		//MD5加密摘要
		param.setSign(getString("sign"));
		
		return param;
	}
	
	public void setTenpayMergepayNotifyAO(TenpayMergepayNotifyAO tenpayMergepayNotifyAO) {
		this.tenpayMergepayNotifyAO = tenpayMergepayNotifyAO;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public List<TenpaySinglepayRecvParamDO> getRecvParamList() {
		return recvParamList;
	}
	
	public String getTotalFee() {
		return totalFee;
	}

	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}
	
	/**
	 * <code>2011-11-11 单笔支付回调通知
	 * 	author ducheng
	 * </code>
	 */

	/**
	 * 
	 * Created on 2011-11-11
	 * <p>Discription: 担保交易单笔回调参数</p>
	 * @param parameters
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Map<String, String>  setParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("ver", getString("ver"));
		parameters.put("cmdno", getString("cmdno"));
		parameters.put("pay_result", getString("pay_result"));
		parameters.put("pay_info", getString("pay_info"));
		parameters.put("date", getString("date"));
		parameters.put("bargainor_id", getString("bargainor_id"));
		parameters.put("transaction_id", getString("transaction_id"));
		parameters.put("sp_billno", getString("sp_billno"));
		parameters.put("total_fee", getString("total_fee"));
		parameters.put("fee_type", getString("fee_type"));
		parameters.put("attach", getString("attach"));
		parameters.put("pay_time", getString("pay_time"));
		parameters.put("sign", getString("sign"));
		return parameters;
	}
	
	/**
	 * <p>财付通合并支付回调后台</p>
	 * 
	 * @author:shihongbo
	 */
	public String mergeBackgroundPayNotify() {
		try {
			PAYLOG.debug("-------------------------------------------------mergeBackgroundPayNotify start 后台单笔回调开始--------------------------------------------------");
			PAYLOG.debug(getString("sp_billno"));
			//合并支付回调通知业务处理
			Result notifyResult = tenpayMergepayNotifyAO.backGroundMergePayNotify(setParameters());
			//处理结果  //只要失败就让财富通补单
			if (!notifyResult.isSuccess())
			{
				return ERROR;
			}
			PAYLOG.debug("-------------------------------------------------mergeBackgroundPayNotify end 后台单笔回调结束,处理成功--------------------------------------------------");
			PAYLOG.debug(getString("sp_billno"));
			//支付成功通知财付通的响应
			String successResponse = "<meta name=\"TENCENT_ONLINE_PAYMENT\" content=\"China TENCENT\"><script language=javascript>window.location.href='http://www.pinju.com/";
			successResponse = successResponse.concat("mergePayPostBack/mergePayAfterNotifySucces.htm")
					.concat("';</script>");
			PAYLOG.debug("-------------------------------------------------mergeBackgroundPayNotify end 后台单笔回调结束,已通知财付通--------------------------------------------------");
			//处理成功时通知财付通
			response.getOutputStream().print(successResponse);
			return null;
	
		}catch (Exception e) {
			log.error("Event=[MergePayNotifyAction#mergePayNotify] 财付通合并支付单笔回调处理失败:", e);
			PAYLOG.debug("mergeBackgroundPayNotify error order_id:".concat(getString("sp_billno")));
			return ERROR;
		}
	}
	
	
	/**
	 * <p>后台支付成功后回调Action</p>
	 * 
	 * @author:shihongbo
	 */
	public String mergePayAfterNotifySucces() {
		return SUCCESS;
	}

	
	
}
