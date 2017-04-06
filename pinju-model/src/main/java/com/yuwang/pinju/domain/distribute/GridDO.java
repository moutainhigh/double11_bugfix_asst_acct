package com.yuwang.pinju.domain.distribute;

import java.io.Serializable;

public class GridDO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7013456195372593577L;
    /**
     * 商品链接地址
     */
    private String url;
    /**
     * 商品图片地址
     */
    private String picUrl;
    /**
     * 商品单价
     */
    private String priceByYuan;
    /**
     * 商品名称
     */
    private String title;
    /**
     * 商品当前库存
     */
    private Long curStock;
    /**
     * 商品原始库存
     */
    private Long oriStock;

    /**
     * 同意数
     */
    private Integer agreeCount;

    /**
     * 反对数
     */
    private Integer opposeCount;

    /**
     * 推荐原因
     */
    private String recommendReason;
    
    /**
     * 商品ID
     */
    private Long id;

    public GridDO(String url, String picUrl, String priceByYuan, String title,
	    Long curStock, Long oriStock, Long id) {
	super();
	this.url = url;
	this.picUrl = picUrl;
	this.priceByYuan = priceByYuan;
	this.title = title;
	this.curStock = curStock;
	this.oriStock = oriStock;
	this.id = id;
    }
    
    public GridDO() {
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getPicUrl() {
	return picUrl;
    }

    public void setPicUrl(String picUrl) {
	this.picUrl = picUrl;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Long getCurStock() {
	return curStock;
    }

    public void setCurStock(Long curStock) {
	this.curStock = curStock;
    }

    public Long getOriStock() {
	return oriStock;
    }

    public void setOriStock(Long oriStock) {
	this.oriStock = oriStock;
    }

    public String getPriceByYuan() {
        return priceByYuan;
    }

    public void setPriceByYuan(String priceByYuan) {
        this.priceByYuan = priceByYuan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getOpposeCount() {
		return opposeCount;
	}

	public void setOpposeCount(Integer opposeCount) {
		this.opposeCount = opposeCount;
	}

	public String getRecommendReason() {
		return recommendReason;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}
}
