package com.yuwang.pinju.domain.search.result;

import java.util.Date;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.common.FeaturesUtil;
import com.yuwang.pinju.common.Money;

/**
 * 商品搜索返回对象
 * 
 * @author liming
 * @since 2011-07-19
 */
public class ItemResult implements java.io.Serializable {

	private static final long serialVersionUID = 6023461889978723345L;

	/**
	 * 限时折扣活动开始时间
	 */
	@Field("activeBeginDate")
	private Date activeBeginDate;

	/**
	 * 限时折扣活动结束时间
	 */
	@Field("activeEndDate")
	private Date activeEndDate;

	/**
	 * 限时折扣价
	 */
	@Field("activePriceNew")
	private Long activePriceNew;

	/**
	 * 限时折扣一口价
	 */
	@Field("activePriceOld")
	private Long activePriceOld;

	@Field("areaCarriage")
	private String areaCarriage;

	@Field("carriage")
	private String carriage;

	/**
	 * 城市
	 */
	@Field("city")
	private String city;

	/**
	 * 商家编码
	 */
	@Field("code")
	private String code;

	/**
	 * 商品库存
	 */
	// @Field("EVALUATENUM")
	// TODO
	private Long curStock;

	@Field("deliveryCosts")
	private Long deliveryCosts;

	@Field("emsCosts")
	private Long emsCosts;
	/**
	 * 评论数
	 */
	@Field("evaluateNum")
	private Long evaluateNum;

	/**
	 * ex:限时折扣
	 */
	@Field("features")
	private String features;

	private Map<String, String> featuresMap;

	private Long freight;

	/**
	 * 创建时间
	 */
	@Field("gmtCreate")
	private Date gmtCreate;

	/**
	 * 高亮标题
	 */
	private String hlTitle;

	/**
	 * 编号
	 */
	@Field("id")
	private Long id;

	/**
	 * 运费
	 */
	@Field("mailCosts")
	private Long mailCosts;

	@Field("numFound")
	private Integer numFound;

	/**
	 * 商品图片
	 */
	@Field("picUrl")
	private String picUrl;

	/**
	 * 商品价格
	 */
	@Field("price")
	private Long price;

	/**
	 * 省份
	 */
	@Field("provinces")
	private String provinces;

	/**
	 * 成交量
	 */
	@Field("salesNum")
	private Long salesNum;

	/**
	 * 卖家ID
	 */
	@Field("sellerId")
	private Long sellerId;

	/**
	 * 卖家昵称
	 */
	@Field("sellerNick")
	private String sellerNick;

	/**
	 * 店铺类目
	 */
	@Field("storeCategories")
	// TODO
	private String storeCategories;

	/**
	 * 标题
	 */
	@Field("title")
	private String title;

	public Date getActiveBeginDate() {
		return activeBeginDate;
	}

	public Date getActiveEndDate() {
		return activeEndDate;
	}

	public Long getActivePriceNew() {
		return activePriceNew;
	}

	public Long getActivePriceOld() {
		return activePriceOld;
	}

	public String getAreaCarriage() {
		return areaCarriage;
	}

	public String getCarriage() {
		return carriage;
	}

	public String getCity() {
		return city;
	}

	public String getCode() {
		return code;
	}

	public Long getCurStock() {
		return curStock;
	}

	public Long getDeliveryCosts() {
		return deliveryCosts == null ? 0l : deliveryCosts;
	}

	public Long getEmsCosts() {
		return emsCosts == null ? 0l : emsCosts;
	}

	public Long getEvaluateNum() {
		return evaluateNum;
	}

	/**
	 * 通过不同类型枚举获取features字段信息
	 * 
	 * @author liuboen
	 * @param featuresEnum
	 * @return
	 */
	public String getFeatureByEnum(ItemFeaturesEnum featuresEnum) {
		return this.getFeaturesMap().get(featuresEnum.getFeatureName().toString());
	}

	public String getFeatures() {
		return features;
	}

	/**
	 * @return the featuresMap
	 */
	public Map<String, String> getFeaturesMap() {
		if (this.featuresMap == null) {
			this.featuresMap = this.setFeaturesMap();
		}
		return this.featuresMap;
	}

	public Long getFreight() {
		if (freight == null) {
			return 0l;
		}
		return freight;
	}

	public String getFreightStr() {
		if (freight == null || freight < 0) {
			return "0";
		} else {
			return new Money(freight).toString();
		}
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public String getHlTitle() {
		return hlTitle;
	}

	public Long getId() {
		return id;
	}

	public Long getMailCosts() {
		return mailCosts == null ? 0l : mailCosts;
	}

	public Integer getNumFound() {
		return numFound;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public Long getPrice() {
		return price;
	}

	public String getPriceStr() {
		return new Money(price).toString();
	}

	public String getOldPriceStr() {
		if (activePriceOld != null && activePriceOld > 0) {
			return new Money(activePriceOld).toString();
		}
		return "0";
	}

	public String getProvinces() {
		return provinces;
	}

	public Long getSalesNum() {
		return salesNum;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public String getStoreCategories() {
		return storeCategories;
	}

	public String getTitle() {
		return title;
	}

	public void setActiveBeginDate(Date activeBeginDate) {
		this.activeBeginDate = activeBeginDate;
	}

	public void setActiveEndDate(Date activeEndDate) {
		this.activeEndDate = activeEndDate;
	}

	public void setActivePriceNew(Long activePriceNew) {
		this.activePriceNew = activePriceNew;
	}

	public void setActivePriceOld(Long activePriceOld) {
		this.activePriceOld = activePriceOld;
	}

	public void setAreaCarriage(String areaCarriage) {
		this.areaCarriage = areaCarriage;
	}

	public void setCarriage(String carriage) {
		this.carriage = carriage;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCurStock(Long curStock) {
		this.curStock = curStock;
	}

	public void setDeliveryCosts(Long deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}

	public void setEmsCosts(Long emsCosts) {
		this.emsCosts = emsCosts;
	}

	public void setEvaluateNum(Long evaluateNum) {
		this.evaluateNum = evaluateNum;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	/**
	 * 设置featureMAP
	 * 
	 * @author liuboen
	 * @return
	 */
	public Map<String, String> setFeaturesMap() {
		String featuresTemp = this.features;
		if (this.featuresMap == null) {
			Map<String, String> map = FeaturesUtil.getFeaturesMapByFeatures(featuresTemp);
			return map;
		} else {
			return this.featuresMap;
		}
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setHlTitle(String hlTitle) {
		this.hlTitle = hlTitle;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMailCosts(Long mailCosts) {
		this.mailCosts = mailCosts;
	}

	public void setNumFound(Integer numFound) {
		this.numFound = numFound;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public void setSalesNum(Long salesNum) {
		this.salesNum = salesNum;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public void setStoreCategories(String storeCategories) {
		this.storeCategories = storeCategories;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
