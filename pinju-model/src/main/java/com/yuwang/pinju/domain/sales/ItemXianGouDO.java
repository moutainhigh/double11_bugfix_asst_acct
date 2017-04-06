package com.yuwang.pinju.domain.sales;

import java.util.Date;
/**
 * 商品限购特供DO
 * @Project:pinju-model
 * @author: lixingquan lixingquan@zba.com
 * @date:2011-8-30
 * @update:2011-8-30
 */
public class ItemXianGouDO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 编号Id
	 */
	private Long id;
	/**
	 * 商品Id
	 */
	private Long itemId;
	/**
	 * 商品标题
	 */
	private String itemTitle;
	/**
	 * 卖家Id
	 */
	private Long sellerId;
	/**
	 * 卖家昵称
	 */
	private String sellerNick;
	/**
	 * 限购码总数量
	 */
	private Long count;

	/**
	 * 状态0-已开始 1-已结束
	 */
	private int status;
	/**
	 * 可购买数量
	 */
	private int buyCount;
	/**
	 * 活动版本号
	 */
	private int version;
	/**
	 * 活动开始时间
	 */
	private Date expiryStart;
	/**
	 * 活动结束时间
	 */
	private Date expiryEnd;
	/**
	 * 记录创建时间
	 */
	private Date gmtCreate ;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getExpiryStart() {
		return expiryStart;
	}

	public void setExpiryStart(Date expiryStart) {
		this.expiryStart = expiryStart;
	}

	public Date getExpiryEnd() {
		return expiryEnd;
	}

	public void setExpiryEnd(Date expiryEnd) {
		this.expiryEnd = expiryEnd;
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

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	
}
