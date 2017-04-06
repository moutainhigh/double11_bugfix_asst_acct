package com.yuwang.pinju.domain.item;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.domain.Paginator;

public class ItemQuery extends Paginator {

	private static final long serialVersionUID = -324403312189591296L;

	/**
	 * 编号组
	 */
	private Long ids[];

	/**
	 * 商品卖家
	 * 
	 */
	private Long sellerId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 商家编码
	 */
	private String code;

	/**
	 * 最小价格
	 */
	private Long minPrice;

	/**
	 * 最小价格
	 */
	private String minPriceInput;

	/**
	 * 最大价格
	 */
	private Long maxPrice;

	/**
	 * 最大价格
	 */
	private String maxPriceInput;

	/**
	 * 最小销量
	 */
	private Long minSales;

	/**
	 * 最小销量
	 */
	private String minSalesInput;

	/**
	 * 最大销量
	 */
	private Long maxSales;

	/**
	 * 最大销量
	 */
	private String maxSalesInput;

	/**
	 * 类型编号
	 */
	private Long category;

	/**
	 * 状态列表
	 */
	private List status;

	/**
	 * 推荐
	 */
	private Long recommendedStatus;

	/**
	 * 类型
	 */
	private int type;

	/**
	 * 类型
	 */
	private String typeInput;

	/**
	 * 过滤类型
	 */
	private int filterType;

	/**
	 * 排序 类型
	 */
	private int orderType;

	/**
	 * 错误信息 返回用
	 */
	private List<String> resultMsg;
	
	/**
	 * 店铺屏蔽状态
	 */
	private Boolean shopStatus;
	
	/**
	 * 运费模版ID
	 */
	private Long freeTemplateId;
	
	public Long getFreeTemplateId() {
		return freeTemplateId;
	}

	public void setFreeTemplateId(Long freeTemplateId) {
		this.freeTemplateId = freeTemplateId;
	}

	public Boolean getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(Boolean shopStatus) {
		this.shopStatus = shopStatus;
	}

	public Long getCategory() {
		return category;
	}

	public String getCode() {
		return StringUtils.trimToEmpty(code);
	}

	public int getFilterType() {
		return filterType;
	}

	public Long[] getIds() {
		return ids;
	}

	public Long getMaxPrice() {
		return maxPrice;
	}

	public String getMaxPriceInput() {
		return StringUtils.trimToEmpty(maxPriceInput);
	}

	public Long getMaxSales() {
		return maxSales;
	}

	public String getMaxSalesInput() {
		return StringUtils.trimToEmpty(maxSalesInput);
	}

	public Long getMinPrice() {
		return minPrice;
	}

	public String getMinPriceInput() {
		return StringUtils.trimToEmpty(minPriceInput);
	}

	public Long getMinSales() {
		return minSales;
	}

	public String getMinSalesInput() {
		return StringUtils.trimToEmpty(minSalesInput);
	}

	public int getOrderType() {
		return orderType;
	}

	public Long getRecommendedStatus() {
		return recommendedStatus;
	}

	public List<String> getResultMsg() {
		return resultMsg;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public List getStatus() {
		return status;
	}

	public String getTitle() {
		return StringUtils.trimToEmpty(title);
	}

	public int getType() {
		return type;
	}

	public String getTypeInput() {
		return typeInput;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setMaxPriceInput(String maxPriceInput) {
		this.maxPriceInput = maxPriceInput;
	}

	public void setMaxSales(Long maxSales) {
		this.maxSales = maxSales;
	}

	public void setMaxSalesInput(String maxSalesInput) {
		this.maxSalesInput = maxSalesInput;
	}

	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}

	public void setMinPriceInput(String minPriceInput) {
		this.minPriceInput = minPriceInput;
	}

	public void setMinSales(Long minSales) {
		this.minSales = minSales;
	}

	public void setMinSalesInput(String minSalesInput) {
		this.minSalesInput = minSalesInput;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public void setRecommendedStatus(Long recommendedStatus) {
		this.recommendedStatus = recommendedStatus;
	}

	public void setResultMsg(List<String> resultMsg) {
		this.resultMsg = resultMsg;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public void setStatus(List status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setTypeInput(String typeInput) {
		this.typeInput = typeInput;
	}

}
