package com.yuwang.pinju.domain.citystation;

import java.io.Serializable;
import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 城市分站DO
 * 
 * @author qiuhongming
 * 
 */
public class CityStationDO extends BaseDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4413855100798005219L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 省份名称
	 */
	private String provName;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 城市指定链接
	 */
	private String cityUrl;
	
	/**
	 * 广告代码
	 */
	private String adCode;
	
	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 操作人id
	 */
	private Integer operateId;

	/**
	 * 操作人名称
	 */
	private String operateName;

	/**
	 * 城市状态（0：未开通；1：开通）
	 */
	private String status;

	private Long memberId;

	private String nickname;

	private Date gmtModified;

	private Date gmtCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityUrl() {
		return cityUrl;
	}

	public void setCityUrl(String cityUrl) {
		this.cityUrl = cityUrl;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getOperateId() {
		return operateId;
	}

	public void setOperateId(Integer operateId) {
		this.operateId = operateId;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public CityStationDO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
