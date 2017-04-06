package com.yuwang.pinju.domain.shop;


public class ShopIshopInfoDO extends ShopInfoDO implements java.io.Serializable {

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
   
    /**
     * 身份证复印件ַ
     */
    private String idCard;
    
   /**
    * 个人作品1
    */
    private String creation1;
    
    /**
     * 个人作品2
     */
    private String creation2;


    public Integer getId(){
        return id;
    }

    public Integer getShopId(){
        return shopId;
    }

  

    public void setId(Integer id){
        this.id = id;
    }

    public void setShopId(Integer shopId){
        this.shopId = shopId;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCreation1() {
		return creation1;
	}

	public void setCreation1(String creation1) {
		this.creation1 = creation1;
	}

	public String getCreation2() {
		return creation2;
	}

	public void setCreation2(String creation2) {
		this.creation2 = creation2;
	}

}

