package com.yuwang.pinju.domain.shop;

import com.yuwang.pinju.domain.ConfigurableSupport;


/**
 * 页面原型
 * @version 1.0
 * @created 17-六月-2011 10:39:44
 */
public class ShopPagePrototypeDO extends ConfigurableSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1399965653562509085L;
	/**
	 * 页面描述
	 */
	private String description;
	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 页面缩阅图
	 */
	private String image;
	/**
	 * 页面名字
	 */
	private String name;
	/**
	 * 页面原型id
	 */
	private Integer pageId;
	/**
	 * 页面初始化的xml结构
	 */
	private String pageStructure;

	public ShopPagePrototypeDO(){

	}

	public void finalize() throws Throwable {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getPageStructure() {
		return pageStructure;
	}

	public void setPageStructure(String pageStructure) {
		this.pageStructure = pageStructure;
	}

}