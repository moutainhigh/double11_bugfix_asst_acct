package com.yuwang.pinju.domain.trade;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @Project: pinju-model
 * @Description: 财付通订单查询发送参数DO
 * @author lixin
 */
public class TenSearchOrderDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 业务代码, 财付通查询接口填 96
	 */
	private String cmdno;
	/**
	 * 商户订单日期：如20051212
	 */
	private String date;
	/**
	 * 商家的商户号,由腾讯公司唯一分配
	 */
	private String bargainor_id;
	/**
	 * 财付通交易单号(订单号)。此查询仅以此为唯一索引
	 */
	private String transaction_id;
	/**
	 * 商户系统内部的定单号，此参数仅在对账时提供。
	 */
	private String sp_billno;
	/**
	 * 后台调用，填写为http://127.0.0.1/
	 */
	private String return_url;
	/**
	 * Md5签名
	 */
	private String sign;
	/**
	 * 版本号必须填4
	 */
	private String version;

	public TenSearchOrderDO() {
	}

	public TenSearchOrderDO(String bargainor_id,
			String transaction_id, String sp_billno) {
		this.bargainor_id = bargainor_id;
		this.transaction_id = transaction_id;
		this.sp_billno = sp_billno;
	}

	public String getCmdno() {
		return cmdno;
	}

	public void setCmdno(String cmdno) {
		this.cmdno = cmdno;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
