package com.yuwang.pinju.domain.item;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class ItemInput {

	public static final int FILE_SIZE_K = 1024;

	public static final int MAX_IMAGE_SIZE = 500;

	/**
	 * 卖家承担运费 类型 1-运费模板 2-运费
	 */
	private int buyFreightType = 2;

	/**
	 * 类目编号
	 */
	private long catetoryId;

	/**
	 * 类目编号路径
	 */
	private String categoryPath;
	
	/**
	 * 城市
	 */
	@NotEmpty(message = "{item.city.empty}")
	private String city;

	/**
	 * 商家编码
	 */
	private String code;

	/**
	 * 快递价格
	 */
	private String deliveryCosts;

	/**
	 * 描述字符串
	 */
	@NotEmpty(message = "{item.description.empty}")
	private String description;

	/**
	 * EMS价格
	 */
	private String emsCosts;

	/**
	 * 有效期
	 */
	private int expiryDate;

	/**
	 * 有效期类型
	 */
	private int expiryType;

	/**
	 * 运费模板
	 */
	private String freeTemplates;

	/**
	 * 运费模板
	 */
	private String freeTemplatesName;
	
	public String getFreeTemplatesName() {
		return freeTemplatesName;
	}

	public void setFreeTemplatesName(String freeTemplatesName) {
		this.freeTemplatesName = freeTemplatesName;
	}

	/**
	 * 运费类型
	 */
	private int freightType = 2;

	/**
	 * 编号
	 */
	private long id;

	/**
	 * 新旧类型
	 */
	@Range(min = 1, max = 3, message = "{item.itemDegree.value}")
	private int itemDegree;

	/**
	 * 平邮价格
	 */
	private String mailCosts;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 数量
	 */
	private String number;

	/**
	 * 图片
	 */
	private File thisPicFile1;
	private String thisPicFile1ContentType;
	private String thisPicFile1FileName;
	
	private File thisPicFile2;
	private String thisPicFile2ContentType;
	private String thisPicFile2FileName;
	
	private File thisPicFile3;
	private String thisPicFile3ContentType;
	private String thisPicFile3FileName;
	
	private File thisPicFile4;
	private String thisPicFile4ContentType;
	private String thisPicFile4FileName;
	
	private File thisPicFile5;
	private String thisPicFile5ContentType;
	private String thisPicFile5FileName;
	
	private File[] picFile;

	private String[] picFileContentType;

	private String[] picFileFileName;

	/**
	 * 图片路径
	 */
	private String picUrl;

	private String[] itemPicUrl;
	
	private String[] itemEditPicUrl;

	/**
	 * 单价
	 */
	@NotEmpty(message = "{item.price.empty}")
	private String price;

	/**
	 * 属性字符串
	 */
	private String propertyValuePair;

	/**
	 * 地区
	 */
	@NotEmpty(message = "{item.provinces.empty}")
	private String provinces;

	/**
	 * 延迟发布小时
	 */
	private String releasedHour;

	/**
	 * 延迟发布分钟
	 */
	private String releasedMinute;

	/**
	 * 发布类型
	 */
	private int releasedType;

	/**
	 * 延迟发布 年月日
	 */
	private String releasedYear;

	/**
	 * 卖家
	 */
	private long sellerId;

	/**
	 * sku数量
	 */
	private String skuIds[];
	
	/**
	 * 自定义sku数量
	 */
	private String skuCustomIds[];
	
	/**
	 * sku属性
	 */
	private SkuDO[] skuList;

	/**
	 * sku属性字符串
	 */
	private String skuPropertyValuePair;

	/**
	 * spu编号
	 */
	private long spuId;

	/**
	 * 店铺类型串
	 */
	private String storeCategories;

	/**
	 * 标题
	 */
	@NotEmpty(message = "{item.title.empty}")
	private String title;

	public int getBuyFreightType() {
		return buyFreightType;
	}

	public long getCatetoryId() {
		return catetoryId;
	}

	public String getCity() {
		return StringUtils.trimToEmpty(city);
	}

	public String getCode() {
		return StringUtils.trimToEmpty(code);
	}

	public String getDeliveryCosts() {
		return StringUtils.trimToEmpty(deliveryCosts).replaceAll(" ", "");
	}

	public String getDescription() {
		return StringUtils.trimToEmpty(description);
	}
	
	public String getEmsCosts() {
		return StringUtils.trimToEmpty(emsCosts).replaceAll(" ", "");
	}

	public int getExpiryDate() {
		return expiryDate;
	}

	public int getExpiryType() {
		return expiryType;
	}

	public String getFreeTemplates() {
		return freeTemplates;
	}

	public int getFreightType() {
		return freightType;
	}

	public long getId() {
		return id;
	}

	public int getItemDegree() {
		return itemDegree;
	}

	public String getMailCosts() {
		return StringUtils.trimToEmpty(mailCosts).replaceAll(" ", "");
	}

	public String getNickName() {
		return StringUtils.trimToEmpty(nickName);
	}

	public String getNumber() {
		return StringUtils.trimToEmpty(number);
	}

	public File[] getPicFile() {
		return picFile;
	}

	public String[] getPicFileContentType() {
		return picFileContentType;
	}

	public String[] getPicFileFileName() {
		return picFileFileName;
	}

	public String getPicUrl() {
		return StringUtils.trimToEmpty(picUrl);
	}

	public String getPrice() {
		return StringUtils.trimToEmpty(price);
	}

	public String getPropertyValuePair() {
		return StringUtils.trimToEmpty(propertyValuePair);
	}

	public String getProvinces() {
		return StringUtils.trimToEmpty(provinces);
	}

	public String getReleasedHour() {
		return StringUtils.trimToEmpty(releasedHour);
	}

	public String getReleasedMinute() {
		return StringUtils.trimToEmpty(releasedMinute);
	}

	public int getReleasedType() {
		return releasedType;
	}

	public String getReleasedYear() {
		return releasedYear;
	}

	public long getSellerId() {
		return sellerId;
	}

	public String[] getSkuIds() {
		return skuIds;
	}

	public String getSkuPropertyValuePair() {
		return StringUtils.trimToEmpty(skuPropertyValuePair);
	}

	public long getSpuId() {
		return spuId;
	}

	public String getStoreCategories() {
		return StringUtils.trimToEmpty(storeCategories);
	}

	public String getTitle() {
		return StringUtils.trimToEmpty(title);
	}

	public void setBuyFreightType(int buyFreightType) {
		this.buyFreightType = buyFreightType;
	}

	public void setCatetoryId(long catetoryId) {
		this.catetoryId = catetoryId;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDeliveryCosts(String deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmsCosts(String emsCosts) {
		this.emsCosts = emsCosts;
	}

	public void setExpiryDate(int expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setExpiryType(int expiryType) {
		this.expiryType = expiryType;
	}

	public void setFreeTemplates(String freeTemplates) {
		this.freeTemplates = freeTemplates;
	}

	public void setFreightType(int freightType) {
		this.freightType = freightType;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setItemDegree(int itemDegree) {
		this.itemDegree = itemDegree;
	}

	public void setMailCosts(String mailCosts) {
		this.mailCosts = mailCosts;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPicFile(File[] picFile) {
		this.picFile = picFile;
	}

	public void setPicFileContentType(String[] picFileContentType) {
		this.picFileContentType = picFileContentType;
	}

	public void setPicFileFileName(String[] picFileFileName) {
		this.picFileFileName = picFileFileName;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setPropertyValuePair(String propertyValuePair) {
		this.propertyValuePair = propertyValuePair;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
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

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public void setSkuIds(String[] skuIds) {
		this.skuIds = skuIds;
	}

	public void setSkuPropertyValuePair(String skuPropertyValuePair) {
		this.skuPropertyValuePair = skuPropertyValuePair;
	}

	public void setSpuId(long spuId) {
		this.spuId = spuId;
	}

	public void setStoreCategories(String storeCategories) {
		this.storeCategories = storeCategories;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String[] getItemPicUrl() {
		return itemPicUrl;
	}

	public void setItemPicUrl(String[] itemPicUrl) {
		this.itemPicUrl = itemPicUrl;
	}
	
	public String[] getItemEditPicUrl() {
		return itemEditPicUrl;
	}

	public void setItemEditPicUrl(String[] itemEditPicUrl) {
		this.itemEditPicUrl = itemEditPicUrl;
	}
	
	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	
	public SkuDO[] getSkuList() {
		return skuList;
	}

	public void setSkuList(SkuDO[] skuList) {
		this.skuList = skuList;
	}
	
	public File getThisPicFile1() {
		return thisPicFile1;
	}

	public void setThisPicFile1(File thisPicFile1) {
		this.thisPicFile1 = thisPicFile1;
	}

	public File getThisPicFile2() {
		return thisPicFile2;
	}

	public void setThisPicFile2(File thisPicFile2) {
		this.thisPicFile2 = thisPicFile2;
	}

	public File getThisPicFile3() {
		return thisPicFile3;
	}

	public void setThisPicFile3(File thisPicFile3) {
		this.thisPicFile3 = thisPicFile3;
	}

	public File getThisPicFile4() {
		return thisPicFile4;
	}

	public void setThisPicFile4(File thisPicFile4) {
		this.thisPicFile4 = thisPicFile4;
	}

	public File getThisPicFile5() {
		return thisPicFile5;
	}

	public void setThisPicFile5(File thisPicFile5) {
		this.thisPicFile5 = thisPicFile5;
	}
	
	public String getThisPicFile1ContentType() {
		return thisPicFile1ContentType;
	}

	public void setThisPicFile1ContentType(String thisPicFile1ContentType) {
		this.thisPicFile1ContentType = thisPicFile1ContentType;
	}

	public String getThisPicFile1FileName() {
		return thisPicFile1FileName;
	}

	public void setThisPicFile1FileName(String thisPicFile1FileName) {
		this.thisPicFile1FileName = thisPicFile1FileName;
	}

	public String getThisPicFile2ContentType() {
		return thisPicFile2ContentType;
	}

	public void setThisPicFile2ContentType(String thisPicFile2ContentType) {
		this.thisPicFile2ContentType = thisPicFile2ContentType;
	}

	public String getThisPicFile2FileName() {
		return thisPicFile2FileName;
	}

	public void setThisPicFile2FileName(String thisPicFile2FileName) {
		this.thisPicFile2FileName = thisPicFile2FileName;
	}

	public String getThisPicFile3ContentType() {
		return thisPicFile3ContentType;
	}

	public void setThisPicFile3ContentType(String thisPicFile3ContentType) {
		this.thisPicFile3ContentType = thisPicFile3ContentType;
	}

	public String getThisPicFile3FileName() {
		return thisPicFile3FileName;
	}

	public void setThisPicFile3FileName(String thisPicFile3FileName) {
		this.thisPicFile3FileName = thisPicFile3FileName;
	}

	public String getThisPicFile4ContentType() {
		return thisPicFile4ContentType;
	}

	public void setThisPicFile4ContentType(String thisPicFile4ContentType) {
		this.thisPicFile4ContentType = thisPicFile4ContentType;
	}

	public String getThisPicFile4FileName() {
		return thisPicFile4FileName;
	}

	public void setThisPicFile4FileName(String thisPicFile4FileName) {
		this.thisPicFile4FileName = thisPicFile4FileName;
	}

	public String getThisPicFile5ContentType() {
		return thisPicFile5ContentType;
	}

	public void setThisPicFile5ContentType(String thisPicFile5ContentType) {
		this.thisPicFile5ContentType = thisPicFile5ContentType;
	}

	public String getThisPicFile5FileName() {
		return thisPicFile5FileName;
	}

	public void setThisPicFile5FileName(String thisPicFile5FileName) {
		this.thisPicFile5FileName = thisPicFile5FileName;
	}

	public String[] getSkuCustomIds() {
		return skuCustomIds;
	}

	public void setSkuCustomIds(String[] skuCustomIds) {
		this.skuCustomIds = skuCustomIds;
	}
}
