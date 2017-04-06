/**
 * 钻石展位，版本号：1.0
 * 通过卖家竞拍，根据出价及创建时间进行展现。
 */
package com.yuwang.api.internal;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuwang.api.ApiException;
import com.yuwang.api.ApiRequest;
import com.yuwang.api.ApiResponse;
import com.yuwang.api.common.Constants;
import com.yuwang.api.util.ApiUtil;
import com.yuwang.pinju.core.order.service.OpenQueryOrderService;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.query.OpenOrderQuery;
import com.yuwang.pinju.domain.order.query.OrderStateEnum;

/**
 * @author xiazhenyu
 * 
 *         创建时间：2011-9-27
 */
public class OpenApiOrder extends BaseApi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7525590308409156067L;

	private OpenQueryOrderService openQueryOrderService;

	public OpenApiOrder() {
		openQueryOrderService = (OpenQueryOrderService) getBean("openQueryOrderService");
	}

	/**
	 * 按条件查询卖家订单(三个月内)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int getOrderList(ApiRequest request, ApiResponse response) {
		try {
			// 只允许数字后加一个逗号
			if (null != request.getTextParams().get("status")) {
				ApiUtil.checkIntArray(request.getTextParams().get("status"));
			}
			OpenOrderQuery ooq = (OpenOrderQuery) request.createDomain();
			// check order status
			checkOrderStautsParam(ooq);
			// check form to date
			checkDate(ooq);
			// set sellerId
			ooq.setSellerId(request.getMemberId());
			// get number of order list
			Long total = openQueryOrderService.sellerOrderSize(ooq);
			ooq.setItems(total.intValue());
			if (total.longValue() == 0L) {
				response.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_NO_DATA);
				return response.getErrorCode();
			}
			setItemsPerPage(ooq, request.getTextParams().get("itemsPerPage"));
			setPage(ooq, request.getTextParams().get("page"));
			List<OrderDO> result = openQueryOrderService.queryOrder(ooq);
			if (result.size() > 0) {
				response.setTotalResults(total.intValue());
				response.setRespData(result);
			} else {
				response.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_NO_DATA);
			}
		} catch (ApiException e) {
			log.error("OpenAPI-getOrderList error", e);
			response.setMsg(e.getMessage());
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		}
		return response.getErrorCode();
	}

	/**
	 * 按订单号查询单个订单详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int getOrderFullInfo(ApiRequest request, ApiResponse response) {
		Map<String, String> map = request.getTextParams();
		try {
			if (StringUtil.isEmpty(map.get("orderId"))) {
				response.setMsg("传入参数出错了，必须传入数字型参数orderId");
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				return response.getErrorCode();
			}
			OrderDO result = openQueryOrderService.queryOrderInfo(Long.valueOf(map.get("orderId")), request
					.getMemberId());
			if (result != null) {
				response.setRespData(result);
			} else {
				response.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_NO_DATA);
			}
		} catch (NumberFormatException e) {
			log.error("OpenAPI-getOrderFullInfo error,input orderId:" + map.get("orderId"), e);
			response.setMsg("传入参数出错了，必须传入数字型参数orderId");
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		}
		return response.getErrorCode();
	}

	/**
	 * 判断开始结束日期是否超过三个月
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private boolean dateVaildator(OpenOrderQuery ooq) {
		Calendar fc = Calendar.getInstance();
		fc.add(Calendar.MARCH, -3);
		if (0 > ooq.getEnd_date().compareTo(fc.getTime())) {
			return false;
		}
		if (0 > ooq.getStart_date().compareTo(fc.getTime())) {
			ooq.setStart_date(fc.getTime());
		}
		return true;
	}

	/**
	 * 设定默认值，并验证大小及在三个月内
	 * 
	 * @param ooq
	 * @throws ApiException
	 */
	private void checkDate(OpenOrderQuery ooq) throws ApiException {
		if (null == ooq.getEnd_date()) {
			ooq.setEnd_date(new Date());
		}
		if (null == ooq.getStart_date()) {
			Calendar c = Calendar.getInstance();
			c.setTime(ooq.getEnd_date());
			c.add(Calendar.MONTH, -3);
			ooq.setStart_date(c.getTime());
		}
		if (0 <= ooq.getStart_date().compareTo(ooq.getEnd_date())) {
			throw new ApiException("结束时间必须大于开始时间。");
		}
		if (!dateVaildator(ooq)) {
			throw new ApiException("结束日期请在三个月之内。");
		}
	}

	/**
	 * 验证传入状态参数是否符合平台要求
	 * 
	 * @param ooq
	 * @throws ApiException
	 */
	private void checkOrderStautsParam(OpenOrderQuery ooq) throws ApiException {
		if(null == ooq.getStatus()){
			ooq.setStatus(new int[0]);
		}
		OrderStateEnum[] orderStateEnum = new OrderStateEnum[ooq.getStatus().length];
		for (int j = 0; j < ooq.getStatus().length; j++) {
			OrderStateEnum ose = OrderStateEnum.getValueByType(ooq.getStatus()[j]);
			if (null != ose) {
				orderStateEnum[j] = ose;
			} else {
				throw new ApiException("传入参数status:" + ooq.getStatus()[j] + "出错了，必须传入正确的类型");
			}
		}
		ooq.setOrderStateEnum(orderStateEnum);
	}
}
