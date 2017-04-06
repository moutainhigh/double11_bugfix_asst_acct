package com.yuwang.pinju.domain.logistics;

import java.util.Date;

public class LogisticsCorpDO {
    private Long id;

    private String corpCode;

    private String corpName;

    private String contacts;

    private String phone;

    private String corpUrl;

    private String address;

    private Long logisticsType;

    private Long logisticsStatus;

    private String memo;

    private String createPeople;

    private Date gmtCreate;

    private String modifiedPeople;

    private Date gmtModified;
    
    private String corpHCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCorpUrl() {
		return corpUrl;
	}

	public void setCorpUrl(String corpUrl) {
		this.corpUrl = corpUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(Long logisticsType) {
		this.logisticsType = logisticsType;
	}

	public Long getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(Long logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreatePeople() {
		return createPeople;
	}

	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getModifiedPeople() {
		return modifiedPeople;
	}

	public void setModifiedPeople(String modifiedPeople) {
		this.modifiedPeople = modifiedPeople;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getCorpHCode() {
		return corpHCode;
	}

	public void setCorpHCode(String corpHCode) {
		this.corpHCode = corpHCode;
	}

}