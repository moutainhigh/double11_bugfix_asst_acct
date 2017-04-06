package com.yuwang.api.domain;

import com.yuwang.api.common.BaseDO;

public class OperationItemDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1694095858596686303L;

	private Long id;
	private String resultMsg;
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
