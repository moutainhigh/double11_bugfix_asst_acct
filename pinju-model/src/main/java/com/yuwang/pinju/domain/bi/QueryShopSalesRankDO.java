package com.yuwang.pinju.domain.bi;

/**
 * @see 店铺销售商品排行查询实体
 * @author 杜成
 * @date 2011-6-20
 * @version 1.0
 */
public class QueryShopSalesRankDO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2536540992362157386L;

	/**
	 * 卖家会员编号
	 */
	private Long memberId;
	/**
	 * 店铺编号
	 */
	private Integer shopId;
	/**
	 * 店铺商品分类编号
	 */
	private Integer categoryId;
	/**
	 * 店铺子类商品分类编号
	 */
	private Integer cateparentId;
	/**
	 * 获取记录数
	 */
	private Integer num = 5;
	/**
	 * 排行类型
	 */
	private Integer rankType = 1;
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCateparentId() {
		return cateparentId;
	}

	public void setCateparentId(Integer cateparentId) {
		this.cateparentId = cateparentId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getRankType() {
		return rankType;
	}

	public void setRankType(Integer rankType) {
		this.rankType = rankType;
	}
	
}
