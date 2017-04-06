package com.yuwang.pinju.domain.search.index;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.yuwang.pinju.domain.BaseDO;
/**
 * 搜索
 * @author zhouzhaohua
 *
 */
@SuppressWarnings("serial")
public class ItemIndexDO extends BaseDO {
	@Field("id")
	private Long id;
	
	@Field("catetoryId")
	private Long catetoryId;
	
	@Field("catetoryName")
	private String catetoryName;
	
	@Field("spuId")
	private Long spuId;
	
	@Field("title")
	private String title;
	
	@Field("storeCategories")
	private String storeCategories;
	
	@Field("sellerId")
	private Long sellerId;
	
	@Field("sellerNick")
	private String sellerNick;
	
	@Field("propertyValuePair")
	private String propertyValuePair;
	
	@Field("picUrl")
	private String picUrl;
	
	@Field("price")
	private Long price;
	
	@Field("provinces")
	private String provinces;
	
	@Field("city")
	private String city;
	
	@Field("deliveryCosts")
	private Long deliveryCosts;
	
	@Field("mailCosts")
	private Long mailCosts;
	
	@Field("emsCosts")
	private Long emsCosts;
	
	//private Long freeTemplateId;
	@Field("startTime")
	private Date startTime;
	
	@Field("endTime")
	private Date endTime;
	/**
	 *一个月成交量
	 */
	@Field("salesNum")
	private Long salesNum;
	/**
	 * 评论数
	 */
	@Field("evaluateNum")
	private Long evaluateNum;
	
	@Field("itemDegree")
	private Long itemDegree;
	
	@Field("features")
	private String features;
	
	@Field("status")
	private Long status;
	
	@Field("curStock")
	private Long curStock;
	
	@Field("gmtCreate")
	private Date gmtCreate;
	
	@Field("code")
	private String code;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCatetoryId() {
		return catetoryId;
	}
	public void setCatetoryId(Long catetoryId) {
		this.catetoryId = catetoryId;
	}
	public String getCatetoryName() {
		return catetoryName;
	}
	public void setCatetoryName(String catetoryName) {
		this.catetoryName = catetoryName;
	}
	public Long getSpuId() {
		return spuId;
	}
	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStoreCategories() {
		return storeCategories;
	}
	public void setStoreCategories(String storeCategories) {
		this.storeCategories = storeCategories;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	public String getPropertyValuePair() {
		return propertyValuePair;
	}
	public void setPropertyValuePair(String propertyValuePair) {
		this.propertyValuePair = propertyValuePair;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getProvinces() {
		return provinces;
	}
	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getDeliveryCosts() {
		return deliveryCosts;
	}
	public void setDeliveryCosts(Long deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}
	public Long getMailCosts() {
		return mailCosts;
	}
	public void setMailCosts(Long mailCosts) {
		this.mailCosts = mailCosts;
	}
	public Long getEmsCosts() {
		return emsCosts;
	}
	public void setEmsCosts(Long emsCosts) {
		this.emsCosts = emsCosts;
	}
	/*public Long getFreeTemplateId() {
		return freeTemplateId;
	}
	public void setFreeTemplateId(Long freeTemplateId) {
		this.freeTemplateId = freeTemplateId;
	}*/
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(Long salesNum) {
		this.salesNum = salesNum;
	}
	public Long getEvaluateNum() {
		return evaluateNum;
	}
	public void setEvaluateNum(Long evaluateNum) {
		this.evaluateNum = evaluateNum;
	}
	public Long getItemDegree() {
		return itemDegree;
	}
	public void setItemDegree(Long itemDegree) {
		this.itemDegree = itemDegree;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getCurStock() {
		return curStock;
	}
	public void setCurStock(Long curStock) {
		this.curStock = curStock;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String toString(){
		return 	
				"\n"+
				"id:" +                	id                +"\n"+
				"catetoryId:" +        	catetoryId        +"\n"+
				"catetoryName:" +      	catetoryName      +"\n"+
				"spuId:" +             	spuId             +"\n"+
				"title:" +             	title             +"\n"+
				"storeCategories:" +   	storeCategories   +"\n"+
				"sellerId:" +          	sellerId          +"\n"+
				"sellerNick:" +        	sellerNick        +"\n"+
				"propertyValuePair:" + 	propertyValuePair +"\n"+
				"picUrl:" +            	picUrl            +"\n"+
				"price:" +             	price             +"\n"+
				"provinces:" +         	provinces         +"\n"+
				"city:" +              	city              +"\n"+
				"deliveryCosts:" +     	deliveryCosts     +"\n"+
				"mailCosts:" +         	mailCosts         +"\n"+
				"emsCosts:" +          	emsCosts          +"\n"+
				"startTime:" +         	startTime         +"\n"+
				"endTime:" +           	endTime           +"\n"+
				"salesNum:" +          	salesNum          +"\n"+
				"evaluateNum:" +       	evaluateNum       +"\n"+
				"itemDegree:" +        	itemDegree        +"\n"+
				"features:" +          	features          +"\n"+
				"status:" +            	status            +"\n"+
				"curStock:" +          	curStock          +"\n"+
				"gmtCreate:" +         	gmtCreate         +"\n"+
				"code:" +              	code              +"\n";
	}
}
