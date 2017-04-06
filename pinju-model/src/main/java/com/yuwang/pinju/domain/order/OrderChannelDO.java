package com.yuwang.pinju.domain.order;

/**
 * Created on 2011-7-30
 * 
 * @see <p>
 *      Discription: 分销订单
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderChannelDO extends OrderItemDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 592144369299700707L;

	private Long id;
	
	/**
	 * 分销商昵称
	 */
	private String channelNickName;

	/**
	 * 返点比率
	 */
	private Long reward;

	/**
	 * 返点金额
	 */
	private Long reAmount;
	
	/**
	 * 分销商会员编号
	 */
	private Long channelIds;
	
	/**
	 * 供应商编号
	 */
	private Long supplierId;
	
	/**
	 * 供应商店铺编号
	 */
	private Integer shopId;
	

	public String getChannelNickName() {
		return channelNickName;
	}

	public void setChannelNickName(String channelNickName) {
		this.channelNickName = channelNickName;
	}


	public Long getReward() {
		return reward;
	}

	public void setReward(Long reward) {
		this.reward = reward;
	}

	public Long getReAmount() {
		return reAmount;
	}

	public void setReAmount(Long reAmount) {
		this.reAmount = reAmount;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(Long channelIds) {
		this.channelIds = channelIds;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	@Override
	public String toString() {
		return "OrderChannelDO [channelIds=" + channelIds
				+ ", channelNickName=" + channelNickName + ", id=" + id
				+ ", reAmount=" + reAmount + ", reward=" + reward
				+ ", supplierId=" + supplierId + "]";
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

}
