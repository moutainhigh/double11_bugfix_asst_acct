package com.yuwang.pinju.domain.distribute;

import com.yuwang.pinju.domain.ConfigurableSupport;
import com.yuwang.pinju.domain.item.ItemDO;

/**
 * 供应商分销商品实体表
 * 
 * @author liyouguo
 * @version 1.0
 * @created 07-七月-2011 11:32:50
 */
public class DistrbuteSupplierItemDO extends ConfigurableSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2154824097619202121L;

	/**
	 * 分销时间
	 */
	private java.util.Date gmtCreate;
	/**
	 * 修改时间
	 */
	private java.util.Date gmtModified;
	/**
	 * 自增编号
	 */
	private Long id;
	/**
	 * 分销商品编号
	 */
	private Long itemId;
	/**
	 * 返点比率（乘100的结果）
	 */
	private Float reward;
	/**
	 * 当前状态【0：分销中，1：停止分销】
	 */
	private Short status;
	/**
	 * 所属供应商
	 */
	private Integer supplierId;
	/**
	 * 商品信息
	 */
	private ItemDO itemDO;
	/**
	 * 分销商数目【正在分销此商品】
	 */
	private Long distributeNum;

	/**
	 * 页面checked flag
	 */
	private Integer checked;
	/**
	 * 分销商ID
	 */
	private Long channelId;
	/**
	 * 分销商昵称
	 */
	private String nickName;
	
	public DistrbuteSupplierItemDO() {

	}

	public DistrbuteSupplierItemDO(Integer supplierId, Long itemId) {
	    super();
	    this.itemId = itemId;
	    this.supplierId = supplierId;
	}

	public void finalize() throws Throwable {

	}

	public java.util.Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(java.util.Date gmtModified) {
		this.gmtModified = gmtModified;
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public ItemDO getItemDO() {
		return itemDO;
	}

	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
	}

	public Float getReward() {
		return reward;
	}

	public void setReward(Float reward) {
		this.reward = reward;
	}

	public Long getDistributeNum() {
		return distributeNum;
	}

	public void setDistributeNum(Long distributeNum) {
		this.distributeNum = distributeNum;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	
	public boolean isChecked() {
		if (null == this.checked || 0 == this.checked) {
			return false;
		}
		return true;
	}

	public Long getChannelId() {
	    return channelId;
	}

	public void setChannelId(Long channelId) {
	    this.channelId = channelId;
	}

	public String getNickName() {
	    return nickName;
	}

	public void setNickName(String nickName) {
	    this.nickName = nickName;
	}

}