package com.yuwang.pinju.domain.shop;

import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class ShopShieldDO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 屏蔽状态 0:未屏蔽 1:屏蔽 2:不限时屏蔽 3:解除屏蔽
	 */
	private Integer shieldType;

	/**
	 * 屏蔽天数
	 */
	private Integer shieldDays;

	/**
	 * 到期时间
	 */
	private Date expireDate;

	/**
	 * 屏蔽原因
	 */
	private String shieldReason;

	/**
	 * 解除屏蔽原因
	 */
	private String recoverReason;

	/**
	 * 创建人员ID
	 */
	private Long createUserId;

	/**
	 * 更新人员ID
	 */
	private Long updateUserId;

	/**
	 * 新增时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 操作状态
	 */
	private Boolean success;
	
	
	/**
	 * 店铺ID[显示]
	 */
	private Integer shopId;
	
	/**
	 * 店铺ID[参数]
	 */
	private List<Integer> shopIdListParam;
	
	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 店主昵称
	 */
	private String nickName;
	
	/**
	 * 店铺开店时间
	 */
	private Date openDate;
	
	/**
	 * 店铺店主id
	 */
	private Long memberId;
	
	/**
	 * 店铺状态
	 */
	private Integer approveStatus;
	
	/**
	 * 卖家仓库中商品数量
	 */
	private Integer warehouseItemCount;
	
	/**
	 * 卖家运营下架商品数量
	 */
	private Integer offShelfItemCount;
	
	/**
	 * 系统当前时间
	 */
	private Date sysDate;
	
	public ShopShieldDO() {
	    super();
	}

	public ShopShieldDO(List<Integer> shopIdListParam) {
	    this.shopIdListParam = shopIdListParam;
	}
	
	public ShopShieldDO(List<Integer> shopIdListParam, Integer approveStatus) {
	    super();
	    this.shopIdListParam = shopIdListParam;
	    this.approveStatus = approveStatus;
	}
	
	public ShopShieldDO(Integer shopId, Long memberId, String shopName, Date openDate) {
	    super();
	    this.shopId = shopId;
	    this.memberId = memberId;
	    this.shopName = shopName;
	    this.openDate = openDate;
	}
	
	public ShopShieldDO(Long memberId) {
	    super();
	    this.memberId = memberId;
	}
	
	public Integer getId() {
		return id;
	}

	public String getShopName() {
		return shopName;
	}

	public Integer getShieldType() {
		return shieldType;
	}

	public Integer getShieldDays() {
		return shieldDays;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getShieldReason() {
		return shieldReason;
	}

	public String getRecoverReason() {
		return recoverReason;
	}


	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setShieldType(Integer shieldType) {
		this.shieldType = shieldType;
	}

	public void setShieldDays(Integer shieldDays) {
		this.shieldDays = shieldDays;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setShieldReason(String shieldReason) {
		this.shieldReason = shieldReason;
	}

	public void setRecoverReason(String recoverReason) {
		this.recoverReason = recoverReason;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Boolean isSuccess() {
	    return success;
	}

	public void setSuccess(Boolean success) {
	    this.success = success;
	}

	public String getNickName() {
	    return nickName;
	}

	public void setNickName(String nickName) {
	    this.nickName = nickName;
	}

	public Date getOpenDate() {
	    return openDate;
	}

	public void setOpenDate(Date openDate) {
	    this.openDate = openDate;
	}

	public Long getCreateUserId() {
	    return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
	    this.createUserId = createUserId;
	}

	public Long getUpdateUserId() {
	    return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
	    this.updateUserId = updateUserId;
	}

	@Override
	public String toString() {
	    return "ShopShieldDO [createUserId=" + createUserId
		    + ", expireDate=" + expireDate + ", gmtCreate=" + gmtCreate
		    + ", gmtModified=" + gmtModified + ", id=" + id
		    + ", memberId=" + memberId + ", nickName=" + nickName
		    + ", openDate=" + openDate + ", recoverReason="
		    + recoverReason + ", shieldDays=" + shieldDays
		    + ", shieldReason=" + shieldReason + ", shieldType="
		    + shieldType + ", shopId=" + shopId + ", shopName="
		    + shopName + ", success=" + success + ", updateUserId="
		    + updateUserId + "]";
	}

	@JSON(serialize=false)
	public String getLaveTime() {
	    Long result = getExpireDate().getTime() - getSysDate().getTime();
	    Long laveDays = (result/(60*60*1000));
	    Long laveMinute = (result%(60*60*1000))/(1000*60);
	    return ((laveDays<10)? ("0" + laveDays):laveDays) + " : " + ((laveMinute<10)? ("0" + laveMinute):laveMinute);
	}

	public Date getGmtCreate() {
	    return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
	    this.gmtCreate = gmtCreate;
	}

	public Integer getShopId() {
	    return shopId;
	}

	public void setShopId(Integer shopId) {
	    this.shopId = shopId;
	}

	public List<Integer> getShopIdListParam() {
		return shopIdListParam;
	}

	public void setShopIdListParam(List<Integer> shopIdListParam) {
		this.shopIdListParam = shopIdListParam;
	}

	public Long getMemberId() {
	    return memberId;
	}

	public void setMemberId(Long memberId) {
	    this.memberId = memberId;
	}

	public Integer getApproveStatus() {
	    return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
	    this.approveStatus = approveStatus;
	}

	public Integer getWarehouseItemCount() {
	    return warehouseItemCount;
	}

	public void setWarehouseItemCount(Integer warehouseItemCount) {
	    this.warehouseItemCount = warehouseItemCount;
	}

	public Integer getOffShelfItemCount() {
	    return offShelfItemCount;
	}

	public void setOffShelfItemCount(Integer offShelfItemCount) {
	    this.offShelfItemCount = offShelfItemCount;
	}

	public Date getSysDate() {
	    return sysDate;
	}

	public void setSysDate(Date sysDate) {
	    this.sysDate = sysDate;
	}

}
