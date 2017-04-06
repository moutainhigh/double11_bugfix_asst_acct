package com.yuwang.pinju.domain.shop;

import java.util.Date;

public class ShopBusinessInfoDO extends ShopInfoDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ����ID
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

    /**
     * Ʒ��֤��·��
     */
    private String brandCertificate;

    /**
     * ��Ʒ��֤��·��
     */
    private String qualityCertificate;

    /**
     * �������֤��·��
     */
    private String hygieneLicense;

    /**
     * ������֤·��
     */
    private String productionLicense;

    /**
     * ��Ӫ���֤·��
     */
    private String businessCertificate;

    /**
     * ��Ӫ��Χ
     */
    private String business;

    /**
     * jϵ������
     */
    private String contactName;

    /**
     * jϵ�˵绰
     */
    private String contactPhone;

    /**
     * jϵ�˵�ַ
     */
    private String contactAddress;

    /**
     * jϵ���ʱ�
     */
    private String contactPostalCode;

    /**
     * ��ҵ���
     */
    private String enterpriseName;

    /**
     * Ӫҵִ��ע���
     */
    private String businessLicenseNumber;

    /**
     * ��֯�����֤��
     */
    private String organizationCodeNumber;

    /**
     * Ӫҵִ����Ч��
     */
    private Date businessLicenseEndDate;

    /**
     * ��������
     */
    private String legalName;

    /**
     * ע���ַ
     */
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
     * ��jϵ�˵绰
     */
    private String emergencyContactPhone;

    /**
     * ��jϵ������
     */
    private String emergencyContactName;

    /**
     * �̱������
     */
    private String trademarkNumber;

    /**
     * Ʒ����
     */
    private String brandName;

    /**
     * Ʒ��Ӣ����
     */
    private String brandEnglishName;

    /**
     * Ʒ��logo
     */
    private String brandLogo;

    /**
     * Ʒ�ƹ���
     */
    private String brandStory;

    /**
     * B�ĵ�������
     */
    private Long sellerNature;
    
    /**
     * 税务登记证
     */
    private String taxPass;
    
    /**
     * qq号码
     */
    private String qq;
    
    /**
     * msn帐号
     */
    private String msn;


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

    public String getBrandCertificate(){
        return brandCertificate;
    }

    public String getQualityCertificate(){
        return qualityCertificate;
    }

    public String getHygieneLicense(){
        return hygieneLicense;
    }

    public String getProductionLicense(){
        return productionLicense;
    }

    public String getBusinessCertificate(){
        return businessCertificate;
    }

    public String getBusiness(){
        return business;
    }

    public String getContactName(){
        return contactName;
    }

    public String getContactPhone(){
        return contactPhone;
    }

    public String getContactAddress(){
        return contactAddress;
    }

    public String getContactPostalCode(){
        return contactPostalCode;
    }

    public String getEnterpriseName(){
        return enterpriseName;
    }

    public String getBusinessLicenseNumber(){
        return businessLicenseNumber;
    }

    public String getOrganizationCodeNumber(){
        return organizationCodeNumber;
    }

    public Date getBusinessLicenseEndDate(){
        return businessLicenseEndDate;
    }

    public String getLegalName(){
        return legalName;
    }

    public String getRegistAddress(){
        return registAddress;
    }

    public String getShopManagerName(){
        return shopManagerName;
    }

    public String getShopManagerTelephone(){
        return shopManagerTelephone;
    }

    public String getShopManagerMobile(){
        return shopManagerMobile;
    }

    public String getShopManagerEmail(){
        return shopManagerEmail;
    }

    public String getShopManagerFax(){
        return shopManagerFax;
    }

    public String getEmergencyContactPhone(){
        return emergencyContactPhone;
    }

    public String getEmergencyContactName(){
        return emergencyContactName;
    }

    public String getTrademarkNumber(){
        return trademarkNumber;
    }

    public String getBrandName(){
        return brandName;
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

	public String getBrandEnglishName(){
        return brandEnglishName;
    }

    public String getBrandLogo(){
        return brandLogo;
    }

    public String getBrandStory(){
        return brandStory;
    }

    public Long getSellerNature(){
        return sellerNature;
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

    public void setBrandCertificate(String brandCertificate){
        this.brandCertificate = brandCertificate;
    }

    public void setQualityCertificate(String qualityCertificate){
        this.qualityCertificate = qualityCertificate;
    }

    public void setHygieneLicense(String hygieneLicense){
        this.hygieneLicense = hygieneLicense;
    }

    public void setProductionLicense(String productionLicense){
        this.productionLicense = productionLicense;
    }

    public void setBusinessCertificate(String businessCertificate){
        this.businessCertificate = businessCertificate;
    }

    public void setBusiness(String business){
        this.business = business;
    }

    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone){
        this.contactPhone = contactPhone;
    }

    public void setContactAddress(String contactAddress){
        this.contactAddress = contactAddress;
    }

    public void setContactPostalCode(String contactPostalCode){
        this.contactPostalCode = contactPostalCode;
    }

    public void setEnterpriseName(String enterpriseName){
        this.enterpriseName = enterpriseName;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber){
        this.businessLicenseNumber = businessLicenseNumber;
    }

    public void setOrganizationCodeNumber(String organizationCodeNumber){
        this.organizationCodeNumber = organizationCodeNumber;
    }

    public void setBusinessLicenseEndDate(Date businessLicenseEndDate){
        this.businessLicenseEndDate = businessLicenseEndDate;
    }

    public void setLegalName(String legalName){
        this.legalName = legalName;
    }

    public void setRegistAddress(String registAddress){
        this.registAddress = registAddress;
    }

    public void setShopManagerName(String shopManagerName){
        this.shopManagerName = shopManagerName;
    }

    public void setShopManagerTelephone(String shopManagerTelephone){
        this.shopManagerTelephone = shopManagerTelephone;
    }

    public void setShopManagerMobile(String shopManagerMobile){
        this.shopManagerMobile = shopManagerMobile;
    }

    public void setShopManagerEmail(String shopManagerEmail){
        this.shopManagerEmail = shopManagerEmail;
    }

    public void setShopManagerFax(String shopManagerFax){
        this.shopManagerFax = shopManagerFax;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone){
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public void setEmergencyContactName(String emergencyContactName){
        this.emergencyContactName = emergencyContactName;
    }

    public void setTrademarkNumber(String trademarkNumber){
        this.trademarkNumber = trademarkNumber;
    }

    public void setBrandName(String brandName){
        this.brandName = brandName;
    }

    public void setBrandEnglishName(String brandEnglishName){
        this.brandEnglishName = brandEnglishName;
    }

    public void setBrandLogo(String brandLogo){
        this.brandLogo = brandLogo;
    }

    public void setBrandStory(String brandStory){
        this.brandStory = brandStory;
    }

    public void setSellerNature(Long sellerNature){
        this.sellerNature = sellerNature;
    }

	public String getTaxPass() {
		return taxPass;
	}

	public void setTaxPass(String taxPass) {
		this.taxPass = taxPass;
	}

}

