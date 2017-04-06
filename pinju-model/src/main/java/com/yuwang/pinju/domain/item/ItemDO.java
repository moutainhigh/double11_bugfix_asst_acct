package com.yuwang.pinju.domain.item;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.common.FeaturesUtil;
import com.yuwang.pinju.common.ImageUtil;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;

/**
 * 
 * @author liming
 * 
 */
public class ItemDO implements java.io.Serializable {

	private static final long serialVersionUID = 6580037002128888343L;

	private Long id;
	private Long catetoryId;
	private Long spuId;
	private String title;
	private String storeCategories;
	private String description;
	private String propertyValuePair;
	private Long itemType;
	private Long sellerId;
	private String picUrl;
	private Long picColor;
	private Long price;
	private String provinces;
	private String city;
	private Long deliveryCosts;
	private Long mailCosts;
	private Long emsCosts;
	private Long freeTemplateId;
	private Date startTime;
	private Date endTime;
	private Long curStock;
	private Long oriStock;
	private Long status;
	private Long itemDegree;
	private Long recommendedStatus;
	private Long collectionNumber;
	private String features;
	private Long expiredDate;
	private Date lastModified;
	private Date gmtModified;
	private Date gmtCreate;
	private String code;
	private String sellerNick;
	private Long version;
	private Map<String,String> featuresMap;
	private String itemKey;
	
	private List<SkuDO> skuList;
	private List<CustomProValueDO> customSkuList;
	private List<ItemPicDO> itemPicList;

	//该商品是否存在
	private Boolean isExist;
	
	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	/**
	 * 价格，单位：元
	 */
	private String priceByYuan;

	// 以下为非数据库字段

	/**
	 * 运费类型
	 */
	private int freightType = 1;

	/**
	 * 有效期类型
	 */
	private int expiryType;

	/**
	 * 发布类型
	 */
	private int releasedType;

	/**
	 * 延迟发布 年月日
	 */
	private String releasedYear;

	/**
	 * 延迟发布分钟
	 */
	private String releasedMinute;

	/**
	 * 延迟发布小时
	 */
	private String releasedHour;

	/**
	 * 图片
	 */
	private File picFile;

	public Long getCatetoryId() {
		return this.catetoryId;
	}

	public String getCity() {
		return this.city;
	}

	public String getCode() {
		return this.code;
	}

	public Long getCollectionNumber() {
		return this.collectionNumber;
	}

	public Long getCurStock() {
		return this.curStock;
	}

	public Long getDeliveryCosts() {
		return this.deliveryCosts;
	}

	public Long getEmsCosts() {
		return this.emsCosts;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public Long getExpiredDate() {
		return this.expiredDate;
	}

	public int getExpiryType() {
		return expiryType;
	}

	public String getFeatures() {
		return this.features;
	}


	public int getFreightType() {
		return freightType;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public Long getId() {
		return this.id;
	}

	public Long getItemDegree() {
		return this.itemDegree;
	}

	public Long getItemType() {
		return this.itemType;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public Long getMailCosts() {
		return this.mailCosts;
	}

	public Long getOriStock() {
		return this.oriStock;
	}

	public Long getPicColor() {
		return this.picColor;
	}

	public File getPicFile() {
		return picFile;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public Long getPrice() {
		return this.price;
	}

	/**
	 * 返回单位元的价格
	 * 
	 * @return
	 * String
	 *
	 */
	public String getPriceByYuan() {
		return priceByYuan;
	}

	public String getPriceStr() {
		return new Money(price).toString();
	}

	public String getPropertyValuePair() {
		return this.propertyValuePair;
	}

	public String getProvinces() {
		return this.provinces;
	}

	public Long getRecommendedStatus() {
		return this.recommendedStatus;
	}

	public String getReleasedHour() {
		return releasedHour;
	}

	public String getReleasedMinute() {
		return releasedMinute;
	}

	public int getReleasedType() {
		return releasedType;
	}

	public String getReleasedYear() {
		return releasedYear;
	}

	/**
	 * 销量
	 * 
	 * @return
	 */
//	@JSON(serialize=false)
//	public Long getSales() {
//		if (oriStock - curStock > 0) {
//			return oriStock - curStock;
//		}
//		return 0l;
//	}
	private Long sales; 
	
	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public Long getSpuId() {
		return this.spuId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public Long getStatus() {
		return this.status;
	}

	public String getStoreCategories() {
		return storeCategories;
	}

	public String getTitle() {
		return this.title;
	}

	public void setCatetoryId(Long catetoryId) {
		this.catetoryId = catetoryId;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCollectionNumber(Long collectionNumber) {
		this.collectionNumber = collectionNumber;
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

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setExpiredDate(Long expiredDate) {
		this.expiredDate = expiredDate;
	}

	public void setExpiryType(int expiryType) {
		this.expiryType = expiryType;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public void setFreightType(int freightType) {
		this.freightType = freightType;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItemDegree(Long itemDegree) {
		this.itemDegree = itemDegree;
	}

	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setMailCosts(Long mailCosts) {
		this.mailCosts = mailCosts;
	}

	public void setOriStock(Long oriStock) {
		this.oriStock = oriStock;
	}

	public void setPicColor(Long picColor) {
		this.picColor = picColor;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setPrice(Long price) {
		this.price = price;
		this.priceByYuan = MoneyUtil.getCentToDollar(this.price);
	}

	public void setPropertyValuePair(String propertyValuePair) {
		this.propertyValuePair = propertyValuePair;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public void setRecommendedStatus(Long recommendedStatus) {
		this.recommendedStatus = recommendedStatus;
	}

	public void setReleasedHour(String releasedHour) {
		this.releasedHour = releasedHour;
	}

	public void setReleasedMinute(String releasedMinute) {
		this.releasedMinute = releasedMinute;
	}

	public void setReleasedType(int releasedType) {
		this.releasedType = releasedType;
	}

	public void setReleasedYear(String releasedYear) {
		this.releasedYear = releasedYear;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public void setStoreCategories(String storeCategories) {
		this.storeCategories = storeCategories;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	

	/**
	 * @return the freeTemplateId
	 */
	public Long getFreeTemplateId() {
		return freeTemplateId;
	}

	/**
	 * @param freeTemplateId the freeTemplateId to set
	 */
	public void setFreeTemplateId(Long freeTemplateId) {
		this.freeTemplateId = freeTemplateId;
	}

	/**
	 * 设置featureMAP
	 * @author liuboen
	 * @return
	 */
	public Map<String,String> setFeaturesMap(){
		String featuresTemp=this.features;
		if (this.featuresMap==null) {
			Map<String,String> map=FeaturesUtil.getFeaturesMapByFeatures(featuresTemp);
			return map;
		}else {
			return this.featuresMap;
		}
	}

	/**
	 * @return the featuresMap
	 */
	public Map<String, String> getFeaturesMap() {
		if (this.featuresMap==null) {
			this.featuresMap=this.setFeaturesMap();
		}
		return this.featuresMap;
	}

	/**
	 * @return the skuList
	 */
	public List<SkuDO> getSkuList() {
		return skuList;
	}

	/**
	 * @param skuList the skuList to set
	 */
	public void setSkuList(List<SkuDO> skuList) {
		this.skuList = skuList;
	}

	/**
	 * @return the skuList
	 */
	public List<ItemPicDO> getItemPicList() {
		return itemPicList;
	}

	/**
	 * @return the isExist
	 */
	public Boolean getIsExist() {
		return isExist;
	}

	/**
	 * @param isExist the isExist to set
	 */
	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	/**
	 * @param skuList the skuList to set
	 */
	public void setItemPicList(List<ItemPicDO> itemPicList) {
		this.itemPicList = itemPicList;
	}
	
	/**
	 * 通过不同类型枚举获取features字段信息
	 * @author liuboen
	 * @param featuresEnum
	 * @return
	 */
	public String getFeatureByEnum(ItemFeaturesEnum featuresEnum){
		return this.getFeaturesMap().get(featuresEnum.getFeatureName().toString());
	}
	
	/**
	 * 获取商品Thumb图
	 * @author liuboen
	 * @return
	 */
	public String getThumbJpg(){
		if (this.picUrl==null||picUrl.equals("")) {
			return "";
		}
		return ImageUtil.getJpgThumb(picUrl);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CustomProValueDO> getCustomSkuList() {
		return customSkuList;
	}

	public void setCustomSkuList(List<CustomProValueDO> customSkuList) {
		this.customSkuList = customSkuList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemDO [catetoryId=" + catetoryId + ", city=" + city
				+ ", curStock=" + curStock + ", endTime=" + endTime
				+ ", expiredDate=" + expiredDate + ", expiryType=" + expiryType
				+ ", features=" + features + ", freeTemplateId="
				+ freeTemplateId + ", freightType=" + freightType
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", id=" + id + ", picUrl=" + picUrl + ", price=" + price
				+ ", priceByYuan=" + priceByYuan + ", propertyValuePair="
				+ propertyValuePair + ", sellerId=" + sellerId
				+ ", sellerNick=" + sellerNick + ", spuId=" + spuId
				+ ", status=" + status + ", title=" + title + ", version="
				+ version + "]";
	}
}