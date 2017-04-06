package com.yuwang.pinju.domain.favorite;

import java.util.Date;

import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class FavoriteShopDO implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2891912071340907123L;
	
	private ShopInfoDO shopInfoDO;
	
	private Integer goodsCount;


	private Long id;


    private Long memberId;


    private Long shopId;


    private String shopName;


    private String titleIdList;


    private Date favoriteDate;


    private String configuration;


    private String reamark;


    private Long isDelete;
    
    private Long categoryId;
    
    private String categoryName;


    private Date modifiedDate;


    private Date createDate;
    
    /**
	 * 查询开始数
	 */
	private int startRow;
	/**
	 * 每页显示数
	 */
	private int pageCount;
	
	/**
	 * 排序条件
	 */
	private String orderBy;
	
	private String favoriteDateStr;


    public Long getId(){
        return id;
    }

    public Long getMemberId(){
        return memberId;
    }

    public Long getShopId(){
        return shopId;
    }

    public String getShopName(){
        return shopName;
    }

    public String getTitleIdList(){
        return titleIdList;
    }

    public Date getFavoriteDate(){
        return favoriteDate;
    }

    public String getConfiguration(){
        return configuration;
    }

    public Long getCategoryId() {
		return categoryId;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getReamark(){
        return reamark;
    }

    public Long getIsDelete(){
        return isDelete;
    }

    public Date getModifiedDate(){
        return modifiedDate;
    }

    public Date getCreateDate(){
        return createDate;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public void setShopId(Long shopId){
        this.shopId = shopId;
    }

    public void setShopName(String shopName){
        this.shopName = shopName;
    }

    public void setTitleIdList(String titleIdList){
        this.titleIdList = titleIdList;
    }

    public void setFavoriteDate(Date favoriteDate){
        this.favoriteDate = favoriteDate;
    }

    public void setConfiguration(String configuration){
        this.configuration = configuration;
    }

    public void setReamark(String reamark){
        this.reamark = reamark;
    }

    public void setIsDelete(Long isDelete){
        this.isDelete = isDelete;
    }

    public void setModifiedDate(Date modifiedDate){
        this.modifiedDate = modifiedDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

	public int getStartRow() {
		return startRow;
	}

	public int getPageCount() {
		return pageCount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public ShopInfoDO getShopInfoDO() {
		return shopInfoDO;
	}

	public void setShopInfoDO(ShopInfoDO shopInfoDO) {
		this.shopInfoDO = shopInfoDO;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getFavoriteDateStr() {
		return favoriteDateStr;
	}

	public void setFavoriteDateStr(String favoriteDateStr) {
		this.favoriteDateStr = favoriteDateStr;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}

