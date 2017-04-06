package com.yuwang.pinju.domain.item;

/**
 * 
 * 基础属性值
 * 
 * @author liming
 * 
 */
public class BasePropertyDO implements java.io.Serializable {

	private static final long serialVersionUID = -3782157086364484039L;

	/**
	 * 属性ID
	 */
	private Long id;
	
	/**
	 * 属性名称
	 */
	private String name;
	
	/**
	 * 是否屏蔽
	 */
	private Long isShielding;
	
	/**
	 * 所属分组
	 */
	private Long affiliatedGroup;
	
	/**
	 * 特性
	 */
	private String features;

	public BasePropertyDO() {
	}

	public BasePropertyDO(Long id) {
		this.id = id;
	}

	public BasePropertyDO(Long id, String name, Long isShielding,
			Long affiliatedGroup, String features) {
		this.id = id;
		this.name = name;
		this.isShielding = isShielding;
		this.affiliatedGroup = affiliatedGroup;
		this.features = features;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIsShielding() {
		return this.isShielding;
	}

	public void setIsShielding(Long isShielding) {
		this.isShielding = isShielding;
	}

	public Long getAffiliatedGroup() {
		return this.affiliatedGroup;
	}

	public void setAffiliatedGroup(Long affiliatedGroup) {
		this.affiliatedGroup = affiliatedGroup;
	}

	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

}