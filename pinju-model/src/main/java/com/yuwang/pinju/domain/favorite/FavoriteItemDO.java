package com.yuwang.pinju.domain.favorite;

import java.util.Date;

import com.yuwang.pinju.domain.item.ItemDO;

public class FavoriteItemDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 297165244175206085L;

    private ItemDO itemDO;
	
	
	private Long id;


    private Long memberId;
    
    private Long itemId;


    private Long shopId;


    private String shopName;


    private Long collectionCount;


    private String titleIdList;


    private Date favoriteDate;


    private String configuration;


    private String reamark;


    private Long isDelete;


    private Date modifiedDate;


    private Date createDate;
    
    private String favoriteDateStr;
    
    private Long categoryId;
    
    private String categoryName;
    
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


    public Long getId(){
        return id;
    }

    public Long getMemberId(){
        return memberId;
    }

    public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getShopId(){
        return shopId;
    }

    public String getShopName(){
        return shopName;
    }

    public Long getCollectionCount(){
        return collectionCount;
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

    public String getReamark(){
        return reamark;
    }

    public Long getIsDelete(){
        return isDelete;
    }

    public Date getModifiedDate(){
        return modifiedDate;
    }

    public ItemDO getItemDO() {
		return itemDO;
	}

	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
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

    public void setCollectionCount(Long collectionCount){
        this.collectionCount = collectionCount;
    }

    public void setTitleIdList(String titleIdList){
        this.titleIdList = titleIdList;
    }

    public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

    public String getFavoriteDateStr() {
		return favoriteDateStr;
	}

	public void setFavoriteDateStr(String favoriteDateStr) {
		this.favoriteDateStr = favoriteDateStr;
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

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}

