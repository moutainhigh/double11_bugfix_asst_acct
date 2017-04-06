package com.yuwang.pinju.domain.servicefee;

public class ServiceFeeDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer categoryLevel;

	private Double platFeeRate;
	
	private Long categoryId;

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public Double getPlatFeeRate() {
		return platFeeRate;
	}

	public void setPlatFeeRate(Double platFeeRate) {
		this.platFeeRate = platFeeRate;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
