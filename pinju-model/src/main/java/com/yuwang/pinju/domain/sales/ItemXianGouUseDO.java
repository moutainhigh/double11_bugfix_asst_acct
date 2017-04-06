package com.yuwang.pinju.domain.sales;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**  
 * @Project: crm-model
 * @Discription: [限购码领用记录DO]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-30 下午04:18:12
 * @update 2011-8-30 下午04:18:12
 * @version V1.0  
 */
public class ItemXianGouUseDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7626139656371759304L;
	
	/**
	 * 限购码领用记录ID
	 */
	private Long id;
	
	/**
	 * 限购记录ID
	 */
	private Long xianGouId;
	
	/**
	 * 限购码
	 */
	private Long code;
	
	/**
	 * 限购码原始值
	 */
	private String codeSource;
	
	/**
	 * 买家ID
	 */
	private Long buyerId;
	
	/**
	 * 买家昵称
	 */
	private String buyerNick;
	
	/**
	 * 卖家ID
	 */
	private Long sellerId;
	
	/**
	 * 卖家昵称
	 */
	private String sellerNick;
	
	/**
	 * 商品ID
	 */
	private Long itemId;
	
	/**
	 * 商品名称
	 */
	private String itemTitle;
	
	/**
	 * 订单ID
	 */
	private Long orderId;
	
	/**
	 * 0-未使用,1-已使用
	 */
	private int isUse;
	
	/**
	 * 活动版本号
	 */
	private Long version;
	
	/**
	 * 记录创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 记录修改时间
	 */
	private Date gmtModified;

	/**
     * 批次号：200为12.12日上线标志
     */
    private Integer batchNum;
    

    public Integer getBatchNum()
    {
        return batchNum;
    }

    public void setBatchNum(Integer batchNum)
    {
        this.batchNum = batchNum;
    }
	
	
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return id;
	}

	public Long getXianGouId(){
		return xianGouId;
	}

	public void setXianGouId(Long xianGouId){
		this.xianGouId = xianGouId;
	}

	public Long getCode(){
		return code;
	}

	public void setCode(Long code){
		this.code = code;
	}

	public Long getBuyerId(){
		return buyerId;
	}

	public void setBuyerId(Long buyerId){
		this.buyerId = buyerId;
	}

	public Long getSellerId(){
		return sellerId;
	}

	public void setSellerId(Long sellerId){
		this.sellerId = sellerId;
	}

	public Long getItemId(){
		return itemId;
	}

	public void setItemId(Long itemId){
		this.itemId = itemId;
	}

	public Long getOrderId(){
		return orderId;
	}

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}

	public int getIsUse(){
		return isUse;
	}

	public void setIsUse(int isUse){
		this.isUse = isUse;
	}

	public Long getVersion(){
		return version;
	}

	public void setVersion(Long version){
		this.version = version;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified(){
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public void setBuyerNick(String buyerNick){
		this.buyerNick = buyerNick;
	}

	public String getBuyerNick(){
		return buyerNick;
	}

	public void setItemTitle(String itemTitle){
		this.itemTitle = itemTitle;
	}

	public String getItemTitle(){
		return itemTitle;
	}

	public void setSellerNick(String sellerNick){
		this.sellerNick = sellerNick;
	}

	public String getSellerNick(){
		return sellerNick;
	}

	public String getCodeSource() {
		return codeSource;
	}

	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}

}
