package com.yuwang.pinju.domain.member;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

public class MemberExtDO extends BaseDO {
	
	public static final String BUYER_CITY = "CITY:1;";

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long memberId;
	
	private Integer buyerTransNum;
	
	private Integer sellerTransNum;
	
	private Integer buyerTransGoodsNum;
	
	private Integer sellerTransGoodsNum;
	
	private Long buyerTransAmount;
	
	private Long sellerTransAmount;
	
	private Integer sellerGoodsNum;
	
	private Integer promotionNum;
	
	private Integer buyerLevel;
	
	private Integer sellerLevel;
	
	private String buyerExt;
	
	private String sellerExt;
	
	private Integer version;
	
	private Date gmtCreate;
	
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getBuyerTransNum() {
		return buyerTransNum;
	}

	public void setBuyerTransNum(Integer buyerTransNum) {
		this.buyerTransNum = buyerTransNum;
	}

	public Integer getSellerTransNum() {
		return sellerTransNum;
	}

	public void setSellerTransNum(Integer sellerTransNum) {
		this.sellerTransNum = sellerTransNum;
	}

	public Integer getBuyerTransGoodsNum() {
		return buyerTransGoodsNum;
	}

	public void setBuyerTransGoodsNum(Integer buyerTransGoodsNum) {
		this.buyerTransGoodsNum = buyerTransGoodsNum;
	}

	public Integer getSellerTransGoodsNum() {
		return sellerTransGoodsNum;
	}

	public void setSellerTransGoodsNum(Integer sellerTransGoodsNum) {
		this.sellerTransGoodsNum = sellerTransGoodsNum;
	}

	public Long getBuyerTransAmount() {
		return buyerTransAmount;
	}

	public void setBuyerTransAmount(Long buyerTransAmount) {
		this.buyerTransAmount = buyerTransAmount;
	}

	public Long getSellerTransAmount() {
		return sellerTransAmount;
	}

	public void setSellerTransAmount(Long sellerTransAmount) {
		this.sellerTransAmount = sellerTransAmount;
	}

	public Integer getSellerGoodsNum() {
		return sellerGoodsNum;
	}

	public void setSellerGoodsNum(Integer sellerGoodsNum) {
		this.sellerGoodsNum = sellerGoodsNum;
	}

	public Integer getPromotionNum() {
		return promotionNum;
	}

	public void setPromotionNum(Integer promotionNum) {
		this.promotionNum = promotionNum;
	}

	public Integer getBuyerLevel() {
		return buyerLevel;
	}

	public void setBuyerLevel(Integer buyerLevel) {
		this.buyerLevel = buyerLevel;
	}

	public Integer getSellerLevel() {
		return sellerLevel;
	}

	public void setSellerLevel(Integer sellerLevel) {
		this.sellerLevel = sellerLevel;
	}

	public String getBuyerExt() {
		return buyerExt;
	}

	public void setBuyerExt(String buyerExt) {
		this.buyerExt = buyerExt;
	}

	public String getSellerExt() {
		return sellerExt;
	}

	public void setSellerExt(String sellerExt) {
		this.sellerExt = sellerExt;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((buyerExt == null) ? 0 : buyerExt.hashCode());
		result = prime * result
				+ ((buyerLevel == null) ? 0 : buyerLevel.hashCode());
		result = prime
				* result
				+ ((buyerTransAmount == null) ? 0 : buyerTransAmount.hashCode());
		result = prime
				* result
				+ ((buyerTransGoodsNum == null) ? 0 : buyerTransGoodsNum
						.hashCode());
		result = prime * result
				+ ((buyerTransNum == null) ? 0 : buyerTransNum.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((promotionNum == null) ? 0 : promotionNum.hashCode());
		result = prime * result
				+ ((sellerExt == null) ? 0 : sellerExt.hashCode());
		result = prime * result
				+ ((sellerGoodsNum == null) ? 0 : sellerGoodsNum.hashCode());
		result = prime * result
				+ ((sellerLevel == null) ? 0 : sellerLevel.hashCode());
		result = prime
				* result
				+ ((sellerTransAmount == null) ? 0 : sellerTransAmount
						.hashCode());
		result = prime
				* result
				+ ((sellerTransGoodsNum == null) ? 0 : sellerTransGoodsNum
						.hashCode());
		result = prime * result
				+ ((sellerTransNum == null) ? 0 : sellerTransNum.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberExtDO other = (MemberExtDO) obj;
		if (buyerExt == null) {
			if (other.buyerExt != null)
				return false;
		} else if (!buyerExt.equals(other.buyerExt))
			return false;
		if (buyerLevel == null) {
			if (other.buyerLevel != null)
				return false;
		} else if (!buyerLevel.equals(other.buyerLevel))
			return false;
		if (buyerTransAmount == null) {
			if (other.buyerTransAmount != null)
				return false;
		} else if (!buyerTransAmount.equals(other.buyerTransAmount))
			return false;
		if (buyerTransGoodsNum == null) {
			if (other.buyerTransGoodsNum != null)
				return false;
		} else if (!buyerTransGoodsNum.equals(other.buyerTransGoodsNum))
			return false;
		if (buyerTransNum == null) {
			if (other.buyerTransNum != null)
				return false;
		} else if (!buyerTransNum.equals(other.buyerTransNum))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (promotionNum == null) {
			if (other.promotionNum != null)
				return false;
		} else if (!promotionNum.equals(other.promotionNum))
			return false;
		if (sellerExt == null) {
			if (other.sellerExt != null)
				return false;
		} else if (!sellerExt.equals(other.sellerExt))
			return false;
		if (sellerGoodsNum == null) {
			if (other.sellerGoodsNum != null)
				return false;
		} else if (!sellerGoodsNum.equals(other.sellerGoodsNum))
			return false;
		if (sellerLevel == null) {
			if (other.sellerLevel != null)
				return false;
		} else if (!sellerLevel.equals(other.sellerLevel))
			return false;
		if (sellerTransAmount == null) {
			if (other.sellerTransAmount != null)
				return false;
		} else if (!sellerTransAmount.equals(other.sellerTransAmount))
			return false;
		if (sellerTransGoodsNum == null) {
			if (other.sellerTransGoodsNum != null)
				return false;
		} else if (!sellerTransGoodsNum.equals(other.sellerTransGoodsNum))
			return false;
		if (sellerTransNum == null) {
			if (other.sellerTransNum != null)
				return false;
		} else if (!sellerTransNum.equals(other.sellerTransNum))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberExtDO[id=" + id +", memberId=" + memberId + ", buyerTransNum=" + buyerTransNum + ", " +
				"buyerTransGoodsNum=" + buyerTransGoodsNum + ", sellerTransGoodsNum= " + sellerTransGoodsNum + " " +
			    ", buyerTransAmount=" + buyerTransAmount + ", sellerTransAmount=" + sellerTransAmount + ", sellerGoodsNum=" + sellerGoodsNum + "" +
			    ", promotionNum=" + promotionNum + ", buyerLevel=" +buyerLevel+ ", sellerLevel=" + sellerLevel + " " +
			    ", buyerExt=" + buyerExt + ", sellerExt=" + sellerExt +", version=" + version + "]";
	}
	
}
