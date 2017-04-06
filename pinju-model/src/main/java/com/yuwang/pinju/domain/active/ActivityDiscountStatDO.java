/**
 * 
 */
package com.yuwang.pinju.domain.active;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**  
 * @Project: pinju-model
 * @Title: ActivityDiscountStatDO.java
 * @Package com.yuwang.pinju.domain.active
 * @Description: 限时折扣统计DO
 * @author liuboen liuboen@zba.com
 * @date 2011-7-28 下午01:44:32
 * @version V1.0  
 */

public class ActivityDiscountStatDO extends BaseDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8813592312739962549L;
	private Long id;
	 /**
     * 统计年月份
     */
    private Long statDate;

    /**
     * 已使用活动时间(小时)
     */
    private Long usedTime;

    /**
     * 已使用活动数
     */
    private Long usedNum;

    /**
     * 卖家昵称
     */
    private String memberNick;

    /**
     * 卖家ID
     */
    private Long memberId;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
	
	private	Long version;


	/**
	 * @return the usedTime
	 */
	public Long getUsedTime() {
		return usedTime;
	}

	/**
	 * @return the statDate
	 */
	public Long getStatDate() {
		return statDate;
	}

	/**
	 * @param statDate the statDate to set
	 */
	public void setStatDate(Long statDate) {
		this.statDate = statDate;
	}

	/**
	 * @param usedTime the usedTime to set
	 */
	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}

	/**
	 * @return the usedNum
	 */
	public Long getUsedNum() {
		return usedNum;
	}

	/**
	 * @param usedNum the usedNum to set
	 */
	public void setUsedNum(Long usedNum) {
		this.usedNum = usedNum;
	}

	/**
	 * @return the memberNick
	 */
	public String getMemberNick() {
		return memberNick;
	}

	/**
	 * @param memberNick the memberNick to set
	 */
	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	/**
	 * @return the memberId
	 */
	public Long getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate the gmtCreate to set
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


}
