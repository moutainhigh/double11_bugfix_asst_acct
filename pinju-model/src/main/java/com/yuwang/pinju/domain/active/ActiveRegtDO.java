package com.yuwang.pinju.domain.active;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 活动报名信息
 * @author qiuhongming
 *
 */
public class ActiveRegtDO extends BaseDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -847375703930986255L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 活动ID
	 */
	private Long activityId;
	/**
	 * 报名类型 1 - 商品；2 - 店铺
	 */
	private Integer registType;
	/**
	 * 商品URL
	 */
	private String auctionUrl = "";
	/**
	 * 商品标题
	 */
	private String auctionTitle = "";
	/**
	 * 促销图片
	 */
	private String salePic = "";
	/**
	 * 原价
	 */
	private Long oriPrice = 0L;
	/**
	 * 促销价
	 */
	private Long salePrice = 0L;
	/**
	 * 数量
	 */
	private Long auctionNum = 0L;
	/**
	 * 是否包邮 0 - 否；1 - 是 
	 */
	private Integer isIsbn = 0;
	/**
	 * 其他信息
	 */
	private String otherInfo;
	/**
	 * 店铺名称
	 */
	private String shopTitle;
	/**
	 * 店铺图片
	 */
	private String shopPic;
	/**
	 * 店铺地址
	 */
	private String shopUrl;
	/**
	 * 店铺其他信息
	 */
	private String shopInfo;
	/**
	 * 审核状态
	 */
	private Integer checkStatus;
	/**
	 * 审核人
	 */
	private String checkNick;
	/**
	 * 审核人数字ID
	 */
	private Long checkId;
	/**
	 * 商家昵称
	 */
	private String nickName;
	/**
	 * 会员编号
	 */
	private Long memberId;
	/**
	 * 折扣
	 */
	private Integer agio;
	/**
	 * 促销信息
	 */
	private String saleInfo;
	/**
	 * 店铺所属一级类目
	 */
	private Long categoryId;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 活动报名时间
	 */
	private Date gmtCreate;
	/**
	 * 报名信息最后修改时间
	 */
	private Date gmtModified;

	public ActiveRegtDO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Integer getRegistType() {
		return registType;
	}

	public void setRegistType(Integer registType) {
		this.registType = registType;
	}

	public String getAuctionUrl() {
		return auctionUrl;
	}

	public void setAuctionUrl(String auctionUrl) {
		this.auctionUrl = auctionUrl;
	}

	public String getAuctionTitle() {
		return auctionTitle;
	}

	public void setAuctionTitle(String auctionTitle) {
		this.auctionTitle = auctionTitle;
	}

	public String getSalePic() {
		return salePic;
	}

	public void setSalePic(String salePic) {
		this.salePic = salePic;
	}

	public Long getOriPrice() {
		return oriPrice;
	}

	public void setOriPrice(Long oriPrice) {
		this.oriPrice = oriPrice;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	public Long getAuctionNum() {
		return auctionNum;
	}

	public void setAuctionNum(Long auctionNum) {
		this.auctionNum = auctionNum;
	}

	public Integer getIsIsbn() {
		return isIsbn;
	}

	public void setIsIsbn(Integer isIsbn) {
		this.isIsbn = isIsbn;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public String getShopPic() {
		return shopPic;
	}

	public void setShopPic(String shopPic) {
		this.shopPic = shopPic;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(String shopInfo) {
		this.shopInfo = shopInfo;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckNick() {
		return checkNick;
	}

	public void setCheckNick(String checkNick) {
		this.checkNick = checkNick;
	}

	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getAgio() {
		return agio;
	}

	public void setAgio(Integer agio) {
		this.agio = agio;
	}

	public String getSaleInfo() {
		return saleInfo;
	}

	public void setSaleInfo(String saleInfo) {
		this.saleInfo = saleInfo;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
