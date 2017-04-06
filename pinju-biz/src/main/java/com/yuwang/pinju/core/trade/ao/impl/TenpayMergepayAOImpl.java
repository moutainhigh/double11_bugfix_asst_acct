package com.yuwang.pinju.core.trade.ao.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.tenpay.MD5Util;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.trade.ao.TenpayMergepayAO;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.trade.TenpayMergepayParamDO;
import com.yuwang.pinju.domain.trade.TenpaySinglepayParamDO;

/**
 * 
 * Created on 2011-10-15
 * <p>Discription: </p>
 * @return
 * @author:[石洪波]
 * @version 1.0
 * @update:[2011-11-15] [杜成]
 * 1.重构支付参数串生成
 * 2.重构发送日志
 * 
 */
public class TenpayMergepayAOImpl extends TenPayAbstractBaseAO implements TenpayMergepayAO {

	private  final   Log log = LogFactory.getLog(this.getClass().getName());
	
	private VouchCreateManager vouchCreateManager;
	
	/**
	 * <p>
	 * 创建合并支付参数
	 * </p>
	 * 
	 * @param paramList
	 * @return 
	 */
	public Result createMergepayParam(List<TenpaySinglepayParamDO> paramList){
		Result result = new ResultSupport();
		result.setModel("paramList", paramList);

		
		try{
			if(paramList == null || paramList.size() == 0){
				result.setSuccess(false);
				return result;
			}
			
			//合并支付最多支持10笔,为true表示超过10
			Boolean requestNoError = paramList.size() > 10;
			
			TenpayMergepayParamDO tenpayMergepayParamDO = new TenpayMergepayParamDO();

			//请求笔数
			Integer requestNO = paramList.size();
			if(requestNO > 10)
				requestNO = 10;
			tenpayMergepayParamDO.setRequestNo(requestNO);
			result.setModel("request_no", requestNO);

			//页面跳转url
			tenpayMergepayParamDO.setReturnUrl(PinjuConstant.TENPAY_MERGE_PAY_RETURNURL);
			result.setModel("return_url",PinjuConstant.TENPAY_MERGE_PAY_RETURNURL);

			//商户号
			tenpayMergepayParamDO.setSpid(paramList.get(0).getBargainorId());
			result.setModel("spid", paramList.get(0).getBargainorId());

			
			List<String> list = new LinkedList<String>();
			
			int size = 0;
			Money totalFee = new Money(0) ;
			for(TenpaySinglepayParamDO param:paramList){
				//合并支付最多支持10笔
				if(size == 10){
					requestNoError = true;
					break;
				}
				
				String s = getRequestBySinglepayParam(param);
				list.add(s);
				
				size++;
				
				totalFee.addTo(new Money(param.getTotalFee()));
			}
			
			result.setModel("totalFee", MoneyUtil.getCentToDollar(totalFee.getCent()));
			tenpayMergepayParamDO.setRequest(list);
			result.setModel("requestList", list);
			//根据tenpayMergepayParamDO取得发送参数
			String mergepayParam = getParmString(tenpayMergepayParamDO);
			//对参数签名
			String sign = createSign(mergepayParam);
			result.setModel("sign", sign);
		
			//插入发送日志信息
			insertTradePaySendLog(paramList, mergepayParam);
			String postUrl = PinjuConstant.TENPAY_PAY_URL;
			//设置返回结果信息
			result.setSuccess(true);
			result.setModel("requestNoError", requestNoError);
			result.setModel("postUrl", postUrl);

		}catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[TenpayMergepayAOImpl#createMergepayParam] 发送支付", e);
		}

		return result;
	}
	
	/**
	 * <p>
	 * 插入发送日志信息
	 * </p>
	 * 
	 * @param paramList
	 * @param mergepayParam
	 * @return
	 */
	private void insertTradePaySendLog(List<TenpaySinglepayParamDO> paramList,
			String mergepayParam) {
		try {
			for (TenpaySinglepayParamDO paramDO : paramList) {

				PaySendLogDO paySendLogDO = new PaySendLogDO();

				// 支付用户内部账号
				paySendLogDO.setPayUserId(paramDO.getBuyerId());

				// 收款用户内部账号
				paySendLogDO.setAcceptUserId(paramDO.getSellerId());

				// 支付平台类型
				paySendLogDO.setPayType(PinjuConstant.TENPAY_SINGLE_PAY_CMDNO);

				// 内部支付订单编号
				paySendLogDO.setOrderPayId(paramDO.getTransactionId());

				// 发送内容(加密后), 合并支付超过4000时只记录4000个
				if (mergepayParam.length() > 4000) {
					paySendLogDO.setSendInfo(mergepayParam.substring(0, 3999));
				} else {
					paySendLogDO.setSendInfo(mergepayParam);
				}

				// 日志生成时间
				paySendLogDO.setCreationTime(new Date());

				// 付款第三方账户
				paySendLogDO.setPayAccount("");

				// 收款第三方账户
				paySendLogDO.setAcceptAccount(paramDO.getBargainorId());

				// 发送类型
				paySendLogDO.setSendType(PinjuConstant.TENPAY_SINGLE_PAY_CMDNO);

				vouchCreateManager.insertTradePaySendLog(paySendLogDO);
			}
		} catch (Exception e) {
			log.error("Event=[TenpayMergepayAOImpl#insertTradePaySendLog] 记录支付日志", e);
		}
	}
	
	
	private String getParmString(TenpayMergepayParamDO param){
		StringBuffer sb = new StringBuffer();
		sb.append("request_no=" + param.getRequestNo());
	    sb.append("&spid=" + param.getSpid());
		int i = 0;
		for(String s:param.getRequest()){
			if(i == 0)
				sb.append("&request=" + s);
			else
				sb.append("&request" + i + "=" + s);
			
			i++;
		}
		sb.append("&return_url=" + param.getReturnUrl());
		return sb.toString();
	}
	
	/**
	 * <p>
	 * 创建合并支付参数签名
	 * </p>
	 * 
	 * @param param
	 * @return 合并支付参数签名
	 */
	private String createSign(String parmString){
		StringBuffer sb = new StringBuffer();
		sb.append(parmString);
		//key只做签名使用,需存在签名字段最后一项
		sb.append("&key=" + PinjuConstant.TENPAY_PAY_MD5KEY);
		log.debug("=======mergepay, sign param:" + sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), CHARSET).toUpperCase();
		return sign;
	}
	
	/**
	 * <p>
	 * 创建单笔支付参数字符串
	 * </p>
	 * 
	 * @param param
	 * @return 单笔支付参数字符串
	 */
	private String getRequestBySinglepayParam(TenpaySinglepayParamDO param){

		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("ver", param.getVer());
		map.put("cmdno", "" + param.getCmdno());
		map.put("date", param.getDate());
		map.put("bank_type", "" + param.getBankType());
		map.put("desc", param.getDesc());
		map.put("purchaser_id", param.getPurchaserId());
		map.put("bargainor_id", param.getBargainorId());
		map.put("transaction_id", param.getTransactionId());
		map.put("sp_billno", param.getSpBillno());
		map.put("total_fee", "" + param.getTotalFee());
		map.put("fee_type", "" + param.getFeeType());
		
		try{
			String p = URLEncoder.encode(param.getReturnUrl(), CHARSET);
			map.put("return_url", p);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		map.put("attach", param.getAttach());
		map.put("cs", param.getCs());
		map.put("spbill_create_ip", param.getSpbillCreateIp());
		
		String request = parametersToString(map);
		log.info("single parameter content：" + request);
		
		return request;
	}
	

	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}
	
	@Override
	protected String getMD5Key() {
		return null;
	}

}
