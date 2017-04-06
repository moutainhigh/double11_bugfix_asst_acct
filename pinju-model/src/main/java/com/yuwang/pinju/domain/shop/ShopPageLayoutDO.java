package com.yuwang.pinju.domain.shop;

import com.yuwang.pinju.domain.BaseDO;

public class ShopPageLayoutDO extends BaseDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6701796956656420582L;
	private String layoutsType;// 布局类型
	private Long userPageId;
	private String name;// 模块名称
	private String id;// 模块编号
	private String title;// 标题
	private String description;// 模块说明
	private String type;// 模块类型 head,left,right,footer
	private String isCustomCode; // 是否是用户自定义模块
	private Long firstUserPageId;// 此用户的首页userPageId

	public String getLayoutsType() {
		return layoutsType;
	}

	public void setLayoutsType(String layoutsType) {
		this.layoutsType = layoutsType;
	}

	public Long getUserPageId() {
		return userPageId;
	}

	public void setUserPageId(Long userPageId) {
		this.userPageId = userPageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsCustomCode() {
		return isCustomCode;
	}

	public void setIsCustomCode(String isCustomCode) {
		this.isCustomCode = isCustomCode;
	}

	public Long getFirstUserPageId() {
		return firstUserPageId;
	}

	public void setFirstUserPageId(Long firstUserPageId) {
		this.firstUserPageId = firstUserPageId;
	}

}
