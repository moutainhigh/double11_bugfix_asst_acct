package com.yuwang.pinju.domain.item;

import java.util.Date;

/**
 * 
 * @author liming
 * 
 */
public class SkuDO implements java.io.Serializable {

	private static final long serialVersionUID = 6651856656179080204L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 卖家编号
	 */
	private Long sellerId;

	/**
	 * 商品编号
	 */
	private Long itemId;

	/**
	 * 销售属性值对1
	 */
	private String salePv1;

	/**
	 * 销售属性值对2
	 */
	private String salePv2;

	/**
	 * 销售属性值对3
	 */
	private String salePv3;

	/**
	 * 销售属性值对4
	 */
	private String salePv4;

	/**
	 * 价格
	 */
	private Long price;

	/**
	 * sku价格（api）
	 */
	private String skuPrice;
	
	/**
	 * 初始数量
	 */
	private Long oriStock;

	/**
	 * 当前数量
	 */
	private Long currentStock;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	private Long status;

	private String sellerCode;
	
	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getSalePv1() {
		return this.salePv1;
	}

	public void setSalePv1(String salePv1) {
		this.salePv1 = salePv1;
	}

	public String getSalePv2() {
		return this.salePv2;
	}

	public void setSalePv2(String salePv2) {
		this.salePv2 = salePv2;
	}

	public String getSalePv3() {
		return this.salePv3;
	}

	public void setSalePv3(String salePv3) {
		this.salePv3 = salePv3;
	}

	public String getSalePv4() {
		return this.salePv4;
	}

	public void setSalePv4(String salePv4) {
		this.salePv4 = salePv4;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	
	public String getSkuPrice() {
		return this.skuPrice;
	}

	public void setSkuPrice(String skuPrice) {
		this.skuPrice = skuPrice;
	}

	public Long getOriStock() {
		return this.oriStock;
	}

	public void setOriStock(Long oriStock) {
		this.oriStock = oriStock;
	}

	public Long getCurrentStock() {
		return this.currentStock;
	}

	public void setCurrentStock(Long currentStock) {
		this.currentStock = currentStock;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}