package com.yuwang.pinju.domain.search.index;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.yuwang.pinju.domain.BaseDO;

@SuppressWarnings("serial")
public class ShopIndexD0 extends BaseDO {
	@Field("id")
	private Long id;
	
	@Field("shopId")
	private Long shopId;
	
	@Field("name")
	private String name;  
	
	@Field("shopLogo")
	private String shopLogo; 
	
	@Field("title")
	private String title;   
	
	@Field("description")
	private String description;
	
	@Field("sellerType")
	private String sellerType;   
	
	@Field("shopCategory")
	private String shopCategory;  
	
	@Field("city")
	private String city;       
	
	@Field("provinces")
	private String provinces;   
	
	@Field("exchangeMargin")
	private String exchangeMargin;
	
	@Field("openDate")
	private Date openDate;   
	
	@Field("productCount")
	private Integer productCount;  
	
	@Field("nickName")
	private String nickName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvinces() {
		return provinces;
	}
	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}
	public String getExchangeMargin() {
		return exchangeMargin;
	}
	public void setExchangeMargin(String exchangeMargin) {
		this.exchangeMargin = exchangeMargin;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Override
	public String toString() {
		return  
				"\n"+
				"id:"             +  id                 + "\n" +                
				"shopId:"         +  shopId             + "\n" +    
				"name:"           +  name               + "\n" +      
				"shopLogo:"       +  shopLogo           + "\n" +  
				"title:"          +  title              + "\n" +     
				"description:"    +  description        + "\n" + 
				"sellerType:"     +  sellerType         + "\n" + 
				"shopCategory:"   +  shopCategory       + "\n" + 
				"city:"           +  city               + "\n" +      
				"provinces:"      +  provinces          + "\n" + 
				"exchangeMargin:" +  exchangeMargin     + "\n" + 
				"openDate:"       +  openDate           + "\n" +       
				"productCount:"   +  productCount       + "\n" +   
				"nickName:"       +  nickName           + "\n";
	}
}
