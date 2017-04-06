package com.yuwang.pinju.domain.shop;

import java.io.StringReader;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ShopInfoDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ����id
     */
    private Integer id;

    /**
     * �û�id
     */
    private Long userId;

    /**
     * ����ID
     */
    private Integer shopId;

    /**
     * ������
     */
    private String name;

    /**
     * ���̼��
     */
    private String title;

    /**
     * ���̽���
     */
    private String description;

    /**
     * ���
     */
    private String shopLogo;

    /**
     * ��β��Ƭ·��
     */
    private String footLogo;

    /**
     * �������� c��b
     */
    private String sellerType;

    /**
     * ��Ʒ4Դ
     */
    private Integer goodsSource;

    /**
     * ��������
     */
    private String shopNature;

    /**
     * ��Ʒ����
     */
    private String shopCategory;

    /**
     * ����
     */
    private String city;

    /**
     * ʡ��
     */
    private String province;

    /**
     * ������ //0-基础消保 1-高级消保
     */
    private String exchangeMargin;

    /**
     * �Ƿ��ǹ�Ӧ��
     */
    private Integer isSupplier;

    /**
     * ��������
     */
    private Date openDate;

    /**
     * ���̵�״̬\: 0\:δ�� 1\:�� -1\:�ر�
     */
    private Integer approveStatus;

    /**
     * ��������
     */
    private String domain;

    /**
     * վ��id
     */
    private String siteId;

    /**
     * ��Ʒ��
     */
    private Integer productCount;

    /**
     * ��ַ
     */
    private String address;

    /**
     * �ⲿ���̵�ַ
     */
    private String outerShopAddressUrl;

    /**
     * �ⲿ���̼���
     */
    private Integer outerShopLevel;

    /**
     * �ⲿ�������۹�ģ
     */
    private Integer outerShopSaleScope;

    /**
     * �ⲿ�����Ƿ���פ��B2C
     */
    private Integer isEnterB2c;

    /**
     * ������Ϣ,���ڽ���µ������
     */
    private String configuration;

    /**
     * ��¼��������
     */
    private Date gmtCreate;

    /**
     * ��¼�޸�����
     */
    private Date gmtModified;
    
    /**
     * 昵称
     */
    private String nickname;
    
    private Properties shopInfoProperties;
    
    /**
     * 域名创建日期
     */
    private Date domainCreate;
    /**
     * 店铺标签
     */
    private String shopLabel;


    public String getShopLabel() {
		return shopLabel;
	}

	public void setShopLabel(String shopLabel) {
		this.shopLabel = shopLabel;
	}

	public Integer getId(){
        return id;
    }

    public Long getUserId(){
        return userId;
    }

    public Integer getShopId(){
        return shopId;
    }

    public String getName(){
        return name;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getShopLogo(){
        return shopLogo;
    }

    public String getFootLogo(){
        return footLogo;
    }

    public String getSellerType(){
        return sellerType;
    }

    public Integer getGoodsSource(){
        return goodsSource;
    }

    public String getShopNature(){
        return shopNature;
    }

    public String getShopCategory(){
        return shopCategory;
    }

    public String getCity(){
        return city;
    }

    public String getProvince(){
        return province;
    }

    public String getExchangeMargin(){
        return exchangeMargin;
    }

    public Integer getIsSupplier(){
        return isSupplier;
    }

    public Date getOpenDate(){
        return openDate;
    }

    public Integer getApproveStatus(){
        return approveStatus;
    }

    public String getDomain(){
        return domain;
    }

    public String getSiteId(){
        return siteId;
    }

    public Integer getProductCount(){
        return productCount;
    }

    public String getAddress(){
        return address;
    }

    public String getOuterShopAddressUrl(){
        return outerShopAddressUrl;
    }

    public Integer getOuterShopLevel(){
        return outerShopLevel;
    }

    public Integer getOuterShopSaleScope(){
        return outerShopSaleScope;
    }

    public Integer getIsEnterB2c(){
        return isEnterB2c;
    }

    public String getConfiguration(){
        return configuration;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public Date getDomainCreate() {
		return domainCreate;
	}

	public void setDomainCreate(Date domainCreate) {
		this.domainCreate = domainCreate;
	}

	public void setId(Integer id){
        this.id = id;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public void setShopId(Integer shopId){
        this.shopId = shopId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setShopLogo(String shopLogo){
        this.shopLogo = shopLogo;
    }

    public void setFootLogo(String footLogo){
        this.footLogo = footLogo;
    }

    public void setSellerType(String sellerType){
        this.sellerType = sellerType;
    }

    public void setGoodsSource(Integer goodsSource){
        this.goodsSource = goodsSource;
    }

    public void setShopNature(String shopNature){
        this.shopNature = shopNature;
    }

    public void setShopCategory(String shopCategory){
        this.shopCategory = shopCategory;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public void setExchangeMargin(String exchangeMargin){
        this.exchangeMargin = exchangeMargin;
    }

    public void setIsSupplier(Integer isSupplier){
        this.isSupplier = isSupplier;
    }

    public void setOpenDate(Date openDate){
        this.openDate = openDate;
    }

    public void setApproveStatus(Integer approveStatus){
        this.approveStatus = approveStatus;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    public void setSiteId(String siteId){
        this.siteId = siteId;
    }

    public void setProductCount(Integer productCount){
        this.productCount = productCount;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setOuterShopAddressUrl(String outerShopAddressUrl){
        this.outerShopAddressUrl = outerShopAddressUrl;
    }

    public void setOuterShopLevel(Integer outerShopLevel){
        this.outerShopLevel = outerShopLevel;
    }

    public void setOuterShopSaleScope(Integer outerShopSaleScope){
        this.outerShopSaleScope = outerShopSaleScope;
    }

    public void setIsEnterB2c(Integer isEnterB2c){
        this.isEnterB2c = isEnterB2c;
    }

    public void setConfiguration(String configuration){
        this.configuration = configuration;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getShopInfoConfig(String key) {
		if (shopInfoProperties == null
				&& StringUtils.isNotEmpty(configuration)) {
			try {
				shopInfoProperties = new Properties();
				shopInfoProperties.load(new StringReader(configuration));
			} catch (Exception ignored) {

			}
		}
		return shopInfoProperties != null ? shopInfoProperties.getProperty(key)
				: null;
	}

}

