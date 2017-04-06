package com.yuwang.pinju.domain.margin;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**  
 * @Project: pinju-model
 * @Description: 卖家保证金DO
 * @author 石兴 shixing@zba.com
 * @date 2011-8-1 下午02:02:32
 * @update 2011-8-1 下午02:02:32
 * @version V1.0  
 */
public class MarginSellerDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6377097061686281030L;

	private Long id;
	
	/**
	 * 卖家Id
	 */
	private Long memberId;
	/**
	 * 卖家昵称
	 */
	private String memberNick;
	/**
	 * 消保类型
	 */
	private int cpType; 
	/**
	 * 所属类目ID
	 */
	private Long categoryId;  
	/**
	 * 所属类目名称
	 */
	private String categoryName; 
	/**
	 * 品聚保证金
	 */
	private Long pinjuMargin; 
	/**
	 * 当前保证金
	 */
	private Long currentMargin ;
	/**
	 * 乐观锁版本号
	 */
	private Integer version;  
	
	private Date gmtCreate;  //记录创建时间
	
	private Date gmtModified;  //记录修改时间
	

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

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public int getCpType() {
		return cpType;
	}

	public void setCpType(int cpType) {
		this.cpType = cpType;
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

	public Long getPinjuMargin() {
		return pinjuMargin;
	}

	public void setPinjuMargin(Long pinjuMargin) {
		this.pinjuMargin = pinjuMargin;
	}

	public Long getCurrentMargin() {
		return currentMargin;
	}

	public void setCurrentMargin(Long currentMargin) {
		this.currentMargin = currentMargin;
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

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}
	
}
