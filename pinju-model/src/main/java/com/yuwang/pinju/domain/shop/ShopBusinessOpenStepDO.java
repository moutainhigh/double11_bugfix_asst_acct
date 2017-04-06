package com.yuwang.pinju.domain.shop;

import com.yuwang.pinju.domain.BaseDO;

public class ShopBusinessOpenStepDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8780450593326705375L;

	/**
	 * 自增长id
	 */
	private Integer id;

	/**
	 * 用户userId
	 */
	private Long userId;
	/**
	 * 用来记录是否签订服务协议
	 */
	private Integer agreement;
	
	private Integer isOpen;
	
	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getAgreement() {
		return agreement;
	}

	public void setAgreement(Integer agreement) {
		this.agreement = agreement;
	}

}
