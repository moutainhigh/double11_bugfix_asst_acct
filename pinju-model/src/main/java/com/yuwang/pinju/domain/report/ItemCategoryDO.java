package com.yuwang.pinju.domain.report;

/**
 * @see
 * <p>Discription: 
 * 	 封装种类的bean
 * </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-8-27
 */
public class ItemCategoryDO implements java.io.Serializable{

	private Long categoryId;
	private String name;
	private String level1Name;
	private String level2Name;
	private String level3Name;
	private String level4Name;
	
	private String displayName;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel1Name() {
		return level1Name;
	}

	public void setLevel1Name(String level1Name) {
		this.level1Name = level1Name;
	}

	public String getLevel2Name() {
		return level2Name;
	}

	public void setLevel2Name(String level2Name) {
		this.level2Name = level2Name;
	}

	public String getLevel3Name() {
		return level3Name;
	}

	public void setLevel3Name(String level3Name) {
		this.level3Name = level3Name;
	}

	public String getLevel4Name() {
		return level4Name;
	}

	public void setLevel4Name(String level4Name) {
		this.level4Name = level4Name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}


