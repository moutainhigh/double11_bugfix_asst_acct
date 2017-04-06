package com.yuwang.pinju.domain.member;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.domain.BaseDO;

/**
 * <p>MEMBER_SELLER_QUALITY 数据表对象映射</p>
 *
 * @author gaobaowen
 * 2011-6-14 13:56:23
 */
public class SellerQualityDO extends BaseDO implements RelationEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 店铺类型 -- 普通店（1）
	 */
	public final static String SELLER_TYPE_COMMON = "1";

	/**
	 * 店铺类型 -- 品牌店（2）
	 */
	public final static String SELLER_TYPE_BRAND = "2";

	/**
	 * 店铺类型 -- 旗舰店（3）
	 */
	public final static String SELLER_TYPE_FLAGSHIP = "3";

	/**
	 * 店铺类型 -- i 小铺（4）
	 */
	public final static String SELLER_TYPE_i_SHOP = "4";

	/**
	 * 卖家级别 - S级（0）（最高）
	 */
	public final static Integer LEVEL_S = 0;

	/**
	 * 卖家级别 - A级（1）
	 */
	public final static Integer LEVEL_A = 1;

	/**
	 * 卖家级别 - B级（2）
	 */
	public final static Integer LEVEL_B = 2;

	/**
	 * 卖家级别 - C级（3）
	 */
	public final static Integer LEVEL_C = 3;

	/**
	 * 卖家级别 - D级（4）（最低，默认值）
	 */
	public final static Integer LEVEL_D = 4;

	/**
	 * 消保类型 -- 基本（100）
	 */
	public final static Integer CP_TYPE_BASIC = 100;

	/**
	 * 消保类型 -- 高级（200）
	 */
	public final static Integer CP_TYPE_ADVANCED = 200;

	@SuppressWarnings("serial")
	private static Map<Integer, String> LEVEL_MAP = new HashMap<Integer, String>(){{
		put(LEVEL_S, "一品");
		put(LEVEL_A, "二品");
		put(LEVEL_B, "三品");
		put(LEVEL_C, "四品");
		put(LEVEL_D, "五品");
	}};

	@SuppressWarnings("serial")
	private static Map<String, String> SELLER_TYPE_MAP = new HashMap<String, String>(){{
		put(SELLER_TYPE_COMMON, "普通店");
		put(SELLER_TYPE_BRAND, "品牌店");
		put(SELLER_TYPE_FLAGSHIP, "旗舰店");
		put(SELLER_TYPE_i_SHOP, " i 小铺");
	}};

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 会员编号
	 */
	private Long memberId;

	/**
	 * 会员账号/昵称
	 */
	private String nickname;

	/**
	 * 店铺编号
	 */
	private Integer shopId;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 店铺类型（1普通店，2品牌店，3旗舰店）
	 */
	private String sellerType;

	/**
	 * 审核状态（SHOP_INFO.APPROVE_STATUS）
	 */
	private Integer approveStatus;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 所在地编号
	 */
	private Integer localId;

	/**
	 * 所在地
	 */
	private String localName;

	/**
	 * 卖家级别（0:S；1:A；2:B；3:C；4:D）
	 */
	private Integer level = LEVEL_D;

	/**
	 * 消保类型（100：基础消保；200：高级消保）
	 */
	private Integer cpType = CP_TYPE_BASIC;

	/**
	 * 保证金金额
	 */
	private Long margin;

	/**
	 * 店铺分类所属一级类目
	 */
	private Long categoryId;

	/**
	 * 店铺分类所属一级类目名称
	 */
	private String categoryName;

	/**
	 * 退款率（75%记为7500）
	 */
	private Integer refundRate = 0;

	/**
	 * 退款成功率（75%记为7500）
	 */
	private Integer refundSuccessRate = 0;

	/**
	 * 维权率（75%记为7500）
	 */
	private Integer rightsRate = 0;

	/**
	 * 维权成功率（75%记为7500）
	 */
	private Integer rightsSuccessRate = 0;

	/**
	 * 纠纷率（75%记为7500）
	 */
	private Integer disputeRate = 0;

	private Date gmtCreate;
	private Date gmtModified;

	@Override
	public boolean isNew() {
		return false;
	}

	public Integer getLevelGrade() {
		String name = LEVEL_MAP.get(level);
		if (name == null) {
			return LEVEL_D;
		}
		return level;
	}

	public String getLevelName() {
		String name = LEVEL_MAP.get(level);
		if (name == null) {
			return LEVEL_MAP.get(LEVEL_D);
		}
		return name;
	}

	public String getSellerTypeName() {
		String name = SELLER_TYPE_MAP.get(sellerType);
		if (name == null) {
			return SELLER_TYPE_MAP.get(SELLER_TYPE_COMMON);
		}
		return name;
	}

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getLocalId() {
		return localId;
	}

	public void setLocalId(Integer localId) {
		this.localId = localId;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getCpType() {
		return cpType;
	}

	public void setCpType(Integer cpType) {
		this.cpType = cpType;
	}

	public Long getMargin() {
		return margin;
	}

	public void setMargin(Long margin) {
		this.margin = margin;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getRefundRate() {
		return refundRate;
	}

	public void setRefundRate(Integer refundRate) {
		this.refundRate = refundRate;
	}

	public Integer getRefundSuccessRate() {
		return refundSuccessRate;
	}

	public void setRefundSuccessRate(Integer refundSuccessRate) {
		this.refundSuccessRate = refundSuccessRate;
	}

	public Integer getRightsRate() {
		return rightsRate;
	}

	public void setRightsRate(Integer rightsRate) {
		this.rightsRate = rightsRate;
	}

	public Integer getRightsSuccessRate() {
		return rightsSuccessRate;
	}

	public void setRightsSuccessRate(Integer rightsSuccessRate) {
		this.rightsSuccessRate = rightsSuccessRate;
	}

	public Integer getDisputeRate() {
		return disputeRate;
	}

	public void setDisputeRate(Integer disputeRate) {
		this.disputeRate = disputeRate;
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
		result = prime * result + ((approveStatus == null) ? 0 : approveStatus.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((cpType == null) ? 0 : cpType.hashCode());
		result = prime * result + ((disputeRate == null) ? 0 : disputeRate.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((localId == null) ? 0 : localId.hashCode());
		result = prime * result + ((localName == null) ? 0 : localName.hashCode());
		result = prime * result + ((margin == null) ? 0 : margin.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((refundRate == null) ? 0 : refundRate.hashCode());
		result = prime * result + ((refundSuccessRate == null) ? 0 : refundSuccessRate.hashCode());
		result = prime * result + ((rightsRate == null) ? 0 : rightsRate.hashCode());
		result = prime * result + ((rightsSuccessRate == null) ? 0 : rightsSuccessRate.hashCode());
		result = prime * result + ((sellerType == null) ? 0 : sellerType.hashCode());
		result = prime * result + ((shopId == null) ? 0 : shopId.hashCode());
		result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
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
		SellerQualityDO other = (SellerQualityDO) obj;
		if (approveStatus == null) {
			if (other.approveStatus != null)
				return false;
		} else if (!approveStatus.equals(other.approveStatus))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (cpType == null) {
			if (other.cpType != null)
				return false;
		} else if (!cpType.equals(other.cpType))
			return false;
		if (disputeRate == null) {
			if (other.disputeRate != null)
				return false;
		} else if (!disputeRate.equals(other.disputeRate))
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
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (localId == null) {
			if (other.localId != null)
				return false;
		} else if (!localId.equals(other.localId))
			return false;
		if (localName == null) {
			if (other.localName != null)
				return false;
		} else if (!localName.equals(other.localName))
			return false;
		if (margin == null) {
			if (other.margin != null)
				return false;
		} else if (!margin.equals(other.margin))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (refundRate == null) {
			if (other.refundRate != null)
				return false;
		} else if (!refundRate.equals(other.refundRate))
			return false;
		if (refundSuccessRate == null) {
			if (other.refundSuccessRate != null)
				return false;
		} else if (!refundSuccessRate.equals(other.refundSuccessRate))
			return false;
		if (rightsRate == null) {
			if (other.rightsRate != null)
				return false;
		} else if (!rightsRate.equals(other.rightsRate))
			return false;
		if (rightsSuccessRate == null) {
			if (other.rightsSuccessRate != null)
				return false;
		} else if (!rightsSuccessRate.equals(other.rightsSuccessRate))
			return false;
		if (sellerType == null) {
			if (other.sellerType != null)
				return false;
		} else if (!sellerType.equals(other.sellerType))
			return false;
		if (shopId == null) {
			if (other.shopId != null)
				return false;
		} else if (!shopId.equals(other.shopId))
			return false;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SellerQualityDO [id=" + id + ", memberId=" + memberId + ", nickname=" + nickname + ", shopId=" + shopId
				+ ", shopName=" + shopName + ", sellerType=" + sellerType + ", approveStatus=" + approveStatus
				+ ", companyName=" + companyName + ", localId=" + localId + ", localName=" + localName + ", level="
				+ level + ", cpType=" + cpType + ", margin=" + margin + ", categoryId=" + categoryId
				+ ", categoryName=" + categoryName + ", refundRate=" + refundRate + ", refundSuccessRate="
				+ refundSuccessRate + ", rightsRate=" + rightsRate + ", rightsSuccessRate=" + rightsSuccessRate
				+ ", disputeRate=" + disputeRate + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
