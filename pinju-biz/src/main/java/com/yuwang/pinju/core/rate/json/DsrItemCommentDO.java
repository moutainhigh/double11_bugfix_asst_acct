package com.yuwang.pinju.core.rate.json;

import com.yuwang.pinju.domain.rate.DsrCommentDO;

/**
 * <p>DSR 商品评价数据</p>
 *
 * @author gaobaowen
 * @since 2011-7-12 15:07:12
 */
public class DsrItemCommentDO {

	private Long itemId;
	private String itemName;
	private String itemImgUrl;
	private Long itemSkuId;
	private String itemSkuAttr;
	private Long itemOrignalPrice;
	private Long itemOrderPrice;
	private String comment;
	private String dsrRate;
	private Integer anonymous;
	private Integer shareSns;
	private Integer status;

	DsrItemCommentDO(DsrCommentDO dsrComment) {
		this.itemId = dsrComment.getItemId();
		this.itemName = dsrComment.getItemTitle();
		this.itemImgUrl = dsrComment.getItemImgUrl();
		this.itemSkuId = dsrComment.getItemSkuId();
		this.itemSkuAttr = dsrComment.getItemSkuAttributes();
		this.itemOrignalPrice = dsrComment.getItemOrignalPrice();
		this.itemOrderPrice = dsrComment.getItemOrderPrice();
		this.comment = dsrComment.getBuyerComment();
		this.dsrRate = dsrComment.getDsrRate();
		this.anonymous = dsrComment.getAnonymous();
		this.shareSns = dsrComment.getShareSns();
		this.status = dsrComment.getStatus();
	}

	public Long getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemImgUrl() {
		return itemImgUrl;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public String getItemSkuAttr() {
		return itemSkuAttr;
	}

	public Long getItemOrignalPrice() {
		return itemOrignalPrice;
	}

	public Long getItemOrderPrice() {
		return itemOrderPrice;
	}
	
	public String getComment() {
		return comment;
	}

	public String getDsrRate() {
		return dsrRate;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public Integer getShareSns() {
		return shareSns;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anonymous == null) ? 0 : anonymous.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((dsrRate == null) ? 0 : dsrRate.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((itemImgUrl == null) ? 0 : itemImgUrl.hashCode());
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((itemOrderPrice == null) ? 0 : itemOrderPrice.hashCode());
		result = prime * result + ((itemOrignalPrice == null) ? 0 : itemOrignalPrice.hashCode());
		result = prime * result + ((itemSkuAttr == null) ? 0 : itemSkuAttr.hashCode());
		result = prime * result + ((itemSkuId == null) ? 0 : itemSkuId.hashCode());
		result = prime * result + ((shareSns == null) ? 0 : shareSns.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DsrItemCommentDO other = (DsrItemCommentDO) obj;
		if (anonymous == null) {
			if (other.anonymous != null)
				return false;
		} else if (!anonymous.equals(other.anonymous))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (dsrRate == null) {
			if (other.dsrRate != null)
				return false;
		} else if (!dsrRate.equals(other.dsrRate))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (itemImgUrl == null) {
			if (other.itemImgUrl != null)
				return false;
		} else if (!itemImgUrl.equals(other.itemImgUrl))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (itemOrderPrice == null) {
			if (other.itemOrderPrice != null)
				return false;
		} else if (!itemOrderPrice.equals(other.itemOrderPrice))
			return false;
		if (itemOrignalPrice == null) {
			if (other.itemOrignalPrice != null)
				return false;
		} else if (!itemOrignalPrice.equals(other.itemOrignalPrice))
			return false;
		if (itemSkuAttr == null) {
			if (other.itemSkuAttr != null)
				return false;
		} else if (!itemSkuAttr.equals(other.itemSkuAttr))
			return false;
		if (itemSkuId == null) {
			if (other.itemSkuId != null)
				return false;
		} else if (!itemSkuId.equals(other.itemSkuId))
			return false;
		if (shareSns == null) {
			if (other.shareSns != null)
				return false;
		} else if (!shareSns.equals(other.shareSns))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	public StringBuilder toStringBuilder(int index, StringBuilder builder) {
		builder.append(index).append(". DsrItemCommentDO");
		builder.append(": itemId=[").append(itemId).append("]");
		builder.append(": itemName=[").append(itemName).append("]");
		builder.append(": itemImgUrl=[").append(itemImgUrl).append("]");
		builder.append(": itemSkuId=[").append(itemSkuId).append("]");
		builder.append(": itemSkuAttr=[").append(itemSkuAttr).append("]");
		builder.append(": itemOrignalPrice=[").append(itemOrignalPrice).append("]");
		builder.append(": itemOrderPrice=[").append(itemOrderPrice).append("]");
		builder.append(": comment=[").append(comment).append("]");
		builder.append(": dsrRate=[").append(dsrRate).append("]");
		builder.append(": anonymous=[").append(anonymous).append("]");
		builder.append(": shareSns=[").append(shareSns).append("]");
		builder.append(": status=[").append(status).append("]");
		return builder;
	}
}
