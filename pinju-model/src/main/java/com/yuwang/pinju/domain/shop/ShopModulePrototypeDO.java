package com.yuwang.pinju.domain.shop;

import com.yuwang.pinju.domain.ConfigurableSupport;


/**
 * 模块原型
 * @author mike
 * @version 1.0
 * @created 17-六月-2011 10:38:14
 */
public class ShopModulePrototypeDO extends ConfigurableSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2583008736838652243L;
	/**
	 * 模块开发者
	 */
	private String author;
	/**
	 * 模块描述
	 */
	private String description;
	/**
	 * 模块编辑：显示的html页面
	 */
	private String editHtml;
	/**
	 * 序列id号
	 */
	private Integer id;
	/**
	 * 模块缩略图地址
	 */
	private String image;
	/**
	 * 模块原型id
	 */
	private Integer moduleId;
	
	/**
	 * 对应页面编号
	 */
	private Integer pageId;

	/**
	 * 模块名字
	 */
	private String name;
	/**
	 * 缩写名
	 */
	private String shortName;
	/**
	 * 排序字段
	 */
	private Integer sortId;
	/**
	 * 赞助商：1为品聚，别的为其它
	 */
	private Integer sponsor;
	/**
	 * 模块类型 1:头顶模块; 2:左侧模块; 3:右侧模块; 4:底部模块
	 */
	private Integer type;
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 标题
	 */
	private String title;

	/**
	 * 模块类型（1：非自定义，2：自定义）
	 */
	private Short moduleType;

	public ShopModulePrototypeDO(){

	}

	public void finalize() throws Throwable {

	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEditHtml() {
		return editHtml;
	}

	public void setEditHtml(String editHtml) {
		this.editHtml = editHtml;
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

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Integer getSponsor() {
		return sponsor;
	}

	public void setSponsor(Integer sponsor) {
		this.sponsor = sponsor;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Short getModuleType() {
		return moduleType;
	}

	public void setModuleType(Short moduleType) {
		this.moduleType = moduleType;
	}
}