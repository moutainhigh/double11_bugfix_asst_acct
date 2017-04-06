package com.yuwang.pinju.domain.shop;


public class ShopFlowInfoDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 店铺类型 0:普通店 1:品牌店 2:旗舰店 3:i小铺
     */
    private String sellerType;
    
    /**
     * 审核状态 -1 未通过 0待审核 1通过审核 2.开店成功 -100未提交
     */
    private Integer auditStatus;
    
    /**
     * 是否填写了基本信息 0:否 1:是
     */
    private Integer isFillStep1;
    
    /**
     * 是否填写了上传信息 0:否 1:是
     */
    private Integer isFillStep2;
    
    /**
     * 是否填写了品牌信息 0:否 1:是
     */
    private Integer isFillStep3;
    
    /**
     * 是否绑定了财付通 0:否 1:是
     */
    private Integer tenpayBind;
    
    /**
     * 是否签约了财付通 0:否 1:是
     */
    private Integer tenpaySign;
    
    /**
     * 是否已经确认了开店协议 0:否 1:是
     */
    private Integer isAgreement;
    
    /**
     * 店铺id
     */
    private Long userId;
    
    /**
     * 是否可以更改店铺类型 0:不可 1:可以
     */
    private Integer canChangeShop;
    
    /**
     * 是否已经完成帐号设定 0:未完成 1:完成  -1:未开始
     */
    private Integer isAccountSet;
    
    /**
     * 是否已经提交完信息 0:未完成 1:完成  -1:未开始
     */
    private Integer isFillComplete;
    
    /**
     * 是否可以缴纳保证金 0:不可 1:可以
     */
    private Integer canPayMargin;
    
	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getIsFillStep1() {
		return isFillStep1;
	}

	public void setIsFillStep1(Integer isFillStep1) {
		this.isFillStep1 = isFillStep1;
	}

	public Integer getIsFillStep2() {
		return isFillStep2;
	}

	public void setIsFillStep2(Integer isFillStep2) {
		this.isFillStep2 = isFillStep2;
	}

	public Integer getIsFillStep3() {
		return isFillStep3;
	}

	public void setIsFillStep3(Integer isFillStep3) {
		this.isFillStep3 = isFillStep3;
	}

	public Integer getTenpayBind() {
		return tenpayBind;
	}

	public void setTenpayBind(Integer tenpayBind) {
		this.tenpayBind = tenpayBind;
	}

	public Integer getTenpaySign() {
		return tenpaySign;
	}

	public void setTenpaySign(Integer tenpaySign) {
		this.tenpaySign = tenpaySign;
	}

	public Integer getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(Integer isAgreement) {
		this.isAgreement = isAgreement;
	}

	public Integer getCanPayMargin() {
		return canPayMargin;
	}

	public void setCanPayMargin(Integer canPayMargin) {
		this.canPayMargin = canPayMargin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIsAccountSet() {
		return isAccountSet;
	}

	public void setIsAccountSet(Integer isAccountSet) {
		this.isAccountSet = isAccountSet;
	}

	public Integer getIsFillComplete() {
		return isFillComplete;
	}

	public void setIsFillComplete(Integer isFillComplete) {
		this.isFillComplete = isFillComplete;
	}

	public Integer getCanChangeShop() {
		return canChangeShop;
	}

	public void setCanChangeShop(Integer canChangeShop) {
		this.canChangeShop = canChangeShop;
	}
    
    

}

