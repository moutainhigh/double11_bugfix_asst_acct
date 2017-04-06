package com.yuwang.pinju.domain.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品类目
 * 
 * @author liming
 * 
 */
public class CategoryDO implements java.io.Serializable {

	private static final long serialVersionUID = -7283322981440931381L;

	/**
	 * 类目ID
	 */
	private Long id;

	/**
	 * 类目父编号
	 */
	private Long parentId;

	/**
	 * 属性名称
	 */
	private String name;

	/**
	 * 类目层级
	 */
	private Long categoryLevel;

	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 排序值
	 */
	private Long sortOrder;
	
	/**
	 * 状态
	 */
	private Long status;
	
	/**
	 * 特征
	 */
	private String features;
	
	/**
	 * 拼音检索
	 */
	private String spell;
	
	/**
	 * 是否是叶子节点
	 */
	private Long isLeaf;
	
	/**
	 * 搜索类目下的商品数
	 */
	private Long itemNum;

	/**
	 * 子类目列表
	 */
	private List<CategoryDO> childCateList = new ArrayList<CategoryDO>();
	/**
	 * 父类目
	 */
	private CategoryDO parentCategoryDO;
	
	public CategoryDO() {
	}

	public CategoryDO(Long id) {
		this.id = id;
	}

	public CategoryDO(Long id, Long parentId, String name,
			Long categoryLevel, Date gmtModify, Date gmtCreate,
			Long sortOrder, Long status, String features,Long isLeaf,String spell) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.categoryLevel = categoryLevel;
		this.gmtModified = gmtModify;
		this.gmtCreate = gmtCreate;
		this.sortOrder = sortOrder;
		this.status = status;
		this.features = features;
		this.isLeaf = isLeaf;
		this.spell = spell;
	}
	public void addChildCate(CategoryDO categoryDO){
		if (this.childCateList == null) {
			this.childCateList = new ArrayList<CategoryDO>();
		}
		childCateList.add(categoryDO);
	}
	/*public void replceChildCate(CategoryDO categoryDO){
		int count =0;
		if (this.childCateList != null) {
			for (CategoryDO childCategoryDO : childCateList) {
				if (childCategoryDO.getId()==categoryDO.getId().longValue()) {
					count++;
					if (categoryDO.getStatus()==1l) {
						childCategoryDO=categoryDO;
					}else {
						childCateList.remove(childCategoryDO);
					}
					break;
				}
			}
		}
		if (count<1) {
			if (this.childCateList == null) {
				this.childCateList = new ArrayList<CategoryDO>();
			}
			childCateList.add(categoryDO);
		}
	}*/
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryLevel() {
		return this.categoryLevel;
	}

	public void setCategoryLevel(Long categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	
	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Long getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public Long getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Long isLeaf) {
		this.isLeaf = isLeaf;
	}


	public Long getItemNum() {
		return itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

	public List<CategoryDO> getChildCateList() {
		return childCateList;
	}
	
	public void setChildCateList(List<CategoryDO> childCateList) {
		this.childCateList = childCateList;
	}

	public CategoryDO getParentCategoryDO() {
		return parentCategoryDO;
	}

	public void setParentCategoryDO(CategoryDO parentCategoryDO) {
		this.parentCategoryDO = parentCategoryDO;
	}
	
	public CategoryDO simpleClone(){
		return new CategoryDO(this.id,this.parentId, this.name,
				this.categoryLevel, this.gmtModified, this.gmtCreate,
				this.sortOrder, this.status, this.features,this.isLeaf,this.spell);
	}
}