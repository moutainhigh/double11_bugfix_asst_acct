package com.yuwang.pinju.domain.shop;

import java.util.Date;

public class ShopCustomerInfoDO extends ShopInfoDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ˳��ID
     */
    private Integer id;

    /**
     * ����ID
     */
    private Integer shopId;

    /**
     * Ӫҵִ��·��
     */
    private String businessLicense;

    /**
     * ��֯�����֤·��
     */
    private String organizationCode;
    
    private String taxPass;
    
    private String enterpriseName;
    private String businessLicenseNumber;
    private String organizationCodeNumber;
    private String business;
    private Date businessLicenseEndDate;
    private String legalName;
    private String registAddress;
    /**
     * ���̸���������
     */
    private String shopManagerName;

    /**
     * �����˵绰
     */
    private String shopManagerTelephone;

    /**
     * �������ֻ�
     */
    private String shopManagerMobile;

    /**
     * ����������
     */
    private String shopManagerEmail;

    /**
     * ����
     */
    private String shopManagerFax;
    /**
     * qq号码
     */
    private String qq;
    
    /**
     * msn帐号
     */
    private String msn;
    /**
     * jϵ�˵�ַ
     */
    private String contactAddress;
   


    public Integer getId(){
        return id;
    }

    public Integer getShopId(){
        return shopId;
    }

    public String getBusinessLicense(){
        return businessLicense;
    }

    public String getOrganizationCode(){
        return organizationCode;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setShopId(Integer shopId){
        this.shopId = shopId;
    }

    public void setBusinessLicense(String businessLicense){
        this.businessLicense = businessLicense;
    }

    public void setOrganizationCode(String organizationCode){
        this.organizationCode = organizationCode;
    }

	public String getTaxPass() {
		return taxPass;
	}

	public void setTaxPass(String taxPass) {
		this.taxPass = taxPass;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getBusinessLicenseNumber() {
		return businessLicenseNumber;
	}

	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.businessLicenseNumber = businessLicenseNumber;
	}

	public String getOrganizationCodeNumber() {
		return organizationCodeNumber;
	}

	public void setOrganizationCodeNumber(String organizationCodeNumber) {
		this.organizationCodeNumber = organizationCodeNumber;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public Date getBusinessLicenseEndDate() {
		return businessLicenseEndDate;
	}

	public void setBusinessLicenseEndDate(Date businessLicenseEndDate) {
		this.businessLicenseEndDate = businessLicenseEndDate;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getRegistAddress() {
		return registAddress;
	}

	public void setRegistAddress(String registAddress) {
		this.registAddress = registAddress;
	}

	public String getShopManagerName() {
		return shopManagerName;
	}

	public void setShopManagerName(String shopManagerName) {
		this.shopManagerName = shopManagerName;
	}

	public String getShopManagerTelephone() {
		return shopManagerTelephone;
	}

	public void setShopManagerTelephone(String shopManagerTelephone) {
		this.shopManagerTelephone = shopManagerTelephone;
	}

	public String getShopManagerMobile() {
		return shopManagerMobile;
	}

	public void setShopManagerMobile(String shopManagerMobile) {
		this.shopManagerMobile = shopManagerMobile;
	}

	public String getShopManagerEmail() {
		return shopManagerEmail;
	}

	public void setShopManagerEmail(String shopManagerEmail) {
		this.shopManagerEmail = shopManagerEmail;
	}

	public String getShopManagerFax() {
		return shopManagerFax;
	}

	public void setShopManagerFax(String shopManagerFax) {
		this.shopManagerFax = shopManagerFax;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}

