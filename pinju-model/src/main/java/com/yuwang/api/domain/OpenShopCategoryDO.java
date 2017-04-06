/**
 * OPEN API二期需求，甩手掌柜
 */
package com.yuwang.api.domain;

import java.util.List;

import com.yuwang.api.common.BaseDO;

/**
 * @author liyouguo
 * 
 * @since 2011-11-14
 */
public class OpenShopCategoryDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3313026138855705758L;

	/**
	 * 分类编号
	 */
	private String id;
	/**
	 * 分类名称
	 */
	private String cateName;
	/**
	 * 所属分类
	 */
	private List<OpenShopCategoryDO> childCates;

	/**
	 * 分类排序
	 */
	private Integer sortId;

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public List<OpenShopCategoryDO> getChildCates() {
		return childCates;
	}

	public void setChildCates(List<OpenShopCategoryDO> childCates) {
		this.childCates = childCates;
	}
}
