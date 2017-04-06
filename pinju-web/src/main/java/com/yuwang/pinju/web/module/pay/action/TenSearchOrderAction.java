package com.yuwang.pinju.web.module.pay.action;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.trade.ao.TenSearchOrderAO;
import com.yuwang.pinju.domain.trade.TenSearchOrderDO;
import com.yuwang.pinju.web.module.BaseAction;

public class TenSearchOrderAction extends BaseAction {

	private static final long serialVersionUID = 4109764123357142460L;

	private TenSearchOrderAO tenSearchOrderAO;

	// 商户的商户号，有腾讯公司唯一分配
	private String bargainor_id = "111";

	// 财付通交易单号（订单号），此查询仅以此为唯一索引
	private String transaction_id = "111";

	// 商户系统内部的订单号，此参数仅在对账时提供 (orderId)
	private String sp_billno = "12161";

	private String signUrl;

	public String tenSearchOrder() {
		TenSearchOrderDO tenSearchOrderDO = new TenSearchOrderDO(bargainor_id,
				transaction_id, sp_billno);

		Result result = tenSearchOrderAO.getOrderDetail(tenSearchOrderDO);
		signUrl = (String) result.getModel("sendDetail");
		return "success";
	}

	public void setTenSearchOrderAO(TenSearchOrderAO tenSearchOrderAO) {
		this.tenSearchOrderAO = tenSearchOrderAO;
	}

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public String getBargainor_id() {
		return bargainor_id;
	}

	public void setBargainor_id(String bargainor_id) {
		this.bargainor_id = bargainor_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getSp_billno() {
		return sp_billno;
	}

	public void setSp_billno(String sp_billno) {
		this.sp_billno = sp_billno;
	}

	public TenSearchOrderAO getTenSearchOrderAO() {
		return tenSearchOrderAO;
	}

}
