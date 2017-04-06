/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.internal;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yuwang.api.ApiException;
import com.yuwang.api.ApiRequest;
import com.yuwang.api.ApiResponse;
import com.yuwang.api.common.Constants;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.order.service.OpenQueryOrderService;
import com.yuwang.pinju.core.util.ObjectUtil;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;

/**
 * 物流相关的开放API接口类
 * 
 * @author liyouguo
 * 
 * @since 2011-9-22
 */
public class OpenApiLogistics extends BaseApi {

	private static LogisticsCorpAO logisticsCorpAO;

	private static OpenQueryOrderService openQueryOrderService;

	public OpenApiLogistics() {
		logisticsCorpAO = (LogisticsCorpAO) super.getBean("logisticsCorpAO");
		openQueryOrderService = (OpenQueryOrderService) getBean("openQueryOrderService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7240863594212164494L;

	/**
	 * 获取平台中当前所有支持的物流公司列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int getLogisticsCorp(ApiRequest request, ApiResponse response) {
		try {
			try {
				String logisticsType = request.getTextParams().get("logisticsType");
				if (logisticsType != null && !"".equals(logisticsType.trim()))
					Long.parseLong(logisticsType);
			} catch (Exception e) {
				response.setMsg("物流类型输入有误（枚举值1：平邮；2：快递；3：EMS）。");
				return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
			}
			LogisticsCorpDO corpDo = (LogisticsCorpDO) request.createDomain();
			corpDo.setLogisticsStatus(1L);// 当前有效的记录
			List<LogisticsCorpDO> list = logisticsCorpAO.getLogisticsCorpByStatus(corpDo);
			if (list == null) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg("获取物流公司信息失败。");
				return response.getErrorCode();
			}
			if (list.size() == 0) {
				response.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				return Constants.SUBMIT_APIMETHOD_NO_DATA;
			}
			response.setRespData(list);
			return Constants.SUBMIT_APIMETHOD_INNER_SUCCESS;
		} catch (ApiException e) {
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		}
	}

	/**
	 * 订单发货
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int orderDelivery(ApiRequest request, ApiResponse response) {
		Map<String, String> map = request.getTextParams();
		String errorMsg = "";
		try {
			String outLogisticsId = map.get("outLogisticsId");
			if (StringUtil.isEmpty(outLogisticsId) || outLogisticsId.length() > 30) {
				errorMsg +="传入参数出错outLogisticsId:" + outLogisticsId + "，必须传入长度不大于30的运单号。";
			}
			String logisticsCode = map.get("logisticsCode");
			if (StringUtil.isEmpty(logisticsCode)) {
				errorMsg +="传入参数出错，必须传入参数物流公司代码logisticsCode。";
			}else{
				if(!openQueryOrderService.checkTradeLogisticsCorp(logisticsCode)){
					errorMsg +="传入参数出错，传入的物流公司代码logisticsCode不存在。";
				}
			}
			String sendAddress = map.get("sendAddress");
			String sendName = map.get("sendName");
			String sendPost = map.get("sendPost");
			String sendMobilePhone = map.get("sendMobilePhone");
			String sendPhone = map.get("sendPhone");
			// 校验发货信息
			errorMsg += checkDeliverySendInfo(sendAddress, sendName, sendPost, sendMobilePhone, sendPhone);
			if (errorMsg.length() > 0) {
				throw new ApiException(errorMsg);
			}
			boolean success = openQueryOrderService.sellerDelivery(Long.valueOf(map.get("orderId")), outLogisticsId,
					logisticsCode, request.getMemberId(), sendAddress, sendName, sendPost, sendMobilePhone,
					sendPhone);
			if (!success) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			}
		} catch (NumberFormatException e) {
			log.error("OpenAPI-orderDelivery error,input orderId:" + map.get("orderId"), e);
			response.setMsg("传入参数出错了，必须传入数字型参数orderId。");
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		} catch (ApiException e) {
			response.setMsg(e.getMessage());
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		}
		return response.getErrorCode();
	}

	/**
	 * 校验发货信息
	 * @param sendAddress
	 * @param sendName
	 * @param sendPost
	 * @param sendMobilePhone
	 * @param sendPhone
	 * @throws ApiException
	 */
	private String checkDeliverySendInfo(String sendAddress, String sendName, String sendPost, String sendMobilePhone,
			String sendPhone) throws ApiException {
		String rtn = "";
		int writedNum = 0;
		if (!StringUtil.isEmpty(sendAddress)) {
			if (sendAddress.length() > 80) {
				rtn+="发货地址不能大于80个字符。";
			}
			writedNum++;
		}
		if (!StringUtil.isEmpty(sendName)) {
			writedNum++;
		}
		if (!StringUtil.isEmpty(sendPost)) {
			writedNum++;
		}
		if (!StringUtil.isEmpty(sendMobilePhone) || !StringUtil.isEmpty(sendPhone)) {
			if (!StringUtil.isEmpty(sendMobilePhone) && !isMobileNO(sendMobilePhone)) {
				rtn+="请填写正确的手机号码。";
			}
			if (!StringUtil.isEmpty(sendPhone) && !ObjectUtil.isTel(sendPhone)) {
				rtn+="请填写正确的电话号码 。";
			}
			writedNum++;
		}
		if (writedNum > 0 && writedNum != 4) {
			rtn+="请完整填写发货人姓名、发货地址、邮政编码及任意一种联系方式。";
		}
		return rtn;
	}
	
	private boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
