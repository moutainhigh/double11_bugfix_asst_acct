/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.domain.distribute;

import java.util.List;

import com.yuwang.pinju.domain.Paginator;

/**
 * @author liyouguo
 * 
 * @since 2011-7-25
 */
public class DistributorItemQuery extends Paginator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3814115258539418753L;
	
	public DistributorItemQuery() {
		super();
		setPage(1);
		setEndRow(20);
		setStartRow(1);
		setItemsPerPage(20);
	}

	private Long distributorId;
	private Short status;
	private List<Long> itemIdList;
	private List<DistrbuteSupplierItemDO> distrbuteSupplierItemDOList;
	private Integer soldItemCount;
	private Integer applyStatusCount;
	private List<DistribureChannelDO> distribureChannelDOList;
	private Long itemId;

	public DistributorItemQuery(Long distributorId) {
	    super(10);
	    this.distributorId = distributorId;
	}

	public DistributorItemQuery(Long distributorId, Long itemId) {
	    super();
	    this.distributorId = distributorId;
	    this.itemId = itemId;
	}

	public DistributorItemQuery(List<Long> itemIdList) {
	    super(10);
	    this.itemIdList = itemIdList;
	}

	public List<Long> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}

	public Long getDistributorId() {
	    return distributorId;
	}

	public void setDistributorId(Long distributorId) {
	    this.distributorId = distributorId;
	}

	public Short getStatus() {
	    return status;
	}

	public void setStatus(Short status) {
	    this.status = status;
	}

	public List<DistrbuteSupplierItemDO> getDistrbuteSupplierItemDOList() {
	    return distrbuteSupplierItemDOList;
	}

	public void setDistrbuteSupplierItemDOList(
		List<DistrbuteSupplierItemDO> distrbuteSupplierItemDOList) {
	    this.distrbuteSupplierItemDOList = distrbuteSupplierItemDOList;
	}

	public Integer getSoldItemCount() {
	    return soldItemCount;
	}

	public void setSoldItemCount(Integer soldItemCount) {
	    this.soldItemCount = soldItemCount;
	}

	public Integer getApplyStatusCount() {
	    return applyStatusCount;
	}

	public void setApplyStatusCount(Integer applyStatusCount) {
	    this.applyStatusCount = applyStatusCount;
	}

	public List<DistribureChannelDO> getDistribureChannelDOList() {
	    return distribureChannelDOList;
	}

	public void setDistribureChannelDOList(
		List<DistribureChannelDO> distribureChannelDOList) {
	    this.distribureChannelDOList = distribureChannelDOList;
	}

	public Long getItemId() {
	    return itemId;
	}

	public void setItemId(Long itemId) {
	    this.itemId = itemId;
	}
	
	
}
